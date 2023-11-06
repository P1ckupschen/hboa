package com.gdproj.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignExcelEntity {

    @ExcelProperty(value = "用户编号", index = 0)
//    @ExcelIgnore
    private Integer userId;
    @ExcelProperty(value = "用户姓名")
    private String userName;
    @ExcelProperty(value = "性别")
    private String gender;
    @ExcelProperty(value = "工资")
    private Double salary;
    @ExcelProperty(value = "入职时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date hireDate;
}
