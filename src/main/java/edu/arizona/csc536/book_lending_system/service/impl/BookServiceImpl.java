package edu.arizona.csc536.book_lending_system.service.impl;

import edu.arizona.csc536.book_lending_system.domain.Vo.BookVo;
import edu.arizona.csc536.book_lending_system.service.IBookService;
import edu.arizona.csc536.book_lending_system.utils.page.Page;

import java.util.List;

/**
 * Implements the interface here
 */
public class BookServiceImpl implements IBookService {
    @Override
    public List<BookVo> selectBooksByBookPartInfo(String partInfo) {
        return null;
    }

    @Override
    public Page<BookVo> findBooksByCategoryId(int categoryId, int pageNum) {
        return null;
    }
}
