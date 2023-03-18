package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.domain.ResponseResult;
import com.blog.domain.entity.User;
import com.blog.domain.vo.UserInfoVo;
import com.blog.enums.AppHttpCodeEnum;
import com.blog.mapper.UserMapper;
import com.blog.service.UserService;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.SecurityUtils;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

import static com.blog.enums.AppHttpCodeEnum.NICKNAME_EXIST;
import static com.blog.enums.AppHttpCodeEnum.USERNAME_EXIST;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    //todo 用户中心
    @Override
    public ResponseResult userInfo() {
        //获取当前用户
        Long userId = SecurityUtils.getUserId();
        //根据当前用户id查询当前用户
        User user = getById(userId);
        //封装成userInfoVo返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }


    //todo 更新个人信息
    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }


    //todo 注册用户
    @Override
    public ResponseResult register(User user) {
        //对数据进行非空判断 要求用户名 密码 等都不为空
        if(!StringUtils.hasText(user.getUserName())){
            ResponseResult.errorResult(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if( StringUtils.hasText(user.getPassword())){
            ResponseResult.errorResult(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if( StringUtils.hasText(user.getEmail())){
            ResponseResult.errorResult(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if( StringUtils.hasText(user.getNickName())){
            ResponseResult.errorResult(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        //判断数据库中是否存在用户
        if(usernameExist(user.getUserName())){
            //用户已经存在
            ResponseResult.errorResult(USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            //昵称存在
            ResponseResult.errorResult(NICKNAME_EXIST);
        }
        ///密码加密处理
        String encodePass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePass); //设置加密之后的密码
        save(user);
        //返回结果
        return ResponseResult.okResult();
    }


    //todo 判断用户名是否存在
    private boolean usernameExist(String username){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,username);
        int count = count(queryWrapper);
        if(count >= 1){
            return true;
        }
        return false;
    }
    //todo 判断昵称是否存在
    private boolean nickNameExist(String nickName){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName,nickName);
        int count = count(queryWrapper);
        if(count >= 1){
            return true;
        }
        return false;
    }
}
