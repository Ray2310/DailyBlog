package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.domain.entity.Menu;
import com.blog.mapper.MenuMapper;
import com.blog.service.MenuService;
import com.blog.utils.SecurityUtils;
import com.blog.utils.SystemConstants;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 查询角色权限信息
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {


    /**
     * 根据用户id查询权限信息<br>
     * 如果用户id为1 代表管理员 ，menus中需要有所有菜单类型为c或者F的，状态为，未被删除的权限
     * @param id 用户id
     * @return 返回该用户权限集合
     */
    @Override
    public List<String> selectPermsByUserId(Long id) {
        //管理员返回所有的权限
        if(SecurityUtils.isAdmin()){
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType, SystemConstants.MENU_TYPE_C,SystemConstants.MENU_TYPE_F);  //菜单类型为C(菜单) 和 F（按钮）
            wrapper.eq(Menu::getStatus,SystemConstants.LINK_STATUS_NORMAL);//状态正常
            List<Menu> menus = list(wrapper);
            List<String> Perms = menus.stream().map(Menu::getPerms).collect(Collectors.toList());
            return Perms;
        }
        // 反之返回相对应用户所具有的权限

        //1. 先查询sys_user_roles查询用户角色id
        //2. 查到角色id之后再到sys_roles_menu查询对应的权限id(menuId)
        //3. 最后通过menuId查询对应的menu信息
        //4. 封装返回
        return getBaseMapper().selectPermsByUserId(id);
    }

    /**
     * 根据用户id查询相关的权限菜单信息
     * @param userId 用户id
     * @return 返回符合要求的val
     */
    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = null;    //封装Menu

        //如果是管理员，返回所有的菜单
        if(SecurityUtils.isAdmin()){
            menus = menuMapper.selectAllRouterMenu();
        }
        else{
            //如果不是管理员 那么返回对应有权限的菜单按钮
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }
        //通过上述得到的Menu无法得到我们需要的父子菜单管理，所以我们需要通过（buildMenuTree）来构建这种父子菜单关系
        //构建Tree
        //先找出第一层的菜单，接着找到他们的子菜单，然后就可以设置children属性中
        List<Menu> menuTree = buildMenuTree(menus,0L);
        return menuTree;
    }

    /**
     * 构建菜单的父子菜单关系
     * <br>
     * 找到父menu对应的子menu ，然后将他们放到一个集合中，最后设置给children这个字段
     * @param menus 传入的方法
     * @param parentId 父菜单id
     * @return
     */
    private List<Menu> buildMenuTree(List<Menu> menus, Long parentId) {
        List<Menu> menuList = menus.stream()//通过这样筛选就可以得到第一层级的menu
                .filter(menu -> menu.getParentId().equals(parentId))
                /*
                传入的menus是得到了第一层的menus（相当于Tree中的root节点），然后需要设置他的子菜单（left 和 right）
                因为menus中有所有的菜单(父子都有)， 所以我们在设置left和right时需要找到他们的子菜单
                所以就调用getChildren找到left或者right的子菜单，然后得到之后再设置给他们
                 */
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuList;
    }

    /**
     * 获取传入参数的子menu的list集合
     *  在menus中找打当前传入的menu的子菜单
     * @param menu 获取它的子菜单
     * @param menus 全部菜单集合
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus){
        List<Menu> children = menus.stream()
                .filter(menu1 -> menu1.getParentId().equals(menu.getId()))
                .map(menu1 -> menu1.setChildren(getChildren(menu1,menus)))  //如果有很多的子菜单，那么就可以用到这个递归
                .collect(Collectors.toList());
        return children;
    }
}
