package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.domain.ResponseResult;
import com.blog.domain.dto.ArticleSummaryDto;
import com.blog.domain.entity.Article;
import com.blog.domain.vo.ArticleVo;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetails(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult writeArticle(ArticleVo articleVo);

    ResponseResult getAllArticle(int pageNum, int pageSize, ArticleSummaryDto articleSummary);

}
