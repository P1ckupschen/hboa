package com.gdproj.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthSignExcelEntity {
    @ExcelProperty(value = "员工编号", index = 0 )
    private Integer userId;
    @ExcelProperty(value = "员工姓名")
    private String Username;
//    @ExcelProperty(value = "当前年份")
//    //当前年份
//    private Integer Year;
//    @ExcelProperty(value = "当前月份")
//    //当前月份
//    private Integer month;
    @ExcelProperty(value = "考勤时期")
    @ColumnWidth(value = 20)
    //当前月份
    private String stage;
    @ExcelProperty(value = "本月实际出勤天数")
    //应出勤
    private Integer attendanceDays;
//    @ExcelProperty(value = "本月应出勤天数")
//    //实际出勤
//    private Integer shouldAttendanceDays;
//    @NumberFormat("#.##%")
//    @NumberFormat(value = "0.00%", roundingMode = RoundingMode.HALF_UP)
//    @ExcelProperty(value = "本月出勤率")
    //出勤率
//    private Double attendanceRate;
    @ExcelProperty(value = "迟到天数")
    //迟到天数
    private Integer lateDays;
    @ExcelProperty(value = "迟到记录")
    @ColumnWidth(value = 30)
    //迟到记录
    private String lateHistory;
    @ExcelProperty(value = "早退天数")
    //早退天数
    private Integer earlyDays;
    @ExcelProperty(value = "早退记录")
    @ColumnWidth(value = 30)
    //早退记录
    private String earlyHistory;
}
