package edu.arizona.csc536.book_lending_system.service.impl;

import edu.arizona.csc536.book_lending_system.domain.BookCategory;
import edu.arizona.csc536.book_lending_system.service.IBookCategoryService;
import edu.arizona.csc536.book_lending_system.utils.page.Page;

/**
 * Implements the interface here
 */
public class BookCategoryServiceImpl implements IBookCategoryService {
    @Override
    public Page<BookCategory> selectBookCategoryByPageNum(int pageNum) {
        return null;
    }

    @Override
    public int deleteBookCategoryById(int bookCategoryId) {
        return 0;
    }
}
