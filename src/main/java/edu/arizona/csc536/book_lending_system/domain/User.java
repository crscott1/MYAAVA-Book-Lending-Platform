package edu.arizona.csc536.book_lending_system.domain;

import lombok.Data;

@Data
public class User {
    private Integer userId;

    private String userName;

    private String userPwd;

    private String userEmail;

   }