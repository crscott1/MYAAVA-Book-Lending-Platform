package edu.arizona.csc536.book_lending_system.service;

import edu.arizona.csc536.book_lending_system.domain.User;
import edu.arizona.csc536.book_lending_system.domain.Vo.BorrowingBooksVo;
import edu.arizona.csc536.book_lending_system.utils.page.Page;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface IUserService {

    // Search Users
    List<User> findUserByUserName(String userName);

    //User Login
    User userLogin(String userName, String password);

    //Update User
    boolean updateUser(User user, HttpServletRequest request);

    //Search Borrowers history
    List<BorrowingBooksVo> findAllBorrowingBooks(HttpServletRequest request);

    //Borrower Return books
    boolean userReturnBook(int bookId, HttpServletRequest request);

    //Borrower borrow books
    boolean userBorrowingBook(int bookId, HttpServletRequest request);

    //Search for Borrowers
    User findUserById(int id);

    //Search Borrowers by Page
    Page<User> findUserByPage(int pageNum);

    //Add new Borrowers
    int insertUser(User user);

    //Delete Borrowers
    int deleteUserById(int userId);
}
