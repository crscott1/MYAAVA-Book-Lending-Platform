package edu.arizona.csc536.book_lending_system;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLTst {
    public static void main(String[] args)
    {
        try {
            Connection conn = SQLInterface.getTestConnection();
            SQLInterface.ClearTables(conn);
            TestBook(conn);
            TestBorrower(conn);
            TestBookLend(conn);
        }catch (SQLException e)
        {
            System.err.println("Encountered SQL err");
            System.err.println(e.getMessage());
        }
    }

    private static void TestBookLend(Connection conn) throws SQLException
    {
        SQLInterface.bookLendField[] blfs = {SQLInterface.bookLendField.lendID, SQLInterface.bookLendField.title, SQLInterface.bookLendField.author, SQLInterface.bookLendField.userID, SQLInterface.bookLendField.outDate, SQLInterface.bookLendField.inDate, SQLInterface.bookLendField.dueDate};
        SQLInterface.insertBookLend(conn, blfs, new String[]{"1", "TB1", "TBA1", "2", "2008-08-11", "2008-08-11", "2008-08-11"});
        SQLInterface.insertBookLend(conn, new SQLInterface.bookLendField[]{blfs[0], blfs[1], blfs[2], blfs[3]}, new String[]{"2", "Puff the magic dragon", "Charles Scott", "5"});
        SQLInterface.insertBookLend(conn, new SQLInterface.bookLendField[]{blfs[0], blfs[1], blfs[2], blfs[3]}, new String[]{"3", "TB2", "TBA2", "3"});
        ResultSet res = SQLInterface.searchBookLend(conn, new SQLInterface.bookLendField[]{blfs[1]}, "TB", false, false);
        assert res != null;
        PrintResultSet(res);
    }

    private static void TestBorrower(Connection conn) throws SQLException
    {
        SQLInterface.borrowerField[] borrowerFields = {SQLInterface.borrowerField.userID, SQLInterface.borrowerField.firstName, SQLInterface.borrowerField.lastName};
        SQLInterface.insertBorrower(conn, borrowerFields, new String[]{"1", "John", "Smith"});
        SQLInterface.insertBorrower(conn, borrowerFields, new String[]{"2", "Cookie", "Monster"});
        SQLInterface.insertBorrower(conn, borrowerFields, new String[]{"3", "Charles", "Scott"});
        SQLInterface.insertBorrower(conn, borrowerFields, new String[]{"4", "Man", "Dude"});
        SQLInterface.insertBorrower(conn, borrowerFields, new String[]{"5", "Test", "Man"});
        ResultSet res = SQLInterface.searchBorrower(conn, new SQLInterface.borrowerField[]{borrowerFields[1]}, "C", false, false);
        assert res != null;
        PrintResultSet(res);
        res = SQLInterface.searchBorrower(conn, new SQLInterface.borrowerField[]{borrowerFields[1], borrowerFields[2]}, "Man", true, false);
        assert res != null;
        PrintResultSet(res);
        res = SQLInterface.searchBorrower(conn, new SQLInterface.borrowerField[]{borrowerFields[1], borrowerFields[2]}, new String[]{"John", "Dud"}, false, false);
        assert res != null;
        PrintResultSet(res);
        res.close();
    }

    private static void TestBook(Connection conn) throws SQLException {
        SQLInterface.bookField[] bookFields = {SQLInterface.bookField.title, SQLInterface.bookField.author, SQLInterface.bookField.description, SQLInterface.bookField.count, SQLInterface.bookField.checkedOut};
        SQLInterface.insertBook(conn, bookFields, new String[]{"TB1", "TBA1", "description", "1", "0"});
        SQLInterface.insertBook(conn, bookFields, new String[]{"TB2", "TBA2", "description", "3", "0"});
        SQLInterface.insertBook(conn, bookFields, new String[]{"Puff the magic dragon", "Charles Scott", "description", "5", "0"});
        SQLInterface.insertBook(conn, bookFields, new String[]{"How to sing", "Donald Trump", "description", "6", "0"});
        SQLInterface.insertBook(conn, bookFields, new String[]{"I love cookies", "Cookie Monster", "description", "1", "0"});
        ResultSet res = SQLInterface.searchBook(conn, new SQLInterface.bookField[]{bookFields[0]}, "uff", false, false);
        assert res != null;
        PrintResultSet(res);
        res = SQLInterface.searchBook(conn, new SQLInterface.bookField[]{bookFields[0], bookFields[1]}, "A1", false, false);
        assert res != null;
        PrintResultSet(res);
        res = SQLInterface.searchBook(conn, new SQLInterface.bookField[]{bookFields[0], bookFields[1]}, new String[]{"Puff the magic dragon", "Charles Scott"}, true, true);
        assert res != null;
        PrintResultSet(res);
        res = SQLInterface.searchBook(conn, new SQLInterface.bookField[]{bookFields[0], bookFields[1]}, new String[]{"Puff the magic dragon", "TBA1"}, true, true);
        assert res != null;
        PrintResultSet(res);
        res = SQLInterface.searchBook(conn, new SQLInterface.bookField[]{bookFields[0], bookFields[1]}, new String[]{"Puff the magic dragon", "Charles Shot"}, true, false);
        assert res != null;
        PrintResultSet(res);
        res = SQLInterface.searchBook(conn, new SQLInterface.bookField[]{bookFields[0], bookFields[1]}, new String[]{"Puff the magic dragon", "Cookie Monster"}, true, false);
        assert res != null;
        PrintResultSet(res);
        res.close();
    }

    private static void PrintResultSet(ResultSet res) throws SQLException {
        while(res.next())
        {
            System.out.println(res.getString(1));
        }
    }
}
