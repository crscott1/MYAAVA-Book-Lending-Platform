package edu.arizona.csc536.book_lending_system.domain;

import lombok.Data;

import java.util.Date;

@Data
public class BorrowingBooks {
    private Integer id;

    private Integer userId;

    private Integer bookId;

    private Date date;

    }