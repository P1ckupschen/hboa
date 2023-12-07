package com.gdproj.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectVo {

    private String Category;
    private Integer projectDays;
    private List<stockSelectVo> materialBill;
    private Integer isLate;
    private String supervisorName;
    private Integer projectId;
    private Integer projectOrderId;

    private String projectName;

    @JsonFormat(pattern = "yyyy-MM-ss HH:mm:ss",timezone = "GMT+8")
    private Date startTime;

    private Integer supervisorId;

    private String projectUrgency;

    private Integer monitorId;

    private Integer examinerId;

    private List<Integer> projectTeam;

    private String projectDescription;

    private List<FileVo> projectAttachments;

    @JsonFormat(pattern = "yyyy-MM-ss HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;

    private Integer createdUser;

    private Integer projectStatus;

    @JsonFormat(pattern = "yyyy-MM-ss HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    private Integer projectProcess;

    private Integer contractId;

    private Integer projectClient;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal projectAmount;

    private Integer isCompleted;

    private String warrantyYear;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal warrantyAmount;

    private List<FileVo> projectPic;

    @JsonFormat(pattern = "yyyy-MM-ss HH:mm:ss",timezone = "GMT+8")
    private Date completedTime;

    private List<FileVo> completedVoucher;

    private List projectArrangement;

    private Integer categoryId;

    @JsonFormat(pattern = "yyyy-MM-ss HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;
}
