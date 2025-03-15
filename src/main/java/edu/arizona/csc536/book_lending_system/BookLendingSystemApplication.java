package edu.arizona.csc536.book_lending_system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class BookLendingSystemApplication {



    public static void main(String[] args) {
        SpringApplication.run(BookLendingSystemApplication.class, args);
    }

}
