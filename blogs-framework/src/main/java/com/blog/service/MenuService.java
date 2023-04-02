package com.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blog.domain.ResponseResult;
import com.blog.domain.entity.Menu;

import java.util.List;

public interface MenuService extends IService<Menu> {
    List<String> selectPermsByUserId(Long id);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    ResponseResult getAll(String status, String menuName);

    ResponseResult addMenu(Menu menu);

    ResponseResult selectById(Long id);

    ResponseResult updateMenu(Menu menu);

    ResponseResult deleteById(Long id);

    List<Menu> selectMenuList(Menu menu);

    List<Long> selectMenuListByRoleId(Long id);

}
