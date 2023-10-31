package com.gdproj.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.annotation.autoLog;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Overtime;
import com.gdproj.entity.NotifyCategory;
import com.gdproj.entity.OvertimeCategory;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.OvertimeService;
import com.gdproj.service.overtimeCategoryService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.CategoryVo;
import com.gdproj.vo.OvertimeVo;
import com.gdproj.vo.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adminOvertime")
@Api(tags = "加班功能")
public class overtimeController {

    @Autowired
    OvertimeService overtimeService;

    @Autowired
    overtimeCategoryService categoryService;

    @GetMapping("/getOvertimeList")
    @autoLog
    @ApiOperation(value = "查询加班列表")
    public ResponseResult getOvertimeList(@RequestParam Integer pageNum,
                                          @RequestParam Integer pageSize,
                                          @RequestParam(required = false,defaultValue = "+id")String sort,
                                          @RequestParam(required = false,defaultValue = "") String title ,
                                          @RequestParam(required = false) Integer departmentId,
                                          @RequestParam(required = false) Integer type,
                                          @RequestParam(required = false) String time){
        pageDto pageDto = new pageDto(pageNum,pageSize,departmentId,type,title,time,sort);

        IPage<OvertimeVo> overtimeList = new Page<OvertimeVo>();

        try {
            overtimeList = overtimeService.getOverTimeList(pageDto);

            PageVo<List<OvertimeVo>> pageList = new PageVo<>();
            pageList.setData(overtimeList.getRecords());
            pageList.setTotal((int) overtimeList.getTotal());
            return ResponseResult.okResult(pageList);

        }catch (SystemException e) {
            return ResponseResult.okResult(e.getCode(), e.getMsg());
        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }


    }

    @PutMapping("updateOvertime")
    @autoLog
    @ApiOperation(value = "更新加班")
    public ResponseResult updateOvertime(@RequestBody OvertimeVo overtimeVo){


        Overtime updateOvertime = BeanCopyUtils.copyBean(overtimeVo, Overtime.class);
//        vo中的 发布人  类型 部门
        System.out.println(updateOvertime);

        boolean b = false;

        try {

            b = overtimeService.updateById(updateOvertime);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }


    }

    @PostMapping("insertOvertime")
    @autoLog
    @ApiOperation(value = "新增加班同时添加流程信息")
    public ResponseResult insertOvertime(@RequestBody OvertimeVo overtimeVo){

        Overtime insertOvertime = BeanCopyUtils.copyBean(overtimeVo, Overtime.class);

        System.out.println(insertOvertime);

        boolean b = false;

        try {

//            b = overtimeService.save(insertOvertime);

            b = overtimeService.insertOvertime(insertOvertime);
            //插入一条流程信息表

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.INSERT_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }

    }

    @DeleteMapping("deleteOvertime")
    @autoLog
    @ApiOperation(value = "删除加班")
    public ResponseResult deleteOvertime(@RequestParam("overtimeId") Integer overtimeId){

        System.out.println(overtimeId);

        boolean b = false;

        try {

            b = overtimeService.removeById(overtimeId);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }

    }

    @DeleteMapping("deleteOvertimeList")
    @autoLog
    //批量删除
    public ResponseResult deleteOvertimeList(@RequestBody Integer categoryId){

        NotifyCategory notifycategory = new NotifyCategory();

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



    //类型的增删改查

    @GetMapping("/getCategoryList")
    @autoLog
    @ApiOperation(value = "查询类型列表")
    public ResponseResult getCategoryList(@RequestParam(required = false) Integer pageNum,@RequestParam(required = false) Integer pageSize){

        pageDto pagedto = new pageDto(pageNum, pageSize);

        IPage<OvertimeCategory> categoryList = new Page<>();

        try {

            if(ObjectUtil.isNull(pagedto.getPageNum())){
                List<OvertimeCategory> list = categoryService.list();
                return ResponseResult.okResult(list);
            }else{
                categoryList = categoryService.getOvertimeCategoryList(pagedto);
                PageVo<List<OvertimeCategory>> pageList = new PageVo<>();
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

        OvertimeCategory overcategory = new OvertimeCategory();


        boolean b = false;

        try {
            overcategory = BeanCopyUtils.copyBean(category, OvertimeCategory.class);

            b = categoryService.updateById(overcategory);

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

        OvertimeCategory overcategory = new OvertimeCategory();

        boolean b = false;

        try {
            overcategory = BeanCopyUtils.copyBean(category, OvertimeCategory.class);

            b = categoryService.save(overcategory);

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
