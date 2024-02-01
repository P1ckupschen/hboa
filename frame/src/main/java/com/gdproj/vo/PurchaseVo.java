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
public class PurchaseVo {

    private Integer purchaseId;

    private String purchaseTitle;

    private List<PurchaseContentVo> purchaseContent;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date createdTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date updateTime;

    private Integer categoryId;

    private String purchaseFeedback;

    private String purchaseDescription;

    private Integer purchaseStatus;

    private List<FileVo> purchaseAttachments;

    private Integer typeId;

    private Integer userId;

    private Integer urgencyLevel;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date deliveryTime;

    private Integer contractId;

    private BigDecimal Amount;

    private String Username;


    private Integer orderId;

}
