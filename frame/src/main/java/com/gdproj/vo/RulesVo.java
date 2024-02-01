package com.gdproj.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RulesVo {

    private Integer rulesId;

    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date inTime;
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date endTime;
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date inAllowance;
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date endAllowance;

    private Integer departmentId;

}
