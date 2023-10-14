package com.gdproj.controller;


import com.gdproj.annotation.autoLog;
import com.gdproj.entity.Department;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.DepartmentService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.departmentVo;
import com.gdproj.vo.selectVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/department")
@Api(tags = "部门功能")
public class departmentController {

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/getListForSelect")
    @autoLog
    @ApiOperation(value = "查询用于选择的部门列表")
    public ResponseResult getListForSelect(){

        List<selectVo> selectList = new ArrayList<>();

        try {

            selectList =departmentService.getListForSelect();

            return ResponseResult.okResult(selectList) ;

        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }


    }


    @GetMapping("/getDepartmentList")
    @autoLog
    @ApiOperation(value = "查询部门列表")
    public ResponseResult getDepartmentList(){
        List<departmentVo> list = new ArrayList<>();
        try {
            list = departmentService.getDepartmentList();
//            list = departmentService.list();
            //生成树结构

        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.DEPARTMENT_LIST_ERROR);
        }
        return ResponseResult.okResult(list);
    }

    @PutMapping("updateDepartment")
    @autoLog
    @ApiOperation(value = "更新部门")
    public ResponseResult updateDepartment(@RequestBody departmentVo vo){


        Department updateInfo = BeanCopyUtils.copyBean(vo, Department.class);
//        vo中的 发布人  类型 部门
        System.out.println(updateInfo);

        boolean b = false;

        try {

            b = departmentService.updateById(updateInfo);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }


    }

    @PostMapping("insertDepartment")
    @autoLog
    @ApiOperation(value = "新增部门")
    public ResponseResult insertDepartment(@RequestBody departmentVo vo){

        Department insertInfo = BeanCopyUtils.copyBean(vo, Department.class);

        boolean b = false;

        try {

            b = departmentService.save(insertInfo);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.INSERT_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }

    }

    @DeleteMapping("deleteDepartment")
    @autoLog
    @ApiOperation(value = "删除部门")
    public ResponseResult deleteDepartment(@PathParam("departmentId") Integer id){

        System.out.println(id);

        boolean b = false;

        try {

            b = departmentService.removeById(id);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }

    }
}
