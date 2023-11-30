package com.gdproj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gdproj.entity.Role;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Administrator
* @description 针对表【sys_role(角色信息表)】的数据库操作Mapper
* @createDate 2023-11-24 14:19:44
* @Entity generator.entity.Role
*/
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

}




