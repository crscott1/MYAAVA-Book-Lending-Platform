package edu.arizona.csc536.book_lending_system.domain.Vo;

import lombok.Data;

@Data
public class BookVo {
    private Integer bookId;

    private String bookName;

    private String bookAuthor;

    private String bookPublish;

    private String isExist;
}
