package com.gdproj.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class notifyVo {

    private Integer notifyId;

    private Integer categoryId;

    private Integer orderId;

    private String notifyContent;

    private String notifyTitle;

    private String notifyIstopping;

    private Integer userId;

    private String notifyRange;

    private List<fileVo> notifyAttachments;

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
