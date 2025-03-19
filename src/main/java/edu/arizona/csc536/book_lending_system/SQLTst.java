package edu.arizona.csc536.book_lending_system;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLTst {

    private static final SQLInterface.bookLendField[] blfs = {SQLInterface.bookLendField.lendID, SQLInterface.bookLendField.title, SQLInterface.bookLendField.author, SQLInterface.bookLendField.userID, SQLInterface.bookLendField.outDate, SQLInterface.bookLendField.dueDate, SQLInterface.bookLendField.inDate};

    private static final SQLInterface.SearchCondition[] scs = {SQLInterface.SearchCondition.strLike, SQLInterface.SearchCondition.strExactly, SQLInterface.SearchCondition.eq, SQLInterface.SearchCondition.gt, SQLInterface.SearchCondition.lt};

    private static final SQLInterface.bookField[] bookFields = {SQLInterface.bookField.title, SQLInterface.bookField.author, SQLInterface.bookField.description, SQLInterface.bookField.count, SQLInterface.bookField.checkedOut};

    private static final SQLInterface.borrowerField[] borrowerFields = {SQLInterface.borrowerField.userID, SQLInterface.borrowerField.firstName, SQLInterface.borrowerField.lastName};
    public static void main(String[] args)
    {
        try {
            Connection conn = SQLInterface.getTestConnection();
            SQLInterface.ClearTables(conn);
            TestBook(conn);
            TestBorrower(conn);
            TestBookLend(conn);
            TestUpdate(conn);
            TestDelete(conn);
        }catch (SQLException e)
        {
            System.err.println("Encountered SQL err");
            System.err.println(e.getMessage());
        }
    }

    private static void TestDelete(Connection conn) throws SQLException
    {
        SQLInterface.insertBook(conn, bookFields, new String[]{"TB3", "TBA1", "description", "1", "0"});
        int id = SQLInterface.getNewUserID(conn);
        SQLInterface.insertBorrower(conn, borrowerFields, new String[]{String.valueOf(id), "John", "Smith"});
        id = SQLInterface.getNewLendID(conn);
        SQLInterface.insertBookLend(conn, blfs, new String[]{String.valueOf(id), "TB3", "TBA1", "6", "2008-08-11", "2008-08-11", "2008-08-11"});
        ResultSet res = SQLInterface.searchBookLend(conn, 5);
        assert res != null;
        PrintResultSet(res, "Test Delete 1: 5");
        SQLInterface.deleteBookLend(conn, 5);
        res = SQLInterface.searchBookLend(conn, 5);
        assert res != null;
        PrintResultSet(res, "Test Delete 1: None");
        res = SQLInterface.searchBook(conn, "TB3", scs[1]);
        assert res != null;
        PrintResultSet(res, "Test Delete 2: TB3");
        SQLInterface.deleteBook(conn, "TB3", "TBA1");
        res = SQLInterface.searchBook(conn, "TB3", scs[1]);
        assert res != null;
        PrintResultSet(res, "Test Delete 2: None");
        res = SQLInterface.searchBorrower(conn, 6);
        assert res != null;
        PrintResultSet(res, "Test Delete 3: 6");
        SQLInterface.deleteBorrower(conn, 6);
        res = SQLInterface.searchBorrower(conn, 6);
        assert res != null;
        PrintResultSet(res, "Test Delete 3: None");
    }

    private static void TestUpdate(Connection conn) throws SQLException
    {
        ResultSet res = SQLInterface.searchBookLend(conn, 1);
        assert res != null;
        PrintResultSet(res, "Test Update 1.1: 1 8/8/11 8/8/11 8/8/11", new int[]{1, 5, 6, 7});
        SQLInterface.updateBookLend(conn, 1, new SQLInterface.bookLendField[]{blfs[5], blfs[6]}, new String[]{"2008-8-21", "2008-8-20"});
        res = SQLInterface.searchBookLend(conn, 1);
        assert res != null;
        PrintResultSet(res, "Test Update 1.2: 1 8/8/11 8/8/21 8/8/20", new int[]{1, 5, 6, 7});
        res = SQLInterface.searchBook(conn, "TB1", scs[1]);
        assert res != null;
        PrintResultSet(res, "Test Update 2.1: TB1 1", new int[]{1,4});
        SQLInterface.updateBook(conn, "TB1", "TBA1", new SQLInterface.bookField[]{bookFields[3]}, new String[]{"10"});
        res = SQLInterface.searchBook(conn, "TB1", scs[1]);
        assert res != null;
        PrintResultSet(res, "Test Update 2.2: TB1 10", new int[]{1, 4});
        res = SQLInterface.searchBorrower(conn, "Charles", "Scott", new SQLInterface.SearchCondition[] {scs[1], scs[1]}, true);
        assert res != null;
        PrintResultSet(res, "Test Update 3.1: Charles Scott", new int[]{2,3});
        SQLInterface.updateBorrower(conn, 3, new SQLInterface.borrowerField[]{borrowerFields[2]}, new String[]{"Ryst"});
        res = SQLInterface.searchBorrower(conn, "Charles", "Ryst", new SQLInterface.SearchCondition[] {scs[1], scs[1]}, true);
        assert res != null;
        PrintResultSet(res, "Test Update 3.2: Charles Ryst", new int[]{2, 3});
        res = SQLInterface.searchBookLend(conn, 2);
        assert res != null;
        PrintResultSet(res, "Test Update 4.1: 2 None None None", new int[]{1, 5, 6, 7});
        SQLInterface.updateBookLend(conn, 2, new SQLInterface.bookLendField[]{blfs[4], blfs[5]}, new String[]{"2025-3-10", "2025-3-21"});
        res = SQLInterface.searchBookLend(conn, 2);
        assert res != null;
        PrintResultSet(res, "Test Update 4.2: 2 25/3/10 25/3/21 None", new int[]{1, 5, 6, 7});
        res = SQLInterface.searchBookLend(conn, 2);
        assert res != null;
        PrintResultSet(res, "Test Update 5.1: 2 None None None", new int[]{1, 5, 6, 7});
        SQLInterface.turnInBook(conn, 2, "2025-3-23");
        res = SQLInterface.searchBookLend(conn, 2);
        assert res != null;
        PrintResultSet(res, "Test Update 5.2: 2 25/3/23 ", new int[]{1, 7});
        res = SQLInterface.searchBook(conn, "Puff", scs[0]);
        assert res != null;
        PrintResultSet(res, "Test Update 5.3: 1", new int[]{5});

    }

    private static void TestBookLend(Connection conn) throws SQLException
    {
        int id = SQLInterface.getNewLendID(conn);
        SQLInterface.insertBookLend(conn, blfs, new String[]{String.valueOf(id), "TB1", "TBA1", "2", "2008-08-11", "2008-08-11", "2008-08-11"});
        ResultSet res = SQLInterface.searchBook(conn, new SQLInterface.bookField[]{bookFields[0]}, "TB1", new SQLInterface.SearchCondition[]{scs[1]}, false);
        assert res != null;
        PrintResultSet(res, "Test BookLend Insert 1: 1", new int[]{5});

        id = SQLInterface.getNewLendID(conn);
        SQLInterface.insertBookLend(conn, new SQLInterface.bookLendField[]{blfs[0], blfs[1], blfs[2], blfs[3]}, new String[]{String.valueOf(id), "Puff the magic dragon", "Charles Scott", "5"});
        res = SQLInterface.searchBook(conn, new SQLInterface.bookField[]{bookFields[0]}, "Puff", new SQLInterface.SearchCondition[]{scs[0]}, false);
        assert res != null;
        PrintResultSet(res, "Test BookLend Insert 2: 1", new int[]{5});

        id = SQLInterface.getNewLendID(conn);
        SQLInterface.insertBookLend(conn, new SQLInterface.bookLendField[]{blfs[0], blfs[1], blfs[2], blfs[3]}, new String[]{String.valueOf(id), "TB2", "TBA2", "3"});
        res = SQLInterface.searchBook(conn, new SQLInterface.bookField[]{bookFields[0]}, "TB2", new SQLInterface.SearchCondition[]{scs[0]}, false);
        assert res != null;
        PrintResultSet(res, "Test BookLend Insert 3: 1", new int[]{5});

        id = SQLInterface.getNewLendID(conn);
        SQLInterface.insertBookLend(conn, new SQLInterface.bookLendField[]{blfs[0], blfs[1], blfs[2], blfs[3]}, new String[]{String.valueOf(id), "Puff the magic dragon", "Charles Scott", "3"});
        res = SQLInterface.searchBook(conn, new SQLInterface.bookField[]{bookFields[0]}, "Puff", new SQLInterface.SearchCondition[]{scs[0]}, false);
        assert res != null;
        PrintResultSet(res, "Test BookLend Insert 4: 2", new int[]{5});

        res = SQLInterface.searchBookLend(conn, new SQLInterface.bookLendField[]{blfs[1]}, "TB", new SQLInterface.SearchCondition[]{scs[0]}, false);
        assert res != null;
        PrintResultSet(res, "Test BookLend Search/Insert 1.1: 1, 3");
        res = SQLInterface.searchBookLend(conn, 2);
        assert res != null;
        PrintResultSet(res, "Test BookLend Search/Insert 2: 2");
        res = SQLInterface.searchBookLendForUser(conn, 5);
        assert res != null;
        PrintResultSet(res, "Test BookLend Search/Insert 3: 2");
        res = SQLInterface.searchBookLend(conn, new SQLInterface.bookLendField[]{blfs[1], blfs[3]}, new String[]{"uff", "3"}, new SQLInterface.SearchCondition[]{scs[0], scs[2]}, false);
        assert res != null;
        PrintResultSet(res, "Test BookLend Search/Insert 4: 2, 3, 4");
        res = SQLInterface.searchBookLend(conn, "TB1", "Charles", new SQLInterface.SearchCondition[]{scs[0], scs[0]}, true);
        assert res != null;
        PrintResultSet(res, "Test BookLend Search/Insert 5: None");
        res = SQLInterface.searchBookLend(conn, "TB1", "TBA1", new SQLInterface.SearchCondition[]{scs[0], scs[0]}, true);
        assert res != null;
        PrintResultSet(res, "Test BookLend Search/Insert 6: 1");
    }

    private static void TestBorrower(Connection conn) throws SQLException
    {
        int id = SQLInterface.getNewUserID(conn);
        SQLInterface.insertBorrower(conn, borrowerFields, new String[]{String.valueOf(id), "John", "Smith"});
        id = SQLInterface.getNewUserID(conn);
        SQLInterface.insertBorrower(conn, borrowerFields, new String[]{String.valueOf(id), "Cookie", "Monster"});
        id = SQLInterface.getNewUserID(conn);
        SQLInterface.insertBorrower(conn, borrowerFields, new String[]{String.valueOf(id), "Charles", "Scott"});
        id = SQLInterface.getNewUserID(conn);
        SQLInterface.insertBorrower(conn, borrowerFields, new String[]{String.valueOf(id), "Man", "Dude"});
        id = SQLInterface.getNewUserID(conn);
        SQLInterface.insertBorrower(conn, borrowerFields, new String[]{String.valueOf(id), "Test", "Man"});
        ResultSet res = SQLInterface.searchBorrower(conn, new SQLInterface.borrowerField[]{borrowerFields[1]}, "C", new SQLInterface.SearchCondition[]{scs[0]}, false);
        assert res != null;
        PrintResultSet(res, "Test Borrower Search/Insert 1: 2, 3");
        res = SQLInterface.searchBorrower(conn, new SQLInterface.borrowerField[]{borrowerFields[1], borrowerFields[2]}, "Man", new SQLInterface.SearchCondition[]{scs[1], scs[1]}, false);
        assert res != null;
        PrintResultSet(res, "Test Borrower Search/Insert 2: 4, 5");
        res = SQLInterface.searchBorrower(conn, new SQLInterface.borrowerField[]{borrowerFields[1], borrowerFields[2]}, new String[]{"John", "Dud"}, new SQLInterface.SearchCondition[]{scs[0], scs[0]}, false);
        assert res != null;
        PrintResultSet(res, "Test Borrower Search/Insert 3: 1, 4");
        res = SQLInterface.searchBorrower(conn, "john", "smith", new SQLInterface.SearchCondition[]{scs[1], scs[1]}, true);
        assert res != null;
        PrintResultSet(res, "Test Borrower Search/Insert 4: 1");
        res = SQLInterface.searchBorrower(conn, "john", "smit", new SQLInterface.SearchCondition[]{scs[1], scs[1]}, true);
        assert res != null;
        PrintResultSet(res, "Test Borrower Search/Insert 5: None");
        res = SQLInterface.searchBorrower(conn, "john", "dude", new SQLInterface.SearchCondition[]{scs[1], scs[1]}, false);
        assert res != null;
        PrintResultSet(res, "Test Borrower Search/Insert 4: 1, 4");
        res.close();
    }

    private static void TestBook(Connection conn) throws SQLException {
        SQLInterface.insertBook(conn, bookFields, new String[]{"TB1", "TBA1", "description", "1", "0"});
        SQLInterface.insertBook(conn, bookFields, new String[]{"TB2", "TBA2", "description", "3", "0"});
        SQLInterface.insertBook(conn, bookFields, new String[]{"Puff the magic dragon", "Charles Scott", "description", "5", "0"});
        SQLInterface.insertBook(conn, bookFields, new String[]{"How to sing", "Donald Trump", "description", "6", "0"});
        SQLInterface.insertBook(conn, bookFields, new String[]{"I love cookies", "Cookie Monster", "Credit to Charles", "1", "0"});
        ResultSet res = SQLInterface.searchBook(conn, new SQLInterface.bookField[]{bookFields[0]}, "uff", new SQLInterface.SearchCondition[]{scs[0]}, false);
        assert res != null;
        PrintResultSet(res, "Test Book Search/Insert 1: Puff");
        res = SQLInterface.searchBook(conn, new SQLInterface.bookField[]{bookFields[0], bookFields[1]}, "A1", new SQLInterface.SearchCondition[]{scs[0], scs[1]}, true);
        assert res != null;
        PrintResultSet(res, "Test Book Search/Insert 2: None");
        res = SQLInterface.searchBook(conn, new SQLInterface.bookField[]{bookFields[0], bookFields[1]}, new String[]{"Puff the magic dragon", "cHaRles Scott"}, new SQLInterface.SearchCondition[]{scs[1], scs[1]}, true);
        assert res != null;
        PrintResultSet(res, "Test Book Search/Insert 3: Puff");
        res = SQLInterface.searchBook(conn, new SQLInterface.bookField[]{bookFields[0], bookFields[1]}, new String[]{"Puff the magic dragon", "TBA1"}, new SQLInterface.SearchCondition[]{scs[1], scs[1]}, true);
        assert res != null;
        PrintResultSet(res, "Test Book Search/Insert 4: None");
        res = SQLInterface.searchBook(conn, new SQLInterface.bookField[]{bookFields[0], bookFields[1]}, new String[]{"Puff the magic dragon", "Charles Shot"}, new SQLInterface.SearchCondition[]{scs[1], scs[1]}, false);
        assert res != null;
        PrintResultSet(res, "Test Book Search/Insert 5: Puff");
        res = SQLInterface.searchBook(conn, new SQLInterface.bookField[]{bookFields[0], bookFields[1]}, new String[]{"Puff the magic dragon", "Cookie Monster"}, new SQLInterface.SearchCondition[]{scs[1], scs[1]}, false);
        assert res != null;
        PrintResultSet(res, "Test Book Search/Insert 6: Puff, Cookies");
        res = SQLInterface.searchBook(conn, new SQLInterface.bookField[]{bookFields[3]}, new String[]{"2"}, new SQLInterface.SearchCondition[]{scs[3]}, false);
        assert res != null;
        PrintResultSet(res, "Test Book Search/Insert 7: TB2, Puff, How to sing");
        res = SQLInterface.searchBook(conn, new SQLInterface.bookField[]{bookFields[3]}, new String[]{"2"}, new SQLInterface.SearchCondition[]{scs[4]}, false);
        assert res != null;
        PrintResultSet(res, "Test Book Search/Insert 8: TB1, Cookies");
        res = SQLInterface.searchBook(conn, new SQLInterface.bookField[]{bookFields[3]}, new String[]{"1"}, new SQLInterface.SearchCondition[]{scs[2]}, false);
        assert res != null;
        PrintResultSet(res, "Test Book Search/Insert 9: TB1, Cookies");
        res = SQLInterface.searchBook(conn, "Charles", scs[0]);
        assert res != null;
        PrintResultSet(res, "Test Book Search/Insert 10: Cookies, Puff ");
        res = SQLInterface.searchBook(conn, "How to sing", "Tb", new SQLInterface.SearchCondition[]{scs[1], scs[0]}, false);
        assert res != null;
        PrintResultSet(res, "Test Book Search/Insert 11: How to sing, TB1, TB2");
        res.close();
    }

    private static void PrintResultSet(ResultSet res, String msg) throws SQLException {
        System.out.println(msg);
        while(res.next())
        {
            System.out.println(res.getString(1));
        }
    }

    private static void PrintResultSet(ResultSet res, String msg, int[] cols) throws SQLException {
        System.out.println(msg);
        while(res.next())
        {
            for(int col : cols)
            {
                System.out.println(res.getString(col));
            }
        }
    }
}
