package edu.arizona.csc536.book_lending_system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.*;

@Slf4j
public class SQLInterface {

    public enum bookField{
        title,
        author,
        description,
        count,
        checkedOut
    }

    public static String bookFieldDtype(bookField bf)
    {
        return switch (bf) {
            case title, author -> "VARCHAR NOTNULL";
            case description -> "TEXT";
            case count, checkedOut -> "INT NOTNULL";
        };
    }

    public enum borrowerField
    {
        firstName,
        lastName,
        userID
    }

    public static String borrowerFieldDtype(borrowerField bf)
    {
        return switch (bf)
        {
            case firstName, lastName -> "VARCHAR NOTNULL";
            case userID -> "INT NOTNULL";
        };
    }

    public enum bookLendField
    {
        lendID,
        title,
        author,
        userID,
        outDate,
        dueDate,
        inDate
    }

    public static String blfDtype(bookLendField blf)
    {
        return switch (blf)
        {
            case lendID, userID -> "INT NOTNULL";
            case title, author -> "VARCHAR NOTNULL";
            case outDate, dueDate, inDate -> "DATE";
        };
    }

    public static String[] lstToStr(Object[] enLst)
    {
        String[] enToStr = new String[enLst.length];
        for(int i = 0; i< enLst.length; i++)
        {
            enToStr[i] = enLst[i].toString();
        }
        return enToStr;
    }

    public static Connection getTestConnection()
    {
        Connection conn = null;
        try{
            conn = getConn("root", "1108");
        }
        catch (Exception e)
        {
            System.err.println("Couldn't get connection");
            System.err.println(e.getMessage());
        }
        return conn;
    }

    public static Connection getConn(String username, String password) throws Exception
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/book-lending-system", username, password);
        return conn;
    }

    public static void ClearTables(Connection conn) throws SQLException {
        Statement statement = conn.createStatement();
        statement.execute("DROP TABLE bookLend");
        statement.execute("DROP TABLE book");
        statement.execute("DROP TABLE borrower");
        statement.execute("CREATE TABLE book (title varchar(255) NOT NULL, author varchar(255) NOT NULL, description text, count int NOT NULL, checkedOut int NOT NULL, PRIMARY KEY(title, author))");
        statement.execute("CREATE TABLE borrower (userID int NOT NULL, firstName varchar(255) NOT NULL, lastName varchar(255) NOT NULL, PRIMARY KEY(userID))");
        statement.execute("CREATE TABLE bookLend (lendID int NOT NULL, title varchar(255) NOT NULL, author varchar(255) NOT NULL, userID int NOT NULL, outDate date, dueDate date, inDate date, PRIMARY KEY(lendID), FOREIGN KEY (title, author) REFERENCES book(title,author), FOREIGN KEY (userID) REFERENCES borrower(userID))");
        statement.close();
    }

    public static ResultSet searchBook(Connection conn, bookField[] field, String exper, boolean matchExactly, boolean matchAll) throws SQLException {
        String[] enToStr = lstToStr(field);
        for(int i = 0; i<field.length; i++) {if(!bookFieldDtype(field[i]).split(" ")[0].equals("VARCHAR")) return null;}
        return search(conn, "book", enToStr, exper, matchExactly, matchAll);
    }

    public static ResultSet searchBook(Connection conn, bookField[] field, String[] exper, boolean matchExactly, boolean matchAll) throws SQLException {
        String[] enToStr = lstToStr(field);
        for(int i = 0; i<field.length; i++) {if(!bookFieldDtype(field[i]).split(" ")[0].equals("VARCHAR")) return null;}
        return search(conn, "book", enToStr, exper, matchExactly, matchAll);
    }

    public static ResultSet searchBorrower(Connection conn, borrowerField[] field, String exper, boolean matchExactly, boolean matchAll) throws SQLException {
        String[] enToStr = lstToStr(field);
        for(int i = 0; i<field.length; i++) {if(!borrowerFieldDtype(field[i]).split(" ")[0].equals("VARCHAR")) return null;}
        return search(conn, "borrower", enToStr, exper, matchExactly, matchAll);
    }

    public static ResultSet searchBorrower(Connection conn, borrowerField[] field, String[] exper, boolean matchExactly, boolean matchAll) throws SQLException {
        String[] enToStr = lstToStr(field);
        for(int i = 0; i<field.length; i++) {if(!borrowerFieldDtype(field[i]).split(" ")[0].equals("VARCHAR")) return null;}
        return search(conn, "borrower", enToStr, exper, matchExactly, matchAll);
    }

    public static ResultSet searchBookLend(Connection conn, bookLendField[] field, String exper, boolean matchExactly, boolean matchAll) throws SQLException {
        String[] enToStr = lstToStr(field);
        for(int i = 0; i<field.length; i++) {if(!blfDtype(field[i]).split(" ")[0].equals("VARCHAR")) return null;}
        return search(conn, "bookLend", enToStr, exper, matchExactly, matchAll);
    }

    public static ResultSet searchBookLend(Connection conn, bookLendField[] field, String[] exper, boolean matchExactly, boolean matchAll) throws SQLException {
        String[] enToStr = lstToStr(field);
        for(int i = 0; i<field.length; i++) {if(!blfDtype(field[i]).split(" ")[0].equals("VARCHAR")) return null;}
        return search(conn, "bookLend", enToStr, exper, matchExactly, matchAll);
    }

    private static ResultSet search(Connection conn, String tableName, String[] field, String exper, boolean exactMatch, boolean matchAll) throws SQLException {
        Statement statement = conn.createStatement();
        String query = "SELECT * FROM "+tableName+" WHERE ";
        exper = getExperString(exactMatch, exper);
        for(int i = 0; i< field.length; i++)
        {
            query += "LOWER("+ field[i] + ") LIKE LOWER("+exper+")";
            if(i < field.length-1)
            {
                if(!matchAll)
                    query += " OR ";
                else
                    query += " AND ";
            }
        }
        ResultSet res = statement.executeQuery(query);
        return res;
    }

    private static ResultSet search(Connection conn, String tableName, String[] field, String[] exper, boolean exactMatch, boolean matchAll) throws SQLException {
        Statement statement = conn.createStatement();
        if(field.length != exper.length) return null;
        String query = "SELECT * FROM "+tableName+" WHERE ";
        for(int i = 0; i< field.length; i++)
        {
            query += "LOWER("+field[i] + ") LIKE LOWER("+getExperString(exactMatch, exper[i])+")";
            if(i < field.length-1)
            {
                if(!matchAll)
                    query += " OR ";
                else
                    query += " AND ";
            }
        }
        ResultSet res = statement.executeQuery(query);
        return res;
    }

    private static String getExperString(boolean exactMatch, String exper)
    {
        if(exactMatch)
            return "'"+exper+"'";
        else
            return "'%"+exper+"%'";
    }

    public static boolean insertBook(Connection conn, bookField[] fields, String[] vals) throws SQLException {
        //title author description count checkedout
        boolean[] hasVal = new boolean[5];
        if(fields.length != vals.length) return false;
        String[] fieldsStrs = new String[fields.length];
        String[] valTypesStrs = new String[fields.length];
        for(int i = 0; i<fields.length; i++)
        {
            switch (fields[i])
            {
                case title:
                    if(!checkValid(hasVal, 0)) return false;
                    break;
                case author:
                    if(!checkValid(hasVal, 1)) return false;
                    break;
                case description:
                    if(!checkValid(hasVal, 2)) return false;
                    break;
                case count:
                    if(!checkValid(hasVal, 3)) return false;
                    break;
                case checkedOut:
                    if(!checkValid(hasVal, 4)) return false;
                    break;
            }
            fieldsStrs[i] = fields[i].toString();
            valTypesStrs[i] = bookFieldDtype(fields[i]);
        }
        if(!hasVal[0] || !hasVal[1] || !hasVal[3] || !hasVal[4]) return false;
        return insert(conn, "book", fieldsStrs, vals, valTypesStrs);
    }

    public static boolean insertBorrower(Connection conn, borrowerField[] fields, String[] vals) throws SQLException {
        //userid, firstname, lastname
        boolean[] hasVal = new boolean[3];
        if(fields.length != vals.length) return false;
        String[] fieldsStrs = new String[fields.length];
        String[] valTypesStrs = new String[fields.length];
        for(int i = 0; i<fields.length; i++)
        {
            switch (fields[i])
            {
                case userID:
                    if(!checkValid(hasVal, 0)) return false;
                    break;
                case firstName:
                    if(!checkValid(hasVal, 1)) return false;
                    break;
                case lastName:
                    if(!checkValid(hasVal, 2)) return false;
                    break;
            }
            fieldsStrs[i] = fields[i].toString();
            valTypesStrs[i] = borrowerFieldDtype(fields[i]);
        }
        if(!hasVal[0] || !hasVal[1] || !hasVal[2]) return false;
        return insert(conn, "borrower", fieldsStrs, vals, valTypesStrs);
    }

    //DOES NOT UPDATE CHECKED OUT IN BOOK TABLE
    public static boolean insertBookLend(Connection conn, bookLendField[] fields, String[] vals) throws SQLException {
        //lendid, title, author, userid, outdate, duedate, indate
        boolean[] hasVal = new boolean[7];
        if(fields.length != vals.length) return false;
        String[] fieldsStrs = new String[fields.length];
        String[] valTypesStrs = new String[fields.length];
        for(int i = 0; i<fields.length; i++)
        {
            switch (fields[i])
            {
                case lendID:
                    if(!checkValid(hasVal, 0)) return false;
                    break;
                case title:
                    if(!checkValid(hasVal, 1)) return false;
                    break;
                case author:
                    if(!checkValid(hasVal, 2)) return false;
                    break;
                case userID:
                    if(!checkValid(hasVal, 3)) return false;
                    break;
                case outDate:
                    if(!checkValid(hasVal, 4)) return false;
                    break;
                case dueDate:
                    if(!checkValid(hasVal, 5)) return false;
                    break;
                case inDate:
                    if(!checkValid(hasVal, 6)) return false;
                    break;
            }
            fieldsStrs[i] = fields[i].toString();
            valTypesStrs[i] = blfDtype(fields[i]);
        }
        if(!hasVal[0] || !hasVal[1] || !hasVal[2] || !hasVal[3]) return false;
        return insert(conn, "bookLend", fieldsStrs, vals, valTypesStrs);
    }

    private static boolean checkValid(boolean[] check, int ind)
    {
        check[ind] = !check[ind];
        return check[ind];
    }

    private static boolean insert(Connection conn, String tableName, String[] fields, String[] values, String[] valTypes) throws SQLException {
        Statement statement = conn.createStatement();
        if(fields.length != values.length) return false;
        String fieldsSQL = "(";
        String valuesSQL = "(";
        for(int i = 0; i<fields.length; i++)
        {
            Object fieldVal = fieldToString(values[i], valTypes[i]);
            if(fieldVal == null) return false;
            fieldsSQL += fields[i];
            valuesSQL += fieldVal.toString();
            if(i < fields.length-1)
            {
                fieldsSQL += ", ";
                valuesSQL += ", ";
            }
        }
        fieldsSQL += ")";
        valuesSQL += ")";
        String query = "INSERT INTO "+tableName+" "+fieldsSQL+" VALUES "+valuesSQL;
        statement.execute(query);
        statement.close();
        return true;
    }

    private static Object fieldToString(String field, String valType)
    {
        valType = valType.split(" ")[0];
        String ret = "";
        switch (valType)
        {
            case "VARCHAR":
            case "TEXT":
                ret += "'"+field+"'";
                return ret;
            case "INT":
                try{
                    int val = Integer.parseInt(field);
                    ret += val;
                    return ret;
                }
                catch(NumberFormatException e)
                {
                    return null;
                }
            case "DATE":
                String[] dates = field.split("-");
                if(dates.length != 3) return null;
                try
                {
                    for (String date : dates) {
                        Integer.parseInt(date);
                    }
                    ret += "'"+field+"'";
                    return ret;
                }
                catch (NumberFormatException e)
                {
                    return null;
                }
        }
        return null;
    }
}
