package com.gdproj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.entity.Account;
import com.gdproj.entity.Deployee;
import com.gdproj.mapper.AccountMapper;
import com.gdproj.service.AccountService;
import com.gdproj.service.DeployeeService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.utils.JwtUtils;
import com.gdproj.vo.AccountVo;
import com.gdproj.vo.DeployeeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【sys_front_account】的数据库操作Service实现
* @createDate 2023-10-18 08:35:04
*/
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account>
    implements AccountService {

    @Autowired
    DeployeeService deployeeService;
    @Override
    public AccountVo getAccountInfo(String token) throws Exception {
        String subToken = token.substring(7);
        String id = (String) JwtUtils.parseJWT(subToken).get("id");
        //id是account 表的 主键id
        LambdaQueryWrapper<Account> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Account::getId,id);
        Account one = getOne(queryWrapper);
        AccountVo vo = BeanCopyUtils.copyBean(one, AccountVo.class);
        vo.setUsername(null);
        vo.setPassword(null);

        if(!ObjectUtil.isEmpty(one.getDeployeeId())){
            Deployee deployee = deployeeService.getById(one.getDeployeeId());
            vo.setDeployee(BeanCopyUtils.copyBean(deployee, DeployeeVo.class));
        }
        return vo;
    }


}




