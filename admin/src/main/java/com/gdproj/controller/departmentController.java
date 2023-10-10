package com.gdproj.controller;


import com.gdproj.entity.Department;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.DepartmentService;
import com.gdproj.vo.selectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/department")
public class departmentController {

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/getListForSelect")
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
    public ResponseResult getDepartmentList(){
        List<Department> list = new ArrayList<>();
        try {
            list = departmentService.list();
        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.DEPARTMENT_LIST_ERROR);
        }
        return ResponseResult.okResult(list);
    }
}
