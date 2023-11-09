package com.gdproj.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.annotation.autoLog;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Deployee;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.DeployeeService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.DeployeeVo;
import com.gdproj.vo.PageVo;
import com.gdproj.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/deployee")
@Api(tags = "员工功能")
public class deployeeController {

    @Autowired
    DeployeeService deployeeService;

    @GetMapping("/getListForSelect")
    @autoLog
    @ApiOperation(value = "查询用于选择的员工列表")
    public ResponseResult getListForSelect(){

        List<UserVo> selectList = new ArrayList<>();

        try {

            selectList =deployeeService.getListForSelect();

            return ResponseResult.okResult(selectList) ;

        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }


    }

    @GetMapping("/getDeployeeList")
    @autoLog
    @ApiOperation(value = "查询员工列表")
    public ResponseResult getDeployeeList(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam(required = false,defaultValue = "+id")String sort,
                                        @RequestParam(required = false,defaultValue = "") String title ,
                                        @RequestParam(required = false) Integer departmentId,
                                        @RequestParam(required = false) Integer type,
                                        @RequestParam(required = false) String time){

        PageQueryDto pageDto = new PageQueryDto(pageNum,pageSize,departmentId,type,title,time,sort);

        IPage<DeployeeVo> deployeeList = new Page<DeployeeVo>();

        try {
            deployeeList = deployeeService.getDeployeeList(pageDto);

            PageVo<List<DeployeeVo>> pageList = new PageVo<>();
            pageList.setData(deployeeList.getRecords());
            pageList.setTotal((int) deployeeList.getTotal());
            return ResponseResult.okResult(pageList);
        }catch (SystemException e){
            return ResponseResult.errorResult(e.getCode(),e.getMsg());
        } catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }


    }


    @PutMapping("updateDeployee")
    @autoLog
    @ApiOperation(value = "更新员工")
    public ResponseResult updateDeployee(@RequestBody DeployeeVo vo){


        Deployee updateInfo = BeanCopyUtils.copyBean(vo, Deployee.class);
//        vo中的 发布人  类型 部门
        System.out.println(updateInfo);

        boolean b = false;

        try {

            b = deployeeService.updateById(updateInfo);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);

        }


    }

    @PostMapping("insertDeployee")
    @autoLog
    @ApiOperation(value = "新增员工")
    public ResponseResult insertDeployee(@RequestBody DeployeeVo vo){

        Deployee insertInfo = BeanCopyUtils.copyBean(vo, Deployee.class);

        boolean b = false;

        try {

            b = deployeeService.save(insertInfo);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.INSERT_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.INSERT_ERROR);

        }

    }

    @DeleteMapping("deleteDeployee")
    @autoLog
    @ApiOperation(value = "删除员工")
    public ResponseResult deleteDeployee(@RequestParam("deployeeId") Integer id){

        System.out.println(id);

        boolean b = false;

        try {

            b = deployeeService.removeById(id);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);

        }

    }
}
