package com.blog.controller;

import com.blog.domain.ResponseResult;
import com.blog.domain.dto.ArticleSummaryDto;
import com.blog.domain.vo.ArticleVo;
import com.blog.service.ArticleService;
import com.blog.service.CategoryService;
import com.blog.service.TagService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 写文章相关接口
 */
@RestController
@RequestMapping("/content/article")
public class AdminArticleController {

    @Resource
    private ArticleService articleService;

    //todo 写博文接口
    @PostMapping
    public ResponseResult writeArticle(@RequestBody ArticleVo articleVo){
        return articleService.writeArticle(articleVo);
    }
    //todo 展示所有博文
    @GetMapping("/list")
    public ResponseResult articleList(int pageNum, int pageSize, ArticleSummaryDto articleSummary){
        return articleService.getAllArticle(pageNum,pageSize,articleSummary);
    }



}
