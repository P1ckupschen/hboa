package com.gdproj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Overtime;
import com.gdproj.vo.overtimeVo;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_overtime】的数据库操作Service
* @createDate 2023-09-14 15:04:32
*/
public interface OvertimeService extends IService<Overtime> {

    IPage<overtimeVo> getOverTimeList(pageDto pageDto);
}
