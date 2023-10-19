package com.gdproj.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String currentStep;

    private String applicantName;

    private String typeName;

    private String runStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Data createdTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Data updateTime;

    private String flowFeedback;

    private Integer flowStatus;





}
