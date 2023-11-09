package com.gdproj.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.annotation.autoLog;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.LogService;
import com.gdproj.vo.LogVo;
import com.gdproj.vo.PageVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/adminLog")
public class logController {

    @Autowired
    LogService logService;

    @GetMapping("/getLogList")
    @ApiOperation(value = "获取操作日志记录")
    @autoLog
    public ResponseResult getLogList(@RequestParam Integer pageNum,
                                     @RequestParam Integer pageSize,
                                     @RequestParam(required = false,defaultValue = "+id")String sort,
                                     @RequestParam(required = false,defaultValue = "") String title ,
                                     @RequestParam(required = false) Integer departmentId,
                                     @RequestParam(required = false) Integer type,
                                     @RequestParam(required = false) String time){

        PageQueryDto pageDto = new PageQueryDto(pageNum,pageSize,departmentId,type,title,time,sort);

        IPage<LogVo> logList = new Page<LogVo>();

        try {
            logList = logService.getLogList(pageDto);

            PageVo<List<LogVo>> pageList = new PageVo<>();
            pageList.setData(logList.getRecords());
            pageList.setTotal((int) logList.getTotal());
            return ResponseResult.okResult(pageList);
        }catch (SystemException e){
            return ResponseResult.errorResult(e.getCode(),e.getMsg());
        } catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }
}
