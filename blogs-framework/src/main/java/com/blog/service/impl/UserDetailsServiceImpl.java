package com.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.blog.domain.entity.LoginUser;
import com.blog.domain.entity.User;
import com.blog.mapper.MenuMapper;
import com.blog.mapper.UserMapper;
import com.blog.utils.SystemConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private UserMapper userMapper;

    /**
     * 使用该方法来重写用户登录的校验工作
     * @param username 传入username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询数据库用户信息
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,username);
        User user = userMapper.selectOne(queryWrapper);
        //判断是否查询到  ： 未查到抛出异常   ： 查到然后作比较
        if(Objects.isNull(user)){
            throw new RuntimeException("用户不存在！！！");
        }
        /**
         * 还需要做的 : 后台用户查询权限信息封装
         *
         */

        //用户是管理员
        if(user.getType().equals(SystemConstants.ADMIN)){
            //查询用户权限集合
            List<String> perms = menuMapper.selectPermsByUserId(user.getId());
            //封装到loginUser
            return new LoginUser(user,perms);
        }
        //返回用户信息
        return new LoginUser(user,null);
    }
}
