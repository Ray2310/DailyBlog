package com.blog.utils;


import com.blog.domain.entity.Menu;
import com.blog.domain.vo.MenuTreeVo;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SystemConverter {

    private SystemConverter() {
    }


    public static List<MenuTreeVo> buildMenuSelectTree(List<Menu> menus) {
        List<MenuTreeVo> MenuTreeVos = menus.stream()
                .map(m -> new MenuTreeVo(null, m.getId(), m.getMenuName(), m.getParentId()))
                .collect(Collectors.toList());
        List<MenuTreeVo> options = MenuTreeVos.stream()
                .filter(o -> o.getParentId().equals(0L))
                .map(o -> o.setChildren(getChildList(MenuTreeVos, o)))
                .collect(Collectors.toList());
        return options;
    }
    /**
     * 得到子节点列表
     */
    private static List<MenuTreeVo> getChildList(List<MenuTreeVo> list, MenuTreeVo option) {
        List<MenuTreeVo> options = list.stream()
                .filter(o -> Objects.equals(o.getParentId(), option.getId()))
                .map(o -> o.setChildren(getChildList(list, o)))
                .collect(Collectors.toList());
        return options;

    }

    //------------上述函数式编程转为常规的for循环------------------------
    /*
    public static List<MenuTreeVo> buildMenuSelectTree(List<Menu> menus) {
    List<MenuTreeVo> MenuTreeVos = new ArrayList<>();
    for (Menu m : menus) {
        MenuTreeVos.add(new MenuTreeVo(null, m.getId(), m.getMenuName(), m.getParentId()));
    }
    List<MenuTreeVo> options = new ArrayList<>();
    for (MenuTreeVo o : MenuTreeVos) {
        if (o.getParentId().equals(0L)) {
            o.setChildren(getChildList(MenuTreeVos, o));
            options.add(o);
        }
    }
    return options;
    }

    private static List<MenuTreeVo> getChildList(List<MenuTreeVo> list, MenuTreeVo option) {
        List<MenuTreeVo> options = new ArrayList<>();
        for (MenuTreeVo o : list) {
            if (Objects.equals(o.getParentId(), option.getId())) {
                o.setChildren(getChildList(list, o));
                options.add(o);
            }
        }
        return options;
    }

     */
}
