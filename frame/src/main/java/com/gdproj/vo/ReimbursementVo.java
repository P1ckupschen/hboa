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
public class ReimbursementVo {

    private Integer reimbursementId;

    private String reimbursementTitle;

    private Integer userId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    private BigDecimal reimbursementAmount;

    private Integer projectId;

    private String reimbursementDescription;

    private List<FileVo> reimbursementAttachments;

    private Integer typeId;

    private Integer reimbursementStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date reimbursementTime;

    private String userName;

    private List<ReimbursementContentVo> reimbursementContent;

    private Integer contractId;
    private String reimbursementAmountCap;

}
