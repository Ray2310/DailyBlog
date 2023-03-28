package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.blog.domain.entity.ArticleTag;
import com.blog.mapper.ArticleTagMapper;
import com.blog.service.ArticleTagService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author 三更  B站： https://space.bilibili.com/663528522
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

    @Override
    public List<Long> selectByArticleId(Long id){
        List<ArticleTag> list = list();
        List<Long> ids = new ArrayList<>();
        for(ArticleTag articleTag : list){
            if(articleTag.getArticleId().equals(id)){
                Long tagId = articleTag.getTagId();
                ids.add(tagId);
            }
        }
        return ids;
    }
    @Override
    public void deleteByArticleId(Long id,List<Long> ids){
        LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleTag::getArticleId,id);
        remove(wrapper);

    }
}
