package com.gdproj.controller;

import com.gdproj.annotation.autoLog;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Payment;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.PaymentService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.PaymentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adminPayment")
@Api(tags = "付款申请功能")
public class paymentController {


    @Autowired
    PaymentService paymentService;

    @GetMapping("/getPaymentList")
    @autoLog
    @ApiOperation(value = "查询支付申请列表")
    public ResponseResult getPaymentList( @Validated PageQueryDto queryDto){

        return paymentService.getPaymentList(queryDto);
    }
    @PostMapping("/insertPayment")
    @autoLog
    @ApiOperation(value = "新增付款申请")
    public ResponseResult insertPayment(@RequestBody PaymentVo vo){

        Payment payment = BeanCopyUtils.copyBean(vo, Payment.class);
        return ResponseResult.okResult(paymentService.insertPayment(payment));
    }

    @PutMapping("/updatePayment")
    @autoLog
    @ApiOperation(value = "更新付款申请")
    public ResponseResult updatePayment(@RequestBody PaymentVo vo){

        return paymentService.updatePayment(vo);

    }

    @DeleteMapping("/deletePayment")
    @autoLog
    @ApiOperation(value = "删除")
    public ResponseResult deletePayment(@RequestParam("paymentId") Integer id){

        return paymentService.deletePayment(id);

    }

}
