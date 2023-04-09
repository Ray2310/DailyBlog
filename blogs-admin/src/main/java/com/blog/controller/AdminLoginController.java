package com.blog.controller;

import com.blog.annotation.SystemLog;
import com.blog.domain.ResponseResult;
import com.blog.domain.entity.User;
import com.blog.enums.AppHttpCodeEnum;
import com.blog.exception.SystemException;
import com.blog.service.AdminLoginService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 后台用户登录登出相关接口
 * @author Ray2310
 */
@RestController
@RequestMapping("/user")
public class AdminLoginController {
    @Resource
    private AdminLoginService loginService;

    //todo 登录接口
    @PostMapping("/login")
    @SystemLog(businessName="用户登录接口")
    public ResponseResult login(@RequestBody User user){
        if (!StringUtils.hasText(user.getUserName())){
            //提示 要传用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }
    //todo 登出接口
    @PostMapping("/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }
}
