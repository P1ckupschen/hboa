package com.gdproj.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.annotation.autoLog;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.DailyUse;
import com.gdproj.entity.DailyUseRecord;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.DailyUseRecordService;
import com.gdproj.service.DailyUseService;
import com.gdproj.service.ToolCategoryService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.DailyUseRecordVo;
import com.gdproj.vo.DailyUseVo;
import com.gdproj.vo.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/adminDailyUse")
@Api(tags = "日常领用功能")
public class dailyuseController {

    @Autowired
    DailyUseService dailyUseService;

    @Autowired
    ToolCategoryService categoryService;

    @Autowired
    DailyUseRecordService recordService;

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
        PageQueryDto pageDto = new PageQueryDto(pageNum,pageSize,departmentId,type,title,time,sort);

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

    @GetMapping("/getMyDailyUseList")
    @autoLog
    @ApiOperation(value = "查询我的日常领用列表")
    public ResponseResult getMyDailyUseList(@Validated PageQueryDto queryDto, HttpServletRequest request){

        IPage<DailyUseVo> DailyUseList = new Page<>();

        try {

            DailyUseList =  dailyUseService.getMyDailyUseList(queryDto,request);

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
        if(dailyUse.getCategoryId() == 1){
            boolean b = dailyUseService.updateById(dailyUse);
            if(b){
                return ResponseResult.okResult(b);
            }else{
                throw  new SystemException(AppHttpCodeEnum.UPDATE_ERROR);
            }
        }else{
            boolean b = dailyUseService.updateDailyUse(dailyUse);
            if(b){
                return ResponseResult.okResult(b);
            }else{
                throw  new SystemException(AppHttpCodeEnum.UPDATE_ERROR);
            }
        }

    }

    @DeleteMapping("/deleteDailyUse")
    @autoLog
    @ApiOperation(value = "删除")
    public ResponseResult deleteDailyUse(@RequestParam("dailyuseId") Integer dailyuseId){
        boolean b = dailyUseService.removeById(dailyuseId);
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
        PageQueryDto pageDto = new PageQueryDto(pageNum,pageSize,departmentId,type,title,time,sort);

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


    @PostMapping("/insertDailyUseRecord")
    @autoLog
    @ApiOperation(value = "新增记录")
    public ResponseResult insertDailyUseRecord(@RequestBody DailyUseRecordVo vo){

        DailyUseRecord record = BeanCopyUtils.copyBean(vo, DailyUseRecord.class);

        boolean b = recordService.save(record);
        if(b){
            return ResponseResult.okResult(b);
        }else{
            throw new SystemException(AppHttpCodeEnum.INSERT_ERROR);
        }

    }

    @PutMapping("/updateDailyUseRecord")
    @autoLog
    @ApiOperation(value = "更新记录")
    public ResponseResult updateDailyUseRecord(@RequestBody DailyUseRecordVo vo){

        DailyUseRecord record = BeanCopyUtils.copyBean(vo, DailyUseRecord.class);
        boolean b = recordService.updateById(record);
        if(b){
            return ResponseResult.okResult(b);
        }else{
            throw  new SystemException(AppHttpCodeEnum.UPDATE_ERROR);
        }

    }

    @DeleteMapping("/deleteDailyUseRecord")
    @autoLog
    @ApiOperation(value = "删除")
    public ResponseResult deleteDailyUseRecord(@RequestParam("recordId") Integer recordId){
        boolean b = recordService.removeById(recordId);
        if(b){
            return ResponseResult.okResult(b);
        }else{
            throw new SystemException(AppHttpCodeEnum.DELETE_ERROR);
        }
    }





}
