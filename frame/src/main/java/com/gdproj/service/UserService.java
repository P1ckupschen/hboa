package com.gdproj.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.entity.User;
import com.gdproj.result.ResponseResult;
import com.gdproj.vo.UserVo;

/**
* @author Administrator
* @description 针对表【sys_user】的数据库操作Service
* @createDate 2023-09-12 08:33:21
*/
public interface UserService extends IService<User> {

    ResponseResult userLogin(String username , String password);


    UserVo getUserInfo(String token) throws Exception;
}
