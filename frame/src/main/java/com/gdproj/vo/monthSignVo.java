package com.gdproj.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class monthSignVo {

    private Integer userId;
    private deployeeVo deployee;
    //当前年份
    private Integer Year;
    //当前月份
    private Integer month;
    //应出勤
    private Integer attendanceDays;
    //实际出勤
    private Integer shouldAttendanceDays;
    //出勤率
    private Integer attendanceRate;
    //迟到天数
    private Integer lateDays;
    //迟到记录
    private List<signVo> lateHistory;
    //早退天数
    private Integer earlyDays;
    //早退记录
    private List<signVo> earlyHistory;

}
