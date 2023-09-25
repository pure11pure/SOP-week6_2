package com.example.week6_2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@EnableCaching
@SpringBootApplication
public class Week62Application {

    public static void main(String[] args) {
        SpringApplication.run(Week62Application.class, args);
    }

}
