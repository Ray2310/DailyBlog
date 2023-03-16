package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.domain.ResponseResult;
import com.blog.domain.entity.Comment;
import com.blog.domain.entity.LoginUser;
import com.blog.domain.entity.User;
import com.blog.domain.vo.CommentVo;
import com.blog.domain.vo.PageVo;
import com.blog.enums.AppHttpCodeEnum;
import com.blog.exception.SystemException;
import com.blog.mapper.CommentMapper;
import com.blog.service.CommentService;
import com.blog.service.UserService;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.SecurityUtils;
import com.blog.utils.SystemConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.sql.rowset.BaseRowSet;
import java.util.List;
import java.util.Objects;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Resource
    private UserService userService;

    //todo 评论列表
    @Override
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        //根据文章id 所对应的 根评论(root_id = -1)
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //对articleId进行判断
        queryWrapper.eq(Comment::getArticleId,articleId);
        queryWrapper.eq(Comment::getRootId, SystemConstants.ARTICLE_ROOT_COMMENT);
        //分页查询
        Page<Comment> pageN = new Page<>(pageNum,pageSize);
        Page<Comment> page = page(pageN, queryWrapper);
        //封装返回
        List<CommentVo> list = toCommentVoList(page.getRecords());
        //查询所有根评论对应的子评论的集合 ，并且赋值给对应的属性children
        for (CommentVo commentVo : list){
            //查询对应子评论
            List<CommentVo> children = getChildren(commentVo.getId());
            commentVo.setChildren(children);
        }
        return ResponseResult.okResult(new PageVo(list,page.getTotal()));
    }

    //todo 添加评论
    @Override
    public ResponseResult addComment(Comment comment) {
        //评论内容不能为空
        if(!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    //todo 根据根评论的id查询对应的子评论的集合
    private List<CommentVo> getChildren(Long commentId){
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId,commentId);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> list = list(queryWrapper);
        //封装成为CommentVo，然后返回
        return toCommentVoList(list);
    }


    //todo comment 集合 和commentVo 集合的拷贝
    private List<CommentVo> toCommentVoList(List<Comment> list){
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);

        //遍历vo
        for (CommentVo commentVo : commentVos){
            //通过createBy查询用户的昵称并且赋值
            String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
            commentVo.setUsername(nickName);
            //通过 toCommentUserId查询用户ude昵称并赋值
            //如果 toCommentUserId != -1才进行查询
            if (commentVo.getToCommentUserId() != -1){
                String toCommentName = userService.getById(commentVo.getToCommentId()).getNickName();
                commentVo.setToCommentUserName(toCommentName);
            }
        }
        return commentVos;
    }
}
