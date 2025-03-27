package edu.arizona.csc536.book_lending_system.service;

import edu.arizona.csc536.book_lending_system.domain.Vo.BorrowingBooksVo;
import edu.arizona.csc536.book_lending_system.utils.page.Page;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;

public interface IBorrowingBooksRecordService {
    // Query all Borrowers Record
    public Page<BorrowingBooksVo> selectAllByPage(int pageNum);


    // Query all users Record
    public ArrayList<BorrowingBooksVo> selectAllBorrowRecord(HttpServletRequest request);

}
