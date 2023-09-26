package com.gdproj.controller;

import cn.hutool.core.bean.copier.BeanToBeanCopier;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Notify;
import com.gdproj.entity.notifyCategory;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.NotifyService;
import com.gdproj.service.notifyCategoryService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.*;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/adminNotify")
public class notifyController {

    @Autowired
    NotifyService notifyService;

    @Autowired
    notifyCategoryService categoryService;


    //公告的增删改查
    @GetMapping("/getNotifyList")
    public ResponseResult getNotifyList(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam(required = false,defaultValue = "+id")String sort,
                                        @RequestParam(required = false,defaultValue = "") String title ,
                                        @RequestParam(required = false) Integer departmentId,
                                        @RequestParam(required = false) Integer type,
                                        @RequestParam(required = false) String time){
        pageDto pageDto = new pageDto(pageNum,pageSize,departmentId,type,title,time,sort);

        IPage<notifyVo> notifyList = new Page<>();

        try {

            notifyList =  notifyService.getNotifyList(pageDto);

            pageVo<List<notifyVo>> pageList = new pageVo<>();
            pageList.setData(notifyList.getRecords());
            pageList.setTotal((int) notifyList.getTotal());
            return ResponseResult.okResult(pageList);
        }catch (SystemException e) {
            return ResponseResult.okResult(e.getCode(), e.getMsg());
        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }

    }

    @PutMapping("updateNotify")
    public ResponseResult updateNotify(@RequestBody notifyVo notifyVo){


        Notify updateNotify = BeanCopyUtils.copyBean(notifyVo, Notify.class);
//        vo中的 发布人  类型 部门
        System.out.println(updateNotify);

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
    public ResponseResult insertNotify(@RequestBody notifyVo notifyVo){

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
    public ResponseResult deleteNotify(@PathParam("notifyId") Integer notifyId){

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

    @DeleteMapping("deleteNotifyList")
    //批量删除
    public ResponseResult deleteNotifyList(@RequestBody Integer categoryId){

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

        IPage<notifyCategory> categoryList = new Page<>();

        try {

            if(ObjectUtil.isNull(pagedto.getPageNum())){
                List<notifyCategory> list = categoryService.list();
                return ResponseResult.okResult(list);
            }else{
                categoryList = categoryService.getNotifyCategoryList(pagedto);

                pageVo<List<notifyCategory>> pageList = new pageVo<>();
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
    public ResponseResult updateCategory(@RequestBody categoryVo category){

        notifyCategory notifycategory = new notifyCategory();

        boolean b = false;

        try {
            notifycategory = BeanCopyUtils.copyBean(category, notifyCategory.class);

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
    public ResponseResult insertCategory(@RequestBody categoryVo category){

        notifyCategory notifycategory = new notifyCategory();

        boolean b = false;

        try {
            notifycategory = BeanCopyUtils.copyBean(category, notifyCategory.class);

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
    public ResponseResult deleteCategory(@PathParam("categoryId") Integer categoryId){

        notifyCategory notifycategory = new notifyCategory();

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
