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
public class ReportVo {


    private Integer reportId;

    private Integer userId;

    private String Username;

    private String Department;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;

    private Integer categoryId;

    private String Category;

    private String reportContent;

    private String reportNextStage;

    private List<FileVo> reportPic;

    private List<FileVo> reportFile;

    private String feedBack;


    private Integer orderId;


}
