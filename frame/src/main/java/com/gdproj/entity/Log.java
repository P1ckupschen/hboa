package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @TableName sys_log
 */
@TableName(value ="sys_log")
public class Log implements Serializable {
    /**
     *
     */
    @TableId(value = "log_id", type = IdType.AUTO)
    private Integer logId;

    /**
     *
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     *
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     *
     */
    @TableField(value = "log_content")
    private String logContent;

    /**
     *
     */
    @TableField(value = "user_ip")
    private String userIp;

    /**
     *
     */
    @TableField(value = "is_deleted")
    @TableLogic
    private Integer isDeleted;

    /**
     *
     */
    @TableField(value = "created_time",fill = FieldFill.INSERT)
    private Date createdTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public Integer getLogId() {
        return logId;
    }

    /**
     *
     */
    public void setLogId(Integer logId) {
        this.logId = logId;
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
    public String getLogContent() {
        return logContent;
    }

    /**
     *
     */
    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    /**
     *
     */
    public String getUserIp() {
        return userIp;
    }

    /**
     *
     */
    public void setUserIp(String userIp) {
        this.userIp = userIp;
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
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     *
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
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
        Log other = (Log) that;
        return (this.getLogId() == null ? other.getLogId() == null : this.getLogId().equals(other.getLogId()))
                && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
                && (this.getLogContent() == null ? other.getLogContent() == null : this.getLogContent().equals(other.getLogContent()))
                && (this.getUserIp() == null ? other.getUserIp() == null : this.getUserIp().equals(other.getUserIp()))
                && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
                && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getLogId() == null) ? 0 : getLogId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getLogContent() == null) ? 0 : getLogContent().hashCode());
        result = prime * result + ((getUserIp() == null) ? 0 : getUserIp().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", logId=").append(logId);
        sb.append(", userId=").append(userId);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", logContent=").append(logContent);
        sb.append(", userIp=").append(userIp);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}