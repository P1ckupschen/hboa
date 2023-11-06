package com.gdproj.controller;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.annotation.autoLog;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Report;
import com.gdproj.entity.NotifyCategory;
import com.gdproj.entity.ReportCategory;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.ReportService;
import com.gdproj.service.reportCategoryService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.CategoryVo;
import com.gdproj.vo.PageVo;
import com.gdproj.vo.ReportVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adminReport")
@Api(tags = "汇报功能")
public class reportController {

    @Autowired
    ReportService reportService;

    @Autowired
    reportCategoryService categoryService;

    @GetMapping("/getReportList")
    @autoLog
    @ApiOperation(value = "查询汇报列表")
    public ResponseResult getReportList(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam(required = false,defaultValue = "+id")String sort,
                                        @RequestParam(required = false,defaultValue = "") String title ,
                                        @RequestParam(required = false) Integer departmentId,
                                        @RequestParam(required = false) Integer type,
                                        @RequestParam(required = false) String time){

        pageDto pageDto = new pageDto(pageNum,pageSize,departmentId,type,title,time,sort);

        IPage<ReportVo> reportList = new Page<ReportVo>();

        try {
            reportList = reportService.getReportList(pageDto);

            PageVo<List<ReportVo>> pageList = new PageVo<>();
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
    @autoLog
    @ApiOperation(value = "更新汇报")
    public ResponseResult updateReport(@RequestBody ReportVo reportVo){


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
    @autoLog
    @ApiOperation(value = "新增汇报")
    public ResponseResult insertReport(@RequestBody ReportVo reportVo){

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
    @autoLog
    @ApiOperation(value = "删除汇报")
    public ResponseResult deleteReport(@RequestParam("reportId") Integer reportId){

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
    @autoLog

    //批量删除                                          TODO
    public ResponseResult deleteReportList(@RequestBody Integer categoryId){

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

        IPage<ReportCategory> categoryList = new Page<>();

        try {

            if(ObjectUtil.isNull(pagedto.getPageNum())){
                List<ReportCategory> list = categoryService.list();

                return ResponseResult.okResult(list);
            }else{

                categoryList = categoryService.getReportCategoryList(pagedto);
                PageVo<List<ReportCategory>> pageList = new PageVo<>();
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

        ReportCategory reportcategory = new ReportCategory();

        boolean b = false;

        try {
            reportcategory = BeanCopyUtils.copyBean(category, ReportCategory.class);

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
    @autoLog
    @ApiOperation(value = "新增类型")
    public ResponseResult insertCategory(@RequestBody CategoryVo category){

        ReportCategory reportcategory = new ReportCategory();

        boolean b = false;

        try {
            reportcategory = BeanCopyUtils.copyBean(category, ReportCategory.class);

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
    @autoLog
    @ApiOperation(value = "删除类型")
    public ResponseResult deleteCategory(@RequestParam("categoryId") Integer categoryId){

        ReportCategory reportcategory = new ReportCategory();

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
