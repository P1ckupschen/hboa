package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.gdproj.handler.ListToVarcharTypeHandler;
import com.gdproj.handler.jsonAndListTypeHandler;
import com.gdproj.vo.fileVo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @TableName sys_notify
 */
@TableName(value ="sys_notify", autoResultMap = true)
public class Notify implements Serializable {
    /**
     * 
     */
    @TableId(value = "notify_id", type = IdType.AUTO)
    private Integer notifyId;

    /**
     * 
     */
    @TableField(value = "category_id")
    private Integer categoryId;

    /**
     * 
     */
    @TableField(value = "order_id")
    private Integer orderId;

    /**
     * 
     */
    @TableField(value = "notify_content")
    private String notifyContent;

    /**
     * 
     */
    @TableField(value = "notify_title")
    private String notifyTitle;

    /**
     * 
     */
    @TableField(value = "effective_date")
    private Date effectiveDate;

    /**
     * 
     */
    @TableField(value = "notify_istopping")
    private String notifyIstopping;

    /**
     * 
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 
     */
    @TableField(value = "notify_range")
    private String notifyRange;

    /**
     * 附件
     */
    @TableField(value = "notify_attachments",typeHandler = jsonAndListTypeHandler.class)
    private List<fileVo> notifyAttachments;

    /**
     * 公告状态（待审核0，生效1，弃用2）
     */
    @TableField(value = "notify_status")
    private Integer notifyStatus;

    /**
     * 
     */
    @TableField(value = "is_deleted")
    @TableLogic
    private String isDeleted;

    /**
     * 审核人
     */
    @TableField(value = "examiner_id")
    private Integer examinerId;

    /**
     * 
     */
    @TableField(value = "created_time")
    private Date createdTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getNotifyId() {
        return notifyId;
    }

    /**
     * 
     */
    public void setNotifyId(Integer notifyId) {
        this.notifyId = notifyId;
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
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * 
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * 
     */
    public String getNotifyContent() {
        return notifyContent;
    }

    /**
     * 
     */
    public void setNotifyContent(String notifyContent) {
        this.notifyContent = notifyContent;
    }

    /**
     * 
     */
    public String getNotifyTitle() {
        return notifyTitle;
    }

    /**
     * 
     */
    public void setNotifyTitle(String notifyTitle) {
        this.notifyTitle = notifyTitle;
    }

    /**
     * 
     */
    public Date getEffectiveDate() {
        return effectiveDate;
    }

    /**
     * 
     */
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    /**
     * 
     */
    public String getNotifyIstopping() {
        return notifyIstopping;
    }

    /**
     * 
     */
    public void setNotifyIstopping(String notifyIstopping) {
        this.notifyIstopping = notifyIstopping;
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
    public String getNotifyRange() {
        return notifyRange;
    }

    /**
     * 
     */
    public void setNotifyRange(String notifyRange) {
        this.notifyRange = notifyRange;
    }

    /**
     * 附件
     */
    public List<fileVo> getNotifyAttachments() {
        return notifyAttachments;
    }

    /**
     * 附件
     */
    public void setNotifyAttachments(List<fileVo> notifyAttachments) {
        this.notifyAttachments = notifyAttachments;
    }

    /**
     * 公告状态（待审核0，生效1，弃用2）
     */
    public Integer getNotifyStatus() {
        return notifyStatus;
    }

    /**
     * 公告状态（待审核0，生效1，弃用2）
     */
    public void setNotifyStatus(Integer notifyStatus) {
        this.notifyStatus = notifyStatus;
    }

    /**
     * 
     */
    public String getIsDeleted() {
        return isDeleted;
    }

    /**
     * 
     */
    public void setIsDeleted(String isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * 审核人
     */
    public Integer getExaminerId() {
        return examinerId;
    }

    /**
     * 审核人
     */
    public void setExaminerId(Integer examinerId) {
        this.examinerId = examinerId;
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
        Notify other = (Notify) that;
        return (this.getNotifyId() == null ? other.getNotifyId() == null : this.getNotifyId().equals(other.getNotifyId()))
            && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
            && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getNotifyContent() == null ? other.getNotifyContent() == null : this.getNotifyContent().equals(other.getNotifyContent()))
            && (this.getNotifyTitle() == null ? other.getNotifyTitle() == null : this.getNotifyTitle().equals(other.getNotifyTitle()))
            && (this.getEffectiveDate() == null ? other.getEffectiveDate() == null : this.getEffectiveDate().equals(other.getEffectiveDate()))
            && (this.getNotifyIstopping() == null ? other.getNotifyIstopping() == null : this.getNotifyIstopping().equals(other.getNotifyIstopping()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getNotifyRange() == null ? other.getNotifyRange() == null : this.getNotifyRange().equals(other.getNotifyRange()))
            && (this.getNotifyAttachments() == null ? other.getNotifyAttachments() == null : this.getNotifyAttachments().equals(other.getNotifyAttachments()))
            && (this.getNotifyStatus() == null ? other.getNotifyStatus() == null : this.getNotifyStatus().equals(other.getNotifyStatus()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getExaminerId() == null ? other.getExaminerId() == null : this.getExaminerId().equals(other.getExaminerId()))
            && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getNotifyId() == null) ? 0 : getNotifyId().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getNotifyContent() == null) ? 0 : getNotifyContent().hashCode());
        result = prime * result + ((getNotifyTitle() == null) ? 0 : getNotifyTitle().hashCode());
        result = prime * result + ((getEffectiveDate() == null) ? 0 : getEffectiveDate().hashCode());
        result = prime * result + ((getNotifyIstopping() == null) ? 0 : getNotifyIstopping().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getNotifyRange() == null) ? 0 : getNotifyRange().hashCode());
        result = prime * result + ((getNotifyAttachments() == null) ? 0 : getNotifyAttachments().hashCode());
        result = prime * result + ((getNotifyStatus() == null) ? 0 : getNotifyStatus().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getExaminerId() == null) ? 0 : getExaminerId().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", notifyId=").append(notifyId);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", orderId=").append(orderId);
        sb.append(", notifyContent=").append(notifyContent);
        sb.append(", notifyTitle=").append(notifyTitle);
        sb.append(", effectiveDate=").append(effectiveDate);
        sb.append(", notifyIstopping=").append(notifyIstopping);
        sb.append(", userId=").append(userId);
        sb.append(", notifyRange=").append(notifyRange);
        sb.append(", notifyAttachments=").append(notifyAttachments);
        sb.append(", notifyStatus=").append(notifyStatus);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", examinerId=").append(examinerId);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}