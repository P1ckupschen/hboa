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
public class OvertimeVo {

    private Integer overtimeId;

    private String Username;

    private String Department;

    private Integer overtimeHours;

    private Integer categoryId;

    private String Category;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    private String overtimeDescription;

    private List<FileVo> overtimePic;

    private Integer overtimeStatus;

    private Integer applicantId;

    private Integer executorId;

    private Integer departmentId;

    private Integer typeId;

    private String overtimeTitle;


}
