package com.gdproj.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.annotation.autoLog;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Department;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.DepartmentService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.DepartmentVo;
import com.gdproj.vo.PageVo;
import com.gdproj.vo.SelectVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

        List<SelectVo> selectList = new ArrayList<>();

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
    public ResponseResult getDepartmentList(
            @RequestParam Integer pageNum,
            @RequestParam Integer pageSize,
            @RequestParam(required = false,defaultValue = "+id")String sort,
            @RequestParam(required = false,defaultValue = "") String title ,
            @RequestParam(required = false) Integer departmentId,
            @RequestParam(required = false) Integer type,
            @RequestParam(required = false) String time){


        pageDto pageDto = new pageDto(pageNum,pageSize,departmentId,type,title,time,sort);

        IPage<DepartmentVo> departmentList = new Page<DepartmentVo>();

        try {
            departmentList = departmentService.getDepartmentList(pageDto);
            //生成树结构

            PageVo<List<DepartmentVo>> pageList = new PageVo<>();
            pageList.setData(departmentList.getRecords());
            pageList.setTotal((int) departmentList.getTotal());
            return ResponseResult.okResult(pageList);
        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.DEPARTMENT_LIST_ERROR);
        }

    }

    @PutMapping("updateDepartment")
    @autoLog
    @ApiOperation(value = "更新部门")
    public ResponseResult updateDepartment(@RequestBody DepartmentVo vo){

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
    public ResponseResult insertDepartment(@RequestBody DepartmentVo vo){

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
    public ResponseResult deleteDepartment(@RequestParam("departmentId") Integer id){

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
