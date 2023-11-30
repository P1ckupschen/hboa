package com.gdproj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.entity.UserRole;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Service
* @createDate 2023-11-24 14:19:44
*/
public interface UserRoleService extends IService<UserRole> {

    List<Integer> getRoleIdsById(Integer deployeeId);
}
