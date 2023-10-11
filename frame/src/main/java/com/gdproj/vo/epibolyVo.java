package com.gdproj.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
public class epibolyVo {
    @TableId(value = "epiboly_id", type = IdType.AUTO)
    private Integer epibolyId;

    private Integer epibolyOrderId;

    private String epibolyName;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;

    private Integer supervisorId;

    private Integer monitorId;

    private Integer examinerId;

    private List epibolyTeam;

    private String epibolyDescription;

    private List<fileVo> epibolyAttachments;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;

    private Integer createdUser;

    private Integer epibolyStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    private Integer epibolyProcess;

    private String contractIds;

    private Integer epibolyClient;

    private BigDecimal epibolyAmount;

    private Integer isCompleted;

    private List<fileVo> epibolyPic;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date completedTime;

    private String completedVoucher;


    private List epibolyArrangement;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    private List materialBill;

    private Integer projectId;

    private String epibolyCompany;

    private String epibolyContacts;

    private projectVo project;
}
