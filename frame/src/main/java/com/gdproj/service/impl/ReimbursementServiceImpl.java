package com.gdproj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.entity.Reimbursement;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.ReimbursementMapper;
import com.gdproj.service.FlowService;
import com.gdproj.service.ReimbursementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【sys_reimbursement】的数据库操作Service实现
* @createDate 2023-10-19 14:22:09
*/
@Service
public class ReimbursementServiceImpl extends ServiceImpl<ReimbursementMapper, Reimbursement>
    implements ReimbursementService {

    @Autowired
    FlowService flowService;

    @Override
    public boolean insertReimbursement(Reimbursement reimbursement) {
        //插入overtime数据
        boolean f =false;
        boolean o =save(reimbursement);
        if(o){
            f = flowService.insertFlow(reimbursement);
        }else{
            throw new SystemException(AppHttpCodeEnum.INSERT_ERROR);
        }
        //同时更新flow 表 增加一条flow数据

        return o && f;
    }
}




