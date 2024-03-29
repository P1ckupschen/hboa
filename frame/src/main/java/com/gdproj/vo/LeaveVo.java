package com.gdproj.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveVo {

    private Integer leaveId;
    private String Username;
    private String Department;
    private Integer orderId;
    private String leaveDays;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;
    private String leaveDescription;
    private Integer categoryId;
    private String Category;
    private List<FileVo> leavePic;

    private Integer leaveStatus;

    private Integer userId;

    private Integer departmentId;

    private Integer typeId;
    private String leaveTitle;



}
