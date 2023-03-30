package com.blog.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleStatusDto {

    private Long roleId;
    //角色状态（0正常 1停用）
    private String status;

}
