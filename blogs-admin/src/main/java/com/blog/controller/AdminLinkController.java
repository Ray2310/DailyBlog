package com.blog.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.blog.domain.ResponseResult;
import com.blog.domain.entity.Link;
import com.blog.service.LinkService;
import com.blog.utils.SystemConstants;
import org.apache.xmlbeans.SchemaTypeSystem;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Calendar;

@RestController
@RequestMapping("/content/link")
public class AdminLinkController {

    @Resource
    private LinkService linkService;

    @GetMapping("/list")
    public ResponseResult getAllLink(int pageNum ,int pageSize ,String name ,String status){
        return linkService.getAll(pageNum,pageSize,name,status);
    }
    @PostMapping
    public ResponseResult addLink(@RequestBody Link link){
        return ResponseResult.okResult(linkService.save(link));
    }
    @GetMapping("/{id}")
    public ResponseResult getLinkById(@PathVariable Long id){
        Link byId = linkService.getById(id);
        return ResponseResult.okResult(byId);
    }
    @PutMapping
    @Transactional
    public ResponseResult updateLink(@RequestBody Link link){
        linkService.updateById(link);
        return ResponseResult.okResult();
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteLink(@PathVariable Long id){
        UpdateWrapper<Link> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",id);
        wrapper.set("del_flag", SystemConstants.DELETE);
        linkService.update(wrapper);
        return ResponseResult.okResult();
    }

}
