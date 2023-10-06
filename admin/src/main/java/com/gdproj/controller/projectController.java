package com.gdproj.controller;

import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.ProjectService;
import com.gdproj.vo.selectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/adminProject")
public class projectController {

    @Autowired
    ProjectService projectService;

    @GetMapping("/getListForSelect")
    public ResponseResult getListForSelect(){

        List<selectVo> selectList = new ArrayList<>();

        try {

            selectList =projectService.getListForSelect();

            return ResponseResult.okResult(selectList) ;

        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }


    }
}
