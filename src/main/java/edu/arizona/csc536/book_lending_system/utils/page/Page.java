package edu.arizona.csc536.book_lending_system.utils.page;

import lombok.Data;

import java.util.List;


@Data
public class Page<T> {  // since many type of object can use Page, User, Book, BookCate...
    private List<T> list;//T type List
    private int pageNum; //current page
    private int pageSize;//size per page
    private int pageCount;//Total Page num
}
