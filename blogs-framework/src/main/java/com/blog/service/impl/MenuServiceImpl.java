package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.domain.ResponseResult;
import com.blog.domain.entity.Menu;
import com.blog.domain.entity.RoleMenu;
import com.blog.domain.vo.MenuVo;
import com.blog.domain.vo.MenuTreeVo;
import com.blog.enums.AppHttpCodeEnum;
import com.blog.mapper.MenuMapper;
import com.blog.service.MenuService;
import com.blog.service.RoleMenuService;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.SecurityUtils;
import com.blog.utils.SystemConstants;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 查询角色权限信息
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Resource
    private RoleMenuService roleMenuService;

    //--------------------后端service------------------------------------

    /**
     * 展示菜单列表，不需要进行分页。可以正对菜单名做模糊查询，也可以根据菜单状态进行查询。
     * 菜单要按照父菜单id 和 OrderNum进行排序
     * @param status 状态
     * @param menuName 菜单名
     * @return
     */
    @Override
    public ResponseResult getAll(String status, String menuName) {

        //1. 按要求查询出所有的菜单
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getDelFlag,SystemConstants.NOT_DELETE);
        wrapper.like(Objects.nonNull(menuName),Menu::getMenuName,menuName);
        wrapper.like(Objects.nonNull(status),Menu::getStatus,status);
        //2. 按照父菜单 和 orderNum进行排序
        wrapper.orderByAsc(Menu::getParentId);
        wrapper.orderByAsc(Menu::getOrderNum);
        List<Menu> list = list(wrapper);
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(list, MenuVo.class);
        //3. 封装为Vo，然后在放到集合中返回
        return ResponseResult.okResult(menuVos);
    }

    /**
     * 新增菜单 或者按钮
     * @param menu 前端传入新增信息
     * @return
     */
    @Override
    public ResponseResult addMenu(Menu menu) {
        if(ObjectUtils.isEmpty(menu.getIcon())){
           return ResponseResult.errorResult(AppHttpCodeEnum.ICON_NOT_NULL);
        }
        if(ObjectUtils.isEmpty(menu.getMenuName())){
            return ResponseResult.errorResult(AppHttpCodeEnum.MENU_NAME_NOT_NULL);
        }
        if(ObjectUtils.isEmpty(menu.getPath())){
            return ResponseResult.errorResult(AppHttpCodeEnum.PATH_NOT_NULL);
        }
        save(menu);
        return ResponseResult.okResult();
    }

    /**
     * 根据id查询对应信息
     * @param id 菜单id
     * @return
     */
    @Override
    public ResponseResult selectById(Long id) {
        Menu menu = getById(id);
        MenuVo menuVo = BeanCopyUtils.copyBean(menu, MenuVo.class);
        return ResponseResult.okResult(menuVo);
    }

    /**
     * 更新菜单实现
     *   不能让菜单的父菜单 == 菜单本身
     * @param menu 菜单信息
     * @return
     */
    @Override
    public ResponseResult updateMenu(Menu menu) {
        System.out.println(menu);
        if(menu.getParentId().equals(menu.getId())){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARENT_NOT_SELF);
        }
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getId,menu.getId());
        remove(wrapper);
        save(menu);
        return ResponseResult.okResult();
    }

    /**
     * 逻辑删除菜单
     *   不能删除有子菜单的父菜单
     * @param id 菜单id
     * @return
     */
    @Override
    public ResponseResult deleteById(Long id) {
        //查询是否有父菜单
        List<Menu> list = list();
        for(Menu children: list) {
            if (children.getParentId().equals(id)) {
                System.out.println("不能删除！");
                return ResponseResult.errorResult(AppHttpCodeEnum.CHILDREN_NOT_NULL);
            }
        }
        UpdateWrapper<Menu> wrapper= new UpdateWrapper<>();
        wrapper.eq("id",id);
        wrapper.set("del_flag",SystemConstants.DELETE);
        update(wrapper);
        return ResponseResult.okResult();
    }

    /**
     * 获取菜单列表
     * @param menu
     * @return
     */
    @Override
    public List<Menu> selectMenuList(Menu menu) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        //menuName模糊查询
        queryWrapper.like(StringUtils.hasText(menu.getMenuName()),Menu::getMenuName,menu.getMenuName());
        queryWrapper.eq(StringUtils.hasText(menu.getStatus()),Menu::getStatus,menu.getStatus());
        //排序 parent_id和order_num
        queryWrapper.orderByAsc(Menu::getParentId,Menu::getOrderNum);
        List<Menu> menus = list(queryWrapper);
        return menus;
    }

    /**
     * 根据id获取菜单id树
     * @param id
     * @return
     */
    @Override
    public List<Long> selectMenuListByRoleId(Long id) {
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId,id);
        List<RoleMenu> list = roleMenuService.list(wrapper);    //对应的id集合
        List<Long> menus = new ArrayList<>();
        for(RoleMenu menu : list){
            Long menuId = menu.getMenuId();
            menus.add(menuId);
        }
        return menus;
    }

    //--------------------前端service------------------------------------
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
    public List<Menu> buildMenuTree(List<Menu> menus, Long parentId) {
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
