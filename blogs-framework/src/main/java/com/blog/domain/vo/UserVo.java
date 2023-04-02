package com.blog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserVo {
    private Long id;
    private String userName;
    private String nickName;
    private String status;
    private String phonenumber;
    private String avatar;
    private String sex;
    private Long updateBy;
    private Date updateTime;
    private Long createBy;
    private Date createTime;



}
