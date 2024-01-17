package com.gdproj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Schedule;
import com.gdproj.result.ResponseResult;
import com.gdproj.vo.ScheduleVo;

/**
* @author Administrator
* @description 针对表【sys_schedule】的数据库操作Service
* @createDate 2023-12-12 14:39:34
*/
public interface ScheduleService extends IService<Schedule> {

    ResponseResult getScheduleList(PageQueryDto queryDto);

    ResponseResult insertSchedule(ScheduleVo vo);

    ResponseResult updateSchedule(ScheduleVo vo);

    ResponseResult deleteSchedule(Integer id);

    ResponseResult getScheduleByid(Integer id , Integer typeId);
}
