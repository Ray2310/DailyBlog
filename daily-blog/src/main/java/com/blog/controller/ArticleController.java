package com.blog.controller;


import com.blog.domain.ResponseResult;
import com.blog.domain.entity.Article;
import com.blog.service.ArticleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Resource
    private ArticleService articleService;

//    @GetMapping("/list")
//    public List<Article> test(){
//        List<Article> list = articleService.list();
//        return list;
//    }


    //todo 查询热门文章
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
        //查询热门文章，然后封装成ResponseResult ，然后返回
        return articleService.hotArticleList();
    }

}
