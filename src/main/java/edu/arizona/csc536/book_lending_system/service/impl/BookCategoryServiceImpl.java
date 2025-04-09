package edu.arizona.csc536.book_lending_system.service.impl;

import edu.arizona.csc536.book_lending_system.domain.BookCategory;
import edu.arizona.csc536.book_lending_system.service.IBookCategoryService;
import edu.arizona.csc536.book_lending_system.utils.page.Page;
import org.springframework.stereotype.Service;
import edu.arizona.csc536.book_lending_system.mapper.*;

import jakarta.annotation.Resource;
import java.util.List;

/**
 * Implements the interface here
 */
@Service
public class BookCategoryServiceImpl implements IBookCategoryService {

    @Resource
    private BookCategoryMapper bookCategoryMapper;

    @Override
    public Page<BookCategory> selectBookCategoryByPageNum(int pageNum) {
        Page<BookCategory> page = new Page<>();
        List<BookCategory> list = bookCategoryMapper.selectByPageNum((pageNum - 1) * 10, 10);
        page.setPageSize(10);
        page.setPageNum(pageNum);
        page.setList(list);
        int recordCount = bookCategoryMapper.selectAllCount();
        int pageCount = recordCount / 10;
        if (recordCount % 10 != 0) {
            pageCount++;
        }
        page.setPageCount(pageCount);
        return page;
    }

    @Override
    public int deleteBookCategoryById(int bookCategoryId) {
        return bookCategoryMapper.deleteByPrimaryKey(bookCategoryId);
    }
}
