package com.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog.domain.ResponseResult;
import com.blog.domain.entity.Menu;
import com.blog.domain.entity.RoleMenu;
import com.blog.domain.vo.MenuTreeVo;
import com.blog.domain.vo.RoleMenuTreeSelectVo;
import com.blog.service.MenuService;
import com.blog.service.RoleMenuService;
import com.blog.service.RoleService;
import com.blog.utils.SystemConverter;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/system/menu")
public class AdminMenuController {

    @Resource
    private RoleMenuService roleMenuService;
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

    /**
     * 回显对应id的角色的权限树
     * @param id
     * @return
     */
    @GetMapping("/roleMenuTreeselect/{id}")
    public ResponseResult roleMenuTreeSelect(@PathVariable Long id){

        //1. 先查寻对应角色的权限id集合， 然后，将id集合一一对应查询出对应的权限集合
        List<Menu> menus = menuService.selectMenuList(new Menu());
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId,id);
        List<RoleMenu> list = roleMenuService.list(wrapper);    //对应的id集合
        List<Long> ids = new ArrayList<>();
        for(RoleMenu menu : list){
            Long menuId = menu.getMenuId();
            ids.add(menuId);
        }
        List<MenuTreeVo> menuTreeVos = SystemConverter.buildMenuSelectTree(menus);
        RoleMenuTreeSelectVo vo = new RoleMenuTreeSelectVo(ids, menuTreeVos);
        return ResponseResult.okResult(vo);
    }

}
