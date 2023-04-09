package com.blog.service.impl;

import com.blog.domain.ResponseResult;
import com.blog.domain.entity.LoginUser;
import com.blog.domain.entity.User;
import com.blog.domain.vo.BlogUserLoginVo;
import com.blog.domain.vo.UserInfoVo;
import com.blog.service.AdminLoginService;
import com.blog.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 后台登陆实现
 */
@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private RedisCache redisCache;

    /**
     * 登录请求处理
     * @param user 登录的用户信息
     * @return 返回登录结果
     */
    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        //判断是否认证通过
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        //获取userId ，生成token
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存入redis
        System.out.println("token: " + jwt);
        //暂时不设置缓存清理时间， 后期需要进行优化
        redisCache.setCacheObject(SystemConstants.LOGIN_KEY + userId,loginUser);
        //封装响应  ： 把token 和userInfoVo(由user转换而成) 封装 ，然后返回
        Map<String,String> map = new HashMap<>();
        map.put("token",jwt);
        return ResponseResult.okResult(map);
    }

    /**
     * 用户登出
     * @return
     */
    @Override
    public ResponseResult logout() {
        //获取 token 解析获取 userId
        Long userId = SecurityUtils.getUserId();
        //redis中删除token 缓存
        redisCache.deleteObject(SystemConstants.LOGIN_KEY + userId);
        return ResponseResult.okResult();
    }
}
