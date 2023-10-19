package com.gdproj.service;

import com.gdproj.entity.User;
import com.gdproj.result.ResponseResult;
import com.gdproj.vo.accountVo;

public interface SystemLoginService {

    //登录
    ResponseResult login(User user);

    ResponseResult frontLogin(accountVo vo);
}