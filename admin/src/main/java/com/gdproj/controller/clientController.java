package com.gdproj.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.annotation.autoLog;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Client;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.ClientService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.ClientVo;
import com.gdproj.vo.PageVo;
import com.gdproj.vo.SelectVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/adminClient")
@Api(tags = "客户功能")
public class clientController {

    @Autowired
    ClientService clientService;


    @GetMapping("/getListForSelect")
    @autoLog
    @ApiOperation(value = "查询用于选择的客户列表", notes = "用于下拉选择")
    public ResponseResult getListForSelect(){

        List<SelectVo> selectList = new ArrayList<>();

        try {

            selectList =clientService.getListForSelect();

            return ResponseResult.okResult(selectList) ;

        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }

    }

    @GetMapping("/getClientList")
    @autoLog
    @ApiOperation(value = "查询客户列表", notes = "可传分页等数据")
    public ResponseResult getClientList(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam(required = false,defaultValue = "+id")String sort,
                                        @RequestParam(required = false,defaultValue = "") String title ,
                                        @RequestParam(required = false) Integer departmentId,
                                        @RequestParam(required = false) Integer type,
                                        @RequestParam(required = false) String time){

        PageQueryDto pageDto = new PageQueryDto(pageNum,pageSize,departmentId,type,title,time,sort);

        IPage<ClientVo> clientList = new Page<>();

        try {
            clientList = clientService.getClientList(pageDto);
            PageVo<List<ClientVo>> pageList = new PageVo<>();
            pageList.setData(clientList.getRecords());
            pageList.setTotal((int) clientList.getTotal());
            return ResponseResult.okResult(pageList);
        }catch (SystemException e){
            return ResponseResult.okResult(e.getCode(),e.getMsg());
        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }

    }

    @GetMapping("/getClientContacts")
    @autoLog
    @ApiOperation(value = "查询对应客户的联系人信息")
    public ResponseResult getClientContacts(@RequestParam("id") Integer Id){
        return clientService.getClientContacts(Id);
    }


    @PutMapping("updateClient")
    @autoLog
    @ApiOperation(value = "更新客户数据", notes = "修改编辑")
    public ResponseResult updateClient(@RequestBody ClientVo vo){


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
    @autoLog
    @ApiOperation(value = "新增客户数据", notes = "新增插入")
    public ResponseResult insertClient(@RequestBody ClientVo vo){

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
    @autoLog
    @ApiOperation(value = "删除客户数据", notes = "删除")
    public ResponseResult deleteClient(@RequestParam("clientId") Integer clientId){

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
