package edu.arizona.csc536.book_lending_system.controller;

import edu.arizona.csc536.book_lending_system.service.IBorrowingBooksRecordService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
public class BorrowingController {

    @Resource
    private IBorrowingBooksRecordService borrowingBooksRecordService;
}
