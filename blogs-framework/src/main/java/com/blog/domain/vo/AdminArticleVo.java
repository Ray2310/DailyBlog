package com.blog.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.blog.domain.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AdminArticleVo {
    private Long id;
    //标题
    private String title;
    //缩略图
    private String thumbnail;
    //是否置顶（0否，1是）
    private String isTop;
    //是否允许评论 1是，0否
    private String isComment;
    //文章内容
    private String content;

    //标签信息
    private List<Long> tags;
    //所属分类id
    private Long categoryId;
    //文章摘要
    private String summary;
    //状态（0已发布，1草稿）
    private String status;


    /**
     * 后台系统需要
     */
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;
    //访问量
    private Long viewCount;
}

