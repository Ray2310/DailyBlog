package com.blog.controller;

import com.blog.domain.ResponseResult;
import com.blog.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/category")
@RestController
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    //todo 分类请求
    @GetMapping("/getCategoryList")
    public ResponseResult getCategoryList(){
        return categoryService.getCategoryList();
    }
}
