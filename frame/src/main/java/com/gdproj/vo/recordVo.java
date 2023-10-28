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

    private Integer categoryId;

    private Integer count;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;

    private Integer userId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date recordTime;

    private Integer projectId;

    private Integer contractId;
    private String recordDescription;

    private List<fileVo> recordAttachments;

    private Integer examinerId;

    private Integer recordStatus;

    private Integer productId;

    private String productUnit;

    private String recordTitle;

    private Integer warehouseId;

    private String Username;

    private String Category;

    private String Department;

    private String productBrand;
    private String productName;

}
