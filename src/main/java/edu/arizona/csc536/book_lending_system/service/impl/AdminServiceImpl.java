package edu.arizona.csc536.book_lending_system.service.impl;

import edu.arizona.csc536.book_lending_system.domain.Admin;
import edu.arizona.csc536.book_lending_system.domain.Book;
import edu.arizona.csc536.book_lending_system.domain.BookCategory;
import edu.arizona.csc536.book_lending_system.service.IAdminService;
import jakarta.servlet.http.HttpServletRequest;
import edu.arizona.csc536.book_lending_system.mapper.*;
import edu.arizona.csc536.book_lending_system.domain.*;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implements the interface here
 */
@Service
public class AdminServiceImpl implements IAdminService {
    @Resource
    private AdminMapper adminMapper;

    @Resource
    private BookMapper bookMapper;

    @Resource
    private BookCategoryMapper bookCategoryMapper;

    @Override
    public boolean adminIsExist(String name) {
        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andAdminNameEqualTo(name);
        List<Admin> admin = adminMapper.selectByExample(adminExample);
        if (null == admin)
            return false;
        if (admin.size() < 1) {
            return false;
        }
        return true;
    }

    @Override
    public Admin adminLogin(String name, String password) {

        AdminExample adminExample = new AdminExample();
        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andAdminNameEqualTo(name);
        List<Admin> admin = adminMapper.selectByExample(adminExample);

        if (null == admin) {
            return null;
        }

        for (Admin a : admin) {
            if (a.getAdminPwd().equals(password)) {
                return a;
            }
        }
        return null;
    }

    @Override
    public boolean addBook(Book book) {
        int n = bookMapper.insert(book);
        if (n > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<BookCategory> getBookCategories() {
        BookCategoryExample bookCategoryExample = new BookCategoryExample();
        return bookCategoryMapper.selectByExample(bookCategoryExample);
    }

    @Override
    public boolean addBookCategory(BookCategory bookCategory) {
        int n = bookCategoryMapper.insert(bookCategory);
        if (n > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateAdmin(Admin admin, HttpServletRequest request) {
        // get admin object from session
        Admin sessionAdmin = (Admin) request.getSession().getAttribute("admin");
        admin.setAdminId(sessionAdmin.getAdminId());
        int n = adminMapper.updateByPrimaryKey(admin);

        if (n > 0) {
            //succeed to change，update session
            Admin newAdmin = adminMapper.selectByPrimaryKey(admin.getAdminId());
            request.getSession().setAttribute("admin", newAdmin);
            return true;
        }

        return false;
    }
}
