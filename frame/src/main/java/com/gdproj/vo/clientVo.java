package com.gdproj.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class clientVo {
    private Integer clientId;
    private String clientName;
    private Integer clientOrderId;

    private Integer clientManager;

    private String clientFrom;

    private Integer clientStatus;

    private Integer categroyId;

    private String Category;

    private String clientCompany;

    private String clientLocation;

    private String clientPhone;

    private String clientFax;

    private String clientEmail;

    private String clientAddr;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;

    private List<Map<String,String>> clientContacts;

    private String clientDescription;

    private Integer createdUser;

    private String Username;

    private List<fileVo> clientAttachments;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    private Integer projectId;

    private Integer contractId;
}
