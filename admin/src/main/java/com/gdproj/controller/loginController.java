package com.gdproj.controller;


import com.gdproj.annotation.autoLog;
import com.gdproj.entity.User;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.SystemLoginService;
import com.gdproj.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/adminLogin")
@Api(tags = "登录功能")
public class loginController {

    @Autowired
    UserService userService;

    @Autowired
    SystemLoginService systemLoginService;

    @PostMapping("/userlogin")
    @autoLog
    @ApiOperation(value = "登录请求")
    public ResponseResult userLogin(@RequestBody User user, HttpServletRequest request){

        return  systemLoginService.login(user);
    }

    @GetMapping("/userinfo")
    @autoLog
    @ApiOperation(value = "登出请求")
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
