package com.gdproj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.entity.UserRole;
import com.gdproj.mapper.UserRoleMapper;
import com.gdproj.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Service实现
* @createDate 2023-11-24 14:19:44
*/
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService {

    @Override
    public List<Integer> getRoleIdsById(Integer deployeeId) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getDeployeeId,deployeeId);
        List<UserRole> list = list(queryWrapper);
        return list.stream().map(UserRole::getRoleId).collect(Collectors.toList());
    }
}




