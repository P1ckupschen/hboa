package com.gdproj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.entity.Account;
import com.gdproj.entity.Deployee;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.AccountMapper;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.AccountService;
import com.gdproj.service.DeployeeService;
import com.gdproj.service.MenuService;
import com.gdproj.service.RoleService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.utils.JwtUtils;
import com.gdproj.utils.RSAUtil;
import com.gdproj.vo.AccountVo;
import com.gdproj.vo.DeployeeVo;
import com.gdproj.vo.PwVo;
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

    @Autowired
    MenuService menuService;

    @Autowired
    RoleService roleService;

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

        //设置权限信息
//        vo.setMenu(menuService.getPermissionsByDeployeeId(one.getDeployeeId()));
        vo.setRole(roleService.getRoleById(one.getDeployeeId()));
        return vo;
    }

    @Override
    public ResponseResult updateAccount(String pw , Integer id) {

        LambdaUpdateWrapper<Account> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Account::getDeployeeId,id).set(Account::getPassword, RSAUtil.encrypt(pw));
        return ResponseResult.okResult(update(updateWrapper));
    }

    @Override
    public ResponseResult createAccount(String username, String password, String id) {

        if(ObjectUtil.isEmpty(username) || ObjectUtil.isEmpty(password)){
            throw new SystemException(AppHttpCodeEnum.ACCOUNT_NULL);
        }
        //save 查deployee表
        return null;
    }

    @Override
    public String parseDeployeePasswordById(Integer deployeeId) {

        LambdaQueryWrapper<Account> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Account::getDeployeeId,deployeeId);
        Account one = getOne(queryWrapper);
        return RSAUtil.decrypt(one.getPassword());
    }

    @Override
    public Account getAccountById(Integer deployeeId) {
        LambdaQueryWrapper<Account> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Account::getDeployeeId,deployeeId);
        return getOne(queryWrapper);
    }

    /**
     * 重置密码
     *
     *  TODO 是不是可以同一个方法呢？默认 requestparam 值为 000000
     * */
    @Override
    public ResponseResult resetPassword(Integer id) {
        LambdaUpdateWrapper<Account> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Account::getDeployeeId , id).set(Account::getPassword , RSAUtil.DEFAULT_PASSWORD);
        if(update(updateWrapper)){
            return ResponseResult.okResult(update(updateWrapper));
        }else{
            return ResponseResult.errorResult(AppHttpCodeEnum.REVISE_PASSWORD_ERROR);
        }
    }
    /**
     * 修改密码
     * */
    @Override
    public ResponseResult revisePassword(PwVo vo) {
        LambdaQueryWrapper<Account> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Account::getDeployeeId , vo.getId());
        Account one = getOne(queryWrapper);
        if(RSAUtil.decrypt(vo.getOldPw()).equals(RSAUtil.decrypt(one.getPassword()))){
            LambdaUpdateWrapper<Account> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Account::getDeployeeId , vo.getId()).set(Account::getPassword , vo.getNewPw());
            if(update(updateWrapper)){
                return ResponseResult.okResult(update(updateWrapper));
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.REVISE_PASSWORD_ERROR);
            }
        }else{
            throw new SystemException(AppHttpCodeEnum.REVISE_PASSWORD_ERROR);
        }

    }

}




