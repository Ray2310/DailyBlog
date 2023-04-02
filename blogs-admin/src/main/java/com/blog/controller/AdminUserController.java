package com.blog.controller;

import com.blog.domain.ResponseResult;
import com.blog.domain.entity.User;
import com.blog.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/system/user")
public class AdminUserController {
    @Resource
    private UserService userService;


    //todo 后台获取用户列表
    @GetMapping("/list")
    public ResponseResult list(int pageNum ,int pageSize,String userName,String status,String phonenumber){

        return userService.listAll(pageNum,pageSize,userName,status,phonenumber);
    }

    @PostMapping
    public ResponseResult addUser(@RequestBody User userDto) {
        return userService.addUser(userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }

    @GetMapping("{id}")
    public ResponseResult getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }
    @PutMapping
    public ResponseResult updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

}
