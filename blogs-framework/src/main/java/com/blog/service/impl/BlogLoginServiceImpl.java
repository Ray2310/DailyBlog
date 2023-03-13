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
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Resource
    private RedisCache redisCache;


    //todo 登录业务
    @Override
    public ResponseResult login(User user) {
        //需要调用AuthenticationManager ,返回Authentication对象
        //authenticationManager 它会调用userDetailsService
        Authentication authenticate = null;
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());

            System.out.println("==="+authenticationToken);
            authenticate = authenticationManager.authenticate(authenticationToken);
        } catch (AuthenticationException e) {
            System.out.println("错误信息:" + e.getMessage());
            e.printStackTrace();
        }


        //判断是否认证通过
        System.out.println("1-----------" + authenticate);
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误！！！");
        }
        //获取userId ，生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        //对userId进行加密
        String jwt = JwtUtil.createJWT(userId);
        System.out.println("BlogLoginServiceImpl: " + jwt);
        //把用户信息存入redis
        redisCache.setCacheObject(SystemConstants.LOGIN_KEY_PREFIX + userId,loginUser);
        //封装响应  ： 把token 和userInfoVo(由user转换而成) 封装 ，然后返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        System.out.println("BlogLoginServiceImpl: " + userInfoVo);
        BlogUserLoginVo userLoginVo = new BlogUserLoginVo(jwt, userInfoVo);
        System.out.println("BlogLoginServiceImpl: " + userLoginVo);
        return ResponseResult.okResult(userLoginVo);
    }
}
