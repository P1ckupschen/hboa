package com.gdproj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.FlowConfig;
import com.gdproj.vo.FlowConfigVo;

/**
* @author Administrator
* @description 针对表【sys_flow_config】的数据库操作Service
* @createDate 2023-10-17 15:09:38
*/
public interface flowConfigService extends IService<FlowConfig> {

    IPage<FlowConfigVo> getFlowConfigList(pageDto pageDto);
}
