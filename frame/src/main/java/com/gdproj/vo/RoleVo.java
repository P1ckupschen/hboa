package com.gdproj.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVo {

    private Integer roleId;

    private String roleName;

    private String roleKey;

    private Integer roleSort;

    private Integer status;

    private String createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date createdTime;

    private String updateUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date updateTime;

    private String remark;


    private Integer orderId;
}
