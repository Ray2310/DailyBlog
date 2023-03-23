package com.blog.controller;

import com.blog.domain.ResponseResult;
import com.blog.domain.entity.LoginUser;
import com.blog.domain.entity.Menu;
import com.blog.domain.entity.User;
import com.blog.domain.vo.AdminUserInfoVo;
import com.blog.domain.vo.RoutersVo;
import com.blog.domain.vo.UserInfoVo;
import com.blog.service.MenuService;
import com.blog.service.RoleService;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.SecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class UserController {
    @Resource
    private RoleService roleService;

    @Resource
    private MenuService menuService;

    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        //1. 查询当前登陆的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //2. 根据用户id查询权限
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        // 根据id查询角色信息
        List<String> roleKeyList = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());
        //3. 封装 返回
        User user = loginUser.getUser();
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms,roleKeyList,userInfoVo);

        return ResponseResult.okResult(adminUserInfoVo);
    }

    @GetMapping("/getRouters")
    public ResponseResult<RoutersVo> getRouters(){
        Long userId = SecurityUtils.getUserId();
        //查询menu 结果是tree形状
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        RoutersVo routersVo = new RoutersVo(menus);
        //封装返回
        return ResponseResult.okResult(routersVo);
    }


}
