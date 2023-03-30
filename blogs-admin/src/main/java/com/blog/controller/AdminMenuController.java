package com.blog.controller;

import com.blog.domain.ResponseResult;
import com.blog.domain.entity.Menu;
import com.blog.domain.vo.MenuTreeVo;
import com.blog.service.MenuService;
import com.blog.service.impl.MenuServiceImpl;
import com.blog.utils.SystemConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/system/menu")
public class AdminMenuController {


    @Resource
    private MenuService menuService;
    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/treeselect")
    public ResponseResult treeselect() {
        //复用之前的selectMenuList方法。方法需要参数，参数可以用来进行条件查询，而这个方法不需要条件，所以直接new Menu()传入
        List<Menu> menus = menuService.selectMenuList(new Menu());
        List<MenuTreeVo> options =  SystemConverter.buildMenuSelectTree(menus);
        return ResponseResult.okResult(options);
    }
}
