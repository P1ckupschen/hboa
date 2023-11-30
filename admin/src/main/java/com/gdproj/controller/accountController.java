package com.gdproj.controller;


import com.gdproj.annotation.autoLog;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.AccountService;
import com.gdproj.utils.JwtUtils;
import com.gdproj.vo.PwVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/Account")
@Api(tags = "登录账号相关功能")
public class accountController {

    @Autowired
    AccountService accountService;

//    @PutMapping("/updateAccount")
//    @ApiOperation(value = "修改")
//    @autoLog
//    public ResponseResult updateAccount(@RequestParam(value = "password") String pw , HttpServletRequest request){
//
//        String authorization = request.getHeader("Authorization");
//        String token = authorization.split(" ")[1];
//        String id = JwtUtils.getMemberIdByJwtToken(token);
//        return accountService.updateAccount(pw , Integer.valueOf(id));
//
//    }

    @PostMapping("/createAccount")
    @ApiOperation(value = "新建账户")
    @autoLog
    public ResponseResult createAccount(@RequestParam(value = "username") String username ,
                                        @RequestParam(value = "password") String password ,
                                        HttpServletRequest request ) {
        String authorization = request.getHeader("Authorization");
        String token = authorization.split(" ")[1];
        String id = JwtUtils.getMemberIdByJwtToken(token);
        return accountService.createAccount(username , password , id);
    }

    //TODO 重置密码接口   修改密码接口
    @PutMapping("/resetPassword")
    @ApiOperation(value = "重置密码")
    @autoLog
    public ResponseResult resetPassword(@RequestBody PwVo vo){
        return accountService.resetPassword(vo.getId());
    }

    @PutMapping("/revisePassword")
    @ApiOperation(value = "修改密码")
    @autoLog
    public ResponseResult revisePassword(@RequestBody PwVo vo){
        return accountService.revisePassword(vo);
    }
}
