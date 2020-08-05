package com.greenleaf.security.springbootsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;

//由于用了springboot，@EnableWebSecurity可以不写
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //User-->UserDetails
    //InMemoryUserDetailsManager-->UserDetailsManager-->UserDetailsService

  /*  @Bean
    //定义用户信息服务（查询用户信息）
    public UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager inMemoryUserDetailsManager = new InMemoryUserDetailsManager();
        //注意这里authorities("p1","p2")要逐个都好隔开
        inMemoryUserDetailsManager.createUser(User.withUsername("zhangsan").password("123").authorities("p1","p2").build());
        inMemoryUserDetailsManager.createUser(User.withUsername("lisi").password("456").authorities("p2").build());
        return inMemoryUserDetailsManager;
    }*/

    @Bean
    //密码编码器
    public PasswordEncoder passwordEncoder(){

        //return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }
    //安全拦截机制

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()//禁用对csrf的限制,开启时会限制除了get以外的大多数方法
                .authorizeRequests()
            //    .anyRequest().authenticated()//任意请求需要登录
                .antMatchers("/r/r1").hasAuthority("p1")
                .antMatchers("/r/r2").hasAuthority("p2")
                .antMatchers("/r/**").authenticated()//所有/r/**的请求必须认证通过
             //   .anyRequest().permitAll()//除了/r/**，其他的请求可以访问。放在前面和放在后面对于权限控制会有差异。细节控制写在前面
            .and()
                .formLogin()//开启支持基于表单的身份验证
                .loginPage("/login-view"). permitAll()//指定自定义登录页，SpringSecurity会以重定向的方式跳转到/login-view
                .loginProcessingUrl("/login")//指定处理登录请求的URL，也就是登录页提交form的那个action地址
                .successForwardUrl("/login-success")//登录成功后跳转的页面
              //  .permitAll();//允许任意用户访问哪怕没有登录的也可以访问.对应上面写了.anyRequest().authenticated(),如果不写这句话则无法访问/login-view。
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)//这里需要通过会话来保存认证信息，所以用required。如果通过token的方式来保存认证信息，则可以设置成stateless
            .and()
                .logout()//提供系统推出支持，使用WebSecurityConfigurerAdapter会自动被应用
                .logoutUrl("/logout")//设置触发退出操作的URL。
                .logoutSuccessUrl("/login-view?logout");//退出之后跳转的URL，默认是/logout?logout. ?logout仅仅是表示退出，没有实际意义。 用了.anyRequest().authenticated()会要求logoutSuccessUrl认证后才可以访问，所以会强制重定向到跳转到登录页
                //.logoutSuccessHandler(logoutSuccessHandler)//定制的LogoutSuccessHandler，用于实现用户退出成功时的处理。如果配了这个，logoutSuccessUrl的设置会被忽略
                //.addLogoutHandler(new CookieClearingLogoutHandler())//添加一个logoutHandler，用于实现用户退出（不管退出成功不成功）时的清理工作。默认SecurityContextLogoutHandler会被添加成最后一个LogoutHandler
                //.invalidateHttpSession(true);//指定是否在退出的时候让HttpSession无效。默认设置为true。
    }

}

