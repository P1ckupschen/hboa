package com.gdproj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.entity.Account;
import com.gdproj.result.ResponseResult;
import com.gdproj.vo.AccountVo;
import com.gdproj.vo.PwVo;

/**
* @author Administrator
* @description 针对表【sys_front_account】的数据库操作Service
* @createDate 2023-10-18 08:35:04
*/
public interface AccountService extends IService<Account> {

    AccountVo getAccountInfo(String token) throws Exception;

    ResponseResult updateAccount(String pw , Integer id);

    ResponseResult createAccount(String username, String password, String id);

    String parseDeployeePasswordById(Integer deployeeId);

    Account getAccountById(Integer deployeeId);

    ResponseResult resetPassword(Integer id);

    ResponseResult revisePassword(PwVo vo);

    Integer getDeployeeIdByOpenId(String openId);
}
