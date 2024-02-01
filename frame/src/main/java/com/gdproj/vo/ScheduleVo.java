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
public class ScheduleVo {

    private Integer scheduleId;

    private Integer projectId;

    private String scheduleName;

    private String scheduleContent;

    private Integer isCompleted;

    private Integer isLate;

    @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "GMT+8")
    private Date planStartTime;

    @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "GMT+8")
    private Date planEndTime;
    @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "GMT+8")
    private Date trueEndTime;

    @JsonFormat(pattern = "yyyy-MM-dd" , timezone = "GMT+8")
    private Date trueStartTime;

    private List<DailyUseContentVo> toolList;

    private String scheduleReport;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date createdTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date updateTime;
    private String supervisorName;
    private String projectName;
    private String timeStage;
    private  List<FileVo> scheduleAttachments;
    private Integer typeId;


    private Integer orderId;
}
