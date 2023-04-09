package com.blog.controller;

import com.blog.domain.ResponseResult;
import com.blog.domain.dto.TagDto;
import com.blog.domain.dto.TagListDto;
import com.blog.domain.vo.PageVo;
import com.blog.service.TagService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 标签相关接口
 * @author Ray2310
 */
@RestController
@RequestMapping("/content/tag")
public class AdminTagController {




    @Resource
    private TagService tagService;


    //todo 获取所有的标签信息
    @GetMapping("/listAllTag")
    public ResponseResult listAllTag(){
        return tagService.listAllTag();
    }

    //todo 分页获取所有标签
    @GetMapping("/list")
    public ResponseResult<PageVo> list(int pageNum, int pageSize, TagListDto tagListDto){

        return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }

    //TODO 新增标签， 测试时需要在数据库记录中有创建时间、更新时间、创建人、创建人字段
    @PostMapping
    public ResponseResult addTag(@RequestBody TagListDto tagListDto){
        return tagService.addTag(tagListDto);
    }

    //todo 删除标签
    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable Long id){
        return tagService.deleteTag(id);
    }

    //todo 根据id 获取标签
    @GetMapping("/{id}")
    public ResponseResult getTagById(@PathVariable Long id){
        return tagService.getTagById(id);
    }

    //todo 更新标签
    @PutMapping
    public ResponseResult updateTag(@RequestBody TagDto tagDto){
        return tagService.updateTag(tagDto);
    }

}
