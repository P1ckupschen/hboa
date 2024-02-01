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
public class TaskVo {

    private Integer taskId;

    private String taskName;

    private Integer applicantId;

    private String assignedUsername;

    private List<Integer> executorIds;

    private List<String> executorUsername;

    private Integer taskUrgency;

    private String taskContent;

    private String taskFeedback;

    private Integer isFeedback;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date completeTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;

    private Integer isOverdue;

    private Integer createdUser;

    private String Username;

    private List<FileVo> taskAttachments;

    private String taskAddr;

    private String taskContacts;

    private String  taskClient;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date taskTime;

    private Integer categoryId;

    private String Category;

    private Integer isCompleted;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date overdueTime;

    private Integer orderId;
}
