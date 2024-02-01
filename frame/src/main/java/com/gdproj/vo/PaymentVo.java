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
public class PaymentVo {

    private Integer paymentId;
    private String paymentTitle;
    private Integer userId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date createdTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date updateTime;
    private BigDecimal paymentAmount;
    private Integer projectId;
    private String payer;
    private String payee;
    private String depositBank;
    private String paymentAccount;
    private String paymentDescription;
    private String paymentMethod;
    private List<FileVo> paymentAttachments;
    private Integer paymentStatus;
    private Integer typeId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date paymentTime;
    private Integer categoryId;
    private String userName;
    //金额大写
    private String paymentAmountCap;
    private String paymentUse;

    private Integer orderId;
}

