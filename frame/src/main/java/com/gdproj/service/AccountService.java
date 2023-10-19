package com.gdproj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.entity.Account;
import com.gdproj.vo.accountVo;

/**
* @author Administrator
* @description 针对表【sys_front_account】的数据库操作Service
* @createDate 2023-10-18 08:35:04
*/
public interface AccountService extends IService<Account> {

    accountVo getAccountInfo(String token) throws Exception;

}
