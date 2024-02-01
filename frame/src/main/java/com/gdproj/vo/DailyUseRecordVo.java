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
public class DailyUseRecordVo {
    private Integer recordId;

    private Integer categoryId;

    private Integer count;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;

    private Integer userId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date recordTime;

    private String recordDescription;

    private List<FileVo> recordAttachments;

    private Integer isReturn;

    private Integer toolId;

    private String recordTitle;

    private Integer dailyuseId;

//    private ToolVo Tool;

    private String toolName;

    private String toolUnit;

    private Integer purposeId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date dailyuseTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date trueReturnTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date planReturnTime;

    private String Username;
    private String Department;
    private String Category;

    private Integer orderId;


}
