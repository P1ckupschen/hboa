package com.gdproj.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.leaveCategory;
import com.gdproj.entity.notifyCategory;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.leaveCategoryService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.categoryVo;
import com.gdproj.vo.leaveVo;
import com.gdproj.vo.signVo;
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
                                       @RequestParam(required = false) Integer type,
                                       @RequestParam(required = false) String time){
        pageDto pageDto = new pageDto(pageNum,pageSize,type,title,time,sort);

        IPage<leaveVo> leaveList = new Page<leaveVo>();

        try {
            leaveList = leaveService.getLeaveList(pageDto);
        }catch (SystemException e){
            return ResponseResult.okResult(e.getCode(),e.getMsg());
        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }

        return ResponseResult.okResult(leaveList);
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
                return ResponseResult.okResult(categoryList);
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

        leaveCategory leavecategory = new leaveCategory();

        boolean b = false;

        try {
            leavecategory = BeanCopyUtils.copyBean(category, leaveCategory.class);

            b = categoryService.save(leavecategory);
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
