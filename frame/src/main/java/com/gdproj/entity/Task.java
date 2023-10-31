package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.gdproj.vo.FileVo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @TableName sys_task
 */
@TableName(value ="sys_task",autoResultMap = true)
public class Task implements Serializable {
    /**
     *
     */
    @TableId(value = "task_id", type = IdType.AUTO)
    private Integer taskId;

    /**
     *
     */
    @TableField(value = "task_name")
    private String taskName;

    /**
     * 申请人，即指派人
     */
    @TableField(value = "applicant_id")
    private Integer applicantId;

    /**
     * 执行人
     */
    @TableField(value = "executor_id")
    private Integer executorId;

    /**
     *
     */
    @TableField(value = "task_urgency")
    private Integer taskUrgency;

    /**
     *
     */
    @TableField(value = "task_content")
    private String taskContent;

    /**
     *
     */
    @TableField(value = "task_feedback")
    private String taskFeedback;

    /**
     * 0未反馈，1已反馈
     */
    @TableField(value = "is_feedback")
    private Integer isFeedback;

    /**
     *
     */
    @TableField(value = "complete_time")
    private Date completeTime;

    /**
     * 0未延期，1延期
     */
    @TableField(value = "is_overdue")
    private Integer isOverdue;

    /**
     *
     */
    @TableField(value = "created_user")
    private Integer createdUser;

    /**
     *
     */
    @TableField(value = "created_time",fill = FieldFill.INSERT)
    private Date createdTime;

    /**
     * 附件
     */
    @TableField(value = "task_attachments",typeHandler = JacksonTypeHandler.class)
    private List<FileVo> taskAttachments;

    /**
     *
     */
    @TableField(value = "task_addr")
    private String taskAddr;

    /**
     *
     */
    @TableField(value = "task_contacts")
    private String taskContacts;

    /**
     *
     */
    @TableField(value = "is_deleted")
    @TableLogic
    private Integer isDeleted;

    /**
     * 任务时间
     */
    @TableField(value = "task_time")
    private Date taskTime;

    /**
     * 0 为自主申请 1为被指派
     */
    @TableField(value = "category_id")
    private Integer categoryId;

    /**
     *
     */
    @TableField(value = "task_client")
    private String taskClient;

    /**
     *
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 延期至几号
     */
    @TableField(value = "overdue_time")
    private Date overdueTime;

    /**
     *
     */
    @TableField(value = "is_completed")
    private Integer isCompleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public Integer getTaskId() {
        return taskId;
    }

    /**
     *
     */
    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    /**
     *
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     *
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * 申请人，即指派人
     */
    public Integer getApplicantId() {
        return applicantId;
    }

    /**
     * 申请人，即指派人
     */
    public void setApplicantId(Integer applicantId) {
        this.applicantId = applicantId;
    }

    /**
     * 执行人
     */
    public Integer getExecutorId() {
        return executorId;
    }

    /**
     * 执行人
     */
    public void setExecutorId(Integer executorId) {
        this.executorId = executorId;
    }

    /**
     *
     */
    public Integer getTaskUrgency() {
        return taskUrgency;
    }

    /**
     *
     */
    public void setTaskUrgency(Integer taskUrgency) {
        this.taskUrgency = taskUrgency;
    }

    /**
     *
     */
    public String getTaskContent() {
        return taskContent;
    }

    /**
     *
     */
    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    /**
     *
     */
    public String getTaskFeedback() {
        return taskFeedback;
    }

    /**
     *
     */
    public void setTaskFeedback(String taskFeedback) {
        this.taskFeedback = taskFeedback;
    }

    /**
     * 0未反馈，1已反馈
     */
    public Integer getIsFeedback() {
        return isFeedback;
    }

    /**
     * 0未反馈，1已反馈
     */
    public void setIsFeedback(Integer isFeedback) {
        this.isFeedback = isFeedback;
    }

    /**
     *
     */
    public Date getCompleteTime() {
        return completeTime;
    }

    /**
     *
     */
    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    /**
     * 0未延期，1延期
     */
    public Integer getIsOverdue() {
        return isOverdue;
    }

    /**
     * 0未延期，1延期
     */
    public void setIsOverdue(Integer isOverdue) {
        this.isOverdue = isOverdue;
    }

    /**
     *
     */
    public Integer getCreatedUser() {
        return createdUser;
    }

    /**
     *
     */
    public void setCreatedUser(Integer createdUser) {
        this.createdUser = createdUser;
    }

    /**
     *
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     *
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * 附件
     */
    public List<FileVo> getTaskAttachments() {
        return taskAttachments;
    }

    /**
     * 附件
     */
    public void setTaskAttachments(List<FileVo> taskAttachments) {
        this.taskAttachments = taskAttachments;
    }

    /**
     *
     */
    public String getTaskAddr() {
        return taskAddr;
    }

    /**
     *
     */
    public void setTaskAddr(String taskAddr) {
        this.taskAddr = taskAddr;
    }

    /**
     *
     */
    public String getTaskContacts() {
        return taskContacts;
    }

    /**
     *
     */
    public void setTaskContacts(String taskContacts) {
        this.taskContacts = taskContacts;
    }

    /**
     *
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     *
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * 任务时间
     */
    public Date getTaskTime() {
        return taskTime;
    }

    /**
     * 任务时间
     */
    public void setTaskTime(Date taskTime) {
        this.taskTime = taskTime;
    }

    /**
     * 0 为自主申请 1为被指派
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * 0 为自主申请 1为被指派
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     *
     */
    public String getTaskClient() {
        return taskClient;
    }

    /**
     *
     */
    public void setTaskClient(String taskClient) {
        this.taskClient = taskClient;
    }

    /**
     *
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     *
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 延期至几号
     */
    public Date getOverdueTime() {
        return overdueTime;
    }

    /**
     * 延期至几号
     */
    public void setOverdueTime(Date overdueTime) {
        this.overdueTime = overdueTime;
    }

    /**
     *
     */
    public Integer getIsCompleted() {
        return isCompleted;
    }

    /**
     *
     */
    public void setIsCompleted(Integer isCompleted) {
        this.isCompleted = isCompleted;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Task other = (Task) that;
        return (this.getTaskId() == null ? other.getTaskId() == null : this.getTaskId().equals(other.getTaskId()))
                && (this.getTaskName() == null ? other.getTaskName() == null : this.getTaskName().equals(other.getTaskName()))
                && (this.getApplicantId() == null ? other.getApplicantId() == null : this.getApplicantId().equals(other.getApplicantId()))
                && (this.getExecutorId() == null ? other.getExecutorId() == null : this.getExecutorId().equals(other.getExecutorId()))
                && (this.getTaskUrgency() == null ? other.getTaskUrgency() == null : this.getTaskUrgency().equals(other.getTaskUrgency()))
                && (this.getTaskContent() == null ? other.getTaskContent() == null : this.getTaskContent().equals(other.getTaskContent()))
                && (this.getTaskFeedback() == null ? other.getTaskFeedback() == null : this.getTaskFeedback().equals(other.getTaskFeedback()))
                && (this.getIsFeedback() == null ? other.getIsFeedback() == null : this.getIsFeedback().equals(other.getIsFeedback()))
                && (this.getCompleteTime() == null ? other.getCompleteTime() == null : this.getCompleteTime().equals(other.getCompleteTime()))
                && (this.getIsOverdue() == null ? other.getIsOverdue() == null : this.getIsOverdue().equals(other.getIsOverdue()))
                && (this.getCreatedUser() == null ? other.getCreatedUser() == null : this.getCreatedUser().equals(other.getCreatedUser()))
                && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
                && (this.getTaskAttachments() == null ? other.getTaskAttachments() == null : this.getTaskAttachments().equals(other.getTaskAttachments()))
                && (this.getTaskAddr() == null ? other.getTaskAddr() == null : this.getTaskAddr().equals(other.getTaskAddr()))
                && (this.getTaskContacts() == null ? other.getTaskContacts() == null : this.getTaskContacts().equals(other.getTaskContacts()))
                && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
                && (this.getTaskTime() == null ? other.getTaskTime() == null : this.getTaskTime().equals(other.getTaskTime()))
                && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
                && (this.getTaskClient() == null ? other.getTaskClient() == null : this.getTaskClient().equals(other.getTaskClient()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
                && (this.getOverdueTime() == null ? other.getOverdueTime() == null : this.getOverdueTime().equals(other.getOverdueTime()))
                && (this.getIsCompleted() == null ? other.getIsCompleted() == null : this.getIsCompleted().equals(other.getIsCompleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTaskId() == null) ? 0 : getTaskId().hashCode());
        result = prime * result + ((getTaskName() == null) ? 0 : getTaskName().hashCode());
        result = prime * result + ((getApplicantId() == null) ? 0 : getApplicantId().hashCode());
        result = prime * result + ((getExecutorId() == null) ? 0 : getExecutorId().hashCode());
        result = prime * result + ((getTaskUrgency() == null) ? 0 : getTaskUrgency().hashCode());
        result = prime * result + ((getTaskContent() == null) ? 0 : getTaskContent().hashCode());
        result = prime * result + ((getTaskFeedback() == null) ? 0 : getTaskFeedback().hashCode());
        result = prime * result + ((getIsFeedback() == null) ? 0 : getIsFeedback().hashCode());
        result = prime * result + ((getCompleteTime() == null) ? 0 : getCompleteTime().hashCode());
        result = prime * result + ((getIsOverdue() == null) ? 0 : getIsOverdue().hashCode());
        result = prime * result + ((getCreatedUser() == null) ? 0 : getCreatedUser().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getTaskAttachments() == null) ? 0 : getTaskAttachments().hashCode());
        result = prime * result + ((getTaskAddr() == null) ? 0 : getTaskAddr().hashCode());
        result = prime * result + ((getTaskContacts() == null) ? 0 : getTaskContacts().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getTaskTime() == null) ? 0 : getTaskTime().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getTaskClient() == null) ? 0 : getTaskClient().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getOverdueTime() == null) ? 0 : getOverdueTime().hashCode());
        result = prime * result + ((getIsCompleted() == null) ? 0 : getIsCompleted().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", taskId=").append(taskId);
        sb.append(", taskName=").append(taskName);
        sb.append(", applicantId=").append(applicantId);
        sb.append(", executorId=").append(executorId);
        sb.append(", taskUrgency=").append(taskUrgency);
        sb.append(", taskContent=").append(taskContent);
        sb.append(", taskFeedback=").append(taskFeedback);
        sb.append(", isFeedback=").append(isFeedback);
        sb.append(", completeTime=").append(completeTime);
        sb.append(", isOverdue=").append(isOverdue);
        sb.append(", createdUser=").append(createdUser);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", taskAttachments=").append(taskAttachments);
        sb.append(", taskAddr=").append(taskAddr);
        sb.append(", taskContacts=").append(taskContacts);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", taskTime=").append(taskTime);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", taskClient=").append(taskClient);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", overdueTime=").append(overdueTime);
        sb.append(", isCompleted=").append(isCompleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}