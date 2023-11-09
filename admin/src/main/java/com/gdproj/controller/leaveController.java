package com.gdproj.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.annotation.autoLog;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Leave;
import com.gdproj.entity.LeaveCategory;
import com.gdproj.entity.NotifyCategory;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.LeaveService;
import com.gdproj.service.leaveCategoryService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.CategoryVo;
import com.gdproj.vo.LeaveVo;
import com.gdproj.vo.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adminLeave")
@Api(tags = "请假功能")
public class leaveController {

    @Autowired
    LeaveService leaveService;

    @Autowired
    leaveCategoryService categoryService;

    @GetMapping("/getLeaveList")
    @autoLog
    @ApiOperation(value = "查询请假列表")
    public ResponseResult getLeaveList(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam(required = false,defaultValue = "+id")String sort,
                                       @RequestParam(required = false,defaultValue = "") String title ,
                                       @RequestParam(required = false) Integer departmentId,
                                       @RequestParam(required = false) Integer type,
                                       @RequestParam(required = false) String time){
        PageQueryDto pageDto = new PageQueryDto(pageNum,pageSize,departmentId,type,title,time,sort);

        IPage<LeaveVo> leaveList = new Page<LeaveVo>();

        try {
            leaveList = leaveService.getLeaveList(pageDto);

            PageVo<List<LeaveVo>> pageList = new PageVo<>();
            pageList.setData(leaveList.getRecords());
            pageList.setTotal((int) leaveList.getTotal());
            return ResponseResult.okResult(pageList);
        }catch (SystemException e){
            return ResponseResult.okResult(e.getCode(),e.getMsg());
        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }


    }

    @PutMapping("updateLeave")
    @autoLog
    @ApiOperation(value = "更新请假")
    public ResponseResult updateLeave(@RequestBody LeaveVo leaveVo){


        Leave updateLeave = BeanCopyUtils.copyBean(leaveVo, Leave.class);
//        vo中的 发布人  类型 部门
        System.out.println(updateLeave);

        boolean b = false;

        try {

            b = leaveService.updateById(updateLeave);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }


    }

    @PostMapping("insertLeave")
    @autoLog
    @ApiOperation(value = "新增请假同时添加流程信息")
    public ResponseResult insertLeave(@RequestBody LeaveVo leaveVo){

        Leave insertLeave = BeanCopyUtils.copyBean(leaveVo, Leave.class);

        boolean b = false;

        try {

//            b = leaveService.save(insertLeave);
            b = leaveService.insertLeave(insertLeave);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.INSERT_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }

    }

    @DeleteMapping("deleteLeave")
    @autoLog
    @ApiOperation(value = "删除请假")
    public ResponseResult deleteLeave(@RequestParam("leaveId") Integer leaveId){

        System.out.println(leaveId);

        boolean b = false;

        try {

            b = leaveService.removeById(leaveId);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }

    }

    @DeleteMapping("deleteLeaveList")
    @autoLog

    //批量删除
    public ResponseResult deleteLeaveList(@RequestBody Integer categoryId){

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
    @ApiOperation(value = "查询类型")
    public ResponseResult getCategoryList(@RequestParam(required = false) Integer pageNum,@RequestParam(required = false) Integer pageSize){

        PageQueryDto pagedto = new PageQueryDto(pageNum, pageSize);

        IPage<LeaveCategory> categoryList = new Page<>();

        try {
            if(ObjectUtil.isNull(pagedto.getPageNum())){
                List<LeaveCategory> list = categoryService.list();
                return ResponseResult.okResult(list);
            }else{
                categoryList = categoryService.getLeaveCategoryList(pagedto);

                PageVo<List<LeaveCategory>> pageList = new PageVo<>();
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

        LeaveCategory leavecategory = new LeaveCategory();

        boolean b = false;

        try {
            leavecategory = BeanCopyUtils.copyBean(category, LeaveCategory.class);

            b = categoryService.updateById(leavecategory);

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

        LeaveCategory leavecategory = new LeaveCategory();

        boolean b = false;

        try {
            leavecategory = BeanCopyUtils.copyBean(category, LeaveCategory.class);

            b = categoryService.save(leavecategory);
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
