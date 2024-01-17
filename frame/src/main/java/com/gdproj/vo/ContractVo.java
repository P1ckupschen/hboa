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
public class ContractVo {

    private Integer contractId;

    private String contractTitle;

    private Integer contractOrderId;

    private Integer categoryId;

    private String Category;

    private Integer contractClient;

    private String clientName;

    private Long contractAmount;

    private String contractAmountCp;

    private String aAddress;

    private String aLinkman;

    private String aPhone;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date aSignTime;

    private String aUser;

    private String bAddress;

    private String bLinkman;

    private String bPhone;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date bSignTime;

    private String bUser;

    private Integer contractStatus;

    private String contractRemark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    private Integer createdUser;

    private Integer followedUser;

    private String followedName;

    private List<FileVo> contractAttachments;

    private String contractContent;

    private Integer parentId;

    private Integer projectId;

    private Long warrantyAmount;

    private Integer warrantyYear;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date warrantyStartTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date warrantyEndTime;

    private Integer canSee;
}
