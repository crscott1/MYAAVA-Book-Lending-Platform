package edu.arizona.csc536.book_lending_system.controller;


import edu.arizona.csc536.book_lending_system.service.IAdminService;
import edu.arizona.csc536.book_lending_system.service.IBookCategoryService;
import edu.arizona.csc536.book_lending_system.service.IUserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * AdminController contains interface to administrator. It receives administrator's HTTP request and calls service layer
 * to execute business logic. And interface return the result from the service.
 */
@Controller  /* Return result is a Page view */
public class AdminController {

    /**
     * Three Level Business Logic: Admin, User, Book
     */
    @Resource
    private IAdminService adminService;

    @Resource
    private IBookCategoryService bookCategoryService;

    @Resource
    private IUserService userService;


    /**
     * The interface should be implemented until we make sure how the front-end user interaction.
     * How uer request, and what Page should be return.
     */



}
