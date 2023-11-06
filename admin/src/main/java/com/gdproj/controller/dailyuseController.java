package com.gdproj.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.annotation.autoLog;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.DailyUse;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.DailyUseService;
import com.gdproj.service.ToolCategoryService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.DailyUseRecordVo;
import com.gdproj.vo.DailyUseVo;
import com.gdproj.vo.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adminDailyUse")
@Api(tags = "日常领用功能")
public class dailyuseController {

    @Autowired
    DailyUseService dailyUseService;

    @Autowired
    ToolCategoryService categoryService;

    @GetMapping("/getDailyUseList")
    @autoLog
    @ApiOperation(value = "查询日常领用列表")
    public ResponseResult getDailyUseList(@RequestParam Integer pageNum,
                                           @RequestParam Integer pageSize,
                                           @RequestParam(required = false,defaultValue = "+id")String sort,
                                           @RequestParam(required = false,defaultValue = "") String title ,
                                           @RequestParam(required = false) Integer departmentId,
                                           @RequestParam(required = false) Integer type,
                                           @RequestParam(required = false) String time){
        pageDto pageDto = new pageDto(pageNum,pageSize,departmentId,type,title,time,sort);

        IPage<DailyUseVo> DailyUseList = new Page<>();

        try {

            DailyUseList =  dailyUseService.getDailyUseList(pageDto);

            PageVo<List<DailyUseVo>> pageList = new PageVo<>();

            pageList.setData(DailyUseList.getRecords());

            pageList.setTotal((int) DailyUseList.getTotal());

            return ResponseResult.okResult(pageList);

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }

    }

    @PostMapping("/insertDailyUse")
    @autoLog
    @ApiOperation(value = "新增日常领用申请/入库申请")
    public ResponseResult insertDailyUse(@RequestBody DailyUseVo vo){

        DailyUse dailyUse = BeanCopyUtils.copyBean(vo, DailyUse.class);

        boolean b = dailyUseService.insertDailyUse(dailyUse);
        if(b){
            return ResponseResult.okResult(b);
        }else{
            throw new SystemException(AppHttpCodeEnum.INSERT_ERROR);
        }

    }

    @PutMapping("/updateDailyUse")
    @autoLog
    @ApiOperation(value = "更新日常领用申请/入库申请")
    public ResponseResult updateDailyUse(@RequestBody DailyUseVo vo){

        DailyUse dailyUse = BeanCopyUtils.copyBean(vo, DailyUse.class);
        // TODO 如果修改了清单怎么办？ 通过过无法修改
        boolean b = dailyUseService.updateById(dailyUse);
        if(b){
            return ResponseResult.okResult(b);
        }else{
            throw  new SystemException(AppHttpCodeEnum.UPDATE_ERROR);
        }

    }

    @PostMapping("/deleteDailyUse")
    @autoLog
    @ApiOperation(value = "删除")
    public ResponseResult deleteDailyUse(@RequestBody Integer id){
        boolean b = dailyUseService.removeById(id);
        if(b){
            return ResponseResult.okResult(b);
        }else{
            throw new SystemException(AppHttpCodeEnum.DELETE_ERROR);
        }
    }



    //record

    @GetMapping("/getDailyUseRecordList")
    @autoLog
    @ApiOperation(value = "查询日常领用详细记录列表")
    public ResponseResult getDailyUseRecordList(@RequestParam Integer pageNum,
                                          @RequestParam Integer pageSize,
                                          @RequestParam(required = false,defaultValue = "+id")String sort,
                                          @RequestParam(required = false,defaultValue = "") String title ,
                                          @RequestParam(required = false) Integer departmentId,
                                          @RequestParam(required = false) Integer type,
                                          @RequestParam(required = false) String time){
        pageDto pageDto = new pageDto(pageNum,pageSize,departmentId,type,title,time,sort);

        IPage<DailyUseRecordVo> DailyUseRecordList = new Page<>();

        try {

            DailyUseRecordList =  dailyUseService.getDailyUseRecordList(pageDto);

            PageVo<List<DailyUseRecordVo>> pageList = new PageVo<>();

            pageList.setData(DailyUseRecordList.getRecords());

            pageList.setTotal((int) DailyUseRecordList.getTotal());

            return ResponseResult.okResult(pageList);

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }

    }









}
