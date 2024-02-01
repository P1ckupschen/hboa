package com.gdproj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Role;
import com.gdproj.result.ResponseResult;
import com.gdproj.vo.RoleVo;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_role】的数据库操作Service
* @createDate 2023-09-11 14:04:31
*/
public interface RoleService extends IService<Role> {

    ResponseResult getRoleList(PageQueryDto queryDto);

    ResponseResult insertRole(RoleVo vo);

    ResponseResult updateRole(RoleVo vo);

    ResponseResult deleteRole(Integer id);

    List<String> getRoleById(Integer deployeeId);

    ResponseResult getRoleListForSelect();

    List<String> getListByRoleKey(String s);
}
