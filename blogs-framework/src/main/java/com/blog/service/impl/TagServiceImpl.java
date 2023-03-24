package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.domain.ResponseResult;
import com.blog.domain.dto.TagListDto;
import com.blog.domain.entity.Tag;
import com.blog.domain.vo.PageVo;
import com.blog.mapper.TagMapper;
import com.blog.service.TagService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * 标签表服务接口
 * @author ray2310
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {


    //实现分页tag列表
    @Override
    public ResponseResult<PageVo> pageTagList(Integer pageNum, Integer pageSize, TagListDto tagListDto) {
        //分页查询
        Page<Tag> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        LambdaQueryWrapper<Tag> wrapper = new LambdaQueryWrapper<>();
        //如果他们有值 ，那么就会调用这个方法， 如果没有就不会调用
        wrapper.eq(StringUtils.hasText(tagListDto.getName()),Tag::getName,tagListDto.getName());
        wrapper.eq(StringUtils.hasText(tagListDto.getRemark()),Tag::getRemark,tagListDto.getRemark());
        page(page,wrapper);
        PageVo pageVo = new PageVo(page.getRecords(),page.getTotal());
        //封装数据返回
        return ResponseResult.okResult(pageVo);
    }
}
