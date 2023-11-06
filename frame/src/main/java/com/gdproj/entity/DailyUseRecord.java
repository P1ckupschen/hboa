package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.gdproj.vo.FileVo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @TableName sys_dailyuse_record
 */
@TableName(value ="sys_dailyuse_record",autoResultMap = true)
public class DailyUseRecord implements Serializable {
    /**
     *
     */
    @TableId(value = "record_id", type = IdType.AUTO)
    private Integer recordId;

    /**
     *
     */
    @TableField(value = "category_id")
    private Integer categoryId;

    /**
     *
     */
    @TableField(value = "count")
    private Integer count;

    /**
     *
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     *
     */
    @TableField(value = "created_time",fill = FieldFill.INSERT)
    private Date createdTime;

    /**
     *
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 出库时间/归还时间
     */
    @TableField(value = "record_time")
    private Date recordTime;

    /**
     *
     */
    @TableField(value = "is_deleted")
    @TableLogic
    private Integer isDeleted;

    /**
     *
     */
    @TableField(value = "record_description")
    private String recordDescription;

    /**
     *
     */
    @TableField(value = "record_attachments",typeHandler = JacksonTypeHandler.class)
    private List<FileVo> recordAttachments;

    /**
     *
     */
    @TableField(value = "record_status")
    private Integer recordStatus;

    /**
     *
     */
    @TableField(value = "tool_id")
    private Integer toolId;

    /**
     * 对应dailyuse 的id 一个record可能对应多个dailyuse
     */
    @TableField(value = "dailyuse_id")
    private Integer dailyuseId;

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

    /**
     * 1个人 2项目
     */
    @TableField(value = "purpose_id")
    private Integer purposeId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public Integer getRecordId() {
        return recordId;
    }

    /**
     *
     */
    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    /**
     *
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     *
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     *
     */
    public Integer getCount() {
        return count;
    }

    /**
     *
     */
    public void setCount(Integer count) {
        this.count = count;
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
     * 出库时间/归还时间
     */
    public Date getRecordTime() {
        return recordTime;
    }

    /**
     * 出库时间/归还时间
     */
    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
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
    public String getRecordDescription() {
        return recordDescription;
    }

    /**
     *
     */
    public void setRecordDescription(String recordDescription) {
        this.recordDescription = recordDescription;
    }

    /**
     *
     */
    public List<FileVo> getRecordAttachments() {
        return recordAttachments;
    }

    /**
     *
     */
    public void setRecordAttachments(List<FileVo> recordAttachments) {
        this.recordAttachments = recordAttachments;
    }

    /**
     *
     */
    public Integer getRecordStatus() {
        return recordStatus;
    }

    /**
     *
     */
    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
    }

    /**
     *
     */
    public Integer getToolId() {
        return toolId;
    }

    /**
     *
     */
    public void setToolId(Integer toolId) {
        this.toolId = toolId;
    }

    /**
     * 对应dailyuse 的id 一个record可能对应多个dailyuse
     */
    public Integer getDailyuseId() {
        return dailyuseId;
    }

    /**
     * 对应dailyuse 的id 一个record可能对应多个dailyuse
     */
    public void setDailyuseId(Integer dailyuseId) {
        this.dailyuseId = dailyuseId;
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

    /**
     * 1个人 2项目
     */
    public Integer getPurposeId() {
        return purposeId;
    }

    /**
     * 1个人 2项目
     */
    public void setPurposeId(Integer purposeId) {
        this.purposeId = purposeId;
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
        DailyUseRecord other = (DailyUseRecord) that;
        return (this.getRecordId() == null ? other.getRecordId() == null : this.getRecordId().equals(other.getRecordId()))
                && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
                && (this.getCount() == null ? other.getCount() == null : this.getCount().equals(other.getCount()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
                && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
                && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
                && (this.getRecordTime() == null ? other.getRecordTime() == null : this.getRecordTime().equals(other.getRecordTime()))
                && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
                && (this.getRecordDescription() == null ? other.getRecordDescription() == null : this.getRecordDescription().equals(other.getRecordDescription()))
                && (this.getRecordAttachments() == null ? other.getRecordAttachments() == null : this.getRecordAttachments().equals(other.getRecordAttachments()))
                && (this.getRecordStatus() == null ? other.getRecordStatus() == null : this.getRecordStatus().equals(other.getRecordStatus()))
                && (this.getToolId() == null ? other.getToolId() == null : this.getToolId().equals(other.getToolId()))
                && (this.getDailyuseId() == null ? other.getDailyuseId() == null : this.getDailyuseId().equals(other.getDailyuseId()))
                && (this.getReturnTime() == null ? other.getReturnTime() == null : this.getReturnTime().equals(other.getReturnTime()))
                && (this.gettReturnTime() == null ? other.gettReturnTime() == null : this.gettReturnTime().equals(other.gettReturnTime()))
                && (this.getPurposeId() == null ? other.getPurposeId() == null : this.getPurposeId().equals(other.getPurposeId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRecordId() == null) ? 0 : getRecordId().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getCount() == null) ? 0 : getCount().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getRecordTime() == null) ? 0 : getRecordTime().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getRecordDescription() == null) ? 0 : getRecordDescription().hashCode());
        result = prime * result + ((getRecordAttachments() == null) ? 0 : getRecordAttachments().hashCode());
        result = prime * result + ((getRecordStatus() == null) ? 0 : getRecordStatus().hashCode());
        result = prime * result + ((getToolId() == null) ? 0 : getToolId().hashCode());
        result = prime * result + ((getDailyuseId() == null) ? 0 : getDailyuseId().hashCode());
        result = prime * result + ((getReturnTime() == null) ? 0 : getReturnTime().hashCode());
        result = prime * result + ((gettReturnTime() == null) ? 0 : gettReturnTime().hashCode());
        result = prime * result + ((getPurposeId() == null) ? 0 : getPurposeId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", recordId=").append(recordId);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", count=").append(count);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", userId=").append(userId);
        sb.append(", recordTime=").append(recordTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", recordDescription=").append(recordDescription);
        sb.append(", recordAttachments=").append(recordAttachments);
        sb.append(", recordStatus=").append(recordStatus);
        sb.append(", toolId=").append(toolId);
        sb.append(", dailyuseId=").append(dailyuseId);
        sb.append(", returnTime=").append(returnTime);
        sb.append(", tReturnTime=").append(tReturnTime);
        sb.append(", purposeId=").append(purposeId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}