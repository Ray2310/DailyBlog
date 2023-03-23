package com.blog.controller;

import com.blog.domain.ResponseResult;
import com.blog.domain.dto.AddCommentDto;
import com.blog.domain.entity.Comment;
import com.blog.service.CommentService;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.SystemConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@Api(description = "评论相关接口")
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
    @ApiImplicitParam()
    public ResponseResult addComment(@RequestBody AddCommentDto addCommentDto){
        Comment comment = BeanCopyUtils.copyBean(addCommentDto, Comment.class);
        return commentService.addComment(comment);
    }

    @ApiOperation(value = "友链评论列表",notes = "获取一页友链评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页号"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小")
    })
    @GetMapping("/linkCommentList")
    public ResponseResult listCommentList(Integer pageNum , Integer pageSize){
        return commentService.commentList(SystemConstants.COMMENT_TYPE_FRIEND,null,pageNum,pageSize);
    }
}
