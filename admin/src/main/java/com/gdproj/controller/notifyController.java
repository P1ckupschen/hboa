package com.gdproj.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.annotation.autoLog;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Notify;
import com.gdproj.entity.NotifyCategory;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.NotifyService;
import com.gdproj.service.notifyCategoryService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.CategoryVo;
import com.gdproj.vo.NotifyVo;
import com.gdproj.vo.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/adminNotify")
@Api(tags = "公告功能")
public class notifyController {

    @Autowired
    NotifyService notifyService;

    @Autowired
    notifyCategoryService categoryService;


    //公告的增删改查
    @GetMapping("/getNotifyList")
    @autoLog
    @ApiOperation(value = "查询公告列表")
    public ResponseResult getNotifyList(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam(required = false,defaultValue = "+id")String sort,
                                        @RequestParam(required = false,defaultValue = "") String title ,
                                        @RequestParam(required = false) Integer departmentId,
                                        @RequestParam(required = false) Integer type,
                                        @RequestParam(required = false) String time,
                                        @RequestParam(required = false) Integer status){
        PageQueryDto pageDto = new PageQueryDto(pageNum,pageSize,departmentId,type,title,time,sort);
        pageDto.setStatus(status);
        IPage<NotifyVo> notifyList = new Page<>();

        try {

            notifyList =  notifyService.getNotifyList(pageDto);

            PageVo<List<NotifyVo>> pageList = new PageVo<>();
            pageList.setData(notifyList.getRecords());
            pageList.setTotal((int) notifyList.getTotal());
            return ResponseResult.okResult(pageList);
        }catch (SystemException e) {
            return ResponseResult.okResult(e.getCode(), e.getMsg());
        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }

    }

    @GetMapping("/getEffectiveNotifyList")
    @autoLog
    @ApiOperation(value = "查询公告列表")
    public ResponseResult getEffectiveNotifyList(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam(required = false,defaultValue = "+id")String sort,
                                        @RequestParam(required = false,defaultValue = "") String title ,
                                        @RequestParam(required = false) Integer departmentId,
                                        @RequestParam(required = false) Integer type,
                                        @RequestParam(required = false) String time,
                                        @RequestParam(required = false) Integer status){
        PageQueryDto pageDto = new PageQueryDto(pageNum,pageSize,departmentId,type,title,time,sort);
        pageDto.setStatus(status);
        IPage<NotifyVo> notifyList = new Page<>();

        try {

            notifyList =  notifyService.getEffectiveNotifyList(pageDto);

            PageVo<List<NotifyVo>> pageList = new PageVo<>();
            pageList.setData(notifyList.getRecords());
            pageList.setTotal((int) notifyList.getTotal());
            return ResponseResult.okResult(pageList);
        }catch (SystemException e) {
            return ResponseResult.okResult(e.getCode(), e.getMsg());
        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }

    }

    /**
     * notify_status
     * 0:待审批
     * 1:生效/已审批
     * 2:弃用/不通过
     *
     * */

    @PutMapping("approvalNotify")
    @autoLog
    @ApiOperation(value = "审批公告")
    public ResponseResult approvalNotify(@RequestBody NotifyVo vo){
        try {
            Notify notify = BeanCopyUtils.copyBean(vo, Notify.class);

            Calendar instance = Calendar.getInstance();

            boolean b = notifyService.updateById(notify);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
            }

        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }

    }


    @PutMapping("updateNotify")
    @autoLog
    @ApiOperation(value = "更新公告")
    public ResponseResult updateNotify(@RequestBody NotifyVo notifyVo){

        Notify updateNotify = BeanCopyUtils.copyBean(notifyVo, Notify.class);
//        vo中的 发布人  类型 部门
        System.out.println(updateNotify);
        if(updateNotify.getNotifyStatus() == 1){
            updateNotify.setEffectiveDate(new Date());
        }
        boolean b = false;

        try {

            b = notifyService.updateById(updateNotify);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }


    }

    @PostMapping("insertNotify")
    @autoLog
    @ApiOperation(value = "新增公告")
    public ResponseResult insertNotify(@RequestBody NotifyVo notifyVo){

        Notify updateNotify = BeanCopyUtils.copyBean(notifyVo, Notify.class);

        boolean b = false;

        try {

            b = notifyService.save(updateNotify);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.INSERT_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }

    }

    @DeleteMapping("deleteNotify")
    @autoLog
    @ApiOperation(value = "删除公告")
    public ResponseResult deleteNotify(@RequestParam("notifyId") Integer notifyId){

        System.out.println(notifyId);

        boolean b = false;

        try {

            b = notifyService.removeById(notifyId);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }

    }

    @PutMapping("/updateReadListById")
    @autoLog
    @ApiOperation(value = "更新已读列表")
    public ResponseResult updateReadListById(@RequestParam("notifyId") Integer notifyid,
                                             @RequestParam("userId")Integer userId){
        return notifyService.updateReadListById(notifyid,userId);
    }


    @DeleteMapping("deleteNotifyList")
    @autoLog
    //批量删除
    public ResponseResult deleteNotifyList(@RequestBody Integer categoryId){

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

        PageQueryDto pagedto = new PageQueryDto(pageNum, pageSize);

        IPage<NotifyCategory> categoryList = new Page<>();

        try {

            if(ObjectUtil.isNull(pagedto.getPageNum())){
                List<NotifyCategory> list = categoryService.list();
                return ResponseResult.okResult(list);
            }else{
                categoryList = categoryService.getNotifyCategoryList(pagedto);

                PageVo<List<NotifyCategory>> pageList = new PageVo<>();
                pageList.setData(categoryList.getRecords());
                pageList.setTotal((int) categoryList.getTotal());
                return ResponseResult.okResult(pageList);
            }

        }catch (SystemException e) {
            return ResponseResult.okResult(e.getCode(), e.getMsg());
        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }

    }

    @PutMapping("updateCategory")
    @autoLog
    @ApiOperation(value = "更新类型")
    public ResponseResult updateCategory(@RequestBody CategoryVo category){

        NotifyCategory notifycategory = new NotifyCategory();

        boolean b = false;

        try {
            notifycategory = BeanCopyUtils.copyBean(category, NotifyCategory.class);

            b = categoryService.updateById(notifycategory);

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

        NotifyCategory notifycategory = new NotifyCategory();

        boolean b = false;

        try {
            notifycategory = BeanCopyUtils.copyBean(category, NotifyCategory.class);

            b = categoryService.save(notifycategory);

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

        NotifyCategory notifycategory = new NotifyCategory();

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
