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
@RequestMapping("/content")
public class AdminArticleController {

    @Resource
    private CategoryService categoryService;
    @Resource
    private TagService tagService;

    @Resource
    private ArticleService articleService;

    //todo 获取所有分类信息
    @GetMapping("/category/listAllCategory")
    public ResponseResult listAllCategory(){
        return categoryService.listAllCategory();
    }

    //todo 获取所有的标签信息

    @GetMapping("/tag/listAllTag")
    public ResponseResult listAllTag(){
        return tagService.listAllTag();
    }
    @PostMapping("/article")
    public ResponseResult writeArticle(@RequestBody ArticleVo articleVo){
        return articleService.writeArticle(articleVo);
    }


    @GetMapping("/article/list")
    public ResponseResult articleList(int pageNum, int pageSize, ArticleSummaryDto articleSummary){
        return articleService.getAllArticle(pageNum,pageSize,articleSummary);
    }

}
