package com.gdproj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.DailyUse;
import com.gdproj.vo.DailyUseRecordVo;
import com.gdproj.vo.DailyUseVo;

/**
* @author Administrator
* @description 针对表【sys_dailyuse】的数据库操作Service
* @createDate 2023-10-31 09:27:07
*/
public interface DailyUseService extends IService<DailyUse> {

    IPage<DailyUseVo> getDailyUseList(pageDto pageDto);

    boolean insertDailyUse(DailyUse dailyUse);

    IPage<DailyUseRecordVo> getDailyUseRecordList(pageDto pageDto);
}
