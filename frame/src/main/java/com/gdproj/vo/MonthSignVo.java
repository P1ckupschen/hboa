package com.gdproj.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthSignVo {

    private Integer userId;
    private DeployeeVo deployee;
    //当前年份
    private Integer Year;
    //当前月份
    private Integer month;
    //实际出
    private Integer attendanceDays;
    //应出勤
//    private Integer shouldAttendanceDays;
    //出勤率
//    private Double attendanceRate;
    //迟到天数
    private Integer lateDays;
    //迟到记录
    private List<SignVo> lateHistory;
    //早退天数
    private Integer earlyDays;
    //早退记录
    private List<SignVo> earlyHistory;

    private Map<Integer,String > inDetail;
    private Map<Integer,String > endDetail;


    private Integer orderId;

}
