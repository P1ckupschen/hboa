package com.gdproj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.entity.DailyUse;
import com.gdproj.entity.DailyUseRecord;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_dailyuse_record】的数据库操作Service
* @createDate 2023-10-31 13:35:04
*/
public interface DailyUseRecordService extends IService<DailyUseRecord> {

    Integer getCountByToolId(Integer toolId);

    boolean insertRecordByDailyUseId(Integer runId);

    List<DailyUseRecord> transferDailyUseContentToRecord(DailyUse dailyUse);
}
