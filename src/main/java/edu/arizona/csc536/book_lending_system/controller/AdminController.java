package edu.arizona.csc536.book_lending_system.controller;

import edu.arizona.csc536.book_lending_system.domain.Admin;
import edu.arizona.csc536.book_lending_system.domain.BookCategory;
import edu.arizona.csc536.book_lending_system.domain.User;
import edu.arizona.csc536.book_lending_system.domain.Vo.BookVo;
import edu.arizona.csc536.book_lending_system.service.IAdminService;
import edu.arizona.csc536.book_lending_system.service.IBookCategoryService;
import edu.arizona.csc536.book_lending_system.service.IUserService;
import edu.arizona.csc536.book_lending_system.utils.page.Page;

// springboot 2:
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;

// springboot 3:
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * AdminController contains interface to administrator. It receives
 * administrator's HTTP request and calls service layer
 * to execute business logic. And interface return the result from the service.
 */

@Controller
public class AdminController {

    @Resource
    private IAdminService adminService;

    @Resource
    private IBookCategoryService bookCategoryService;

    @Resource
    private IUserService userService;

    /**
     * Check if the admin username exists
     */
    @RequestMapping("/isAdminExist")
    @ResponseBody
    public String adminIsExist(@Param("adminName") String adminName) {
        boolean exists = adminService.adminIsExist(adminName);
        return exists ? "true" : "false";
    }

    /**
     * Admin login logic
     */
    @PostMapping("/adminLogin")
    public String adminLogin(@Param("userName") String userName,
            @Param("password") String password,
            HttpServletRequest request) {
        Admin admin = adminService.adminLogin(userName, password);
        if (admin == null) {
            request.getSession().setAttribute("flag", 1); // login failed
            return "index";
        }
        request.getSession().setAttribute("flag", 0); // login success
        request.getSession().setAttribute("admin", admin);
        return "admin/index";
    }

    /**
     * Return the add-book page
     */
    @RequestMapping("/addBookPage")
    public String addBookPage() {
        return "admin/addBook";
    }

    /**
     * Return the add-category page with pagination
     */
    @RequestMapping("/addCategoryPage")
    public String addCategoryPage(@RequestParam("pageNum") int pageNum, Model model) {
        Page<BookCategory> page = bookCategoryService.selectBookCategoryByPageNum(pageNum);
        model.addAttribute("page", page);
        return "admin/addCategory";
    }

    /**
     * Return the book status page
     */
    @RequestMapping("/showStausPage")
    public String showStatusPage() {
        return "admin/showStaus";
    }

    /**
     * Admin home page
     */
    @RequestMapping("/adminIndex")
    public String returnAdminIndexPage() {
        return "admin/index";
    }

    /**
     * Return the user list page (paginated)
     */
    @RequestMapping("/showUsersPage")
    public String showUsersPage(Model model, @RequestParam("pageNum") int pageNum) {
        Page<User> page = userService.findUserByPage(pageNum);
        model.addAttribute("page", page);
        return "admin/showUsers";
    }

    /**
     * Return the book list page (initial empty view)
     */
    @RequestMapping("/showBooksPage")
    public String showBooksPage(Model model) {
        Page<BookVo> page = new Page<>();
        page.setPageCount(1);
        page.setPageNum(1);
        model.addAttribute("page", page);
        return "admin/showBooks";
    }

    /**
     * Admin logout
     */
    @RequestMapping("/adminLogOut")
    public String userLogOut(HttpServletRequest request) {
        request.getSession().invalidate();
        return "index";
    }

    /**
     * Return the add-user page
     */
    @RequestMapping("/addUserPage")
    public String addUserPage() {
        return "admin/addUser";
    }

    /**
     * Return admin info page
     */
    @RequestMapping("/adminInfoPage")
    public String adminInfo() {
        return "admin/adminInfo";
    }

    /**
     * Update admin info
     */
    @RequestMapping("/updateAdmin")
    @ResponseBody
    public boolean updateAdmin(Admin admin, HttpServletRequest request) {
        return adminService.updateAdmin(admin, request);
    }
}
