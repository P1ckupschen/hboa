package com.gdproj.controller;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.*;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.ReportService;
import com.gdproj.service.reportCategoryService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/adminReport")
public class reportController {

    @Autowired
    ReportService reportService;

    @Autowired
    reportCategoryService categoryService;

    @GetMapping("/getReportList")
    public ResponseResult getReportList(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam(required = false,defaultValue = "+id")String sort,
                                        @RequestParam(required = false,defaultValue = "") String title ,
                                        @RequestParam(required = false) Integer departmentId,
                                        @RequestParam(required = false) Integer type,
                                        @RequestParam(required = false) String time){

        pageDto pageDto = new pageDto(pageNum,pageSize,departmentId,type,title,time,sort);

        IPage<reportVo> reportList = new Page<reportVo>();

        try {
            reportList = reportService.getReportList(pageDto);

            pageVo<List<reportVo>> pageList = new pageVo<>();
            pageList.setData(reportList.getRecords());
            pageList.setTotal((int) reportList.getTotal());
            return ResponseResult.okResult(pageList);
        }catch (SystemException e){
            return ResponseResult.errorResult(e.getCode(),e.getMsg());
        } catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }


    }


    @PutMapping("updateReport")
    public ResponseResult updateReport(@RequestBody reportVo reportVo){


        Report updateReport = BeanCopyUtils.copyBean(reportVo, Report.class);
//        vo中的 发布人  类型 部门
        System.out.println(updateReport);

        boolean b = false;

        try {

            b = reportService.updateById(updateReport);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }


    }

    @PostMapping("insertReport")
    public ResponseResult insertReport(@RequestBody reportVo reportVo){

        Report updateReport = BeanCopyUtils.copyBean(reportVo, Report.class);

        boolean b = false;

        try {

            b = reportService.save(updateReport);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.INSERT_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }

    }

    @DeleteMapping("deleteReport")
    public ResponseResult deleteReport(@PathParam("reportId") Integer reportId){

        System.out.println(reportId);

        boolean b = false;

        try {

            b = reportService.removeById(reportId);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }

    }

    @DeleteMapping("deleteReportList")
    //批量删除                                          TODO
    public ResponseResult deleteReportList(@RequestBody Integer categoryId){

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

        IPage<reportCategory> categoryList = new Page<>();

        try {

            if(ObjectUtil.isNull(pagedto.getPageNum())){
                List<reportCategory> list = categoryService.list();

                return ResponseResult.okResult(list);
            }else{

                categoryList = categoryService.getReportCategoryList(pagedto);
                pageVo<List<reportCategory>> pageList = new pageVo<>();
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

        reportCategory reportcategory = new reportCategory();

        boolean b = false;

        try {
            reportcategory = BeanCopyUtils.copyBean(category, reportCategory.class);

            b = categoryService.updateById(reportcategory);

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

        reportCategory reportcategory = new reportCategory();

        boolean b = false;

        try {
            reportcategory = BeanCopyUtils.copyBean(category, reportCategory.class);

            b = categoryService.save(reportcategory);

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

        reportCategory reportcategory = new reportCategory();

        System.out.println(categoryId);

        boolean b = false;

        try {
//            notifycategory = BeanCopyUtils.copyBean(category, notifyCategory.class);

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
