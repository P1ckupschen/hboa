package com.gdproj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.entity.Payment;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.PaymentMapper;
import com.gdproj.service.FlowService;
import com.gdproj.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【sys_payment】的数据库操作Service实现
* @createDate 2023-10-19 14:05:08
*/
@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, Payment>
    implements PaymentService {


    @Autowired
    FlowService flowService;

    @Override
    public boolean insertPayment(Payment payment) {

        //插入overtime数据
        boolean f =false;
        boolean o =save(payment);
        if(o){
            f = flowService.insertFlow(payment);
        }else{
            throw new SystemException(AppHttpCodeEnum.INSERT_ERROR);
        }
        //同时更新flow 表 增加一条flow数据
        return o && f;
    }

}




