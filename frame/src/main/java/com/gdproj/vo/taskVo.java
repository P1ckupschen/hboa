package com.gdproj.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gdproj.handler.jsonAndListTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class taskVo {

    private Integer taskId;

    private String taskName;

    private Integer assignedId;

    private String assignedUsername;

    private Integer executorId;

    private String executorUsername;

    private Integer taskUrgency;

    private String taskContent;

    private String taskFeedback;

    private Integer isFeedback;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date completeTime;

    private Integer isOverdue;

    private Integer createdUser;

    private String Username;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createdTime;

    private List<fileVo> taskAttachments;

    private String taskAddr;

    private String taskContacts;

    private String  taskClient;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date taskTime;

    private Integer categoryId;

    private String Category;
}
