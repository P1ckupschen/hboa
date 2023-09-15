package com.gdproj.controller;


import com.gdproj.dto.loginDto;
import com.gdproj.entity.User;
import com.gdproj.exception.TokenExpiredException;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.SystemLoginService;
import com.gdproj.service.UserService;
import com.gdproj.utils.Iputil;
import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/adminLogin")
public class loginController {

    @Autowired
    UserService userService;

    @Autowired
    SystemLoginService systemLoginService;

    @PostMapping("/userlogin")
    public ResponseResult userLogin(@RequestBody User user, HttpServletRequest request){

//        System.out.println(Iputil.getIp(request));

        return  systemLoginService.login(user);
    }

    @GetMapping("/userinfo")
    public ResponseResult getUserInfo(){

        userService.getUserInfo();

        return ResponseResult.okResult("userinfo");
    }


    @PostMapping("/success")
    public ResponseResult success(){

        return ResponseResult.okResult();
    }

    @PostMapping("/error")
    public ResponseResult error(){

        return ResponseResult.errorResult(4000,"sdsdsddsd");
    }

}
