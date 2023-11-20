package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName sys_error_log
 */
@TableName(value ="sys_error_log")
public class ErrorLog implements Serializable {
    /**
     * 
     */
    @TableId(value = "error_id", type = IdType.AUTO)
    private Integer errorId;

    /**
     * 
     */
    @TableField(value = "error_time",fill = FieldFill.INSERT)
    private Date errorTime;

    /**
     * 
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 
     */
    @TableField(value = "ip")
    private String ip;

    /**
     * 
     */
    @TableField(value = "user_name")
    private String userName;

    /**
     * 
     */
    @TableField(value = "error_url")
    private String errorUrl;

    /**
     * 
     */
    @TableField(value = "error_method")
    private String errorMethod;

    /**
     * 
     */
    @TableField(value = "error_name")
    private String errorName;

    /**
     * 
     */
    @TableField(value = "method_params")
    private String methodParams;

    /**
     * 
     */
    @TableField(value = "is_deleted")
    @TableLogic
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getErrorId() {
        return errorId;
    }

    /**
     * 
     */
    public void setErrorId(Integer errorId) {
        this.errorId = errorId;
    }

    /**
     * 
     */
    public Date getErrorTime() {
        return errorTime;
    }

    /**
     * 
     */
    public void setErrorTime(Date errorTime) {
        this.errorTime = errorTime;
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
    public String getIp() {
        return ip;
    }

    /**
     * 
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * 
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 
     */
    public String getErrorUrl() {
        return errorUrl;
    }

    /**
     * 
     */
    public void setErrorUrl(String errorUrl) {
        this.errorUrl = errorUrl;
    }

    /**
     * 
     */
    public String getErrorMethod() {
        return errorMethod;
    }

    /**
     * 
     */
    public void setErrorMethod(String errorMethod) {
        this.errorMethod = errorMethod;
    }

    /**
     * 
     */
    public String getErrorName() {
        return errorName;
    }

    /**
     * 
     */
    public void setErrorName(String errorName) {
        this.errorName = errorName;
    }

    /**
     * 
     */
    public String getMethodParams() {
        return methodParams;
    }

    /**
     * 
     */
    public void setMethodParams(String methodParams) {
        this.methodParams = methodParams;
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
        ErrorLog other = (ErrorLog) that;
        return (this.getErrorId() == null ? other.getErrorId() == null : this.getErrorId().equals(other.getErrorId()))
            && (this.getErrorTime() == null ? other.getErrorTime() == null : this.getErrorTime().equals(other.getErrorTime()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getIp() == null ? other.getIp() == null : this.getIp().equals(other.getIp()))
            && (this.getUserName() == null ? other.getUserName() == null : this.getUserName().equals(other.getUserName()))
            && (this.getErrorUrl() == null ? other.getErrorUrl() == null : this.getErrorUrl().equals(other.getErrorUrl()))
            && (this.getErrorMethod() == null ? other.getErrorMethod() == null : this.getErrorMethod().equals(other.getErrorMethod()))
            && (this.getErrorName() == null ? other.getErrorName() == null : this.getErrorName().equals(other.getErrorName()))
            && (this.getMethodParams() == null ? other.getMethodParams() == null : this.getMethodParams().equals(other.getMethodParams()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getErrorId() == null) ? 0 : getErrorId().hashCode());
        result = prime * result + ((getErrorTime() == null) ? 0 : getErrorTime().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getIp() == null) ? 0 : getIp().hashCode());
        result = prime * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
        result = prime * result + ((getErrorUrl() == null) ? 0 : getErrorUrl().hashCode());
        result = prime * result + ((getErrorMethod() == null) ? 0 : getErrorMethod().hashCode());
        result = prime * result + ((getErrorName() == null) ? 0 : getErrorName().hashCode());
        result = prime * result + ((getMethodParams() == null) ? 0 : getMethodParams().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", errorId=").append(errorId);
        sb.append(", errorTime=").append(errorTime);
        sb.append(", userId=").append(userId);
        sb.append(", ip=").append(ip);
        sb.append(", userName=").append(userName);
        sb.append(", errorUrl=").append(errorUrl);
        sb.append(", errorMethod=").append(errorMethod);
        sb.append(", errorName=").append(errorName);
        sb.append(", methodParams=").append(methodParams);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}