package com.blog.controller;

import com.blog.domain.ResponseResult;
import com.blog.domain.dto.ArticleSummaryDto;
import com.blog.domain.vo.AdminArticleVo;
import com.blog.domain.vo.ArticleVo;
import com.blog.service.ArticleService;
import com.blog.service.CategoryService;
import com.blog.service.TagService;
import org.apache.commons.collections4.Put;
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

    //todo 获取要更新的博文
    @GetMapping("/{id}")
    public ResponseResult updateBefore(@PathVariable Long id){
        return articleService.updateBefore(id);
    }

    //todo 更新文章
    @PutMapping
    public ResponseResult updateNow(@RequestBody AdminArticleVo articleVo){
        return articleService.updateNow(articleVo);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteArticleById(@PathVariable Long id){
        return articleService.deleteArticleById(id);
    }

}
