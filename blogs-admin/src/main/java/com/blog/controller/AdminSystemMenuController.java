package com.blog.controller;

import com.blog.domain.ResponseResult;
import com.blog.domain.entity.Menu;
import com.blog.service.MenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/system/menu")
public class AdminSystemMenuController {

    @Resource
    private MenuService menuService;

    //todo 菜单列表
    @GetMapping("/list")
    public ResponseResult getAll(String status,String menuName){
        return menuService.getAll(status,menuName);
    }

    //todo 新增菜单
    @PostMapping
    public ResponseResult addMenu(@RequestBody Menu menu){
        return menuService.addMenu(menu);
    }

    //todo 根据id查询对应菜单
    @GetMapping("/{id}")
    public ResponseResult selectById(@PathVariable Long id){
        return menuService.selectById(id);
    }

    @PutMapping
    public ResponseResult updateMenu(@RequestBody Menu menu){
        return menuService.updateMenu(menu);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteById(@PathVariable Long id){
        return menuService.deleteById(id);
    }


}
