package com.gdproj.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Leave;
import com.gdproj.entity.Notify;
import com.gdproj.entity.leaveCategory;
import com.gdproj.entity.notifyCategory;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.leaveCategoryService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.*;
import org.omg.PortableInterceptor.INACTIVE;
import com.gdproj.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/adminLeave")
public class leaveController {

    @Autowired
    LeaveService leaveService;

    @Autowired
    leaveCategoryService categoryService;

    @GetMapping("/getLeaveList")
    public ResponseResult getLeaveList(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam(required = false,defaultValue = "+id")String sort,
                                       @RequestParam(required = false,defaultValue = "") String title ,
                                       @RequestParam(required = false) Integer departmentId,
                                       @RequestParam(required = false) Integer type,
                                       @RequestParam(required = false) String time){
        pageDto pageDto = new pageDto(pageNum,pageSize,departmentId,type,title,time,sort);

        IPage<leaveVo> leaveList = new Page<leaveVo>();

        try {
            leaveList = leaveService.getLeaveList(pageDto);

            pageVo<List<leaveVo>> pageList = new pageVo<>();
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
    public ResponseResult updateLeave(@RequestBody leaveVo leaveVo){


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
    public ResponseResult insertLeave(@RequestBody leaveVo leaveVo){

        Leave insertLeave = BeanCopyUtils.copyBean(leaveVo, Leave.class);

        boolean b = false;

        try {

            b = leaveService.save(insertLeave);

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
    public ResponseResult deleteLeave(@PathParam("leaveId") Integer leaveId){

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
    //批量删除
    public ResponseResult deleteLeaveList(@RequestBody Integer categoryId){

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

        IPage<leaveCategory> categoryList = new Page<>();

        try {
            if(ObjectUtil.isNull(pagedto.getPageNum())){
                List<leaveCategory> list = categoryService.list();
                return ResponseResult.okResult(list);
            }else{
                categoryList = categoryService.getLeaveCategoryList(pagedto);

                pageVo<List<leaveCategory>> pageList = new pageVo<>();
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

        leaveCategory leavecategory = new leaveCategory();

        boolean b = false;

        try {
            leavecategory = BeanCopyUtils.copyBean(category, leaveCategory.class);

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
    public ResponseResult insertCategory(@RequestBody categoryVo category){

        leaveCategory leavecategory = new leaveCategory();

        boolean b = false;

        try {
            leavecategory = BeanCopyUtils.copyBean(category, leaveCategory.class);

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
