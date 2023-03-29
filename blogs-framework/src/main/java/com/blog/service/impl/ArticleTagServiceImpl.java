package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.domain.entity.ArticleTag;
import com.blog.mapper.ArticleTagMapper;
import com.blog.service.ArticleTagService;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * 文章标签类业务实现类
 */
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag> implements ArticleTagService {

    //todo 根据文章id查询对应的标签
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
    //todo 根据文章id删除对应的标签
    @Override
    public void deleteByArticleId(Long id,List<Long> ids){
        LambdaQueryWrapper<ArticleTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleTag::getArticleId,id);
        remove(wrapper);

    }
}
