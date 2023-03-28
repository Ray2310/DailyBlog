package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.domain.ResponseResult;
import com.blog.domain.dto.ArticleSummaryDto;
import com.blog.domain.entity.Article;
import com.blog.domain.entity.ArticleTag;
import com.blog.domain.entity.Category;
import com.blog.domain.vo.*;
import com.blog.enums.AppHttpCodeEnum;
import com.blog.mapper.ArticleMapper;
import com.blog.service.ArticleService;

import com.blog.service.ArticleTagService;
import com.blog.service.CategoryService;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.RedisCache;
import com.blog.utils.SystemConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 文章接口实现
 * @author Ray2310
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Resource
    private ArticleTagService articleTagService;

    @Resource
    private CategoryService categoryService;

    @Autowired
    private RedisCache redisCache;

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

        Page<Article> pageN = new Page<>(SystemConstants.CURRENT_NOW,SystemConstants.PAGE_SIZE);
        //判空
        if (ObjectUtils.isEmpty(pageN)){
            return ResponseResult.errorResult(AppHttpCodeEnum.valueOf("暂无热门文章"));
        }
        List<Article> articles = page(pageN, queryWrapper).getRecords();
        for (Article article: articles){
            Integer viewCount = redisCache.getCacheMapValue(SystemConstants.VIEW_COUNT_KEY,article.getId().toString());
            article.setViewCount(viewCount.longValue());
        }
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
            //设置从缓存中查询文章的浏览量
            Integer viewCount = redisCache.getCacheMapValue(SystemConstants.VIEW_COUNT_KEY,article.getId().toString());
            article.setViewCount(viewCount.longValue());
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
        /**
         * 从redis中获取viewCount
         */
        Integer viewCount = redisCache.getCacheMapValue(SystemConstants.VIEW_COUNT_KEY, id.toString());
        article.setViewCount(viewCount.longValue());
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

    //todo 实现更新博客浏览量
    @Override
    public ResponseResult updateViewCount(Long id) {
        //更新redis中对应博客id的浏览量
        redisCache.incrementCacheMapValue(SystemConstants.VIEW_COUNT_KEY,id.toString(),1);
        return ResponseResult.okResult() ;
    }

    //todo 后台写文章详情
    @Override
    @Transactional  //添加事务
    public ResponseResult writeArticle(ArticleVo articleVo) {
        Article article = BeanCopyUtils.copyBean(articleVo, Article.class);
        save(article);
        //将标签id的集合存入标签文章集合表中
        List<ArticleTag> collect = articleVo.getTags().stream().map(tagId -> new ArticleTag(article.getId(), tagId)).collect(Collectors.toList());
        articleTagService.saveBatch(collect);
       return ResponseResult.okResult();
    }


    //todo 后台博文获取所有博文
    @Override
    public ResponseResult getAllArticle(int pageNum, int pageSize, ArticleSummaryDto articleSummary) {
        LambdaQueryWrapper<Article> queryWrapper = new LambdaQueryWrapper<>();
        //如果有categoryId ，那么查询和传入的就需要相同
        //进行模糊查询
        queryWrapper.like(Objects.nonNull(articleSummary.getTitle()),Article::getTitle,articleSummary.getTitle());
        queryWrapper.like(Objects.nonNull(articleSummary.getSummary()),Article::getTitle,articleSummary.getTitle());
        //状态 ： 正式发布
        queryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_PUT);
        //置顶的文章（对isTop进行排序）
        //分页查询
        Page<Article> pageN = new Page<>(pageNum,pageSize);
        Page<Article> page = page(pageN, queryWrapper);
        //查询categoryName ，因为我们封装的是categoryName，但是查询出来的确实categoryId，所以需要在进行查询
        List<Article> articles = page.getRecords();
        List<AdminArticleVo> articleVos = BeanCopyUtils.copyBeanList(articles, AdminArticleVo.class);
        PageVo pageVo = new PageVo(articleVos, page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    /**
     * 后台更新博文前获取博文所有信息
     * @param id 文章id
     * @return 返回所有信息
     */
    @Override
    public ResponseResult updateBefore(Long id) {
        //1. 首先根据id获取所有信息
        AdminArticleVo articleVo = BeanCopyUtils.copyBean(getById(id), AdminArticleVo.class);
        //2. 获取所有的标签id，然后找出我们需要的
        List<Long> ids = articleTagService.selectByArticleId(id);
        articleVo.setTags(ids);
        //2. 根据文章id 获取其所有的标签tags
        System.out.println(articleVo);
        //3. 封装返回
        return ResponseResult.okResult(articleVo);
    }

    /**
     * 首先更新请求
     * @param articleVo 需要更新的文章
     * @return
     */
    @Override
    public ResponseResult updateNow(AdminArticleVo articleVo) {
        UpdateWrapper<Article> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",articleVo.getId());
        Article article = BeanCopyUtils.copyBean(articleVo, Article.class);
        wrapper.set("id",articleVo.getId());
        wrapper.set("title",articleVo.getTitle());
        wrapper.set("content",articleVo.getContent());
        wrapper.set("summary",articleVo.getSummary());
        wrapper.set("category_id",articleVo.getCategoryId());
        wrapper.set("thumbnail",articleVo.getThumbnail());
        wrapper.set("is_top",articleVo.getIsTop());
        wrapper.set("status",articleVo.getStatus());
        wrapper.set("view_count",articleVo.getViewCount());
        wrapper.set("is_comment",articleVo.getIsComment());
        wrapper.set("update_by",articleVo.getUpdateBy());
        wrapper.set("update_time",articleVo.getUpdateTime());
        update(wrapper);
        //先删除对应的映射关系
        articleTagService.deleteByArticleId(articleVo.getId(),articleVo.getTags());
        //然后重新添加新增的标签映射关系
        List<ArticleTag> collect = articleVo.getTags().stream().map(tagId -> new ArticleTag(article.getId(), tagId)).collect(Collectors.toList());
        articleTagService.saveBatch(collect);
        return ResponseResult.okResult();
    }

    /**
     * 根据id逻辑删除文章
     * @param id 文章id
     * @return 删除结果
     */
    @Override
    public ResponseResult deleteArticleById(Long id) {
        UpdateWrapper<Article> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",id);
        wrapper.set("del_flag",SystemConstants.DELETE);
        update(wrapper);
        return ResponseResult.okResult();
    }
}
