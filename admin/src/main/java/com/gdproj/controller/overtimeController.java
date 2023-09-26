package com.gdproj.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.*;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.OvertimeService;
import com.gdproj.service.overtimeCategoryService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/adminOvertime")
public class overtimeController {

    @Autowired
    OvertimeService overtimeService;

    @Autowired
    overtimeCategoryService categoryService;

    @GetMapping("/getOvertimeList")
    public ResponseResult getOvertimeList(@RequestParam Integer pageNum,
                                          @RequestParam Integer pageSize,
                                          @RequestParam(required = false,defaultValue = "+id")String sort,
                                          @RequestParam(required = false,defaultValue = "") String title ,
                                          @RequestParam(required = false) Integer departmentId,
                                          @RequestParam(required = false) Integer type,
                                          @RequestParam(required = false) String time){
        pageDto pageDto = new pageDto(pageNum,pageSize,departmentId,type,title,time,sort);

        IPage<overtimeVo> overtimeList = new Page<overtimeVo>();

        try {
            overtimeList = overtimeService.getOverTimeList(pageDto);

            pageVo<List<overtimeVo>> pageList = new pageVo<>();
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
    public ResponseResult updateOvertime(@RequestBody overtimeVo overtimeVo){


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
    public ResponseResult insertOvertime(@RequestBody overtimeVo overtimeVo){

        Overtime insertOvertime = BeanCopyUtils.copyBean(overtimeVo, Overtime.class);

        System.out.println(insertOvertime);

        boolean b = false;

        try {

            b = overtimeService.save(insertOvertime);

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
    public ResponseResult deleteOvertime(@PathParam("overtimeId") Integer overtimeId){

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
    //批量删除
    public ResponseResult deleteOvertimeList(@RequestBody Integer categoryId){

        notifyCategory notifycategory = new notifyCategory();

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
    public ResponseResult getCategoryList(@RequestParam(required = false) Integer pageNum,@RequestParam(required = false) Integer pageSize){

        pageDto pagedto = new pageDto(pageNum, pageSize);

        IPage<overtimeCategory> categoryList = new Page<>();

        try {

            if(ObjectUtil.isNull(pagedto.getPageNum())){
                List<overtimeCategory> list = categoryService.list();
                return ResponseResult.okResult(list);
            }else{
                categoryList = categoryService.getOvertimeCategoryList(pagedto);
                pageVo<List<overtimeCategory>> pageList = new pageVo<>();
                pageList.setData(categoryList.getRecords());
                pageList.setTotal((int) categoryList.getTotal());
                return ResponseResult.okResult(pageList);
            }

        }catch (Exception e){
            return  ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }

    }

    @PutMapping("updateCategory")
    public ResponseResult updateCategory(@RequestBody categoryVo category){

        overtimeCategory overcategory = new overtimeCategory();


        boolean b = false;

        try {
            overcategory = BeanCopyUtils.copyBean(category, overtimeCategory.class);

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
    public ResponseResult insertCategory(@RequestBody categoryVo category){

        overtimeCategory overcategory = new overtimeCategory();

        boolean b = false;

        try {
            overcategory = BeanCopyUtils.copyBean(category, overtimeCategory.class);

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
    public ResponseResult deleteCategory(@PathParam("categoryId") Integer categoryId){


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
