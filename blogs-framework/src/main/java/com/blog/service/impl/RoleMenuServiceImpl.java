package com.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.domain.entity.RoleMenu;
import com.blog.mapper.RoleMenuMapper;
import com.blog.service.RoleMenuService;
import org.springframework.stereotype.Service;

/**
 * 角色菜单关联实现类
 * @author Ray2310
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

}
