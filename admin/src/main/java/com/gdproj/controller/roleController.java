package com.gdproj.controller;

import com.gdproj.annotation.autoLog;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.RoleService;
import com.gdproj.vo.RoleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Role")
@Api(tags = "角色管理")
public class roleController {


    @Autowired
    RoleService roleService;


    @GetMapping("/getListForSelect")
    @ApiOperation(value = "查询角色列表")
    @autoLog
    public ResponseResult getRoleListForSelect(){
        return roleService.getRoleListForSelect();
    }


    @GetMapping("/getRoleList")
    @ApiOperation(value = "查询角色列表")
    @autoLog
    public ResponseResult getRoleList(@Validated PageQueryDto queryDto){
        return roleService.getRoleList(queryDto);
    }

    @PostMapping("/insertRole")
    @ApiOperation(value = "新增角色")
    @autoLog
    public ResponseResult insertRole(@RequestBody RoleVo vo){
        return roleService.insertRole(vo);
    }
    @PutMapping("/updateRole")
    @ApiOperation(value = "修改角色")
    @autoLog
    public ResponseResult updateRole(@RequestBody RoleVo vo){
        return roleService.updateRole(vo);
    }
    @DeleteMapping("/deleteRole")
    @ApiOperation(value = "删除角色")
    @autoLog
    public ResponseResult deleteRole(@RequestParam("roleId") Integer id){
        return roleService.deleteRole(id);
    }
}
