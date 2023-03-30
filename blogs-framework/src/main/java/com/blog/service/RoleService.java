package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.domain.ResponseResult;
import com.blog.domain.dto.RoleDto;
import com.blog.domain.dto.RoleStatusDto;
import com.blog.domain.entity.Role;

import java.util.List;

public interface RoleService extends IService<Role> {
    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult getList(int pageNum, int pageSize, String roleName, String status);

    ResponseResult changeStatus(RoleStatusDto roleDto);


    ResponseResult AddRole(RoleDto roleDto);
}
