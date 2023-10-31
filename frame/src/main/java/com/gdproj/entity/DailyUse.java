package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.gdproj.vo.FileVo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @TableName sys_dailyuse
 */
@TableName(value ="sys_dailyuse",autoResultMap = true)
public class DailyUse implements Serializable {
    /**
     * 
     */
    @TableId(value = "dailyuse_id", type = IdType.AUTO)
    private Integer dailyuseId;

    /**
     * 
     */
    @TableField(value = "dailyuse_title")
    private String dailyuseTitle;

    /**
     * 
     */
    @TableField(value = "dailyuse_content")
    private String dailyuseContent;

    /**
     * 
     */
    @TableField(value = "created_time",fill = FieldFill.INSERT)
    private Date createdTime;

    /**
     * 
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 0个人使用 1项目使用
     */
    @TableField(value = "category_id")
    private Integer categoryId;

    /**
     * 
     */
    @TableField(value = "dailyuse_feedback")
    private String dailyuseFeedback;

    /**
     * 
     */
    @TableField(value = "dailyuse_description")
    private String dailyuseDescription;

    /**
     * 
     */
    @TableField(value = "dailyuse_status")
    private Integer dailyuseStatus;

    /**
     * 
     */
    @TableField(value = "dailyuse_attachments",typeHandler = JacksonTypeHandler.class)
    private List<FileVo> dailyuseAttachments;

    /**
     * 
     */
    @TableField(value = "type_id")
    private Integer typeId;

    /**
     * 
     */
    @TableField(value = "is_deleted")
    @TableLogic
    private Integer isDeleted;

    /**
     * 
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 
     */
    @TableField(value = "project_id")
    private Integer projectId;

    /**
     * 
     */
    @TableField(value = "return_time")
    private Date returnTime;

    /**
     * 
     */
    @TableField(value = "t_return_time")
    private Date tReturnTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getDailyuseId() {
        return dailyuseId;
    }

    /**
     * 
     */
    public void setDailyuseId(Integer dailyuseId) {
        this.dailyuseId = dailyuseId;
    }

    /**
     * 
     */
    public String getDailyuseTitle() {
        return dailyuseTitle;
    }

    /**
     * 
     */
    public void setDailyuseTitle(String dailyuseTitle) {
        this.dailyuseTitle = dailyuseTitle;
    }

    /**
     * 
     */
    public String getDailyuseContent() {
        return dailyuseContent;
    }

    /**
     * 
     */
    public void setDailyuseContent(String dailyuseContent) {
        this.dailyuseContent = dailyuseContent;
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
     * 0个人使用 1项目使用
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * 0个人使用 1项目使用
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 
     */
    public String getDailyuseFeedback() {
        return dailyuseFeedback;
    }

    /**
     * 
     */
    public void setDailyuseFeedback(String dailyuseFeedback) {
        this.dailyuseFeedback = dailyuseFeedback;
    }

    /**
     * 
     */
    public String getDailyuseDescription() {
        return dailyuseDescription;
    }

    /**
     * 
     */
    public void setDailyuseDescription(String dailyuseDescription) {
        this.dailyuseDescription = dailyuseDescription;
    }

    /**
     * 
     */
    public Integer getDailyuseStatus() {
        return dailyuseStatus;
    }

    /**
     * 
     */
    public void setDailyuseStatus(Integer dailyuseStatus) {
        this.dailyuseStatus = dailyuseStatus;
    }

    /**
     * 
     */
    public List<FileVo> getDailyuseAttachments() {
        return dailyuseAttachments;
    }

    /**
     * 
     */
    public void setDailyuseAttachments(List<FileVo> dailyuseAttachments) {
        this.dailyuseAttachments = dailyuseAttachments;
    }

    /**
     * 
     */
    public Integer getTypeId() {
        return typeId;
    }

    /**
     * 
     */
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
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
     * 
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 
     */
    public Integer getProjectId() {
        return projectId;
    }

    /**
     * 
     */
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    /**
     * 
     */
    public Date getReturnTime() {
        return returnTime;
    }

    /**
     * 
     */
    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    /**
     * 
     */
    public Date gettReturnTime() {
        return tReturnTime;
    }

    /**
     * 
     */
    public void settReturnTime(Date tReturnTime) {
        this.tReturnTime = tReturnTime;
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
        DailyUse other = (DailyUse) that;
        return (this.getDailyuseId() == null ? other.getDailyuseId() == null : this.getDailyuseId().equals(other.getDailyuseId()))
            && (this.getDailyuseTitle() == null ? other.getDailyuseTitle() == null : this.getDailyuseTitle().equals(other.getDailyuseTitle()))
            && (this.getDailyuseContent() == null ? other.getDailyuseContent() == null : this.getDailyuseContent().equals(other.getDailyuseContent()))
            && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
            && (this.getDailyuseFeedback() == null ? other.getDailyuseFeedback() == null : this.getDailyuseFeedback().equals(other.getDailyuseFeedback()))
            && (this.getDailyuseDescription() == null ? other.getDailyuseDescription() == null : this.getDailyuseDescription().equals(other.getDailyuseDescription()))
            && (this.getDailyuseStatus() == null ? other.getDailyuseStatus() == null : this.getDailyuseStatus().equals(other.getDailyuseStatus()))
            && (this.getDailyuseAttachments() == null ? other.getDailyuseAttachments() == null : this.getDailyuseAttachments().equals(other.getDailyuseAttachments()))
            && (this.getTypeId() == null ? other.getTypeId() == null : this.getTypeId().equals(other.getTypeId()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getProjectId() == null ? other.getProjectId() == null : this.getProjectId().equals(other.getProjectId()))
            && (this.getReturnTime() == null ? other.getReturnTime() == null : this.getReturnTime().equals(other.getReturnTime()))
            && (this.gettReturnTime() == null ? other.gettReturnTime() == null : this.gettReturnTime().equals(other.gettReturnTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDailyuseId() == null) ? 0 : getDailyuseId().hashCode());
        result = prime * result + ((getDailyuseTitle() == null) ? 0 : getDailyuseTitle().hashCode());
        result = prime * result + ((getDailyuseContent() == null) ? 0 : getDailyuseContent().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getDailyuseFeedback() == null) ? 0 : getDailyuseFeedback().hashCode());
        result = prime * result + ((getDailyuseDescription() == null) ? 0 : getDailyuseDescription().hashCode());
        result = prime * result + ((getDailyuseStatus() == null) ? 0 : getDailyuseStatus().hashCode());
        result = prime * result + ((getDailyuseAttachments() == null) ? 0 : getDailyuseAttachments().hashCode());
        result = prime * result + ((getTypeId() == null) ? 0 : getTypeId().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getProjectId() == null) ? 0 : getProjectId().hashCode());
        result = prime * result + ((getReturnTime() == null) ? 0 : getReturnTime().hashCode());
        result = prime * result + ((gettReturnTime() == null) ? 0 : gettReturnTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", dailyuseId=").append(dailyuseId);
        sb.append(", dailyuseTitle=").append(dailyuseTitle);
        sb.append(", dailyuseContent=").append(dailyuseContent);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", dailyuseFeedback=").append(dailyuseFeedback);
        sb.append(", dailyuseDescription=").append(dailyuseDescription);
        sb.append(", dailyuseStatus=").append(dailyuseStatus);
        sb.append(", dailyuseAttachments=").append(dailyuseAttachments);
        sb.append(", typeId=").append(typeId);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", userId=").append(userId);
        sb.append(", projectId=").append(projectId);
        sb.append(", returnTime=").append(returnTime);
        sb.append(", tReturnTime=").append(tReturnTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}