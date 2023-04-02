package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.domain.ResponseResult;

import com.blog.domain.entity.User;

public interface UserService extends IService<User> {
    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult listAll(int pageNum, int pageSize, String userName,String status, String phonenumber);

    ResponseResult addUser(User userDto);

    ResponseResult deleteUser(Long id);

    ResponseResult getUserById(Long id);

    ResponseResult updateUser(User user);
}
