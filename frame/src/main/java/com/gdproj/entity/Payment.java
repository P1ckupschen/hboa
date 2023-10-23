package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.gdproj.vo.fileVo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 
 * @TableName sys_payment
 */
@TableName(value ="sys_payment",autoResultMap = true)
public class Payment implements Serializable {
    /**
     * 
     */
    @TableId(value = "payment_id", type = IdType.AUTO)
    private Integer paymentId;

    /**
     * 
     */
    @TableField(value = "payment_title")
    private String paymentTitle;

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
    @TableField(value = "payment_amount")
    private BigDecimal paymentAmount;

    /**
     * 
     */
    @TableField(value = "project_id")
    private Integer projectId;

    /**
     * 
     */
    @TableField(value = "payer")
    private String payer;

    /**
     * 
     */
    @TableField(value = "payee")
    private String payee;

    /**
     * 开户银行
     */
    @TableField(value = "deposit_bank")
    private String depositBank;

    /**
     * 账户 加密
     */
    @TableField(value = "paymeng_account")
    private String paymengAccount;

    /**
     * 
     */
    @TableField(value = "payment_description")
    private String paymentDescription;

    /**
     * 现金 or 银行
     */
    @TableField(value = "payment_method")
    private String paymentMethod;

    /**
     * 
     */
    @TableField(value = "payment_attachments",typeHandler = JacksonTypeHandler.class)
    private List<fileVo> paymentAttachments;

    /**
     * 
     */
    @TableField(value = "is_deleted")
    @TableLogic
    private Integer isDeleted;

    /**
     * 0待审批，1已审批，2不通过
     */
    @TableField(value = "payment_status")
    private Integer paymentStatus;

    /**
     * 
     */
    @TableField(value = "type_id")
    private Integer typeId;

    /**
     * 
     */
    @TableField(value = "payment_time")
    private Date paymentTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getPaymentId() {
        return paymentId;
    }

    /**
     * 
     */
    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    /**
     * 
     */
    public String getPaymentTitle() {
        return paymentTitle;
    }

    /**
     * 
     */
    public void setPaymentTitle(String paymentTitle) {
        this.paymentTitle = paymentTitle;
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
    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }

    /**
     * 
     */
    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
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
    public String getPayer() {
        return payer;
    }

    /**
     * 
     */
    public void setPayer(String payer) {
        this.payer = payer;
    }

    /**
     * 
     */
    public String getPayee() {
        return payee;
    }

    /**
     * 
     */
    public void setPayee(String payee) {
        this.payee = payee;
    }

    /**
     * 开户银行
     */
    public String getDepositBank() {
        return depositBank;
    }

    /**
     * 开户银行
     */
    public void setDepositBank(String depositBank) {
        this.depositBank = depositBank;
    }

    /**
     * 账户 加密
     */
    public String getPaymengAccount() {
        return paymengAccount;
    }

    /**
     * 账户 加密
     */
    public void setPaymengAccount(String paymengAccount) {
        this.paymengAccount = paymengAccount;
    }

    /**
     * 
     */
    public String getPaymentDescription() {
        return paymentDescription;
    }

    /**
     * 
     */
    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription;
    }

    /**
     * 现金 or 银行
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * 现金 or 银行
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * 
     */
    public List<fileVo> getPaymentAttachments() {
        return paymentAttachments;
    }

    /**
     * 
     */
    public void setPaymentAttachments(List<fileVo> paymentAttachments) {
        this.paymentAttachments = paymentAttachments;
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
     * 0待审批，1已审批，2不通过
     */
    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    /**
     * 0待审批，1已审批，2不通过
     */
    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
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
    public Date getPaymentTime() {
        return paymentTime;
    }

    /**
     * 
     */
    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
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
        Payment other = (Payment) that;
        return (this.getPaymentId() == null ? other.getPaymentId() == null : this.getPaymentId().equals(other.getPaymentId()))
            && (this.getPaymentTitle() == null ? other.getPaymentTitle() == null : this.getPaymentTitle().equals(other.getPaymentTitle()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getPaymentAmount() == null ? other.getPaymentAmount() == null : this.getPaymentAmount().equals(other.getPaymentAmount()))
            && (this.getProjectId() == null ? other.getProjectId() == null : this.getProjectId().equals(other.getProjectId()))
            && (this.getPayer() == null ? other.getPayer() == null : this.getPayer().equals(other.getPayer()))
            && (this.getPayee() == null ? other.getPayee() == null : this.getPayee().equals(other.getPayee()))
            && (this.getDepositBank() == null ? other.getDepositBank() == null : this.getDepositBank().equals(other.getDepositBank()))
            && (this.getPaymengAccount() == null ? other.getPaymengAccount() == null : this.getPaymengAccount().equals(other.getPaymengAccount()))
            && (this.getPaymentDescription() == null ? other.getPaymentDescription() == null : this.getPaymentDescription().equals(other.getPaymentDescription()))
            && (this.getPaymentMethod() == null ? other.getPaymentMethod() == null : this.getPaymentMethod().equals(other.getPaymentMethod()))
            && (this.getPaymentAttachments() == null ? other.getPaymentAttachments() == null : this.getPaymentAttachments().equals(other.getPaymentAttachments()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getPaymentStatus() == null ? other.getPaymentStatus() == null : this.getPaymentStatus().equals(other.getPaymentStatus()))
            && (this.getTypeId() == null ? other.getTypeId() == null : this.getTypeId().equals(other.getTypeId()))
            && (this.getPaymentTime() == null ? other.getPaymentTime() == null : this.getPaymentTime().equals(other.getPaymentTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPaymentId() == null) ? 0 : getPaymentId().hashCode());
        result = prime * result + ((getPaymentTitle() == null) ? 0 : getPaymentTitle().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getPaymentAmount() == null) ? 0 : getPaymentAmount().hashCode());
        result = prime * result + ((getProjectId() == null) ? 0 : getProjectId().hashCode());
        result = prime * result + ((getPayer() == null) ? 0 : getPayer().hashCode());
        result = prime * result + ((getPayee() == null) ? 0 : getPayee().hashCode());
        result = prime * result + ((getDepositBank() == null) ? 0 : getDepositBank().hashCode());
        result = prime * result + ((getPaymengAccount() == null) ? 0 : getPaymengAccount().hashCode());
        result = prime * result + ((getPaymentDescription() == null) ? 0 : getPaymentDescription().hashCode());
        result = prime * result + ((getPaymentMethod() == null) ? 0 : getPaymentMethod().hashCode());
        result = prime * result + ((getPaymentAttachments() == null) ? 0 : getPaymentAttachments().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getPaymentStatus() == null) ? 0 : getPaymentStatus().hashCode());
        result = prime * result + ((getTypeId() == null) ? 0 : getTypeId().hashCode());
        result = prime * result + ((getPaymentTime() == null) ? 0 : getPaymentTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", paymentId=").append(paymentId);
        sb.append(", paymentTitle=").append(paymentTitle);
        sb.append(", userId=").append(userId);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", paymentAmout=").append(paymentAmount);
        sb.append(", projectId=").append(projectId);
        sb.append(", payer=").append(payer);
        sb.append(", payee=").append(payee);
        sb.append(", depositBank=").append(depositBank);
        sb.append(", paymengAccount=").append(paymengAccount);
        sb.append(", paymentDescription=").append(paymentDescription);
        sb.append(", paymentMethod=").append(paymentMethod);
        sb.append(", paymentAttachments=").append(paymentAttachments);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", paymentStatus=").append(paymentStatus);
        sb.append(", typeId=").append(typeId);
        sb.append(", paymentTime=").append(paymentTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}