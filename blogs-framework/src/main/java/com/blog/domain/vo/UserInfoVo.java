package com.blog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import sun.util.resources.cldr.mg.LocaleNames_mg;

/**
 * 用来封装登录时的用户信息
 */
@Data
@Accessors(chain = true)
public class UserInfoVo {
    private Long id;
    //昵称
    private String nickName;
    //头像
    private String avatar;
    private String sex;
    private String email;
}
