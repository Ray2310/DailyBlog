package com.blog.controller;

import com.blog.domain.ResponseResult;
import com.blog.domain.entity.Comment;
import com.blog.service.CommentService;
import com.blog.utils.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {


    @Autowired
    private CommentService commentService;

    //todo 评论列表
    @GetMapping("/commentList")
    public ResponseResult commentList(Long articleId ,Integer pageNum , Integer pageSize){
        return commentService.commentList(SystemConstants.COMMENT_TYPE_ARTICLE,articleId,pageNum,pageSize);
    }

    //todo 添加评论
    @PostMapping
    public ResponseResult addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }

    @GetMapping("/linkCommentList")
    public ResponseResult listCommentList(Integer pageNum , Integer pageSize){
        return commentService.commentList(SystemConstants.COMMENT_TYPE_FRIEND,null,pageNum,pageSize);
    }
}
