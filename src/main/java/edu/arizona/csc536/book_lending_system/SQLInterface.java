package edu.arizona.csc536.book_lending_system;

import java.sql.*;
import java.util.Arrays;

public class SQLInterface {

    /*********************************************
     * Enums
     *********************************************/

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

    public static String[] bookFieldDtype(bookField[] bfs)
    {
        String[] dts = new String[bfs.length];
        for(int i = 0; i<dts.length; i++)
        {
            dts[i] = bookFieldDtype(bfs[i]);
        }
        return dts;
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

    public static String[] borrowerFieldDtype(borrowerField[] bfields)
    {
        String[] dts = new String[bfields.length];
        for(int i = 0; i<dts.length; i++)
        {
            dts[i] = borrowerFieldDtype(bfields[i]);
        }
        return dts;
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

    public static String[] blfDtype(bookLendField[] blfs)
    {
        String[] dts = new String[blfs.length];
        for(int i = 0; i<dts.length; i++)
        {
            dts[i] = blfDtype(blfs[i]);
        }
        return dts;
    }

    public enum SearchCondition
    {
        strLike,
        strExactly,
        eq,
        gt,
        lt
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

    /*********************************************
     * Connection
     *********************************************/

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
        conn.setAutoCommit(false);
        return conn;
    }

    /*********************************************
     * ID Creation Utility
     *********************************************/

    public static int getNewUserID(Connection conn)
    {
        try{
            while(true)
            {
                Statement statement = conn.createStatement();
                ResultSet res = statement.executeQuery("SELECT * FROM idtracker");
                res.next();
                int next = res.getInt("userID");
                res = statement.executeQuery("SELECT * FROM borrower WHERE userID="+next);
                String updateQuery = "UPDATE idtracker SET userID="+(next+1);
                if(!res.next())
                {
                    statement.execute(updateQuery);
                    statement.close();
                    conn.commit();
                    return next;
                }
                statement.execute(updateQuery);
                statement.close();
                conn.commit();
            }
        }
        catch (SQLException e)
        {
            System.err.println("SQL ERR getting userID: "+e.getMessage());
        }
        return -1;
    }

    public static int getNewLendID(Connection conn)
    {
        try{
            while(true)
            {
                Statement statement = conn.createStatement();
                ResultSet res = statement.executeQuery("SELECT * FROM idtracker");
                res.next();
                int next = res.getInt("lendID");
                res = statement.executeQuery("SELECT * FROM booklend WHERE lendID="+next);
                String updateQuery = "UPDATE idtracker SET lendID="+(next+1);
                if(!res.next())
                {
                    statement.execute(updateQuery);
                    statement.close();
                    conn.commit();
                    return next;
                }
                statement.execute(updateQuery);
                statement.close();
                conn.commit();
            }
        }
        catch (SQLException e)
        {
            System.err.println("SQL ERR getting lendID: "+e.getMessage());
        }
        return -1;
    }

    /*********************************************
     * Clear Tables
     *********************************************/

    public static void ClearTables(Connection conn) throws SQLException {
        Statement statement = conn.createStatement();
        statement.execute("DROP TABLE bookLend");
        statement.execute("DROP TABLE book");
        statement.execute("DROP TABLE borrower");
        statement.execute("DROP TABLE idtracker");
        statement.execute("CREATE TABLE book (title varchar(255) NOT NULL, author varchar(255) NOT NULL, description text, count int NOT NULL, checkedOut int NOT NULL, PRIMARY KEY(title, author))");
        statement.execute("CREATE TABLE borrower (userID int NOT NULL, firstName varchar(255) NOT NULL, lastName varchar(255) NOT NULL, PRIMARY KEY(userID))");
        statement.execute("CREATE TABLE bookLend (lendID int NOT NULL, title varchar(255) NOT NULL, author varchar(255) NOT NULL, userID int NOT NULL, outDate date, dueDate date, inDate date, PRIMARY KEY(lendID), FOREIGN KEY (title, author) REFERENCES book(title,author), FOREIGN KEY (userID) REFERENCES borrower(userID))");
        statement.execute("CREATE TABLE idtracker (userID int NOT NULL, lendID int NOT NULL)");
        statement.execute("INSERT INTO idtracker (userID, lendID) VALUES (1,1)");
        statement.close();
    }

    /*********************************************
     * Public Search Methods
     *********************************************/
    public static ResultSet searchBook(Connection conn, String searchAnywhere, SearchCondition cond) {
        try {
            return search(conn, "book", new String[]{"title", "author", "description"}, new String[]{"VARCHAR", "VARCHAR", "VARCHAR"}, searchAnywhere, new SearchCondition[]{cond, cond, cond}, false);
        }
        catch (SQLException e) {
            System.err.println("SQL ERROR OCCURRED "+e.getMessage());
        }
        return null;
    }

    public static ResultSet searchBook(Connection conn, String searchTitle, String searchAuthor, SearchCondition[] conds, boolean matchAll) {
        try{
            return search(conn, "book", new String[]{"title", "author"}, new String[] {"VARCHAR", "VARCHAR"}, new String[] {searchTitle, searchAuthor}, conds, matchAll);
        }
        catch (SQLException e) {
            System.err.println("SQL ERROR OCCURRED "+e.getMessage());
        }
        return null;
    }

    public static ResultSet searchBorrower(Connection conn, int userID) {
        try{
            return search(conn, "borrower", new String[]{"userID"}, new String[] {"INT"}, new String[]{String.valueOf(userID)}, new SearchCondition[]{SearchCondition.eq}, true);
        }
        catch (SQLException e) {
            System.err.println("SQL ERROR OCCURRED "+e.getMessage());
        }
        return null;
    }

    public static ResultSet searchBorrower(Connection conn, String firstName, String lastName, SearchCondition[] conds, boolean matchAll) {
        try{
            return search(conn, "borrower", new String[]{"firstName", "lastName"}, new String[] {"VARCHAR", "VARCHAR"}, new String[]{firstName, lastName}, conds, matchAll);
        }
        catch (SQLException e) {
            System.err.println("SQL ERROR OCCURRED "+e.getMessage());
        }
        return null;
    }

    public static ResultSet searchBookLend(Connection conn, int lendID)  {
        try{
            return search(conn, "bookLend", new String[]{"lendID"}, new String[]{"INT"}, String.valueOf(lendID), new SearchCondition[]{SearchCondition.eq}, true);
        }
        catch (SQLException e) {
            System.err.println("SQL ERROR OCCURRED "+e.getMessage());
        }
        return null;
    }

    public static ResultSet searchBookLendForUser(Connection conn, int userID) {
        try{
            return search(conn, "bookLend", new String[]{"userID"}, new String[]{"INT"}, String.valueOf(userID), new SearchCondition[]{SearchCondition.eq}, true);
        }
        catch (SQLException e) {
            System.err.println("SQL ERROR OCCURRED "+e.getMessage());
        }
        return null;
    }

    public static ResultSet searchBookLend(Connection conn, String title, String author, SearchCondition[] conds, boolean matchAll) {
        try{
            return search(conn, "bookLend", new String[]{"title", "author"}, new String[] {"VARCHAR","VARCHAR"}, new String[] {title, author}, conds, matchAll);
        }
        catch (SQLException e) {
            System.err.println("SQL ERROR OCCURRED "+e.getMessage());
        }
        return null;
    }

    public static ResultSet searchBook(Connection conn, bookField[] fields, String exper, SearchCondition[] conds, boolean matchAll) {
        try{
            return search(conn, "book", lstToStr(fields), bookFieldDtype(fields), exper, conds, matchAll);
        }
        catch (SQLException e) {
            System.err.println("SQL ERROR OCCURRED "+e.getMessage());
        }
        return null;
    }

    public static ResultSet searchBook(Connection conn, bookField[] fields, String[] expers, SearchCondition[] conds, boolean matchAll) {
        try{
            return search(conn, "book", lstToStr(fields), bookFieldDtype(fields), expers, conds, matchAll);
        }
        catch (SQLException e) {
            System.err.println("SQL ERROR OCCURRED "+e.getMessage());
        }
        return null;
    }

    public static ResultSet searchBorrower(Connection conn, borrowerField[] fields, String exper, SearchCondition[] conds, boolean matchAll) {
        try{
            return search(conn, "borrower", lstToStr(fields), borrowerFieldDtype(fields), exper, conds, matchAll);
        }
        catch (SQLException e) {
            System.err.println("SQL ERROR OCCURRED "+e.getMessage());
        }
        return null;
    }

    public static ResultSet searchBorrower(Connection conn, borrowerField[] fields, String[] expers, SearchCondition[] conds, boolean matchAll) {
        try{
            return search(conn, "borrower", lstToStr(fields), borrowerFieldDtype(fields), expers, conds, matchAll);
        }
        catch (SQLException e) {
            System.err.println("SQL ERROR OCCURRED "+e.getMessage());
        }
        return null;
    }

    public static ResultSet searchBookLend(Connection conn, bookLendField[] fields, String exper, SearchCondition[] conds, boolean matchAll) {
        try{
            return search(conn, "bookLend", lstToStr(fields), blfDtype(fields), exper, conds, matchAll);
        }
        catch (SQLException e) {
            System.err.println("SQL ERROR OCCURRED "+e.getMessage());
        }
        return null;
    }

    public static ResultSet searchBookLend(Connection conn, bookLendField[] fields, String[] expers, SearchCondition[] conds, boolean matchAll) {
        try{
            return search(conn, "bookLend", lstToStr(fields), blfDtype(fields), expers, conds, matchAll);
        }
        catch (SQLException e) {
            System.err.println("SQL ERROR OCCURRED "+e.getMessage());
        }
        return null;
    }

    /*********************************************
     * Public Existence Check Utility
     *********************************************/
    public static boolean checkLendExists(Connection conn, int lendID) {
        try{
            String q1 = "SELECT * FROM bookLend WHERE lendID="+lendID;
            Statement statement = conn.createStatement();
            if(!checkSingleValResSet(statement.executeQuery(q1)))
            {
                statement.close();
                return false;
            }
            statement.close();
            return true;
        }
        catch (SQLException e) {
            System.err.println("SQL ERROR OCCURRED "+e.getMessage());
        }
        return false;
    }

    public static boolean checkBookExists(Connection conn, String title, String author)  {
        try{
            Statement statement = conn.createStatement();
            String q1 = "SELECT * FROM book WHERE title='"+sanitizeVal(title)+"' AND author='"+sanitizeVal(author)+"'";
            if(!checkSingleValResSet(statement.executeQuery(q1)))
            {
                statement.close();
                return false;
            }
            statement.close();
            return true;
        }
        catch(SQLException e){
            System.err.println("SQL ERROR OCCURRED "+e.getMessage());
        }
        return false;
    }

    public static boolean checkBorrowerExists(Connection conn, int userID) {
        try{
            String q1 = "SELECT * FROM borrower WHERE userID="+userID;
            Statement statement = conn.createStatement();
            if(!checkSingleValResSet(statement.executeQuery(q1)))
            {
                statement.close();
                return false;
            }
            statement.close();
            return true;
        }
        catch(SQLException e)
        {
            System.err.println("SQL ERROR OCCURRED "+e.getMessage());
        }
        return false;
    }

    /*********************************************
     * Public Insertion Methods
     *********************************************/
    public static boolean insertBook (Connection conn, bookField[] fields, String[] vals)
    {
        try{
            if(insertBookP(conn, fields, vals))
            {
                conn.commit();
                return true;
            }
            conn.rollback();
            return false;
        }
        catch(SQLException e)
        {
            System.err.println("SQL ERROR OCCURRED "+e.getMessage());
        }
        return false;
    }

    public static boolean insertBorrower(Connection conn, borrowerField[] fields, String[] vals)
    {
        try{
            if(insertBorrowerP(conn, fields, vals))
            {
                conn.commit();
                return true;
            }
            conn.rollback();
            return false;
        }
        catch(SQLException e)
        {
            System.err.println("SQL ERROR OCCURRED "+e.getMessage());
        }
        return false;
    }

    public static boolean insertBookLend(Connection conn, bookLendField[] fields, String[] vals){
        try{
            if(insertBookLendP(conn, fields, vals))
            {
                conn.commit();
                return true;
            }
            conn.rollback();
            return false;
        }
        catch(SQLException e)
        {
            System.err.println("SQL ERROR OCCURRED "+e.getMessage());
        }
        return false;
    }

    /*********************************************
     * Public Deletion Methods
     *********************************************/

    public static boolean deleteBook(Connection conn, String bookName, String authorName)  {
        try {
            if(deleteBookP(conn, bookName, authorName))
            {
                conn.commit();
                return true;
            }
            conn.rollback();
            return false;
        }
        catch(SQLException e) {
            System.err.println("SQL ERROR OCCURRED "+e.getMessage());
        }
        return false;
    }

    public static boolean deleteBorrower(Connection conn, int userID) {
        try {
            if(deleteBorrowerP(conn, userID))
            {
                conn.commit();
                return true;
            }
            conn.rollback();
            return false;
        }
        catch(SQLException e) {
                System.err.println("SQL ERROR OCCURRED "+e.getMessage());
        }
        return false;
    }

    public static boolean deleteBookLend(Connection conn, int lendID)   {
       try{
            if(delete(conn, "bookLend", new String[] {"lendID"}, new String[] {"INT"}, new String[] {String.valueOf(lendID)}))
            {
                conn.commit();
                return true;
            }
           conn.rollback();
           return false;
       }
       catch(SQLException e) {
           System.err.println("SQL ERROR OCCURRED "+e.getMessage());
       }
       return false;
    }

    /*********************************************
     * Public Update Methods
     *********************************************/

    public static boolean updateBook(Connection conn, String title, String author, bookField[] updateFields, String[] updateValues)
    {
        try{
            if(updateBookP(conn, title, author, updateFields, updateValues))
            {
                conn.commit();
                return true;
            }
            conn.rollback();
            return false;
        }
        catch(SQLException e){
            System.err.println("SQL ERROR OCCURRED "+e.getMessage());
        }
        return false;
    }

    public static boolean updateBorrower(Connection conn, int userID, borrowerField[] updateFields, String[] updateValues)
    {
        try{
            if(updateBorrowerP(conn, userID, updateFields, updateValues))
            {
                conn.commit();
                return true;
            }
            conn.rollback();
            return false;
        }
        catch(SQLException e){
            System.err.println("SQL ERROR OCCURRED "+e.getMessage());
        }
        return false;
    }

    public static boolean updateBookLend(Connection conn, int lendID, bookLendField[] updateFields, String[] updateValues){
        try{
            if(updateBookLendP(conn, lendID, updateFields, updateValues))
            {
                conn.commit();
                return true;
            }
            conn.rollback();
            return false;
        }
        catch(SQLException e){
            System.err.println("SQL ERROR OCCURRED "+e.getMessage());
        }
        return false;
    }

    public static boolean turnInBook(Connection conn, int lendID, String date)
    {
        try{
            if(turnInBookP(conn, lendID, date))
            {
                conn.commit();
                return true;
            }
            conn.rollback();
            return false;
        }
        catch(SQLException e){
            System.err.println("SQL ERROR OCCURRED "+e.getMessage());
        }
        return false;
    }

    /*********************************************
     * Internal Search Methods
     *********************************************/
    private static ResultSet search(Connection conn, String tableName, String[] fields, String[] dtypes, String exper, SearchCondition[] conds, boolean matchAll) throws SQLException {
        Statement statement = conn.createStatement();
        if(fields.length != dtypes.length || fields.length != conds.length)
        {
            System.err.println("In SEARCH: passed number of fields must equal number of search conditions and field data types");
            return null;
        }
        String query = "SELECT * FROM "+tableName+" WHERE ";
        for(int i = 0; i< fields.length; i++)
        {
            String add = getWhereClause(fields[i], exper, dtypes[i], conds[i]);
            if(add == null) return null;
            query += add;
            if(i < fields.length-1)
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

    private static ResultSet search(Connection conn, String tableName, String[] fields, String[] dtypes, String[] expers, SearchCondition[] conds, boolean matchAll) throws SQLException {
        Statement statement = conn.createStatement();
        if(fields.length != expers.length || fields.length != dtypes.length || fields.length != conds.length)
        {
            System.err.println("In SEARCH: passed number of fields must equal number of search conditions and field data types");
            return null;
        }
        String query = "SELECT * FROM "+tableName+" WHERE ";
        for(int i = 0; i< fields.length; i++)
        {
            String add = getWhereClause(fields[i], expers[i], dtypes[i], conds[i]);
            if(add == null) return null;
            query += add;
            if(i < fields.length-1)
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

    /*********************************************
     * Internal Insertion Methods
     *********************************************/

    private static boolean insertBookP(Connection conn, bookField[] fields, String[] vals) throws SQLException {
        //title author description count checkedout
        boolean[] hasVal = new boolean[5];
        if(fields.length != vals.length)
        {
            System.err.println("In INSERT: got different lengths of field and value arrays");
            return false;
        }
        String[] fieldsStrs = new String[fields.length];
        String[] valTypesStrs = new String[fields.length];
        String title = "";
        String author = "";
        for(int i = 0; i<fields.length; i++)
        {
            switch (fields[i])
            {
                case title:
                    if(!checkValid(hasVal, 0))
                    {
                        System.err.println("In INSERT: got multiple values for title");
                        return false;
                    }
                    title = vals[i];
                    break;
                case author:
                    if(!checkValid(hasVal, 1))
                    {
                        System.err.println("In INSERT: got multiple values for author");
                        return false;
                    }
                    author = vals[i];
                    break;
                case description:
                    if(!checkValid(hasVal, 2))
                    {
                        System.err.println("In INSERT: got multiple values for description");
                        return false;
                    }
                    break;
                case count:
                    if(!checkValid(hasVal, 3))
                    {
                        System.err.println("In INSERT: got multiple values for count");
                        return false;
                    }
                    break;
                case checkedOut:
                    if(!checkValid(hasVal, 4))
                    {
                        System.err.println("In INSERT: got multiple values for checkedOut");
                        return false;
                    }
                    break;
            }
            fieldsStrs[i] = fields[i].toString();
            valTypesStrs[i] = bookFieldDtype(fields[i]);
        }
        if(!hasVal[0] || !hasVal[1] || !hasVal[3])
        {
            System.err.println("In INSERT: insertions to book must have title, author, and count");
            return false;
        }
        if(checkBookExists(conn, title, author))
        {
            System.err.println("In INSERT: The database already contains a book with the passed title and author");
            return false;
        }
        if(!hasVal[4])
        {
            fieldsStrs = Arrays.copyOf(fieldsStrs, fieldsStrs.length+1);
            vals = Arrays.copyOf(vals, vals.length+1);
            fieldsStrs[fieldsStrs.length-1] = "checkedOut";
            vals[vals.length-1] = "0";
        }
        return insert(conn, "book", fieldsStrs, vals, valTypesStrs);
    }

    private static boolean insertBorrowerP(Connection conn, borrowerField[] fields, String[] vals) throws SQLException {
        //userid, firstname, lastname
        boolean[] hasVal = new boolean[3];
        if(fields.length != vals.length)
        {
            System.err.println("In INSERT: got different lengths of field and value arrays");
            return false;
        }
        String[] fieldsStrs = new String[fields.length];
        String[] valTypesStrs = new String[fields.length];
        for(int i = 0; i<fields.length; i++)
        {
            switch (fields[i])
            {
                case userID:
                    if(!checkValid(hasVal, 0)){
                        System.err.println("In INSERT: got multiple values for userID");
                        return false;
                    }
                    try{
                        if(checkBorrowerExists(conn, Integer.parseInt(vals[i])))
                        {
                            System.err.println("In INSERT: The database already contains a borrower with the passed id");
                            return false;
                        }
                    }
                    catch(NumberFormatException e)
                    {
                        System.err.println("In INSERT: userID field must be an integer");
                        return false;
                    }
                    break;
                case firstName:
                    if(!checkValid(hasVal, 1))
                    {
                        System.err.println("In INSERT: got multiple values for firstName");
                        return false;
                    }
                    break;
                case lastName:
                    if(!checkValid(hasVal, 2))
                    {
                        System.err.println("In INSERT: got multiple values for lastName");
                        return false;
                    }
                    break;
            }
            fieldsStrs[i] = fields[i].toString();
            valTypesStrs[i] = borrowerFieldDtype(fields[i]);
        }
        if(!hasVal[0] || !hasVal[1] || !hasVal[2])
        {
            System.err.println("In INSERT: insertions to borrower must have a userID, first name, and last name");
            return false;
        }
        return insert(conn, "borrower", fieldsStrs, vals, valTypesStrs);
    }

    private static boolean insertBookLendP(Connection conn, bookLendField[] fields, String[] vals) throws SQLException {
        //lendid, title, author, userid, outdate, duedate, indate
        boolean[] hasVal = new boolean[7];
        if(fields.length != vals.length)
        {
            System.err.println("In INSERT: got different lengths of field and value arrays");
            return false;
        }
        String[] fieldsStrs = new String[fields.length];
        String[] valTypesStrs = new String[fields.length];
        String title = "";
        String author = "";
        String userID = "";
        for(int i = 0; i<fields.length; i++)
        {
            switch (fields[i])
            {
                case lendID:
                    if(!checkValid(hasVal, 0)) {
                        System.err.println("In INSERT: got multiple values for lendID");
                        return false;
                    }
                    try{
                        if(checkLendExists(conn, Integer.parseInt(vals[i])))
                        {
                            System.err.println("In INSERT: The database already contains a lending with the passed id");
                            return false;
                        }
                    }
                    catch(NumberFormatException e)
                    {
                        System.err.println("In INSERT: lendID field must be an integer");
                        return false;
                    }
                    break;
                case title:
                    if(!checkValid(hasVal, 1)) {
                        System.err.println("In INSERT: got multiple values for title");
                        return false;
                    }
                    title = vals[i];
                    break;
                case author:
                    if(!checkValid(hasVal, 2)) {
                        System.err.println("In INSERT: got multiple values for author");
                        return false;
                    }
                    author = vals[i];
                    break;
                case userID:
                    if(!checkValid(hasVal, 3))
                    {
                        System.err.println("In INSERT: got multiple values for userID");
                        return false;
                    }
                    userID = vals[i];
                    break;
                case outDate:
                    if(!checkValid(hasVal, 4))
                    {
                        System.err.println("In INSERT: got multiple values for outDate");
                        return false;
                    }
                    break;
                case dueDate:
                    if(!checkValid(hasVal, 5)){
                        System.err.println("In INSERT: got multiple values for dueDate");
                        return false;
                    }
                    break;
                case inDate:
                    if(!checkValid(hasVal, 6)) {
                        System.err.println("In INSERT: got multiple values for inDate");
                        return false;
                    }
                    break;
            }
            fieldsStrs[i] = fields[i].toString();
            valTypesStrs[i] = blfDtype(fields[i]);
        }
        if(!hasVal[0] || !hasVal[1] || !hasVal[2] || !hasVal[3])
        {
            System.err.println("In INSERT: insertions to bookLend must have a lendID, title, author, and userID");
            return false;
        }
        int[] counts = getCountAndCheckedOut(conn, title, author);
        if(counts == null) {
            System.err.println("In INSERT: Lendings must be of a book in the database");
            return false;
        }
        int count = counts[0];
        int checkedOut = counts[1];
        if(count == checkedOut)
        {
            System.err.println("In INSERT: Cannot lend a book with all its copies checked out");
            return false;
        }
        if(!checkBorrowerExists(conn, Integer.parseInt(userID)))
        {
            System.err.println("In INSERT: userID for lending not recognized");
            return false;
        }
        if(!updateBook(conn, title, author, new bookField[]{bookField.checkedOut}, new String[] {String.valueOf(checkedOut+1)})) return false;
        return insert(conn, "bookLend", fieldsStrs, vals, valTypesStrs);
    }

    private static boolean insert(Connection conn, String tableName, String[] fields, String[] values, String[] valTypes) throws SQLException {
        Statement statement = conn.createStatement();
        if(fields.length != values.length)
        {
            System.err.println("In INSERT: received different number of fields and values");
            return false;
        }
        String fieldsSQL = "(";
        String valuesSQL = "(";
        for(int i = 0; i<fields.length; i++)
        {
            Object fieldVal = fieldToString(values[i], valTypes[i]);
            if(fieldVal == null) return false;
            fieldsSQL += fields[i];
            valuesSQL += fieldVal;
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

    /*********************************************
     * Internal Deletion Methods
     *********************************************/
    private static boolean deleteBookP(Connection conn, String bookName, String authorName) throws SQLException
    {
        Statement statement = conn.createStatement();
        String q1 = "SELECT * FROM bookLend WHERE title='"+sanitizeVal(bookName)+"' AND author='"+sanitizeVal(authorName)+"'";
        ResultSet res = statement.executeQuery(q1);
        if(res.next()){
            System.err.println("In DELETE: cannot delete a book that has lends in database. Delete lends first.");
            return false;
        }
        return delete(conn, "book", new String[]{"title", "author"}, new String[]{"VARCHAR", "VARCHAR"}, new String[]{bookName, authorName});
    }

    private static boolean deleteBorrowerP(Connection conn, int userID) throws SQLException {
        Statement statement = conn.createStatement();
        String q1 = "SELECT * FROM bookLend WHERE userID="+userID;
        ResultSet res = statement.executeQuery(q1);
        if(res.next()){
            System.err.println("In DELETE: cannot delete a borrower that has lends in database. Delete lends first.");
            return false;
        }
        return delete(conn, "borrower", new String[]{"userID"}, new String[]{"INT"}, new String[]{String.valueOf(userID)});
    }

    private static boolean delete(Connection conn, String tablename, String[] keyFields, String[] valtypes, String[] values) throws SQLException {
        String sql = "DELETE FROM "+tablename+" WHERE ";
        for(int i = 0; i<keyFields.length; i++)
        {
            sql += keyFields[i]+"="+fieldToString(values[i], valtypes[i]);
            if(i+1 < keyFields.length)
            {
                sql += " AND ";
            }
        }
        Statement statement = conn.createStatement();
        statement.execute(sql);
        statement.close();
        return true;
    }

    /*********************************************
     * Internal Update Methods
     *********************************************/

    private static boolean updateBookP(Connection conn, String title, String author, bookField[] updateFields, String[] updateValues) throws SQLException {
        if(updateFields.length != updateValues.length)
        {
            System.err.println("In UPDATE: field and value array lengths must match");
            return false;
        }
        if(!checkBookExists(conn, title, author))
        {
            System.err.println("In UPDATE: could not find book with passed title and author");
            return false;
        }
        String[] fieldStrs = new String[updateFields.length];
        for(int i=0; i<updateFields.length; i++)
        {
            if(updateFields[i] == bookField.title || updateFields[i] == bookField.author) {
                System.err.println("In UPDATE: cannot update primary key values (title/author). delete the existing record and reinsert instead.");
                return false;
            }
            fieldStrs[i] = bookFieldDtype(updateFields[i]);
        }
        String[] updateExprs = strsToExprs(updateValues, fieldStrs);
        return update(conn, "book", new String[] {"title", "author"}, new String[] {title, author}, new String[] {"VARCHAR", "VARCHAR"}, lstToStr(updateFields), updateExprs);
    }

    private static boolean updateBorrowerP(Connection conn, int userID, borrowerField[] updateFields, String[] updateValues) throws SQLException {
        if(updateFields.length != updateValues.length)
        {
            System.err.println("In UPDATE: field and value array lengths must match");
            return false;
        }
        if(!checkBorrowerExists(conn, userID))
        {
            System.err.println("In UPDATE: could not find borrower with passed id");
            return false;
        }
        String[] fieldStrs = new String[updateFields.length];
        for(int i=0; i<updateFields.length; i++)
        {
            if(updateFields[i] == borrowerField.userID)
            {
                System.err.println("In UPDATE: cannot update primary key values (userID). delete the existing record and reinsert instead.");
                return false;
            }
            fieldStrs[i] = borrowerFieldDtype(updateFields[i]);
        }
        String[] updateExprs = strsToExprs(updateValues, fieldStrs);
        return update(conn, "borrower", new String[] {"userID"}, new String[] {String.valueOf(userID)}, new String[] {"INT"}, lstToStr(updateFields), updateExprs);
    }

    private static boolean updateBookLendP(Connection conn, int lendID, bookLendField[] updateFields, String[] updateValues) throws SQLException {
        if(updateFields.length != updateValues.length) {
            System.err.println("In UPDATE: field and value array lengths must match");
            return false;
        }
        if(!checkLendExists(conn, lendID)) {
            System.err.println("In UPDATE: could not find lending with passed id");
            return false;
        }
        String[] fieldStrs = new String[updateFields.length];
        for(int i=0; i<updateFields.length; i++)
        {
            if(updateFields[i] == bookLendField.lendID || updateFields[i] == bookLendField.title || updateFields[i] == bookLendField.author || updateFields[i] == bookLendField.userID)
            {
                System.err.println("In UPDATE: cannot update primary key values (lendID) or foreign key values (title/author/userID). delete the existing record and reinsert instead.");
                return false;
            }
            fieldStrs[i] = blfDtype(updateFields[i]);
        }
        String[] updateExprs = strsToExprs(updateValues, fieldStrs);
        return update(conn, "bookLend", new String[] {"lendID"}, new String[] {String.valueOf(lendID)}, new String[] {"INT"}, lstToStr(updateFields), updateExprs);
    }

    private static boolean turnInBookP(Connection conn, int lendID, String date) throws SQLException {
        if(!checkLendExists(conn, lendID))
        {
            System.err.println("In UPDATE: could not find lending with passed id");
            return false;
        }
        ResultSet res = searchBookLend(conn, lendID);
        res.next();
        String title = res.getString("title");
        String author = res.getString("author");
        int[] counts = getCountAndCheckedOut(conn, title, author);
        if(counts == null)
        {
            return false;
        }
        if(!updateBookLend(conn, lendID, new bookLendField[]{bookLendField.inDate}, new String[]{date})) return false;
        return updateBook(conn, title, author, new bookField[]{bookField.checkedOut}, new String[]{String.valueOf(counts[1]-1)});
    }

    private static boolean update(Connection conn, String tablename, String[] keyFields, String[] keyValues, String[] keyFieldTypes, String[] updateFields, String[] updateValues) throws SQLException {
        String sql = "UPDATE "+tablename+" SET ";
        for(int i = 0; i<updateFields.length; i++)
        {
            sql += updateFields[i]+"="+updateValues[i];
            if(i+1 < updateFields.length)
            {
                sql += ", ";
            }
        }
        sql += " WHERE ";
        for(int i = 0; i<keyFields.length; i++)
        {
            sql += keyFields[i]+"="+fieldToString(keyValues[i], keyFieldTypes[i]);
            if(i+1 < keyFields.length)
            {
                sql += " AND ";
            }
        }
        Statement statement = conn.createStatement();
        statement.execute(sql);
        statement.close();
        return true;
    }

    /*********************************************
     * SQL Formatting Utilities
     *********************************************/
    private static String getExperString(boolean exactMatch, String exper)
    {
        if(exactMatch)
            return "'"+sanitizeVal(exper)+"'";
        else
            return "'%"+sanitizeVal(exper)+"%'";
    }

    private static String[] strsToExprs(String[] inStrs, String[] valTypes)
    {
        String[] exprs = new String[inStrs.length];
        for(int i=0; i<inStrs.length; i++)
        {
            exprs[i] = fieldToString(inStrs[i], valTypes[i]);
        }
        return exprs;
    }

    private static String fieldToString(String field, String valType)
    {
        valType = valType.split(" ")[0];
        String ret = "";
        switch (valType)
        {
            case "VARCHAR":
            case "TEXT":
                ret += "'"+sanitizeVal(field)+"'";
                return ret;
            case "INT":
                try{
                    int val = Integer.parseInt(field);
                    ret += val;
                    return ret;
                }
                catch(NumberFormatException e)
                {
                    System.err.println("ERR: received non-integer input for type INT");
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
                    System.err.println("ERR: Received non-integer input for type DATE");
                    return null;
                }
        }
        return null;
    }

    private static String sanitizeVal(String val)
    {
        String ret = "";
        for(int i = 0; i < val.length(); i++)
        {
            ret += val.charAt(i);
            if(val.charAt(i) == '\'')
            {
                ret += "'";
            }
        }
        return ret;
    }

    private static String getWhereClause(String field, String value, String dtype, SearchCondition cond)
    {
        switch (dtype.split(" ")[0])
        {
            case "VARCHAR":
            case "TEXT":
                String exper;
                if(cond == SearchCondition.strExactly)
                {
                    exper =  getExperString(true, value);
                }
                else if(cond == SearchCondition.strLike)
                {
                    exper =  getExperString(false, value);
                }
                else
                {
                    System.err.println("ERR: received invalid search condition for type TEXT/VARCHAR");
                    return null;
                }
                return "LOWER("+ field + ") LIKE LOWER("+exper+")";
            case "INT":
            case "DATE":
                String val = fieldToString(value, dtype);
                if(val == null)
                {
                    return null;
                }
                return switch (cond) {
                    case eq -> field + "=" + val;
                    case gt -> field + ">" + val;
                    case lt -> field + "<" + val;
                    default -> null;
                };
        }
        return null;
    }

    /*********************************************
     * Other Utilities
     *********************************************/

    private static int[] getCountAndCheckedOut(Connection conn, String title, String author) throws SQLException {
        int[] ret = new int[2];
        Statement statement = conn.createStatement();
        String q1 = "SELECT * FROM book WHERE title='"+sanitizeVal(title)+"' AND author='"+sanitizeVal(author)+"'";
        ResultSet res = statement.executeQuery(q1);
        if(!res.next()) return null;
        int count = res.getInt("count");
        int checkedOut = res.getInt("checkedOut");
        ret[0] = count;
        ret[1] = checkedOut;
        return ret;
    }

    private static boolean checkValid(boolean[] check, int ind)
    {
        check[ind] = !check[ind];
        return check[ind];
    }

    private static boolean checkSingleValResSet(ResultSet res) throws SQLException {
        if(!res.next()) return false;
        return !res.next();
    }
}
