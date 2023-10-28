package com.gdproj.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class signVo {

    private Integer signId;

    private Integer userId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date inTime;

    private String signIp;

    private Integer departmentId;

    private String signAddr;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    private Integer signStatus;

    private Integer workTime;

    private Integer tWorkTime;

    private Integer isLate;

    private String Late;

    private Integer isOut;

    private String Out;

    private Integer isAway;

    private String Away;

    private Integer isEarly;

    private String Early;

    private deployeeVo deployee;

    private String Department;

    private String Username;

}
