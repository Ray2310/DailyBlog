package com.blog.controller;

import com.blog.domain.ResponseResult;
import com.blog.domain.dto.TagListDto;
import com.blog.domain.vo.PageVo;
import com.blog.service.TagService;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 标签请求
 */
@RestController
@RequestMapping("/content/tag")
public class TagController {

    @Resource
    private TagService tagService;

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

}
