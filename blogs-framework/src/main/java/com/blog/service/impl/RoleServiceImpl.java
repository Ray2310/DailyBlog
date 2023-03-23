package com.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.domain.entity.Role;
import com.blog.mapper.RoleMapper;
import com.blog.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询角色信息
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否为管理员角色
        if(id == 1){
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");  //管理员角色
            return roleKeys;
        }
        //如果不是返回对应id的角色信息(连表查询)
        return getBaseMapper().selectRoleKeyByUserId(id);
    }
}
