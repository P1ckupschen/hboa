package com.gdproj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.*;
import com.gdproj.result.ResponseResult;
import com.gdproj.vo.FlowVo;

import javax.servlet.http.HttpServletRequest;

/**
* @author Administrator
* @description 针对表【sys_flow】的数据库操作Service
* @createDate 2023-10-17 15:09:38
*/
public interface FlowService extends IService<Flow> {



    boolean approveFlow(FlowVo vo);

    IPage<FlowVo> getFlowList(PageQueryDto pageDto, HttpServletRequest request);

    boolean insertFlow(Overtime insertOvertime);
    boolean insertFlow(Leave insertLeave);

    boolean insertFlow(Warehouse warehouse);
    boolean insertFlow(Payment payment);

    boolean insertFlow(Reimbursement reimbursement);

    boolean insertFlow(DailyUse dailyUse);

    boolean insertFlow(Purchase purchase);

    ResponseResult getFlowListByCurrentUser(PageQueryDto queryDto, HttpServletRequest request);

    ResponseResult getFlowDetail(Integer typeId, Integer runId);

    ResponseResult deleteFlow(Integer id);
}
