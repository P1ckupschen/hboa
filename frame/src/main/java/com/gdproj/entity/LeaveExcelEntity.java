package com.gdproj.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveExcelEntity {

    @ExcelProperty(value = "员工编号", index = 0 )
    private Integer userId;
    @ExcelProperty(value = "员工姓名")
    private String Username;
    @ExcelProperty(value = "统计时间段")
    @ColumnWidth(value = 20)
    private String stage;
    @ExcelProperty(value = "共请假天数")
    private double leaveDays;
    @ExcelProperty(value = "请假记录")
    @ColumnWidth(value = 30)
    private String leaveHistory;
}
