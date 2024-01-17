package com.gdproj.controller;

import com.gdproj.annotation.autoLog;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.ScheduleService;
import com.gdproj.vo.ScheduleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Schedule")
@Api(tags = "项目、外包安排")
public class scheduleController {

    @Autowired
    ScheduleService scheduleService;

    @GetMapping("getScheduleList")
    @autoLog
    @ApiOperation(value = "获取安排列表")
    public ResponseResult getScheduleList(@Validated PageQueryDto queryDto){

        return scheduleService.getScheduleList(queryDto);
    }

    @GetMapping("getScheduleById")
    @autoLog
    @ApiOperation(value = "通过id查询")
    public ResponseResult getScheduleByid(@RequestParam("id") Integer id,
                                          @RequestParam("typeId") Integer typeId){

        return scheduleService.getScheduleByid(id,typeId);
    }

    @PostMapping("insertSchedule")
    @autoLog
    @ApiOperation(value = "新增安排")
    public ResponseResult insertSchedule(@RequestBody ScheduleVo vo){

        return scheduleService.insertSchedule(vo);
    }

    @PutMapping("updateSchedule")
    @autoLog
    @ApiOperation(value = "更新安排")
    public ResponseResult updateSchedule(@RequestBody ScheduleVo vo){

        return scheduleService.updateSchedule(vo);
    }

    @DeleteMapping("deleteSchedule")
    @autoLog
    @ApiOperation(value = "删除安排")
    public ResponseResult deleteSchedule(@RequestParam("scheduleId") Integer Id){

        return scheduleService.deleteSchedule(Id);
    }
}
