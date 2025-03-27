package edu.arizona.csc536.book_lending_system.service;

import edu.arizona.csc536.book_lending_system.domain.BookCategory;
import edu.arizona.csc536.book_lending_system.utils.page.Page;

public interface IBookCategoryService {
    //Search Book by Category by Page
    public Page<BookCategory> selectBookCategoryByPageNum(int pageNum);

    int deleteBookCategoryById(int bookCategoryId);
}
