package com.gdproj.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentLoopMerge;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonthSignExcelEntity {
    @ContentLoopMerge(eachRow = 2)
    @ExcelProperty(value = "员工编号", index = 0 )
    private Integer orderId;
    @ContentLoopMerge(eachRow = 2)
    @ExcelProperty(value = "员工姓名")
    private String Username;

    /**
     * 上午/下午
     * */
    @ExcelProperty(value = "时间段")
    private String time;

    @ExcelProperty(value = "考勤时期")
    @ColumnWidth(value = 20)
    //当前月份
    private String stage;
    @ExcelProperty(value = "本月实际出勤天数")
    //实际出勤
    private Integer attendanceDays;
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

    private List<Map<Integer, String>> daysDetail;
}
