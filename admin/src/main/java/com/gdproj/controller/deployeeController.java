package com.gdproj.controller;

import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.result.ResponseResult;

import com.gdproj.service.DeployeeService;
import com.gdproj.vo.userVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/deployee")
public class deployeeController {

    @Autowired
    DeployeeService deployeeService;

    @GetMapping("/getListForSelect")
    public ResponseResult getListForSelect(){

        List<userVo> selectList = new ArrayList<>();

        try {

            selectList =deployeeService.getListForSelect();

            return ResponseResult.okResult(selectList) ;

        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }


    }
    @GetMapping("/getDeployeeList")
    public ResponseResult getdeployeeList(){

        return ResponseResult.okResult() ;
    }
}
