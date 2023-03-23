package com.blog.domain.vo;

import com.blog.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 封装菜单按钮相关信息
 *      menus 菜单
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class RoutersVo {
    //菜单集合
    private List<Menu> menus;
}
