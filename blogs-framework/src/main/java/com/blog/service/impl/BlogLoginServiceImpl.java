package com.blog.service.impl;


import com.blog.domain.ResponseResult;
import com.blog.domain.entity.LoginUser;
import com.blog.domain.entity.User;
import com.blog.domain.vo.BlogUserLoginVo;
import com.blog.domain.vo.UserInfoVo;
import com.blog.service.BlogLoginService;
import com.blog.utils.*;

import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.support.SecurityWebApplicationContextUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 前端登录接口实现
 * @author Ray2310
 */
@Service
public class BlogLoginServiceImpl implements BlogLoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Resource
    private UserDetailsServiceImpl userDetailsService;

    @Resource
    private RedisCache redisCache;

    /**
     * 登录业务
     */
    @Override
    public ResponseResult login(User user) {
        //需要调用AuthenticationManager ,返回Authentication对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        //authenticationManager 它会调用userDetailsService
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //获取userid 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        System.out.println("jwtToken : " + jwt);
        //把用户信息存入redis
        redisCache.setCacheObject(SystemConstants.LOGIN_KEY_PREFIX + userId,loginUser);
        //封装响应  ： 把token 和userInfoVo(由user转换而成) 封装 ，然后返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo userLoginVo = new BlogUserLoginVo(jwt, userInfoVo);
        return ResponseResult.okResult(userLoginVo);
    }


    /**
     * 退出登录
     * @return
     */
    @Override
    public ResponseResult logout() {
        //获取 token 解析获取 userId
        //通过security获取用户登陆信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userId = loginUser.getUser().getId();
        //删除redis中的缓存
        redisCache.deleteObject(SystemConstants.LOGIN_KEY_PREFIX + userId);
        return ResponseResult.okResult();
    }
}
