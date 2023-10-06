package com.gdproj.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Client;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.ClientService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.clientVo;
import com.gdproj.vo.pageVo;
import com.gdproj.vo.selectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/adminClient")
public class clientController {

    @Autowired
    ClientService clientService;


    @GetMapping("/getListForSelect")
    public ResponseResult getListForSelect(){

        List<selectVo> selectList = new ArrayList<>();

        try {

            selectList =clientService.getListForSelect();

            return ResponseResult.okResult(selectList) ;

        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }

    }

    @GetMapping("/getClientList")
    public ResponseResult getClientList(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam(required = false,defaultValue = "+id")String sort,
                                        @RequestParam(required = false,defaultValue = "") String title ,
                                        @RequestParam(required = false) Integer departmentId,
                                        @RequestParam(required = false) Integer type,
                                        @RequestParam(required = false) String time){

        pageDto pageDto = new pageDto(pageNum,pageSize,departmentId,type,title,time,sort);

        IPage<clientVo> clientList = new Page<>();

        try {
            clientList = clientService.getClientList(pageDto);
            pageVo<List<clientVo>> pageList = new pageVo<>();
            pageList.setData(clientList.getRecords());
            pageList.setTotal((int) clientList.getTotal());
            return ResponseResult.okResult(pageList);
        }catch (SystemException e){
            return ResponseResult.okResult(e.getCode(),e.getMsg());
        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }

    }


    @PutMapping("updateClient")
    public ResponseResult updateClient(@RequestBody clientVo vo){


        Client updateInfo = BeanCopyUtils.copyBean(vo, Client.class);
//        vo中的 发布人  类型 部门
        System.out.println(updateInfo);

        boolean b = false;

        try {

            b = clientService.updateById(updateInfo);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }


    }

    @PostMapping("insertClient")
    public ResponseResult insertClient(@RequestBody clientVo vo){

        Client insertInfo = BeanCopyUtils.copyBean(vo, Client.class);

        boolean b = false;

        try {

            b = clientService.save(insertInfo);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.INSERT_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }

    }

    @DeleteMapping("deleteClient")
    public ResponseResult deleteClient(@PathParam("clientId") Integer clientId){

        boolean b = false;

        try {

            b = clientService.removeById(clientId);

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
