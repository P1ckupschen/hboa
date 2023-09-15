package com.gdproj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gdproj.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Administrator
* @description 针对表【sys_user】的数据库操作Mapper
* @createDate 2023-09-12 08:33:21
* @Entity generator.domain.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




