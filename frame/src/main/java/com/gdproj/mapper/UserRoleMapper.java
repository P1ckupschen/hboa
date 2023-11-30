package com.gdproj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gdproj.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Administrator
* @description 针对表【sys_user_role(用户和角色关联表)】的数据库操作Mapper
* @createDate 2023-11-24 14:19:44
* @Entity generator.entity.UserRole
*/
@Mapper
public interface UserRoleMapper extends BaseMapper<UserRole> {

}




