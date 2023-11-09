package com.gdproj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.*;
import com.gdproj.vo.FlowVo;

/**
* @author Administrator
* @description 针对表【sys_flow】的数据库操作Service
* @createDate 2023-10-17 15:09:38
*/
public interface FlowService extends IService<Flow> {



    boolean approveFlow(FlowVo vo);

    IPage<FlowVo> getFlowList(PageQueryDto pageDto);

    boolean insertFlow(Overtime insertOvertime);
    boolean insertFlow(Leave insertLeave);

    boolean insertFlow(Warehouse warehouse);
    boolean insertFlow(Payment payment);

    boolean insertFlow(Reimbursement reimbursement);

    boolean insertFlow(DailyUse dailyUse);

    boolean insertFlow(Purchase purchase);
}
