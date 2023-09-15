package com.gdproj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.gdproj.entity.User;
import com.gdproj.mapper.UserMapper;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.UserService;
import com.gdproj.utils.RSAUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_user】的数据库操作Service实现
* @createDate 2023-09-12 08:33:21
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {


    public ResponseResult userLogin(String username, String password){


        RSAUtil rsaUtil = new RSAUtil();
        //正确
//        System.out.println(rsaUtil.decrypt(password));
//
//        System.out.println(rsaUtil.encrypt("Aa112211"));
//        System.out.println(rsaUtil.decrypt(rsaUtil.encrypt("Aa112211")));
//        if(   rsaUtil.decrypt(rsaUtil.encrypt("Aa112211")).equals("Aa112211")  ){
//            System.out.println("相等");
//        }

        //账号唯一
        LambdaQueryWrapper<User> lambdaQueryWrapper =new LambdaQueryWrapper<>();

        lambdaQueryWrapper.eq(User::getUsername,username);

        User one = getOne(lambdaQueryWrapper);

//        if(rsaUtil.decrypt(one.getPassword()).equals(rsaUtil.decrypt(password)) ){
//            //如果相等说明密码正确  生成token信息返回
//        }


        //前端加密传输密码
        return ResponseResult.okResult(one);
    }


    public void getUserInfo() {

    }



}




