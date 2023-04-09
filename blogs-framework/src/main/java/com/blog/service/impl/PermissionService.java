package com.blog.service.impl;


import com.blog.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户权限判断
 * @author Ray2310
 *
 */
@Service("ps")
public class PermissionService {

     /**
     * 判断当前用户是否具有permission
     * @param perm 要判断的权限
     * @return
     */
    public boolean hasPerms(String perm){
        //如果是超级管理员  直接返回true
        if(SecurityUtils.isAdmin()){
            return true;
        }
        //否则  获取当前登录用户所具有的权限列表 如何判断是否存在perm
        List<String> permissions = SecurityUtils.getLoginUser().getPerms();
        return permissions.contains(perm);
    }
}
