package com.gdproj.controller;

import com.gdproj.annotation.autoLog;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Purchase;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.PurchaseService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.PurchaseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adminPurchase")
@Api(tags = "采购申请功能")
public class purchaseController {

    @Autowired
    PurchaseService purchaseService;


    @GetMapping("/getPurchaseList")
    @autoLog
    @ApiOperation(value = "获取采购申请列表")
    public ResponseResult getPurchaseList(@Validated PageQueryDto queryVo) {

        return purchaseService.getPurchaseList(queryVo);

    }
    @PostMapping("/insertPurchase")
    @autoLog
    @ApiOperation(value = "新增采购申请")
    public ResponseResult insertPurchase(@RequestBody PurchaseVo vo){

        Purchase purchase = BeanCopyUtils.copyBean(vo, Purchase.class);

        boolean b = purchaseService.insertPurchase(purchase);
        if(b){
            return ResponseResult.okResult(b);
        }else{
            throw new SystemException(AppHttpCodeEnum.INSERT_ERROR);
        }

    }

    @PutMapping("/updatePurchase")
    @autoLog
    @ApiOperation(value = "更新采购申请")
    public ResponseResult updatePurchase(@RequestBody PurchaseVo vo){

        Purchase purchase = BeanCopyUtils.copyBean(vo, Purchase.class);
        boolean b = purchaseService.updateById(purchase);
        if(b){
            return ResponseResult.okResult(b);
        }else{
            throw  new SystemException(AppHttpCodeEnum.UPDATE_ERROR);
        }

    }

    @DeleteMapping("/deletePurchase")
    @autoLog
    @ApiOperation(value = "删除")
    public ResponseResult deletePurchase(@RequestBody Integer id){
        boolean b = purchaseService.removeById(id);
        if(b){
            return ResponseResult.okResult(b);
        }else{
            throw new SystemException(AppHttpCodeEnum.DELETE_ERROR);
        }
    }
}
