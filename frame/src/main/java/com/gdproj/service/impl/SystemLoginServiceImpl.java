package com.gdproj.service.impl;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.gdproj.entity.Account;
import com.gdproj.entity.User;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.AccountService;
import com.gdproj.service.SystemLoginService;
import com.gdproj.service.UserService;
import com.gdproj.utils.JwtUtils;
import com.gdproj.utils.RSAUtil;
import com.gdproj.vo.AccountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;


/**
 * @author 35238
 * @date 2023/7/22 0022 21:39
 */
@Service
//认证，判断用户登录是否成功
public class SystemLoginServiceImpl implements SystemLoginService {

    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService;
    @Override
    public ResponseResult login(User user , HttpSession session) {

        User one = new User();

        try {
            //找到数据库的对应
            LambdaQueryWrapper<User> lambdaQueryWrapper =new LambdaQueryWrapper<>();

            lambdaQueryWrapper.eq(User::getUsername,user.getUsername());

            one = userService.getOne(lambdaQueryWrapper);

            one.setLoginTime(new Date());

            userService.updateById(one);
            //密码正确 登录信息 生成token
            if(RSAUtil.decrypt(one.getPassword()).equals(RSAUtil.decrypt(user.getPassword()))){

                String jwtToken = JwtUtils.getJwtToken(one.getId().toString());

//                ServletContext holdContext = session.getServletContext();
//                // 先判断密码是否正确  再看是否已有登录
//                if(!ObjectUtil.isEmpty(session.getServletContext().getAttribute("用户"+one.getId()))){
//                    // 说明已有登陆 刷新存的token值
//                    holdContext.setAttribute("用户"+one.getId() ,jwtToken);
//                }else{
//                    // 如果没有 说明用户未先登录 往里面放值  TODO 以什么为主键 以什么为值
//                    holdContext.setAttribute("用户"+one.getId(),one.getId());
//                }

                return ResponseResult.okResult(jwtToken);

            }else{

                return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);

            }

        }catch (Exception e){

            e.printStackTrace();

            return  ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_FAIL);
        }

//        System.out.println(RSAUtil.encrypt("Aa112211"));
//        System.out.println(RSAUtil.decrypt("H7bgrDF48dcd4+RHyw0cRzzI/SBicgj+aMXqdPSZIe/IrFQ06jm9AkScZN+QddRAWrLmk9Ea5nWhr3FixtR6t+se+3F0ovdpJ+s5Crt9wkKDagqi1yKN0CxFv6k0JXCTvV9IBL8QhIXQY5Rf64qdYhQ2b6wd9ULsHb+NMVV/J/GmtrkiEHGPTzllACzkG8UK7dTMdjTq8mnsYDpBqAm6TowSyG54BGlEXk8ny0HGZ3/HNFItaQbF0wjOU+4HoX79qFLvDBv8kGAJvzP86fWIu2jGqwYOH+/CUVX5Gn2wcspzXfjC1XQGCeN1JoM1+pRtHkE8CGRnmKcQoN0965TgDg=="));

    }

    @Override
    public ResponseResult frontLogin(AccountVo vo , HttpSession session) {
        Account one = new Account();

        try {


            //找到数据库的对应
            LambdaQueryWrapper<Account> lambdaQueryWrapper =new LambdaQueryWrapper<>();

            lambdaQueryWrapper.eq(Account::getUsername,vo.getUsername());

            one = accountService.getOne(lambdaQueryWrapper);

            one.setLoginTime(new Date());

            LambdaUpdateWrapper<Account> updateWrapper = new LambdaUpdateWrapper<>();

            updateWrapper.set(Account::getLoginTime , new Date());

            if(!ObjectUtil.isEmpty(vo.getOpenid())){
                updateWrapper.eq(Account::getDeployeeId,one.getDeployeeId());
                updateWrapper.set(Account::getOpenId,vo.getOpenid());
            }
            accountService.update(updateWrapper);
            //如果 密码正确 登录信息 生成token
            if(RSAUtil.decrypt(one.getPassword()).equals(RSAUtil.decrypt(vo.getPassword()))){

                String jwtToken = JwtUtils.getJwtToken(one.getId().toString());

                return ResponseResult.okResult(jwtToken);

            }else{

                return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_ERROR);

            }

        }catch (Exception e){

            e.printStackTrace();

            return  ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_FAIL);
        }

//        System.out.println(RSAUtil.encrypt("Aa112211"));
//        System.out.println(RSAUtil.decrypt("H7bgrDF48dcd4+RHyw0cRzzI/SBicgj+aMXqdPSZIe/IrFQ06jm9AkScZN+QddRAWrLmk9Ea5nWhr3FixtR6t+se+3F0ovdpJ+s5Crt9wkKDagqi1yKN0CxFv6k0JXCTvV9IBL8QhIXQY5Rf64qdYhQ2b6wd9ULsHb+NMVV/J/GmtrkiEHGPTzllACzkG8UK7dTMdjTq8mnsYDpBqAm6TowSyG54BGlEXk8ny0HGZ3/HNFItaQbF0wjOU+4HoX79qFLvDBv8kGAJvzP86fWIu2jGqwYOH+/CUVX5Gn2wcspzXfjC1XQGCeN1JoM1+pRtHkE8CGRnmKcQoN0965TgDg=="));



    }
}