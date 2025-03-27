package edu.arizona.csc536.book_lending_system.service;

import java.util.List;
import edu.arizona.csc536.book_lending_system.domain.Admin;
import edu.arizona.csc536.book_lending_system.domain.Book;
import edu.arizona.csc536.book_lending_system.domain.BookCategory;
import jakarta.servlet.http.HttpServletRequest;

public interface IAdminService {
    //Check adminIs Exist
    boolean adminIsExist(String name);

    //CHeck Admin Login
    Admin adminLogin(String name, String password);

    //Add New Books
    boolean addBook(Book book);

    //Get all getBookCategories
    List<BookCategory> getBookCategories();

    //Add new BookCategory
    boolean addBookCategory(BookCategory bookCategory);

    // Add new Admin
    boolean updateAdmin(Admin admin, HttpServletRequest request)
}
