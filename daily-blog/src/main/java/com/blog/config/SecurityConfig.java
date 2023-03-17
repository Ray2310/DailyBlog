package com.blog.config;
import com.blog.filter.JwtAuthenticationTokenFilter;
import com.blog.hander.security.AccessDeniedHandlerImpl;
import com.blog.hander.security.AuthenticationEntryPointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private AccessDeniedHandlerImpl accessDeniedHandler;
    @Autowired
    private AuthenticationEntryPointImpl authenticationEntryPoint;

    /**
     * 密码加密
     * @return
     */
    public static void main(String[] args) {
        passwordEncoder();
    }
    @Bean
    public static PasswordEncoder passwordEncoder(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        String encode = bCryptPasswordEncoder.encode("1234");
//        System.out.println(encode);
//        boolean matches = bCryptPasswordEncoder.matches("1234", "$2a$10$YcgEz3ksftVieI8jycP/3enGCr8fVQB5L8Qo6ayN.4jr1C5DwTDNC");
//        System.out.println(matches);
        //$2a$10$k7qjBivqu4Cy6YUlfnpC5eDYca7WXn.t63l03kFYNireCmbxJQW6K
        //$10$Jnq31rRkNV3RNzXe0REsEOSKaYK8UgVZZqlNlNXqn
        //$2a$10$YcgEz3ksftVieI8jycP/3enGCr8fVQB5L8Qo6ayN.4jr1C5DwTDNC
        //$10$k7qjBivqu4Cy6YUlfnpC5eDYca7WXn.t63l03kFYNireCmbxJQW6K
        return bCryptPasswordEncoder;
    }



    //todo 认证
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        HttpSecurity disable = http.csrf().disable();
        System.out.println("----"+disable.toString());
        http
                .authorizeRequests()
                //不通过session获取SecurityContext
                //对于登录接口 ，匿名访问
                .mvcMatchers("/login").anonymous()
                .antMatchers("/logout").authenticated()
                .antMatchers("/link/getAllLink").authenticated()
                //剩下的都不需要认证即可访问
                .anyRequest().permitAll()
                        .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ;
        http.logout().disable();
        //允许跨域
        http.cors();
        //将自定义的filter添加到过滤器链中
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        //配置认证和授权的异常处理器
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);

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
