package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.gdproj.vo.FileVo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @TableName sys_ticket
 */
@TableName(value ="sys_ticket",autoResultMap = true)
public class Ticket implements Serializable {
    /**
     * 
     */
    @TableId(value = "ticket_id", type = IdType.AUTO)
    private Integer ticketId;

    /**
     * 
     */
    @TableField(value = "ticket_name")
    private String ticketName;

    /**
     * 
     */
    @TableField(value = "ticket_company")
    private String ticketCompany;

    /**
     * 
     */
    @TableField(value = "ticket_url",typeHandler = JacksonTypeHandler.class)
    private List<FileVo> ticketUrl;

    /**
     * 
     */
    @TableField(value = "created_time",fill = FieldFill.INSERT)
    private Date createdTime;

    /**
     * 
     */
    @TableField(value = "created_user")
    private Integer createdUser;

    /**
     * 
     */
    @TableField(value = "is_deleted")
    @TableLogic
    private Integer isDeleted;

    public String getTicketRemark() {
        return ticketRemark;
    }

    public void setTicketRemark(String ticketRemark) {
        this.ticketRemark = ticketRemark;
    }

    @TableField(value = "ticket_remark")
    private String ticketRemark;

    /**
     * 
     */
    @TableField(value = "ticket_time")
    private Date ticketTime;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    @TableField(value="category_id")
    private Integer categoryId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getTicketId() {
        return ticketId;
    }

    /**
     * 
     */
    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    /**
     * 
     */
    public String getTicketName() {
        return ticketName;
    }

    /**
     * 
     */
    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    /**
     * 
     */
    public String getTicketCompany() {
        return ticketCompany;
    }

    /**
     * 
     */
    public void setTicketCompany(String ticketCompany) {
        this.ticketCompany = ticketCompany;
    }

    /**
     * 
     */
    public List<FileVo> getTicketUrl() {
        return ticketUrl;
    }

    /**
     * 
     */
    public void setTicketUrl(List<FileVo> ticketUrl) {
        this.ticketUrl = ticketUrl;
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
    public Date getTicketTime() {
        return ticketTime;
    }

    /**
     * 
     */
    public void setTicketTime(Date ticketTime) {
        this.ticketTime = ticketTime;
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
        Ticket other = (Ticket) that;
        return (this.getTicketId() == null ? other.getTicketId() == null : this.getTicketId().equals(other.getTicketId()))
            && (this.getTicketName() == null ? other.getTicketName() == null : this.getTicketName().equals(other.getTicketName()))
            && (this.getTicketCompany() == null ? other.getTicketCompany() == null : this.getTicketCompany().equals(other.getTicketCompany()))
            && (this.getTicketUrl() == null ? other.getTicketUrl() == null : this.getTicketUrl().equals(other.getTicketUrl()))
            && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
            && (this.getCreatedUser() == null ? other.getCreatedUser() == null : this.getCreatedUser().equals(other.getCreatedUser()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getTicketTime() == null ? other.getTicketTime() == null : this.getTicketTime().equals(other.getTicketTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTicketId() == null) ? 0 : getTicketId().hashCode());
        result = prime * result + ((getTicketName() == null) ? 0 : getTicketName().hashCode());
        result = prime * result + ((getTicketCompany() == null) ? 0 : getTicketCompany().hashCode());
        result = prime * result + ((getTicketUrl() == null) ? 0 : getTicketUrl().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getCreatedUser() == null) ? 0 : getCreatedUser().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getTicketTime() == null) ? 0 : getTicketTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", ticketId=").append(ticketId);
        sb.append(", ticketName=").append(ticketName);
        sb.append(", ticketCompany=").append(ticketCompany);
        sb.append(", ticketUrl=").append(ticketUrl);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", createdUser=").append(createdUser);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", ticketTime=").append(ticketTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}