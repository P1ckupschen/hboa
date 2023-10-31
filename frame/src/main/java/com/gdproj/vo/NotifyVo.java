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
public class NotifyVo {

    private Integer notifyId;

    private Integer categoryId;

    private Integer orderId;

    private String notifyContent;

    private String notifyTitle;

    private String notifyIstopping;

    private Integer userId;

    private String notifyRange;

    private List<FileVo> notifyAttachments;

    private Integer notifyStatus;

    private Integer examinerId;

    private String Category;
    private String Username;
    private String department;
    private Integer departmentId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date effectiveDate;
    private String examinerName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;


}
