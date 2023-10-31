package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.gdproj.vo.FileVo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 
 * @TableName sys_reimbursement
 */
@TableName(value ="sys_reimbursement",autoResultMap = true)
public class Reimbursement implements Serializable {
    /**
     * 
     */
    @TableId(value = "reimbursement_id", type = IdType.AUTO)
    private Integer reimbursementId;

    /**
     * 
     */
    @TableField(value = "reimbursement_title")
    private String reimbursementTitle;

    /**
     * 
     */
    @TableField(value = "user_id")
    private Integer userId;

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
     * 
     */
    @TableField(value = "reimbursement_amount")
    private BigDecimal reimbursementAmount;

    /**
     * 
     */
    @TableField(value = "project_id")
    private Integer projectId;

    /**
     * 
     */
    @TableField(value = "reimbursement_description")
    private String reimbursementDescription;

    /**
     * 
     */
    @TableField(value = "reimbursement_attachments",typeHandler = JacksonTypeHandler.class)
    private List<FileVo> reimbursementAttachments;

    /**
     * 
     */
    @TableField(value = "is_deleted")
    @TableLogic
    private Integer isDeleted;

    /**
     * 
     */
    @TableField(value = "type_id")
    private Integer typeId;

    /**
     * 
     */
    @TableField(value = "reimbursement_status")
    private Integer reimbursementStatus;

    /**
     * 
     */
    @TableField(value = "reimbursement_time")
    private Date reimbursementTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getReimbursementId() {
        return reimbursementId;
    }

    /**
     * 
     */
    public void setReimbursementId(Integer reimbursementId) {
        this.reimbursementId = reimbursementId;
    }

    /**
     * 
     */
    public String getReimbursementTitle() {
        return reimbursementTitle;
    }

    /**
     * 
     */
    public void setReimbursementTitle(String reimbursementTitle) {
        this.reimbursementTitle = reimbursementTitle;
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
     * 
     */
    public BigDecimal getReimbursementAmount() {
        return reimbursementAmount;
    }

    /**
     *
     */
    public void setReimbursementAmount(BigDecimal reimbursementAmount) {
        this.reimbursementAmount = reimbursementAmount;
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
    public String getReimbursementDescription() {
        return reimbursementDescription;
    }

    /**
     * 
     */
    public void setReimbursementDescription(String reimbursementDescription) {
        this.reimbursementDescription = reimbursementDescription;
    }

    /**
     * 
     */
    public List<FileVo> getReimbursementAttachments() {
        return reimbursementAttachments;
    }

    /**
     * 
     */
    public void setReimbursementAttachments(List<FileVo> reimbursementAttachments) {
        this.reimbursementAttachments = reimbursementAttachments;
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
    public Integer getReimbursementStatus() {
        return reimbursementStatus;
    }

    /**
     * 
     */
    public void setReimbursementStatus(Integer reimbursementStatus) {
        this.reimbursementStatus = reimbursementStatus;
    }

    /**
     * 
     */
    public Date getReimbursementTime() {
        return reimbursementTime;
    }

    /**
     * 
     */
    public void setReimbursementTime(Date reimbursementTime) {
        this.reimbursementTime = reimbursementTime;
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
        Reimbursement other = (Reimbursement) that;
        return (this.getReimbursementId() == null ? other.getReimbursementId() == null : this.getReimbursementId().equals(other.getReimbursementId()))
            && (this.getReimbursementTitle() == null ? other.getReimbursementTitle() == null : this.getReimbursementTitle().equals(other.getReimbursementTitle()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getReimbursementAmount() == null ? other.getReimbursementAmount() == null : this.getReimbursementAmount().equals(other.getReimbursementAmount()))
            && (this.getProjectId() == null ? other.getProjectId() == null : this.getProjectId().equals(other.getProjectId()))
            && (this.getReimbursementDescription() == null ? other.getReimbursementDescription() == null : this.getReimbursementDescription().equals(other.getReimbursementDescription()))
            && (this.getReimbursementAttachments() == null ? other.getReimbursementAttachments() == null : this.getReimbursementAttachments().equals(other.getReimbursementAttachments()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getTypeId() == null ? other.getTypeId() == null : this.getTypeId().equals(other.getTypeId()))
            && (this.getReimbursementStatus() == null ? other.getReimbursementStatus() == null : this.getReimbursementStatus().equals(other.getReimbursementStatus()))
            && (this.getReimbursementTime() == null ? other.getReimbursementTime() == null : this.getReimbursementTime().equals(other.getReimbursementTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getReimbursementId() == null) ? 0 : getReimbursementId().hashCode());
        result = prime * result + ((getReimbursementTitle() == null) ? 0 : getReimbursementTitle().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getReimbursementAmount() == null) ? 0 : getReimbursementAmount().hashCode());
        result = prime * result + ((getProjectId() == null) ? 0 : getProjectId().hashCode());
        result = prime * result + ((getReimbursementDescription() == null) ? 0 : getReimbursementDescription().hashCode());
        result = prime * result + ((getReimbursementAttachments() == null) ? 0 : getReimbursementAttachments().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getTypeId() == null) ? 0 : getTypeId().hashCode());
        result = prime * result + ((getReimbursementStatus() == null) ? 0 : getReimbursementStatus().hashCode());
        result = prime * result + ((getReimbursementTime() == null) ? 0 : getReimbursementTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", reimbursementId=").append(reimbursementId);
        sb.append(", reimbursementTitle=").append(reimbursementTitle);
        sb.append(", userId=").append(userId);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", reimbursementAmout=").append(reimbursementAmount);
        sb.append(", projectId=").append(projectId);
        sb.append(", reimbursementDescription=").append(reimbursementDescription);
        sb.append(", reimbursementAttachments=").append(reimbursementAttachments);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", typeId=").append(typeId);
        sb.append(", reimbursementStatus=").append(reimbursementStatus);
        sb.append(", reimbursementTime=").append(reimbursementTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}