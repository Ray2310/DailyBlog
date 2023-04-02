package com.blog.domain.entity;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户表(User)表实体类
 * @author makejava
 * @since 2023-03-13 21:15:56
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@TableName("sys_user")
public class User {
    //主键
    @TableId
    private Long id;
    //用户名
    @TableField(value = "user_name")
    private String userName;
    //昵称
    @TableField(value = "nick_name")
    private String nickName;
    //密码
    @TableField(value = "password")
    private String password;
    //用户类型：0代表普通用户，1代表管理员
    @TableField(value = "type")
    private String type;

    //账号状态（0正常 1停用）
    @TableField(value = "status")
    private String status;
    //邮箱
    @TableField(value = "email")
    private String email;
    //手机号
    @TableField(value = "phonenumber")
    private String phonenumber;
    //用户性别（0男，1女，2未知）
    @TableField(value = "sex")
    private String sex;
    //头像
    @TableField(value = "avatar")
    private String avatar;

    @TableField(fill = FieldFill.INSERT)
    private Long createBy;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    //删除标志（0代表未删除，1代表已删除）
    @TableField(value = "del_flag")
    private Integer delFlag;
    //关联角色id数组，非user表字段
    @TableField(exist = false)
    private List<Long> roleIds;
}

