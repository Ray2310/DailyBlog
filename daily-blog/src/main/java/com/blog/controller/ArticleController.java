package com.blog.controller;


import com.blog.domain.ResponseResult;
import com.blog.domain.entity.Article;
import com.blog.service.ArticleService;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Resource
    private ArticleService articleService;


    //todo 查询热门文章
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList(){
        return articleService.hotArticleList();
    }

    //todo 文章分页
    @GetMapping("/articleList")
    //如果在请求路径后面直接 /+值的 需要使用 @PathVariable
    //如果是从请求体中获取出来的就需要加上 @RequestBody
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId){

        return articleService.articleList(pageNum,pageSize,categoryId);
    }
    //todo 查询文章详情
    @GetMapping("/{id}")
    public ResponseResult getArticleDetails(@PathVariable("id") Long id){
        return articleService.getArticleDetails(id);
    }

    @PutMapping("updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);
    }

}
