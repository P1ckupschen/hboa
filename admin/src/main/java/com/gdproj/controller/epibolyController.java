package com.gdproj.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.annotation.autoLog;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Epiboly;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.EpibolyService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.EpibolyVo;
import com.gdproj.vo.PageVo;
import com.gdproj.vo.SelectVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/adminEpiboly")
@Api(tags = "外包功能")
public class epibolyController {

    @Autowired
    EpibolyService epibolyService;


    @GetMapping("/getListForSelect")
    @autoLog
    @ApiOperation(value = "查询用于选择的外包列表")
    public ResponseResult getListForSelect(){

        List<SelectVo> selectList = new ArrayList<>();

        try {

            selectList =epibolyService.getListForSelect();

            return ResponseResult.okResult(selectList) ;

        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }


    }

    @GetMapping("/getEpibolyList")
    @autoLog
    @ApiOperation(value = "查询外包列表")
    public ResponseResult getEpibolyList(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam(required = false,defaultValue = "+id")String sort,
                                        @RequestParam(required = false,defaultValue = "") String title ,
                                        @RequestParam(required = false) Integer departmentId,
                                        @RequestParam(required = false) Integer type,
                                        @RequestParam(required = false) String time){

        PageQueryDto pageDto = new PageQueryDto(pageNum,pageSize,departmentId,type,title,time,sort);

        IPage<EpibolyVo> epibolyList = new Page<>();

        try {
            epibolyList = epibolyService.getEpibolyList(pageDto);
            PageVo<List<EpibolyVo>> pageList = new PageVo<>();
            pageList.setData(epibolyList.getRecords());
            pageList.setTotal((int) epibolyList.getTotal());
            return ResponseResult.okResult(pageList);
        }catch (SystemException e){
            return ResponseResult.okResult(e.getCode(),e.getMsg());
        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }

    }

    @GetMapping("/getEpibolyListByCurrentId")
    @autoLog
    @ApiOperation(value = "查询我的项目列表")
    public ResponseResult getEpibolyListByCurrentId(@Validated PageQueryDto queryDto , HttpServletRequest request){

        return epibolyService.getEpibolyListByCurrentId(queryDto,request);

    }
    @PutMapping("updateEpiboly")
    @autoLog
    @ApiOperation(value = "更新外包")
    public ResponseResult updateEpiboly(@RequestBody EpibolyVo vo){


        Epiboly updateInfo = BeanCopyUtils.copyBean(vo, Epiboly.class);
//        vo中的 发布人  类型 部门

        boolean b = false;

        try {

            b = epibolyService.updateById(updateInfo);
            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);

        }


    }

    @PostMapping("insertEpiboly")
    @autoLog
    @ApiOperation(value = "新增外包")
    public ResponseResult insertEpiboly(@RequestBody EpibolyVo vo){

        Epiboly insertInfo = BeanCopyUtils.copyBean(vo, Epiboly.class);

        boolean b = false;

        try {

            b = epibolyService.save(insertInfo);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.INSERT_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.INSERT_ERROR);

        }

    }

    @DeleteMapping("deleteEpiboly")
    @autoLog
    @ApiOperation(value = "删除外包")
    public ResponseResult deleteEpiboly(@RequestParam("epibolyId") Integer Id){

        boolean b = false;

        try {

            b = epibolyService.removeById(Id);

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
