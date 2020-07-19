package com.greenleaf.security.springbootsecurity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//由于用了springboot，@EnableWebMvc和@ComponentScan可以不写，且jsp视图解析器的前后缀在application.properties文件中配置
@Configuration//相当于springmvc.xml
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //没用Security时，login页面通过ViewController来跳转。
        //默认URL根路径重定向到/login，此跳转路径由Security提供
     //   registry.addViewController("/").setViewName("redirect:/login");

        //自定义登录页面
        registry.addViewController("/").setViewName("redirect:/login-view");
        registry.addViewController("/login-view").setViewName("login");
        registry.addViewController("/login-view2").setViewName("hello");
    }

}
