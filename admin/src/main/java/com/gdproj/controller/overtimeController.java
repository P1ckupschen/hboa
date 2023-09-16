package com.gdproj.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.dto.pageDto;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.OvertimeService;
import com.gdproj.vo.leaveVo;
import com.gdproj.vo.overtimeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/overtime")
public class overtimeController {

    @Autowired
    OvertimeService overtimeService;

    @GetMapping("/getOvertimeList")
    public ResponseResult getOvertimeList(@RequestParam Integer pageNum,
                                          @RequestParam Integer pageSize,
                                          @RequestParam(required = false,defaultValue = "+id")String sort,
                                          @RequestParam(required = false,defaultValue = "") String title ,
                                          @RequestParam(required = false) Integer type,
                                          @RequestParam(required = false) String time){
        pageDto pageDto = new pageDto(pageNum,pageSize,type,title,time,sort);

        IPage<overtimeVo> overtimeList = new Page<overtimeVo>();

        try {
            overtimeList = overtimeService.getOverTimeList(pageDto);
        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }

        return ResponseResult.okResult(overtimeList);

    }

}
