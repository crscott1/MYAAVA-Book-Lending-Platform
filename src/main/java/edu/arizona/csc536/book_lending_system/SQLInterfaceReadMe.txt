
/*********************************************
* Enums
*********************************************/

bookField: Field names for the book table
    Public method bookFieldDtype converts a string or array of bookField to strings representing their corresponding sql data types
borrowerField: Field names for the borrower table
    Public method borrowerFieldDtype converts a string or array of borrowerField to strings representing their corresponding sql data types
bookLendField: Field names for the bookLned table
    Public method blfDtype converts a string or array of bookLendField to strings representing their corresponding sql data types
SearchCondition: enum representing different possible comparisons the sql functionality can make. strLike matches two strings
    such that the first contains the second, strExactly is string equality-use these for strings only. eq, gt, and lt are
    =, >, and < respectively and should be used for int and date values.
lstToStr: utility method that converts a list of enums to a list of strings where each string is the name of the corresponding enum (.toString)

/*********************************************
* Connection
*********************************************/
getTestConnection: returns a JDBC connection object using localhost 3306 with user/pass root 1108, to be used for testing
getConnection: returns a JDBC connection object connected to localhost 3306 on an account with the passed account details

/*********************************************
* ID Creation Utility
*********************************************/
getNewUserID: returns an unused userID that can be assigned to a new user
getNewLendID: returns an unused lendID that can be assigned to a new lending

/*********************************************
* Clear Tables
*********************************************/
ClearTables: completely resets the book, borrower, bookLend, and idtracker tables for testing.

/*********************************************
* Search Methods
*********************************************/
Each of these methods offers simple and detailed overloads depending on what you need. Simple should be fine for most cases.
All of them need to be passed a pre-established database connection and will return null when something goes wrong (invalid values
passed, sql exception encountered).
searchBook:
    OL1: pass a string and a search condition (strLike or strExactly). the method will search the book table title, author,
        and description for that string (looking for one of those fields to match exactly or contain it, depending on the
        search condition) and return a JDBC resultset with all books that match.
    OL2: pass a book title, author, a length 2 array of search conditions (strLike or strExactly for title and author respectively),
        and a boolean. the method will search the book table title and author fields for the appropriate value (matching exactly
        or by contains, depending on the search specifier in the array) and return a JDBC resultset with all books that match.
        The boolean indicates whether to require that *both* the title and author be matched (true) or if records can
        match either or (false).
    OL3: pass an array of book fields, a string search expression, an array of search conditions, and a boolean. Method
        will search all fields for the passed expression, using the passed conditions. If the bool is set to true, will
        require that all fields match the passed expression based on search condition.
    OL4: Same as OL3 but takes an array of search expressions and searches each field for the one at the same index.
searchBorrower:
    OL1: pass a userID. the method will return a JDBC resultset containing the borrower (if any) with that id.
    OL2: pass a first name, last name, a length 2 array of search conditions (strLike or strExactly for first name and last name respectively),
        and a boolean. the method will search the borrower table name fields for the appropriate value (matching exactly
        or by contains, depending on the search specifier in the array) and return a JDBC resultset with all borrowers that match.
        The boolean indicates whether to require that *both* the first and last name be matched (true) or if records can
        match either or (false).
    OL3: pass an array of borrower fields, a string search expression, an array of search conditions, and a boolean. Method
        will search all fields for the passed expression, using the passed conditions. If the bool is set to true, will
        require that all fields match the passed expression based on search condition.
    OL4: Same as OL3 but takes an array of search expressions and searches each field for the one at the same index.
searchBookLend:
    OL1: pass a lendID. the method will return a JDBC resultset containing the lending (if any) with that id.
    OL2: pass a book title, author, a length 2 array of search conditions (strLike or strExactly for title and author respectively),
        and a boolean. the method will search the bookLend table title and author fields for the appropriate value (matching exactly
        or by contains, depending on the search specifier in the array) and return a JDBC resultset with all lendings that match.
        The boolean indicates whether to require that *both* the title and author be matched (true) or if records can
        match either or (false).
    OL3: pass an array of bookLend fields, a string search expression, an array of search conditions, and a boolean. Method
        will search all fields for the passed expression, using the passed conditions. If the bool is set to true, will
        require that all fields match the passed expression based on search condition.
    OL4: Same as OL3 but takes an array of search expressions and searches each field for the one at the same index.
searchBookLendForUser: pass a userID, the method will return all lendings to that user found in bookLend
checkBookExists: takes a title and an author, returns true if a book with that title and author exists in the database
checkBorrowerExists: takes a userID, returns true if a borrower with that id exists in the database
checkLendExists: takes a lendID, returns true if a lending with that id exists in the database

/*********************************************
* Insertion Methods
*********************************************/
Each of these methods needs to be sent a pre-established database connection. They will return false if some error occurs
in the insertion process and print an error message, true if the record is successfully inserted.
insertBook: send an array of bookFields and an array of string values of equal size. each value will be inserted for the
    corresponding field at the same index. required fields are title, author, and count. checkedOut will default to 0
    if not passed. title and author together must be unique (e.g. two books may have the same author or same title,
    but not the same title and same author).
insertBorrower: send an array of borrowerFields and an array of string values of equal size. each value will be inserted for the
    corresponding field at the same index. all fields required. userID must be unique
insertBookLend: send an array of bookLendFields and an array of string values of equal size. each value will be inserted for the
    corresponding field at the same index. all non-date fields required, dates may be null. title and author must map to
    a book in the db and userID must map to an author in the db. lendID must be unique. Will AUTOMATICALLY update the
    book's checkedOut count and will fail and commit nothing if all copies of the book are checked out

/*********************************************
* Deletion Methods
*********************************************/
Each of these methods needs to be sent a pre-established database connection. They will return false if some error occurs
in the deletion process and print an error message, true if the record is successfully deleted. No records match returns true.
deleteBook: deletes the book with the passed title and author. will fail if lendings of the book exist in the database;
    delete these first
deleteBorrower: deletes the borrower with the passed userID. will fail if lendings to this user exist in the database;
    delete them first
deleteBookLend: deletes the lending with the passed lendID.

/*********************************************
* Update Methods
*********************************************/
Each of these methods needs to be sent a pre-established database connection. They will return false if some error occurs
in the update process and print an error message, true if the record is successfully updated. Record not exists will return false.
updateBook: takes a title, an author, an array of bookFields to update and a same-length array of values to update those fields to.
    finds a record matching the title and author and updates the fields. cannot update title or author, to do so the record
    will need to be deleted and reinserted.
updateBorrower: takes a userID, an array of borrowerFields to update and a same-length array of values to update those fields to.
    finds the borrower with that id and updates the fields. cannot update userID, to do so the record will need to be deleted and reinserted.
updateBookLend: takes a lendID, an array of bookLendFields to update and a same-length array of values to update those fields to.
    finds the lending with that id and updates the fields. cannot update lendID, title, author, or userID, to do so the record
    will need to be deleted and reinserted.
turnInBook: takes a lendID and a date. finds the lending with that id, sets its inDate to the passed date, and updates
    the checkedOut value for the corresponding book