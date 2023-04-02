package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.domain.ResponseResult;
import com.blog.domain.dto.RoleDto;
import com.blog.domain.dto.RoleStatusDto;
import com.blog.domain.entity.Menu;
import com.blog.domain.entity.Role;
import com.blog.domain.entity.RoleMenu;
import com.blog.domain.vo.PageVo;
import com.blog.domain.vo.RoleVo;
import com.blog.mapper.RoleMapper;
import com.blog.service.RoleMenuService;
import com.blog.service.RoleService;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.SystemConstants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 查询角色信息
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMenuService roleMenuService;

    //--------------------后台----------------------------

    /**
     * 获取所有的角色， 需要可以根据角色名 and 状态 模糊查询
     * @param pageNum 页数
     * @param pageSize 每页大小
     * @param roleName 角色名
     * @param status 状态
     * @return 封装返回
     */
    @Override
    public ResponseResult getList(int pageNum, int pageSize, String roleName, String status) {
        //1. 先查询出未删除的
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getDelFlag, SystemConstants.NOT_DELETE);
        //2. 按照求排序
        wrapper.orderByAsc(Role::getRoleSort);
        //3. 进行模糊查询
        wrapper.like(Objects.nonNull(roleName),Role::getRoleName,roleName);
        wrapper.like(Objects.nonNull(status),Role::getStatus,status);
        //4. 分页
        Page<Role> page = new Page<>(pageNum,pageSize);
        page(page,wrapper);
        //5. 封装返回
        List<Role> records = page.getRecords();
        List<RoleVo> roleVos = BeanCopyUtils.copyBeanList(records, RoleVo.class);
        PageVo pageVos = new PageVo(roleVos,page.getTotal());
        return ResponseResult.okResult(pageVos);
    }

    /**
     * 改变角色状态
     * @param roleDto 封装角色id 和状态
     * @return
     */
    @Override
    public ResponseResult changeStatus(RoleStatusDto roleDto) {
        UpdateWrapper<Role> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",roleDto.getRoleId());
        wrapper.set("status",roleDto.getStatus());
        update(wrapper);
        Role byId = getById(roleDto.getRoleId());
        return ResponseResult.okResult();
    }

    /**
     * 添加角色信息
     * @param roleDto 用户写入的需要添加的角色信息
     * @return
     */
    @Override
    public ResponseResult AddRole(RoleDto roleDto) {

        Role role = BeanCopyUtils.copyBean(roleDto, Role.class);
        save(role);
        //todo 还需要将角色和对应的菜单权限信息添加
        Long[] menuIds = roleDto.getMenuIds();
        for(Long id : menuIds){
            roleMenuService.save(new RoleMenu(role.getId(),id));
        }
        return ResponseResult.okResult();
    }

    /**
     * 根据id获取需要修改的角色信息
     * @param id 角色id
     * @return
     */
    @Override
    public ResponseResult getRoleById(Long id) {
        Role role = getById(id);
        RoleVo roleVo = BeanCopyUtils.copyBean(role, RoleVo.class);
        return ResponseResult.okResult(roleVo);
    }

    /**
     * 进行修改 ，注意关联对应的菜单树信息
     * @param role 传入对应的信息
     * @return
     */
    @Override
    public ResponseResult updateRole(Role role) {
        updateById(role);
        //删除之前的菜单关系
        LambdaQueryWrapper<RoleMenu> roleMenuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roleMenuLambdaQueryWrapper.eq(RoleMenu::getRoleId,role.getId());
        roleMenuService.remove(roleMenuLambdaQueryWrapper);
        //重新建立菜单关系
        //还需要将角色和对应的菜单权限信息添加
        Long[] menuIds = role.getMenuIds();
        for(Long id : menuIds){
            roleMenuService.save(new RoleMenu(role.getId(),id));
        }
        return ResponseResult.okResult();
    }

    /**
     * 根据id删除角色信息， 注意还要删除对应的角色关联的菜单
     * 还有就是逻辑删除
     * @param id 角色id
     * @return
     */
    @Override
    public ResponseResult deleteRole(Long id) {
        UpdateWrapper<Role> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",id);
        wrapper.set("del_flag",SystemConstants.DELETE);
        update(wrapper);
        //删除对应的关联信息
        LambdaQueryWrapper<RoleMenu> roleMenuLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roleMenuLambdaQueryWrapper.eq(RoleMenu::getRoleId,id);
        roleMenuService.remove(roleMenuLambdaQueryWrapper);
        return ResponseResult.okResult();
    }
    @Override
    public ResponseResult listRoles() {
        //1. 先查询出未删除的
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Role::getDelFlag, SystemConstants.NOT_DELETE);
        //2. 按照求排序
        wrapper.orderByAsc(Role::getRoleSort);
        wrapper.eq(Role::getStatus,SystemConstants.STATUS_OK);
        List<Role> list = list(wrapper);
        //5. 封装返回
        List<RoleVo> roleVos = BeanCopyUtils.copyBeanList(list, RoleVo.class);
        return ResponseResult.okResult(roleVos);
    }
    //--------------------前端----------------------------
    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否为管理员角色
        if(id == 1){
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");  //管理员角色
            return roleKeys;
        }
        //如果不是返回对应id的角色信息(连表查询)
        return getBaseMapper().selectRoleKeyByUserId(id);
    }
}
