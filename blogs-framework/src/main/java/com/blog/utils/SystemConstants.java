package com.blog.utils;

public class SystemConstants {
    //文章已发布
    public static final int ARTICLE_STATUS_PUT = 0;
    //文章未发布
    public static final int ARTICLE_STATUS_NOT_PUT = 1;

    //分页数据当前页
    public static final int CURRENT_NOW = 1;
    //分页数据的分页大小
    public static final int PAGE_SIZE = 10;

    //文章的分类状态 0 正常 1 禁用
    public static final String ARTICLE_CATEGORY_STATUS = "0";

    //友链状态
    public static final String LINK_STATUS_NORMAL = "0";

    //封装login时的key的前缀
    public static final String LOGIN_KEY_PREFIX = "bloglogin: ";
    //文章根评论
    public static final int ARTICLE_ROOT_COMMENT = -1;

    //评论列表类型
    public static final String COMMENT_TYPE_ARTICLE = "0";
    //友链评论
    public static final String COMMENT_TYPE_FRIEND = "1";



}
