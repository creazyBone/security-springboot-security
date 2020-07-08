package com.greenleaf.security.springbootsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

//由于用了springboot，@EnableWebSecurity可以不写
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    //定义用户信息服务（查询用户信息）
    public UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        //注意这里authorities("p1","p2")要逐个都好隔开
        inMemoryUserDetailsManager.createUser(User.withUsername("zhangsan").password("123").authorities("p1","p2").build());
        inMemoryUserDetailsManager.createUser(User.withUsername("lisi").password("456").authorities("p2").build());
        return inMemoryUserDetailsManager;
    }
    @Bean
    //密码编码器
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
    //安全拦截机制

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/r/r1").hasAuthority("p1")
                .antMatchers("/r/r2").hasAuthority("p2")
                .antMatchers("/r/**").authenticated()//所有/r/**的请求必须认证通过
                .anyRequest().permitAll()//除了/r/**，其他的请求可以访问
                .and()
                .formLogin()//允许表单登录
                .successForwardUrl("/login-success");//登录成功后跳转的页面
    }
}

