package edu.arizona.csc536.book_lending_system.controller;

import edu.arizona.csc536.book_lending_system.service.IBookService;
import edu.arizona.csc536.book_lending_system.service.IBorrowingBooksRecordService;
import edu.arizona.csc536.book_lending_system.service.IUserService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
public class UserController {
    @Resource
    private IUserService userService;

    @Resource
    private IBorrowingBooksRecordService borrowingBooksRecordService;

    @Resource
    private IBookService bookService;
}
