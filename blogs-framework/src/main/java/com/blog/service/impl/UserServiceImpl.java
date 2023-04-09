package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blog.domain.ResponseResult;

import com.blog.domain.entity.Role;
import com.blog.domain.entity.User;
import com.blog.domain.entity.UserRole;
import com.blog.domain.vo.PageVo;
import com.blog.domain.vo.UserInfoVo;
import com.blog.domain.vo.UserUpdateDataVo;
import com.blog.domain.vo.UserVo;
import com.blog.enums.AppHttpCodeEnum;
import com.blog.mapper.UserMapper;
import com.blog.service.RoleService;
import com.blog.service.UserRoleService;
import com.blog.service.UserService;
import com.blog.utils.BeanCopyUtils;
import com.blog.utils.SecurityUtils;
import com.blog.utils.SystemConstants;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.blog.enums.AppHttpCodeEnum.*;

/**
 * 用户信息接口实现类
 * @author Ray2310
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Resource
    private UserRoleService userRoleService;

    @Resource
    private RoleService roleService;

    //-----------------------blog后台----------------------------------


    /**
     * 进行用户列表查询
     * 要求： 1. 能够进行分页展示 2. 可以通过用户名 进行模糊查询 3. 可以通过手机号、状态进行查询
     * @param pageNum 当前页
     * @param pageSize 分页大小
     * @param userName 用户名
     * @param phonenumber 手机号
     * @return
     */
    @Override
    public ResponseResult listAll(int pageNum, int pageSize, String userName, String status,String phonenumber) {
        // 可以通过用户名 进行模糊查询
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        //可以通过手机号、状态进行查询
        wrapper.like(StringUtils.hasText(userName),User::getUserName,userName);
        wrapper.eq(StringUtils.hasText(status),User::getStatus,status);
        wrapper.eq(StringUtils.hasText(phonenumber),User::getPhonenumber,phonenumber);
        // 能够进行分页展示
        Page<User> page = new Page<>(pageNum,pageSize);
        page(page,wrapper);
        List<User> records = page.getRecords();
        List<UserVo> userVos = BeanCopyUtils.copyBeanList(records, UserVo.class);
        PageVo pageVo = new PageVo(userVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    /**
     * 新增用户
     1. 首先要返回所有的角色列表（状态正常，没有删除的	）
     2. 用户输入密码存储时需要进行加密存储
     3. 相关信息不能为空
     4. 相关用户名、手机号、邮箱...不能相同
     * @param userDto
     * @return
     */
    @Override
    @Transactional
    public ResponseResult addUser(User userDto) {
        //对数据进行非空判断 要求用户名 密码 等都不为空
        if(!StringUtils.hasText(userDto.getUserName())){
            ResponseResult.errorResult(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(userDto.getPassword())){
            ResponseResult.errorResult(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if( !StringUtils.hasText(userDto.getEmail())){
            ResponseResult.errorResult(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if( !StringUtils.hasText(userDto.getNickName())){
            ResponseResult.errorResult(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        //判断数据库中是否存在用户
        if(usernameExist(userDto.getUserName())){
            //用户已经存在
            ResponseResult.errorResult(USERNAME_EXIST);
        }
        if(nickNameExist(userDto.getNickName())){
            //昵称存在
            ResponseResult.errorResult(NICKNAME_EXIST);
        }
        if(phoneNumberExist(userDto.getPhonenumber())){
            //昵称存在
            ResponseResult.errorResult(PHONENUMBER_EXIST);
        }
        if(EmailExist(userDto.getEmail())){
            //昵称存在
            ResponseResult.errorResult(EMAIL_EXIST);
        }
        //密码加密处理
        System.out.println("-------------" + userDto.getPassword());
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword())); //设置加密之后的密码

        save(userDto);
        //添加对应的角色用户信息关系
        List<Long> roleIds = userDto.getRoleIds();
        for (Long roleId : roleIds){
            userRoleService.save(new UserRole(userDto.getId(),roleId));
        }
        return ResponseResult.okResult();
    }

    /**
     * 逻辑删除用户
     * @param id
     * @return
     */
    @Override
    public ResponseResult deleteUser(Long id) {
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",id);
        wrapper.set("del_flag", SystemConstants.DELETE);
        update(wrapper);
        return ResponseResult.okResult();
    }

    /**
     * 回显要删除的用户信息
     * 需要回显用户关联的角色状态
     * @param id 用户id
     * @return
     */
    @Override
    public ResponseResult getUserById(Long id) {
        //1. 查询用户信息回显
        User user = getById(id);
        //2. 查询角色信息
        List<Role> roles = roleService.list();
        //3. 查询用户角色管理信息
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId,id);
        List<UserRole> list = userRoleService.list(wrapper);
        List<Long> ids = new ArrayList<>();
        for(UserRole userRole : list){
            ids.add(userRole.getRoleId());
        }
        UserUpdateDataVo vo = new UserUpdateDataVo(ids, roles, user);
        return ResponseResult.okResult(vo);
    }

    /**
     * 更新用户信息
     * @param user 用户信息
     * @return
     */
    @Override
    @Transactional
    public ResponseResult updateUser(User user) {
        updateById(user);
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId,user.getId());
        userRoleService.remove(wrapper);
        //添加对应的角色用户信息关系
        List<Long> roleIds = user.getRoleIds();
        for (Long roleId : roleIds){
            userRoleService.save(new UserRole(user.getId(),roleId));
        }
        return ResponseResult.okResult();
    }
//-----------------------blog前端----------------------------------

    /**
     * 用户中心
     * @return
     */
    @Override
    public ResponseResult userInfo() {
        //获取当前用户
        Long userId = SecurityUtils.getUserId();
        //根据当前用户id查询当前用户
        User user = getById(userId);
        //封装成userInfoVo返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }


    /**
     * 更新用户信息
     * @param user 用户信息
     * @return
     */
    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }


    /**
     * 注册用户
     * @param user 注册的用户信息
     * @return
     */
    @Override
    public ResponseResult register(User user) {
        //对数据进行非空判断 要求用户名 密码 等都不为空
        if(!StringUtils.hasText(user.getUserName())){
            ResponseResult.errorResult(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            ResponseResult.errorResult(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if( !StringUtils.hasText(user.getEmail())){
            ResponseResult.errorResult(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if( !StringUtils.hasText(user.getNickName())){
            ResponseResult.errorResult(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        //判断数据库中是否存在用户
        if(usernameExist(user.getUserName())){
            //用户已经存在
            ResponseResult.errorResult(USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            //昵称存在
            ResponseResult.errorResult(NICKNAME_EXIST);
        }

        ///密码加密处理
        String encodePass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePass); //设置加密之后的密码
        save(user);
        //返回结果
        return ResponseResult.okResult();
    }


    /**
     * 查询数据库判断用户名是否存在
     * @param username 用户名
     * @return
     */
    private boolean usernameExist(String username){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,username);
        int count = count(queryWrapper);
        if(count >= 1){
            return true;
        }
        return false;
    }

    /**
     * 判断昵称是否存在
     * @param nickName 昵称
     * @return
     */
    private boolean nickNameExist(String nickName){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getNickName,nickName);
        int count = count(queryWrapper);
        if(count >= 1){
            return true;
        }
        return false;
    }

    /**
     * 判断电话是否存在
     * @param phonenumber 电话
     * @return
     */
    private boolean phoneNumberExist(String phonenumber){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getPhonenumber,phonenumber);
        int count = count(queryWrapper);
        if(count >= 1){
            return true;
        }
        return false;
    }

    /**
     * 判断邮箱是否存在
     * @param Email 邮箱
     * @return
     */
    private boolean EmailExist(String Email){
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getEmail,Email);
        int count = count(queryWrapper);
        if(count >= 1){
            return true;
        }
        return false;
    }


}
