package com.blog.controller;

import com.blog.domain.ResponseResult;
import com.blog.domain.dto.RoleDto;
import com.blog.domain.dto.RoleStatusDto;
import com.blog.domain.entity.Role;
import com.blog.domain.vo.RoleVo;
import com.blog.service.MenuService;
import com.blog.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/system/role")
public class AdminRoleController {



    @Resource
    private RoleService roleService;

    //todo 获取角色列表
    @GetMapping("/list")
    public ResponseResult getList(int pageNum, int pageSize,String roleName,String status){
        return roleService.getList(pageNum,pageSize,roleName,status);
    }

    /**
     * 这里注意， 前端接口中的请求体 中定义的id 不是和数据库中对应的 而使roleId
     * @param role
     * @return
     */
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody RoleStatusDto role){
        return roleService.changeStatus(role);
    }
    @PostMapping
    public ResponseResult AddRole(@RequestBody RoleDto roleDto){
        return roleService.AddRole(roleDto);
    }



}
