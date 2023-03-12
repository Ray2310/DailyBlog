package com.blog.controller;


import com.blog.domain.ResponseResult;
import com.blog.domain.entity.Article;
import com.blog.service.ArticleService;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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



}
