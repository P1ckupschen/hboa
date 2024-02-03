package com.gdproj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Payment;
import com.gdproj.result.ResponseResult;
import com.gdproj.vo.PaymentVo;

import javax.servlet.http.HttpServletRequest;

/**
* @author Administrator
* @description 针对表【sys_payment】的数据库操作Service
* @createDate 2023-10-19 14:05:08
*/
public interface PaymentService extends IService<Payment> {

    boolean insertPayment(Payment payment);

    ResponseResult getPaymentList(PageQueryDto queryDto);

    ResponseResult updatePayment(PaymentVo vo);

    ResponseResult deletePayment(Integer id);

    ResponseResult getMyPaymentList(PageQueryDto queryDto, HttpServletRequest request);
}
