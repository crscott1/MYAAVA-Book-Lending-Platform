package edu.arizona.csc536.book_lending_system.service;

import java.util.List;

import edu.arizona.csc536.book_lending_system.domain.Vo.BookVo;
import edu.arizona.csc536.book_lending_system.utils.page.Page;


public interface IBookService {
    /**
     * Search for Books by keywords
     *
     * @param partInfo
     * @return
     */
    List<BookVo> selectBooksByBookPartInfo(String partInfo);

    /**
     * Search for Books by Category
     *
     * @param categoryId
     * @return
     */
    Page<BookVo> findBooksByCategoryId(int categoryId, int pageNum);

}
