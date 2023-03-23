package com.blog.service;

import com.blog.domain.ResponseResult;
import com.blog.domain.entity.User;

public interface AdminLoginService {
    ResponseResult login(User user);

    ResponseResult logout();

}
