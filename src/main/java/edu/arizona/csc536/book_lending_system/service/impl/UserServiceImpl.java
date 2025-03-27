package edu.arizona.csc536.book_lending_system.service.impl;

import edu.arizona.csc536.book_lending_system.domain.User;
import edu.arizona.csc536.book_lending_system.domain.Vo.BorrowingBooksVo;
import edu.arizona.csc536.book_lending_system.service.IUserService;
import edu.arizona.csc536.book_lending_system.utils.page.Page;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class UserServiceImpl implements IUserService {
    @Override
    public List<User> findUserByUserName(String userName) {
        return null;
    }

    @Override
    public User userLogin(String userName, String password) {
        return null;
    }

    @Override
    public boolean updateUser(User user, HttpServletRequest request) {
        return false;
    }

    @Override
    public List<BorrowingBooksVo> findAllBorrowingBooks(HttpServletRequest request) {
        return null;
    }

    @Override
    public boolean userReturnBook(int bookId, HttpServletRequest request) {
        return false;
    }

    @Override
    public boolean userBorrowingBook(int bookId, HttpServletRequest request) {
        return false;
    }

    @Override
    public User findUserById(int id) {
        return null;
    }

    @Override
    public Page<User> findUserByPage(int pageNum) {
        return null;
    }

    @Override
    public int insertUser(User user) {
        return 0;
    }

    @Override
    public int deleteUserById(int userId) {
        return 0;
    }
}
