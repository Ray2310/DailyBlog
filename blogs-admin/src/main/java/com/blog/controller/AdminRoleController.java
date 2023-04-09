package com.blog.controller;

import com.blog.domain.ResponseResult;
import com.blog.domain.dto.RoleDto;
import com.blog.domain.dto.RoleStatusDto;
import com.blog.domain.entity.Role;
import com.blog.domain.vo.RoleVo;
import com.blog.service.MenuService;
import com.blog.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 角色相关接口
 * @author Ray2310
 */
@RestController
@RequestMapping("/system/role")
public class AdminRoleController {



    @Resource
    private RoleService roleService;

    //todo 获取角色列表
    @GetMapping("/list")
    public ResponseResult getList(int pageNum, int pageSize,String roleName,String status){
        return roleService.getList(pageNum,pageSize,roleName,status);
    }

     //todo 这里注意， 前端接口中的请求体 中定义的id 不是和数据库中对应的 而是roleId
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody RoleStatusDto role){
        return roleService.changeStatus(role);
    }
    //todo 新增角色
    @PostMapping
    public ResponseResult AddRole(@RequestBody RoleDto roleDto){
        return roleService.AddRole(roleDto);
    }

    //todo 根据id 获取角色信息
    @GetMapping("/{id}")
    public ResponseResult getRoleById(@PathVariable Long id){
        return roleService.getRoleById(id);
    }

    //todo 更新角色信息
    @PutMapping
    public ResponseResult updateRole(@RequestBody Role role){
        return roleService.updateRole(role);
    }

    //todo 删除角色信息
    @DeleteMapping("/{id}")
    public ResponseResult deleteRole(@PathVariable Long id){
        return roleService.deleteRole(id);
    }


    /**
     * 作用  : 在新增用户接口中 ，前端需要传入所有的角色信息供用户选择
     * @return 返回不分页的所有角色信息
     */
    @GetMapping("/listAllRole")
    public ResponseResult listRoles(){
        return roleService.listRoles();
    }

}
