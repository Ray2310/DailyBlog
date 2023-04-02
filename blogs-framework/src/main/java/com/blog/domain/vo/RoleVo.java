package com.blog.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleVo {
    //角色ID@TableId
    private Long id;
    //角色名称
    private String roleName;
    //角色权限字符串
    private String roleKey;
    //显示顺序
    private Integer roleSort;

    //角色状态（0正常 1停用）
    private String status;

    //----updateNeed-----
    //删除标志（0代表存在 2代表删除）
    private String delFlag;
    private Long createBy;
    private Long updateBy;
    private Date updateTime;
    //备注
    private String remark;

}
