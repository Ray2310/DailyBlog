package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.domain.ResponseResult;
import com.blog.domain.entity.Article;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

}
