package com.blog.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.blog.annotation.SystemLog;
import com.blog.domain.ResponseResult;
import com.blog.domain.entity.Link;
import com.blog.service.LinkService;
import com.blog.utils.SystemConstants;
import org.apache.xmlbeans.SchemaTypeSystem;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Calendar;

/**
 * 后台友链相关接口
 * @author Ray2310
 */
@RestController
@RequestMapping("/content/link")
public class AdminLinkController {

    @Resource
    private LinkService linkService;

    //todo 分页展示 友链列表
    @SystemLog(businessName="获取友链信息")
    @GetMapping("/list")
    public ResponseResult getAllLink(int pageNum ,int pageSize ,String name ,String status){
        return linkService.getAll(pageNum,pageSize,name,status);
    }
    //todo 新增友链数据
    @PostMapping
    public ResponseResult addLink(@RequestBody Link link){
        return ResponseResult.okResult(linkService.save(link));
    }
    //todo 根据id获取友链数据
    @GetMapping("/{id}")
    public ResponseResult getLinkById(@PathVariable Long id){
        Link byId = linkService.getById(id);
        return ResponseResult.okResult(byId);
    }

    //todo 更新友链数据
    @PutMapping
    public ResponseResult updateLink(@RequestBody Link link){
        linkService.updateById(link);
        return ResponseResult.okResult();
    }
    //todo 删除友链
    @DeleteMapping("/{id}")
    public ResponseResult deleteLink(@PathVariable Long id){
        UpdateWrapper<Link> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",id);
        wrapper.set("del_flag", SystemConstants.DELETE);
        linkService.update(wrapper);
        return ResponseResult.okResult();
    }

}
