package com.gdproj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Flow;
import com.gdproj.entity.Leave;
import com.gdproj.entity.Overtime;
import com.gdproj.vo.flowVo;

/**
* @author Administrator
* @description 针对表【sys_flow】的数据库操作Service
* @createDate 2023-10-17 15:09:38
*/
public interface FlowService extends IService<Flow> {

    boolean insertFlow(Overtime insertOvertime);

    boolean approveFlow(flowVo vo);

    IPage<flowVo> getFlowList(pageDto pageDto);

    boolean insertFlow(Leave insertLeave);
}
