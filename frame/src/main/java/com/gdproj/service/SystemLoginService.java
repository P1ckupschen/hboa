package com.gdproj.service;

import com.gdproj.entity.User;
import com.gdproj.result.ResponseResult;

public interface SystemLoginService {

    //登录
    ResponseResult login(User user);

}