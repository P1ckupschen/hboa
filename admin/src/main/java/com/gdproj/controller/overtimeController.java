package com.gdproj.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.leaveCategory;
import com.gdproj.entity.overtimeCategory;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.OvertimeService;
import com.gdproj.service.overtimeCategoryService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.categoryVo;
import com.gdproj.vo.leaveVo;
import com.gdproj.vo.overtimeVo;
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
                                          @RequestParam(required = false) Integer type,
                                          @RequestParam(required = false) String time){
        pageDto pageDto = new pageDto(pageNum,pageSize,type,title,time,sort);

        IPage<overtimeVo> overtimeList = new Page<overtimeVo>();

        try {
            overtimeList = overtimeService.getOverTimeList(pageDto);
        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }

        return ResponseResult.okResult(overtimeList);

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
                return ResponseResult.okResult(categoryList);
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

            if(b == true){
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

            if(b == true){
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

            if(b == true){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }

    }

}
