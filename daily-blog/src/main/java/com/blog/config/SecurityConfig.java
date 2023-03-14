package com.blog.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 密码加密
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



    //todo 认证
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        HttpSecurity disable = http.csrf().disable();
        System.out.println("----"+disable.toString());
        http
                .authorizeRequests()
                //不通过session获取SecurityContext
                //对于登录接口 ，任何人都可以访问
                .mvcMatchers("/login").anonymous()
                //剩下的都不需要认证即可访问
                .anyRequest().permitAll()
                        .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ;
        http.logout().disable();
        //允许跨域
        http.cors();
    }



    /**
     * 将Authentication 对象注入到容器中
     * @return
     * @throws Exception
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
