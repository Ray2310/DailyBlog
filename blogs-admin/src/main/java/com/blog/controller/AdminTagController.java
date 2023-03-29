package com.blog.controller;

import com.blog.domain.ResponseResult;
import com.blog.domain.dto.TagDto;
import com.blog.domain.dto.TagListDto;
import com.blog.domain.vo.PageVo;
import com.blog.service.TagService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

    /**
     *提供标签功能，一个文章可以有多个标签。
     *
     * 在后台需要分页查询标签功能，要求能够根据签名进行分页查询。**后期可以增加备注查询等需求**
     *
     * 注意 ：不要把删除了的标签查询出来
     * @param pageNum 第几页
     * @param pageSize 分页大小
    //     * @param name 标签名
    //     * @param remark 备注
     * @return
     */
    @GetMapping("/list")
    public ResponseResult<PageVo> list(int pageNum, int pageSize, TagListDto tagListDto){

        return tagService.pageTagList(pageNum,pageSize,tagListDto);
    }

    //TODO 新增标签， 测试时需要在数据库记录中有创建时间、更新时间、创建人、创建人字段
    @PostMapping
    public ResponseResult addTag(@RequestBody TagListDto tagListDto){
        return tagService.addTag(tagListDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteTag(@PathVariable Long id){
        return tagService.deleteTag(id);
    }

    @GetMapping("/{id}")
    public ResponseResult getTagById(@PathVariable Long id){
        return tagService.getTagById(id);
    }

    @PutMapping
    public ResponseResult updateTag(@RequestBody TagDto tagDto){
        return tagService.updateTag(tagDto);
    }

}
