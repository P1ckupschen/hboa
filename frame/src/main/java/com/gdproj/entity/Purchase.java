package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.gdproj.vo.FileVo;
import com.gdproj.vo.PurchaseContentVo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 
 * @TableName sys_purchase
 */
@TableName(value ="sys_purchase",autoResultMap = true)
public class Purchase implements Serializable {
    /**
     * 
     */
    @TableId(value = "purchase_id", type = IdType.AUTO)
    private Integer purchaseId;

    /**
     * 
     */
    @TableField(value = "purchase_title")
    private String purchaseTitle;

    /**
     * 
     */
    @TableField(value = "purchase_content" ,typeHandler = JacksonTypeHandler.class)
    private List<PurchaseContentVo> purchaseContent;

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
    @TableField(value = "category_id")
    private Integer categoryId;

    /**
     * 
     */
    @TableField(value = "purchase_feedback")
    private String purchaseFeedback;

    /**
     * 
     */
    @TableField(value = "purchase_description")
    private String purchaseDescription;

    /**
     * 
     */
    @TableField(value = "purchase_status")
    private Integer purchaseStatus;

    /**
     * 
     */
    @TableField(value = "purchase_attachments",typeHandler = JacksonTypeHandler.class)
    private List<FileVo> purchaseAttachments;

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
     * 1 普通 2 紧急 3 非常紧急
     */
    @TableField(value = "urgency_level")
    private Integer urgencyLevel;

    /**
     * 
     */
    @TableField(value = "delivery_time")
    private Date deliveryTime;

    /**
     * 绑定合同编号
     */
    @TableField(value = "contract_id")
    private Integer contractId;

    @TableField(value = "amount")
    private BigDecimal Amount;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getPurchaseId() {
        return purchaseId;
    }

    /**
     * 
     */
    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }

    /**
     * 
     */
    public String getPurchaseTitle() {
        return purchaseTitle;
    }

    /**
     * 
     */
    public void setPurchaseTitle(String purchaseTitle) {
        this.purchaseTitle = purchaseTitle;
    }

    /**
     * 
     */
    public List<PurchaseContentVo> getPurchaseContent() {
        return purchaseContent;
    }

    /**
     * 
     */
    public void setPurchaseContent(List<PurchaseContentVo>  purchaseContent) {
        this.purchaseContent = purchaseContent;
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
    public String getPurchaseFeedback() {
        return purchaseFeedback;
    }

    /**
     * 
     */
    public void setPurchaseFeedback(String purchaseFeedback) {
        this.purchaseFeedback = purchaseFeedback;
    }

    /**
     * 
     */
    public String getPurchaseDescription() {
        return purchaseDescription;
    }

    /**
     * 
     */
    public void setPurchaseDescription(String purchaseDescription) {
        this.purchaseDescription = purchaseDescription;
    }

    /**
     * 
     */
    public Integer getPurchaseStatus() {
        return purchaseStatus;
    }

    /**
     * 
     */
    public void setPurchaseStatus(Integer purchaseStatus) {
        this.purchaseStatus = purchaseStatus;
    }

    /**
     * 
     */
    public List<FileVo> getPurchaseAttachments() {
        return purchaseAttachments;
    }

    /**
     * 
     */
    public void setPurchaseAttachments( List<FileVo> purchaseAttachments) {
        this.purchaseAttachments = purchaseAttachments;
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
     * 1 普通 2 紧急 3 非常紧急
     */
    public Integer getUrgencyLevel() {
        return urgencyLevel;
    }

    /**
     * 1 普通 2 紧急 3 非常紧急
     */
    public void setUrgencyLevel(Integer urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }

    /**
     * 
     */
    public Date getDeliveryTime() {
        return deliveryTime;
    }

    /**
     * 
     */
    public void setDeliveryTime(Date deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    /**
     * 绑定合同编号
     */
    public Integer getContractId() {
        return contractId;
    }

    /**
     * 绑定合同编号
     */
    public void setContractId(Integer contractId) {
        this.contractId = contractId;
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
        Purchase other = (Purchase) that;
        return (this.getPurchaseId() == null ? other.getPurchaseId() == null : this.getPurchaseId().equals(other.getPurchaseId()))
            && (this.getPurchaseTitle() == null ? other.getPurchaseTitle() == null : this.getPurchaseTitle().equals(other.getPurchaseTitle()))
            && (this.getPurchaseContent() == null ? other.getPurchaseContent() == null : this.getPurchaseContent().equals(other.getPurchaseContent()))
            && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
            && (this.getPurchaseFeedback() == null ? other.getPurchaseFeedback() == null : this.getPurchaseFeedback().equals(other.getPurchaseFeedback()))
            && (this.getPurchaseDescription() == null ? other.getPurchaseDescription() == null : this.getPurchaseDescription().equals(other.getPurchaseDescription()))
            && (this.getPurchaseStatus() == null ? other.getPurchaseStatus() == null : this.getPurchaseStatus().equals(other.getPurchaseStatus()))
            && (this.getPurchaseAttachments() == null ? other.getPurchaseAttachments() == null : this.getPurchaseAttachments().equals(other.getPurchaseAttachments()))
            && (this.getTypeId() == null ? other.getTypeId() == null : this.getTypeId().equals(other.getTypeId()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getUrgencyLevel() == null ? other.getUrgencyLevel() == null : this.getUrgencyLevel().equals(other.getUrgencyLevel()))
            && (this.getDeliveryTime() == null ? other.getDeliveryTime() == null : this.getDeliveryTime().equals(other.getDeliveryTime()))
            && (this.getContractId() == null ? other.getContractId() == null : this.getContractId().equals(other.getContractId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPurchaseId() == null) ? 0 : getPurchaseId().hashCode());
        result = prime * result + ((getPurchaseTitle() == null) ? 0 : getPurchaseTitle().hashCode());
        result = prime * result + ((getPurchaseContent() == null) ? 0 : getPurchaseContent().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getPurchaseFeedback() == null) ? 0 : getPurchaseFeedback().hashCode());
        result = prime * result + ((getPurchaseDescription() == null) ? 0 : getPurchaseDescription().hashCode());
        result = prime * result + ((getPurchaseStatus() == null) ? 0 : getPurchaseStatus().hashCode());
        result = prime * result + ((getPurchaseAttachments() == null) ? 0 : getPurchaseAttachments().hashCode());
        result = prime * result + ((getTypeId() == null) ? 0 : getTypeId().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getUrgencyLevel() == null) ? 0 : getUrgencyLevel().hashCode());
        result = prime * result + ((getDeliveryTime() == null) ? 0 : getDeliveryTime().hashCode());
        result = prime * result + ((getContractId() == null) ? 0 : getContractId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", purchaseId=").append(purchaseId);
        sb.append(", purchaseTitle=").append(purchaseTitle);
        sb.append(", purchaseContent=").append(purchaseContent);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", purchaseFeedback=").append(purchaseFeedback);
        sb.append(", purchaseDescription=").append(purchaseDescription);
        sb.append(", purchaseStatus=").append(purchaseStatus);
        sb.append(", purchaseAttachments=").append(purchaseAttachments);
        sb.append(", typeId=").append(typeId);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", userId=").append(userId);
        sb.append(", urgencyLevel=").append(urgencyLevel);
        sb.append(", deliveryTime=").append(deliveryTime);
        sb.append(", contractId=").append(contractId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public BigDecimal getAmount() {
        return Amount;
    }

    public void setAmount(BigDecimal amount) {
        Amount = amount;
    }
}