package com.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@MapperScan("com.blog.mapper")
@SpringBootApplication
@EnableScheduling   //定时任务开启
@EnableSwagger2
public class DailyBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run (DailyBlogApplication.class, args);
    }
}
