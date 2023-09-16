package com.gdproj.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class overtimeVo {

    private Integer overtimeId;


    private String Username;

    private String Department;

    private Integer overtimeDays;

    private Integer categoryId;

    private String Category;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    private String overtimeDescription;

    private String overtimePic;

    private String isDeleted;

    private String overtimeStatus;

    private Integer userId;

    private Integer departmentId;



}
