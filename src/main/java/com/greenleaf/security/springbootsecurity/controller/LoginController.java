package com.greenleaf.security.springbootsecurity.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @RequestMapping(value = "/login-success",produces = {"text/plain;charset=utf-8"})
    public String loginSuccess(){
        //提示具体用户名称登录成功
        return getUsername() + "登录成功";
    }

    @GetMapping(value = "/r/r1",produces = {"text/plain;charset=utf-8"})
    public String r1(){
        return "访问资源1";
    }

    @GetMapping(value = "/r/r2",produces = {"text/plain;charset=utf-8"})
    public String r2(){
        return "访问资源2";
    }

    @GetMapping(value = "/q/q3",produces = {"text/plain;charset=utf-8"})
    public String r3(){
        return "访问资源3";
    }


    private String getUsername(){
        String username;
        //当前认证通过的用户身份
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //用户身份
        Object principal = authentication.getPrincipal();
        if(principal == null){
            username = "匿名";
        }
        if(principal instanceof org.springframework.security.core.userdetails.UserDetails){
            username = ((UserDetails)principal).getUsername();
        }else{
            username = principal.toString();
        }
        return username;
    }
}
