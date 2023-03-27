package com.blog.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginUser implements UserDetails {

    protected User user;

    //用户权限集合
    private List<String> perms;

    /**
     *
     * @return 返回权限集合
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    //todo 暂时改为true
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    //todo 暂时改为true
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    //todo 暂时改为true
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    //todo 暂时改为true
    @Override
    public boolean isEnabled() {
        return true;
    }
}
