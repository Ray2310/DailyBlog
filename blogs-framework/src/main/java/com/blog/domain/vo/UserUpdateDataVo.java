package com.blog.domain.vo;

import com.blog.domain.entity.Role;
import com.blog.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDataVo {
    private List<Long> roleIds;
    private List<Role> roles;
    private User user;
}
