package edu.arizona.csc536.book_lending_system.domain;

import lombok.Data;

@Data
public class Admin {
    private Integer adminId;

    private String adminName;

    private String adminPwd;

    private String adminEmail;

   }