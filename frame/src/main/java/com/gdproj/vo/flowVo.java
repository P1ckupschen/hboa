package com.gdproj.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class flowVo {

    private Integer flowId;

    private Integer typeId;

    private String flowTitle;

    private Integer currentStepId;

    private Integer runId;

    private Integer currentUserId;

    private Integer totalLevel;

    private Integer flowStatus;

    private String flowFeedback;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    private String ApplicantName;

    private String currentStep;

    private String typeName;

    private String runStatus;


}
