package com.gdproj.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class signVo {
    /**
     *
     */
    private Integer signId;

    /**
     *
     */
    private String signUser;

    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date inTime;

    /**
     *
     */
    private String signIp;

    /**
     *
     */
    private String departmentId;

    /**
     *
     */
    private String signAddr;

    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    /**
     *
     */
    private Integer signStatus;

    /**
     *
     */
    private Integer workTime;

    /**
     *
     */
    private Integer tWorkTime;

    /**
     *
     */
    private String isLate;

    /**
     *
     */
    private String isOut;

    /**
     *
     */
    private String isAway;

    /**
     *
     */
    private String isEarly;

    /**
     *
     */
    private Integer isDeleted;

}
