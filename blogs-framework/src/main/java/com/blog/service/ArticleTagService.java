package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.domain.entity.ArticleTag;

import java.util.List;

public interface ArticleTagService extends IService<ArticleTag> {
    List<Long> selectByArticleId(Long id);



    void deleteByArticleId(Long id, List<Long> ids);
}