package com.blog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 需要封装
 *    权限集合
 *    角色集合
 *    用户信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)    //设置成为可访问
public class AdminUserInfoVo {
    //权限集合
    private List<String> permissions;
    //角色集合
    private List<String> roles;
    //用户信息
    private UserInfoVo user;
}
