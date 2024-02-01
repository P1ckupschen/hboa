package com.gdproj.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OvertimeExcelEntity {

    @ExcelProperty(value = "员工编号", index = 0 )
    private Integer orderId;
    @ExcelProperty(value = "员工姓名")
    private String Username;
    @ExcelProperty(value = "统计时间段")
    @ColumnWidth(value = 20)
    private String stage;
    @ExcelProperty(value = "共加班小时数")
    private double overtimeDays;
    @ExcelProperty(value = "加班记录")
    @ColumnWidth(value = 30)
    private String overtimeHistory;
}
