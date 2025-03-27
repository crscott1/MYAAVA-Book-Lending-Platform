package edu.arizona.csc536.book_lending_system.controller;

import edu.arizona.csc536.book_lending_system.service.IAdminService;
import edu.arizona.csc536.book_lending_system.service.IBookCategoryService;
import edu.arizona.csc536.book_lending_system.service.IBookService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
public class BookController {
    @Resource
    private IAdminService adminService;
    @Resource
    private IBookService bookService;
    @Resource
    private IBookCategoryService bookCategoryService;
}
