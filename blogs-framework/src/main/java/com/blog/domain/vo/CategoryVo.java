package com.blog.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryVo {
    //描述
    private String description;
    private Long id;
    private String name;
    //状态0:正常,1禁用
    private String status;
}
