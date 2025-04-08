package edu.arizona.csc536.book_lending_system.controller;

import edu.arizona.csc536.book_lending_system.domain.Book;
import edu.arizona.csc536.book_lending_system.domain.BookCategory;
import edu.arizona.csc536.book_lending_system.domain.Vo.BookVo;
import edu.arizona.csc536.book_lending_system.service.IAdminService;
import edu.arizona.csc536.book_lending_system.service.IBookCategoryService;
import edu.arizona.csc536.book_lending_system.service.IBookService;
import edu.arizona.csc536.book_lending_system.utils.page.Page;

//springboot 2
import javax.annotation.Resource;

// springboot 3:
// import jakarta.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BookController {

    @Resource
    private IAdminService adminService;

    @Resource
    private IBookService bookService;

    @Resource
    private IBookCategoryService bookCategoryService;

    /**
     * Admin adds a new book
     */
    @RequestMapping("/addBook")
    @ResponseBody
    public String addBook(Book book) {
        boolean res = adminService.addBook(book);
        return res ? "true" : "false";
    }

    /**
     * Show paginated books by category (admin view)
     */
    @RequestMapping("/showBooksResultPageByCategoryId")
    public String showBooksResultPageByCategoryId(@RequestParam("pageNum") int pageNum,
            @RequestParam("bookCategory") int bookCategory,
            Model model) {
        Page<BookVo> page = bookService.findBooksByCategoryId(bookCategory, pageNum);
        model.addAttribute("page", page);
        model.addAttribute("bookCategory", bookCategory);
        return "admin/showBooks";
    }

    /**
     * User searches for books with partial info
     */
    @RequestMapping("/findBookByBookPartInfo")
    public String findBooksResultPage(@RequestParam("bookPartInfo") String bookPartInfo, Model model) {
        List<BookVo> bookVos = bookService.selectBooksByBookPartInfo(bookPartInfo);
        model.addAttribute("bookList", bookVos);
        return "user/findBook";
    }

    /**
     * Return all book categories
     */
    @RequestMapping("/findAllBookCategory")
    @ResponseBody
    public List<BookCategory> findAllBookCategory() {
        return adminService.getBookCategories();
    }

    /**
     * Admin creates a new book category
     */
    @RequestMapping("/addBookCategory")
    @ResponseBody
    public String addBookCategory(BookCategory bookCategory) {
        boolean b = adminService.addBookCategory(bookCategory);
        return b ? "true" : "false";
    }

    /**
     * Delete a book category by ID
     */
    @RequestMapping("/deleteCategory")
    @ResponseBody
    public String deleteBookCategoryById(@RequestParam("bookCategoryId") int bookCategoryId) {
        int res = bookCategoryService.deleteBookCategoryById(bookCategoryId);
        return res > 0 ? "true" : "false";
    }
}
