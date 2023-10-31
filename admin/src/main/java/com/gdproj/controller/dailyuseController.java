package com.gdproj.controller;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.annotation.autoLog;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.DailyUse;
import com.gdproj.entity.ToolCategory;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.ToolCategoryService;
import com.gdproj.service.DailyUseService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.CategoryVo;
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

        boolean b =false;

        try {

            b = dailyUseService.insertDailyUse(dailyUse);

            return ResponseResult.okResult(b);
        }catch (Exception e){
            //审批输入失败
            return ResponseResult.errorResult(AppHttpCodeEnum.INSERT_ERROR);
        }

    }








    @GetMapping("/getCategoryList")
    @autoLog
    @ApiOperation(value = "查询类型列表")
    public ResponseResult getCategoryList(@RequestParam(required = false) Integer pageNum,@RequestParam(required = false) Integer pageSize){

        pageDto pagedto = new pageDto(pageNum, pageSize);

        IPage<ToolCategory> categoryList = new Page<>();

        try {

            if(ObjectUtil.isNull(pagedto.getPageNum())){
                List<ToolCategory> list = categoryService.list();
                return ResponseResult.okResult(list);
            }else{
                categoryList = categoryService.getDailyUseCategoryList(pagedto);
                PageVo<List<ToolCategory>> pageList = new PageVo<>();
                pageList.setData(categoryList.getRecords());
                pageList.setTotal((int) categoryList.getTotal());
                return ResponseResult.okResult(pageList);
            }

        }catch (Exception e){
            return  ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }

    }

    @PutMapping("updateCategory")
    @autoLog
    @ApiOperation(value = "更新类型")
    public ResponseResult updateCategory(@RequestBody CategoryVo category){

        ToolCategory category1 = new ToolCategory();


        boolean b = false;

        try {
            category1 = BeanCopyUtils.copyBean(category, ToolCategory.class);

            b = categoryService.updateById(category1);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }


    }

    @PostMapping("insertCategory")
    @autoLog
    @ApiOperation(value = "新增类型")
    public ResponseResult insertCategory(@RequestBody CategoryVo category){

        ToolCategory category1 = new ToolCategory();

        boolean b = false;

        try {
            category1 = BeanCopyUtils.copyBean(category, ToolCategory.class);

            b = categoryService.save(category1);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.INSERT_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }

    }

    @DeleteMapping("deleteCategory")
    @autoLog
    @ApiOperation(value = "删除类型")
    public ResponseResult deleteCategory(@RequestParam("categoryId") Integer categoryId){


        System.out.println(categoryId);

        boolean b = false;

        try {

            b = categoryService.removeById(categoryId);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }

    }
}
