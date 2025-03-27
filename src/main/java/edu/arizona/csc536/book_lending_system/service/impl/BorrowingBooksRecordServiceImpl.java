package edu.arizona.csc536.book_lending_system.service.impl;

import edu.arizona.csc536.book_lending_system.domain.Vo.BorrowingBooksVo;
import edu.arizona.csc536.book_lending_system.service.IBorrowingBooksRecordService;
import edu.arizona.csc536.book_lending_system.utils.page.Page;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;

public class BorrowingBooksRecordServiceImpl implements IBorrowingBooksRecordService {
    @Override
    public Page<BorrowingBooksVo> selectAllByPage(int pageNum) {
        return null;
    }

    @Override
    public ArrayList<BorrowingBooksVo> selectAllBorrowRecord(HttpServletRequest request) {
        return null;
    }
}
