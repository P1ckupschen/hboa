package com.gdproj.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.annotation.autoLog;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Warehouse;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.WarehouseService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.pageVo;
import com.gdproj.vo.warehouseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adminWarehouse")
@Api(tags = "出入库申请功能")
public class warehouseController {

    @Autowired
    WarehouseService warehouseService;

    @GetMapping("/getWarehouseList")
    @autoLog
    @ApiOperation(value = "查询出入库审批列表")
    public ResponseResult getWarehouseList(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam(required = false,defaultValue = "+id")String sort,
                                        @RequestParam(required = false,defaultValue = "") String title ,
                                        @RequestParam(required = false) Integer departmentId,
                                        @RequestParam(required = false) Integer type,
                                        @RequestParam(required = false) String time){
        pageDto pageDto = new pageDto(pageNum,pageSize,departmentId,type,title,time,sort);

        IPage<warehouseVo> warehouseList = new Page<>();

        try {

            warehouseList =  warehouseService.getWarehouseList(pageDto);

            pageVo<List<warehouseVo>> pageList = new pageVo<>();

            pageList.setData(warehouseList.getRecords());

            pageList.setTotal((int) warehouseList.getTotal());

            return ResponseResult.okResult(pageList);

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }

    }

    @PostMapping("/insertWarehouse")
    @autoLog
    @ApiOperation(value = "新增出入库审批")
    public ResponseResult insertWarehouse(@RequestBody warehouseVo vo){

        Warehouse warehouse = BeanCopyUtils.copyBean(vo, Warehouse.class);

        boolean b =false;

        try {

            b = warehouseService.insertWarehouse(warehouse);

            return ResponseResult.okResult(b);
        }catch (Exception e){
            //审批输入失败
            return ResponseResult.errorResult(AppHttpCodeEnum.INSERT_ERROR);
        }

    }
}
