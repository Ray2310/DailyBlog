package com.blog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 接口文档中要去响应回去的字段
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotArticle {
    //文章id
    private Long id;
    //文章标题
    private String title;
    //访问量
    private Long viewCount;
}
