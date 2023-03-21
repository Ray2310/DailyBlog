package com.blog.runner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog.domain.entity.Article;
import com.blog.mapper.ArticleMapper;
import com.blog.utils.RedisCache;
import com.blog.utils.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;

@Component
public class ViewCountRunner implements CommandLineRunner {

    @Resource
    private ArticleMapper articleMapper;

    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
    //todo 在其中书写我们需要启动时执行的需求
        //查询博客信息 id viewCount
        List<Article> articles = articleMapper.selectList(null);
        //使用stream流处理
        Map<String, Integer> ViewCountMap = articles.stream().collect(Collectors.toMap(article -> article.getId().toString(), article -> article.getViewCount().intValue()));
        // 存入redis
        redisCache.setCacheMap(SystemConstants.VIEW_COUNT_KEY,ViewCountMap);

    }


}
