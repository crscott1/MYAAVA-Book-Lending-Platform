package edu.arizona.csc536.book_lending_system.service.impl;

import edu.arizona.csc536.book_lending_system.domain.Admin;
import edu.arizona.csc536.book_lending_system.domain.Book;
import edu.arizona.csc536.book_lending_system.domain.BookCategory;
import edu.arizona.csc536.book_lending_system.service.IAdminService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
 * Implements the interface here
 */
public class AdminServiceImpl implements IAdminService {
    @Override
    public boolean adminIsExist(String name) {
        return false;
    }

    @Override
    public Admin adminLogin(String name, String password) {
        return null;
    }

    @Override
    public boolean addBook(Book book) {
        return false;
    }

    @Override
    public List<BookCategory> getBookCategories() {
        return null;
    }

    @Override
    public boolean addBookCategory(BookCategory bookCategory) {
        return false;
    }

    @Override
    public boolean updateAdmin(Admin admin, HttpServletRequest request) {
        return false;
    }
}
