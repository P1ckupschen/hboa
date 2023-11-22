package com.gdproj.service;

import com.gdproj.entity.User;
import com.gdproj.result.ResponseResult;
import com.gdproj.vo.AccountVo;

import javax.servlet.http.HttpSession;

public interface SystemLoginService {

    //登录
    ResponseResult login(User user , HttpSession session);

    ResponseResult frontLogin(AccountVo vo , HttpSession session);
}