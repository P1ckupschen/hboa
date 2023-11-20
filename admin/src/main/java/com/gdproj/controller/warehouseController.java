package com.gdproj.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.annotation.autoLog;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Warehouse;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.RecordService;
import com.gdproj.service.WarehouseService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.PageVo;
import com.gdproj.vo.WarehouseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adminWarehouse")
@Api(tags = "出入库申请功能")
public class warehouseController {

    @Autowired
    WarehouseService warehouseService;

    @Autowired
    RecordService recordService;

    @GetMapping("/getWarehouseList")
    @autoLog
    @ApiOperation(value = "查询出入库审批列表")
    //TODO  content 部分需要 修改 Warehouse
//    public ResponseResult getWarehouseList(@RequestParam Integer pageNum,
//                                        @RequestParam Integer pageSize,
//                                        @RequestParam(required = false,defaultValue = "+id")String sort,
//                                        @RequestParam(required = false,defaultValue = "") String title ,
//                                        @RequestParam(required = false) Integer departmentId,
//                                        @RequestParam(required = false) Integer type,
//                                        @RequestParam(required = false) String time){
//        PageQueryDto pageDto = new PageQueryDto(pageNum,pageSize,departmentId,type,title,time,sort);
    public ResponseResult getWarehouseList(@Validated PageQueryDto pageDto) {

        IPage<WarehouseVo> warehouseList = new Page<>();

        try {

            warehouseList = warehouseService.getWarehouseList(pageDto);

            PageVo<List<WarehouseVo>> pageList = new PageVo<>();

            pageList.setData(warehouseList.getRecords());

            pageList.setTotal((int) warehouseList.getTotal());

            return ResponseResult.okResult(pageList);

        } catch (Exception e) {

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }

    }

    @PostMapping("/insertWarehouse")
    @autoLog
    @ApiOperation(value = "新增出入库审批")
    public ResponseResult insertWarehouse(@RequestBody WarehouseVo vo) {

        Warehouse warehouse = BeanCopyUtils.copyBean(vo, Warehouse.class);

        boolean b = warehouseService.insertWarehouse(warehouse);
        if (b) {
            return ResponseResult.okResult(b);
        } else {
            throw new SystemException(AppHttpCodeEnum.INSERT_ERROR);
        }
    }
    @PutMapping("/updateWarehouse")
    @autoLog
    @ApiOperation(value = "更新记录")
    public ResponseResult updateWarehouse(@RequestBody WarehouseVo vo){

        Warehouse warehouse = BeanCopyUtils.copyBean(vo, Warehouse.class);
        boolean b = warehouseService.updateById(warehouse);
        // TODO 是否需要同时修改对应的record记录；

        if(b){
            return ResponseResult.okResult(b);
        }else{
            throw  new SystemException(AppHttpCodeEnum.UPDATE_ERROR);
        }

    }

    @DeleteMapping("/deleteWarehouse")
    @autoLog
    @ApiOperation(value = "删除")
    public ResponseResult deleteWarehouse(@RequestParam("warehouseId") Integer warehouseId){
        boolean b = warehouseService.removeById(warehouseId);
        if(b){
            return ResponseResult.okResult(b);
        }else{
            throw new SystemException(AppHttpCodeEnum.DELETE_ERROR);
        }
    }
}
