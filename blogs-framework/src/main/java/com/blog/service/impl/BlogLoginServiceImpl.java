package com.blog.service.impl;


import com.blog.domain.ResponseResult;
import com.blog.domain.entity.LoginUser;
import com.blog.domain.entity.User;
import com.blog.domain.vo.BlogUserLoginVo;
import com.blog.domain.vo.UserInfoVo;
import com.blog.service.BlogLoginService;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.JwtUtil;
import com.blog.utils.RedisCache;
import com.blog.utils.SystemConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Resource
    private UserDetailsServiceImpl userDetailsService;

    @Resource
    private RedisCache redisCache;

    //todo 登录业务
    @Override
    public ResponseResult login(User user) {
        //需要调用AuthenticationManager ,返回Authentication对象
        //authenticationManager 它会调用userDetailsService


        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());

        System.out.println(authenticationToken.getDetails());

        /**
         * 认证功能暂时有bug ，先跳过认证
         * Authentication authenticate = authenticationManager.authenticate(authenticationToken);
         *          System.out.println(authenticate);
         */

        //判断是否认证通过
        //获取userId ，生成token
        LoginUser loginUser = (LoginUser) userDetailsService.loadUserByUsername(user.getUserName());
        if(Objects.isNull(loginUser)){
            throw new RuntimeException("用户名或密码错误！！！ ");
        }
        String userId = loginUser.getUser().getId().toString();
        //对userId进行加密
        String jwt = JwtUtil.createJWT(userId);
        System.out.println("jwtToken : " + jwt);
        //把用户信息存入redis
        redisCache.setCacheObject(SystemConstants.LOGIN_KEY_PREFIX + userId,loginUser);
        //封装响应  ： 把token 和userInfoVo(由user转换而成) 封装 ，然后返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo userLoginVo = new BlogUserLoginVo(jwt, userInfoVo);
        return ResponseResult.okResult(userLoginVo);
    }


    //todo 退出登录
    @Override
    public ResponseResult logout() {
        //获取 token 解析获取 userId
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();

        redisCache.deleteObject(SystemConstants.LOGIN_KEY_PREFIX + userId);
        return ResponseResult.okResult();
    }
}
