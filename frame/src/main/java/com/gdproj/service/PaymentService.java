package com.gdproj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.entity.Payment;

/**
* @author Administrator
* @description 针对表【sys_payment】的数据库操作Service
* @createDate 2023-10-19 14:05:08
*/
public interface PaymentService extends IService<Payment> {

    boolean insertPayment(Payment payment);

}
