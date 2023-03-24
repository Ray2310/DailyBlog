package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.domain.ResponseResult;
import com.blog.domain.dto.TagListDto;
import com.blog.domain.entity.Tag;
import com.blog.domain.vo.PageVo;


import java.util.List;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2022-07-19 22:33:38
 */
public interface TagService extends IService<Tag> {

    ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto);
//
//    List<TagVo> listAllTag();
}

