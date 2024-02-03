package com.gdproj.controller;


import cn.hutool.crypto.SecureUtil;
import com.gdproj.annotation.autoLog;
import com.gdproj.entity.User;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.AccountService;
import com.gdproj.service.SystemLoginService;
import com.gdproj.service.UserService;
import com.gdproj.vo.AccountVo;
import com.gdproj.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/adminLogin")
@Api(tags = "登录功能")
public class loginController {

    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService;

    @Autowired
    SystemLoginService systemLoginService;

    @PostMapping("/userlogin")
    @autoLog
    @ApiOperation(value = "后台登录请求")
    public ResponseResult userLogin(@RequestBody User user,HttpSession session){

        return  systemLoginService.login(user,session);
    }

    @PostMapping("/frontLogin")
    @autoLog
    @ApiOperation(value = "前台登录请求")
    public ResponseResult frontLogin(@RequestBody AccountVo vo, HttpServletRequest request){
        SecureUtil.disableBouncyCastle();

        return  systemLoginService.frontLogin(vo,request);
    }

    @GetMapping("/Userinfo")
    @autoLog
    @ApiOperation(value = "获取用户信息")
    public ResponseResult getUserInfo(HttpServletRequest request){

        String token = request.getHeader("Authorization");
        try {
            UserVo vo = userService.getUserInfo(token);

            return ResponseResult.okResult(vo);
        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.GET_ACCOUNTINFO_ERROR);
        }
//        =userService.getUserInfo();
    }

    @GetMapping("/frontUserinfo")
    @autoLog
    @ApiOperation(value = "获取前台用户信息")
    public ResponseResult getFrontUserInfo(HttpServletRequest request){

        String token = request.getHeader("Authorization");
        try {
            AccountVo vo = accountService.getAccountInfo(token);

            return ResponseResult.okResult(vo);
        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.GET_ACCOUNTINFO_ERROR);
        }

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
