package com.gdproj.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageDTO;
import com.gdproj.dto.pageDto;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.SignService;
import com.gdproj.vo.leaveVo;
import com.gdproj.vo.pageVo;
import com.gdproj.vo.signVo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/adminSign")
public class signController {

    @Autowired
    SignService signService;

    @GetMapping("/getSignList")
    public ResponseResult getSignList(@RequestParam Integer pageNum,
                                      @RequestParam Integer pageSize,
                                      @RequestParam(required = false,defaultValue = "+id")String sort,
                                      @RequestParam(required = false,defaultValue = "") String title ,
                                      @RequestParam(required = false) Integer departmentId,
                                      @RequestParam(required = false) Integer type,
                                      @RequestParam(required = false) String time){

        pageDto pageDto = new pageDto(pageNum,pageSize,departmentId,type,title,time,sort);

        IPage<signVo> signList = new Page<signVo>();

        try {

            signList  =  signService.getSignList(pageDto);

            pageVo<List<signVo>> pageList = new pageVo<>();
            pageList.setData(signList.getRecords());
            pageList.setTotal((int) signList.getTotal());
            return ResponseResult.okResult(pageList);

        }catch (SystemException e){

            return ResponseResult.errorResult(e.getCode(),e.getMsg());

        } catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.LIST_ERROR);

        }


    }
}
