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
public class DailyUseVo {

    private Integer dailyuseId;

    private String dailyuseTitle;

    private List<DailyUseContentVo> dailyuseContent;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    private Integer categoryId;

    private Integer purposeId;

    private String dailyuseFeedback;

    private String dailyuseDescription;

    private Integer dailyuseStatus;

    private List<FileVo> dailyuseAttachments;

    private Integer typeId;

    private Integer userId;

    private Integer projectId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date returnTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date tReturnTime;

    private String Category;

    private String Username;
}
