package com.blog.controller;

import com.blog.annotation.SystemLog;
import com.blog.domain.ResponseResult;
import com.blog.domain.entity.User;
import com.blog.service.UserService;
import org.apache.poi.POIDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/userInfo")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }

    @SystemLog(businessName="更新用户信息")
    @PutMapping("/userInfo")
    public ResponseResult updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }

    @PostMapping("register")
    public ResponseResult register(@RequestBody User user){
        return userService.register(user);
    }
}
