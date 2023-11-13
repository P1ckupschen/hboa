package com.gdproj.controller;


import cn.hutool.core.convert.NumberChineseFormatter;
import com.gdproj.annotation.autoLog;
import com.gdproj.result.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/common")
@Api(tags = "通用控制器方法")
public class commonController {

    @GetMapping("/NumberToChineseCap")
    @ApiOperation(value = "金额转大写")
    @autoLog
    public ResponseResult NumberToChineseCap(BigDecimal amount){
        return ResponseResult.okResult(NumberChineseFormatter.format(amount.doubleValue(), true, true));
    }
}
