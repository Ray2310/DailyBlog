package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.domain.ResponseResult;
import com.blog.domain.entity.Comment;

public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String s, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}
