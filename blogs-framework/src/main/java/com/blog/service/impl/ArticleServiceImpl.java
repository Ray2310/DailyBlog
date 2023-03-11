package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.domain.ResponseResult;
import com.blog.domain.entity.Article;
import com.blog.domain.vo.HotArticle;
import com.blog.enums.AppHttpCodeEnum;
import com.blog.mapper.ArticleMapper;
import com.blog.service.ArticleService;

import com.blog.utils.BeanCopyUtils;
import com.blog.utils.SystemConstants;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 文章接口实现
 * @author Ray2310
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {


    //todo 查询热门文章
    /*需求:
    查询出浏览量最高的前10篇文章的信息。 要求展览示文章标题和浏量。八能够让用户自己点击跳转到具体的文章详请进行浏览
    注意 :`不要把草稿展示出来 ，不要把删除的文章查询出来`
     */
    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章 封装返回
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //用 LambdaQueryWrapper 写查询条件
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_PUT);
        queryWrapper.orderByDesc(Article::getViewCount);

        Page<Article> page = new Page(SystemConstants.CURRENT_NOW,SystemConstants.PAGE_SIZE);
        //判空
        if (ObjectUtils.isEmpty(page)){
            return ResponseResult.errorResult(AppHttpCodeEnum.valueOf("暂无热门文章"));
        }
        page(page,queryWrapper);
        List<Article> articles = page.getRecords();
        // 类的赋值拷贝 Article中的某些字段 ---> HotArticle
        //使用BeanUtils进行拷贝
        List<HotArticle> hotArticles = BeanCopyUtils.copyBeanList(articles, HotArticle.class);
        return ResponseResult.okResult(hotArticles);
    }

}
