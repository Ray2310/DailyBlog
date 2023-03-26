package com.blog.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.blog.domain.entity.ArticleTag;
import com.blog.mapper.ArticleTagMapper;
import org.springframework.stereotype.Service;

/**
 * @Author 三更  B站： https://space.bilibili.com/663528522
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {
}
