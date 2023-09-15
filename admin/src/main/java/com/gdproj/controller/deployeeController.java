package com.gdproj.controller;

import com.gdproj.result.ResponseResult;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/deployee")
public class deployeeController {
    @GetMapping("/getDeployeeList")
    public ResponseResult getdeployeeList(){
        return ResponseResult.okResult() ;
    }
}
