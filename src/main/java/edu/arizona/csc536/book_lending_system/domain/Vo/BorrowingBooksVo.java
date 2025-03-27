package edu.arizona.csc536.book_lending_system.domain.Vo;

import edu.arizona.csc536.book_lending_system.domain.Book;
import edu.arizona.csc536.book_lending_system.domain.User;
import lombok.Data;


/**
 * View Object: mix object in convenient for front-end interaction
 */
@Data
public class BorrowingBooksVo {
    private User user;
    private Book book;  //borrowing book
    private String dateOfBorrowing;
    private String dateOfReturn;
}
