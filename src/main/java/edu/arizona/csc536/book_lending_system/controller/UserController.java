package edu.arizona.csc536.book_lending_system.controller;

import edu.arizona.csc536.book_lending_system.domain.User;
import edu.arizona.csc536.book_lending_system.domain.Vo.BorrowingBooksVo;
import edu.arizona.csc536.book_lending_system.service.IBookService;
import edu.arizona.csc536.book_lending_system.service.IBorrowingBooksRecordService;
import edu.arizona.csc536.book_lending_system.service.IUserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Resource
    private IUserService userService;

    @Resource
    private IBorrowingBooksRecordService borrowingBooksRecordService;

    @Resource
    private IBookService bookService;

    @PostMapping("/userLogin")
    public String userLogin(@Param("userName") String userName,
            @Param("password") String password,
            HttpServletRequest request) {
        User user = userService.userLogin(userName, password);
        if (user != null) {
            request.getSession().setAttribute("flag", 0);
            request.getSession().setAttribute("user", user);
            return "user/index";
        }
        request.getSession().setAttribute("flag", 1);
        return "index";
    }

    @RequestMapping("/isUserExist")
    @ResponseBody
    public String isUserExist(@Param("userName") String userName) {
        List<User> users = userService.findUserByUserName(userName);
        return (users == null || users.isEmpty()) ? "false" : "true";
    }

    @RequestMapping("/userBorrowBookRecord")
    public String userBorrowBookRecord(Model model, HttpServletRequest request) {
        ArrayList<BorrowingBooksVo> res = borrowingBooksRecordService.selectAllBorrowRecord(request);
        model.addAttribute("borrowingBooksList", res);
        return "user/borrowingBooksRecord";
    }

    @RequestMapping("/userReturnBooksPage")
    public String userReturnBooksPage() {
        return "user/returnBooks";
    }

    @RequestMapping("/userMessagePage")
    public String userMessagePage(Model model, HttpServletRequest request) {
        User session_user = (User) request.getSession().getAttribute("user");
        User user = userService.findUserById(session_user.getUserId());
        model.addAttribute("message_user", user);
        return "user/userMessage";
    }

    @RequestMapping("/borrowingPage")
    public String borrowing() {
        return "user/borrowingBooks";
    }

    @RequestMapping("/userIndex")
    public String userIndex() {
        return "user/index";
    }

    @RequestMapping("/updateUser")
    @ResponseBody
    public boolean updateUser(User user, HttpServletRequest request) {
        return userService.updateUser(user, request);
    }

    @RequestMapping("/userReturnBook")
    @ResponseBody
    public boolean returnBook(int bookId, HttpServletRequest request) {
        return userService.userReturnBook(bookId, request);
    }

    @RequestMapping("/userBorrowingBook")
    @ResponseBody
    public boolean borrowingBook(int bookId, HttpServletRequest request) {
        System.out.println(bookId);
        return userService.userBorrowingBook(bookId, request);
    }

    @RequestMapping("/adminLoginPage")
    public String adminLoginPage() {
        return "adminLogin";
    }

    @RequestMapping("/userLogOut")
    public String userLogOut(HttpServletRequest request) {
        request.getSession().invalidate();
        return "index";
    }

    @RequestMapping("/findBookPage")
    public String findBookPage() {
        return "user/findBook";
    }

    @RequestMapping("/deleteUser")
    @ResponseBody
    public String deleteUserByUserId(@RequestParam("userId") int userId) {
        int res = userService.deleteUserById(userId);
        return res > 0 ? "true" : "false";
    }

    @RequestMapping("/addUser")
    @ResponseBody
    public String addUser(User user) {
        int res = userService.insertUser(user);
        return res > 0 ? "true" : "false";
    }
}
