package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.domain.ResponseResult;
import com.blog.domain.entity.Article;
import com.blog.domain.entity.Category;
import com.blog.domain.vo.ArticleDetailVo;
import com.blog.domain.vo.ArticleListVo;
import com.blog.domain.vo.HotArticle;
import com.blog.domain.vo.PageVo;
import com.blog.enums.AppHttpCodeEnum;
import com.blog.mapper.ArticleMapper;
import com.blog.service.ArticleService;

import com.blog.service.CategoryService;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.SystemConstants;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * 文章接口实现
 * @author Ray2310
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource
    private CategoryService categoryService;

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

        Page<Article> pageN = new Page(SystemConstants.CURRENT_NOW,SystemConstants.PAGE_SIZE);
        //判空
        if (ObjectUtils.isEmpty(pageN)){
            return ResponseResult.errorResult(AppHttpCodeEnum.valueOf("暂无热门文章"));
        }
        List<Article> articles = page(pageN, queryWrapper).getRecords();
        // 类的赋值拷贝 Article中的某些字段 ---> HotArticle
        //使用BeanUtils进行拷贝
        List<HotArticle> hotArticles = BeanCopyUtils.copyBeanList(articles, HotArticle.class);
        return ResponseResult.okResult(hotArticles);
    }



    //todo 文章分页
    /*
    在首页查询文章页面都有文章列表  ，首页 ：查询所有文章
    分类页面： 查询对应分类的文章列表
    要求 ：：1. 只能查询正式发布的文章 2. 置顶文章要显示在最前面
     */
    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper();
        //如果有categoryId ，那么查询和传入的就需要相同
        queryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0,Article::getCategoryId,categoryId);
        //状态 ： 正式发布
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_PUT);
        //置顶的文章（对isTop进行排序）
        queryWrapper.orderByDesc(Article::getIsTop);
        //分页查询
        Page<Article> pageN = new Page<>(pageNum,pageSize);
        Page<Article> page = page(pageN, queryWrapper);
        //查询categoryName ，因为我们封装的是categoryName，但是查询出来的确实categoryId，所以需要在进行查询
        List<Article> records = page.getRecords();

        for (Article article  : records){
            Category category = categoryService.getById(article.getCategoryId());
            article.setCategoryName(category.getName());
        }

        //封装查询结果
        List<ArticleListVo> articleListVo = BeanCopyUtils.copyBeanList(records, ArticleListVo.class);
        PageVo pageVo = new PageVo(articleListVo, page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    //todo 查询文章详情
    /*
    要在文章列表页面点击阅读全文时能够跳转到文章详情页面 ，可以让用户阅读文章正文
    要求： 1. 要在文章详情中展示其分类名
     */
    @Override
    public ResponseResult getArticleDetails(Long id) {
        //根据文章id查询文章
        Article article = getById(id);
        //转换成vo格式
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if(category == null){
            return ResponseResult.okResult(articleDetailVo);
        }
        articleDetailVo.setCategoryName(category.getName());
        //封装响应，返回
        return ResponseResult.okResult(articleDetailVo);
    }

}
