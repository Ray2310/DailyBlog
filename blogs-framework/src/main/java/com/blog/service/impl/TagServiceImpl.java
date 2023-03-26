package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.injector.methods.SelectById;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.domain.ResponseResult;
import com.blog.domain.dto.TagDto;
import com.blog.domain.dto.TagListDto;
import com.blog.domain.entity.Category;
import com.blog.domain.entity.Tag;
import com.blog.domain.entity.User;
import com.blog.domain.vo.PageVo;
import com.blog.domain.vo.TagVo;
import com.blog.enums.AppHttpCodeEnum;
import com.blog.mapper.TagMapper;
import com.blog.service.TagService;
import com.blog.service.UserService;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.SecurityUtils;
import com.blog.utils.SystemConstants;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 标签表服务接口
 * @author ray2310
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Resource
    private UserService userService;

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

    //todo 新增标签需求   需要在数据库记录中有创建时间、更新时间、创建人、创建人字段
    @Override
    public ResponseResult addTag(TagListDto tagListDto) {
        //1. 接收请求信息,判断信息是否为空
        if(ObjectUtils.isEmpty(tagListDto)){
            return ResponseResult.errorResult(AppHttpCodeEnum.TAG_ERROR);
        }
        //2. 获取标签创建者 、获取创建时间
        Long userId = SecurityUtils.getUserId();
        //3. 将的到的信息转换为tag 存储到数据库中
        Tag tag  = new Tag();
        tag.setName(tagListDto.getName());
        tag.setRemark(tagListDto.getRemark());
        tag.setCreateBy(userId);
        save(tag);
        return ResponseResult.okResult(tag);
    }

    //todo 删除标签需求 需要设置逻辑删除 也就是
    //`del_flag` int DEFAULT '0' COMMENT '删除标志（0代表未删除，1代表已删除）',
    @Override
    public ResponseResult deleteTag(Long id) {
        //1. 从数据库中查找要删除的id
        //2. 修改其中的delFlag = 1
        //返回删除信息
        UpdateWrapper<Tag> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        updateWrapper.set("del_flag",1);
        update(updateWrapper);
        return ResponseResult.okResult();
    }

    //todo 获取需要修改的标签信息
    @Override
    public ResponseResult getTagById(Long id) {
        Tag tag = getById(id);
        TagDto dto = BeanCopyUtils.copyBean(tag, TagDto.class);
        return ResponseResult.okResult(dto);
    }

    //todo 修改信息
    @Override
    public ResponseResult updateTag(TagDto tagDto) {
        System.out.println(tagDto.toString());
        UpdateWrapper<Tag> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",tagDto.getId());
        updateWrapper.set("name",tagDto.getName());
        updateWrapper.set("remark",tagDto.getRemark());
        update(updateWrapper);
        return ResponseResult.okResult();
    }

    //todo 获取所有的标签，不分页的
    @Override
    public ResponseResult listAllTag() {
        //查询出所有没有删除的标签
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Tag::getDelFlag, SystemConstants.TAG_NOTDEL);
        List<Tag> list = list(queryWrapper);
        List<TagVo> tagVos = BeanCopyUtils.copyBeanList(list, TagVo.class);
        return ResponseResult.okResult(tagVos);
    }
}
