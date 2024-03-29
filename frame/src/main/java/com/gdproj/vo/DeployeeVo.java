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
public class DeployeeVo {

    private Integer deployeeId;

    private String deployeeName;

    private String deployeeJob;

    private String deployeePhone;

    private String deployeeAddr;

    private Integer departmentId;

    private Integer deployeeStatus;

    private List<Integer> deployeeRole;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date entryTime;

    private Integer isContract;

    private Integer isEnsurance;

    private List<FileVo> contract;

    private String idCard;

    private String password;

    private String username;

    private Integer userId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    private List<FileVo> ensurance;


    private Integer orderId;

}
