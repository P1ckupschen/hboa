package com.gdproj.controller;


import com.gdproj.annotation.autoLog;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.ErrorLogService;
import com.gdproj.service.LogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/adminLog")
public class logController {

    @Autowired
    LogService logService;

    @Autowired
    ErrorLogService errorLogService;

    @GetMapping("/getLogList")
    @ApiOperation(value = "获取操作日志记录")
    @autoLog
    public ResponseResult getLogList(@Validated PageQueryDto queryDto){

            return logService.getLogList(queryDto);

    }


    //错误日志
    @GetMapping("/getErrorLogList")
    @ApiOperation(value = "获取错误日志记录")
    @autoLog
    public ResponseResult getErrorLogList(@Validated PageQueryDto queryDto){

        return errorLogService.getErrorLogList(queryDto);

    }
}
