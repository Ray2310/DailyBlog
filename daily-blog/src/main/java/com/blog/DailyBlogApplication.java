package com.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@MapperScan("com.blog.mapper")
@SpringBootApplication
public class DailyBlogApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(DailyBlogApplication.class, args);
    }
}
