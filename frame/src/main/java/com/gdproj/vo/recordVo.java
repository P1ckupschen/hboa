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
public class recordVo {
    private Integer recordId;
    private Integer productId;
    private String productName;
    private Integer examinerId;
    private String examinerUsername;
    private Integer productOrderId;
    private Integer categoryId;
    private String Category;
    private Integer count;
    private String productBrand;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;

    private String productBatchs;

    private Integer userId;

    private String Username;

    private String Department;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date recordTime;

    private Integer projectId;

    private Integer contractId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date outTime;


    private String productUnit;

    private String recordDescription;

    private List<fileVo> recordAttachments;
}
