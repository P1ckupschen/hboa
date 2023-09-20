package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gdproj.handler.jsonAndListTypeHandler;
import com.gdproj.vo.fileVo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @TableName sys_record
 */
@TableName(value ="sys_record",autoResultMap = true)
public class Record implements Serializable {
    /**
     * 
     */
    @TableId(value = "record_id", type = IdType.AUTO)
    private Integer recordId;

    /**
     * 
     */
    @TableField(value = "product_id")
    private Integer productId;

    /**
     * 
     */
    @TableField(value = "product_order_id")
    private Integer productOrderId;

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
    @TableField(value = "product_brand")
    private String productBrand;

    /**
     * 
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     * 
     */
    @TableField(value = "created_time")
    private Date createdTime;

    /**
     * 生产批次
     */
    @TableField(value = "product_batchs")
    private String productBatchs;

    /**
     * 
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 出入库时间
     */
    @TableField(value = "in_time")
    private Date inTime;

    /**
     * 
     */
    @TableField(value = "project_id")
    private Integer projectId;

    /**
     * 
     */
    @TableField(value = "contract_id")
    private Integer contractId;

    /**
     * 
     */
    @TableField(value = "out_time")
    private Date outTime;

    /**
     * 
     */
    @TableField(value = "is_deleted")
    private Integer isDeleted;

    /**
     * 
     */
    @TableField(value = "product_unit")
    private String productUnit;

    /**
     * 
     */
    @TableField(value = "record_description")
    private String recordDescription;

    /**
     * 
     */
    @TableField(value = "record_attachments",typeHandler = jsonAndListTypeHandler.class)
    private List<fileVo> recordAttachments;

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
    public Integer getProductId() {
        return productId;
    }

    /**
     * 
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * 
     */
    public Integer getProductOrderId() {
        return productOrderId;
    }

    /**
     * 
     */
    public void setProductOrderId(Integer productOrderId) {
        this.productOrderId = productOrderId;
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
    public String getProductBrand() {
        return productBrand;
    }

    /**
     * 
     */
    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
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
     * 生产批次
     */
    public String getProductBatchs() {
        return productBatchs;
    }

    /**
     * 生产批次
     */
    public void setProductBatchs(String productBatchs) {
        this.productBatchs = productBatchs;
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
     * 出入库时间
     */
    public Date getInTime() {
        return inTime;
    }

    /**
     * 出入库时间
     */
    public void setInTime(Date inTime) {
        this.inTime = inTime;
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
    public Integer getContractId() {
        return contractId;
    }

    /**
     * 
     */
    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    /**
     * 
     */
    public Date getOutTime() {
        return outTime;
    }

    /**
     * 
     */
    public void setOutTime(Date outTime) {
        this.outTime = outTime;
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
    public String getProductUnit() {
        return productUnit;
    }

    /**
     * 
     */
    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
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
    public List<fileVo> getRecordAttachments() {
        return recordAttachments;
    }

    /**
     * 
     */
    public void setRecordAttachments(List<fileVo> recordAttachments) {
        this.recordAttachments = recordAttachments;
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
        Record other = (Record) that;
        return (this.getRecordId() == null ? other.getRecordId() == null : this.getRecordId().equals(other.getRecordId()))
            && (this.getProductId() == null ? other.getProductId() == null : this.getProductId().equals(other.getProductId()))
            && (this.getProductOrderId() == null ? other.getProductOrderId() == null : this.getProductOrderId().equals(other.getProductOrderId()))
            && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
            && (this.getCount() == null ? other.getCount() == null : this.getCount().equals(other.getCount()))
            && (this.getProductBrand() == null ? other.getProductBrand() == null : this.getProductBrand().equals(other.getProductBrand()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
            && (this.getProductBatchs() == null ? other.getProductBatchs() == null : this.getProductBatchs().equals(other.getProductBatchs()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getInTime() == null ? other.getInTime() == null : this.getInTime().equals(other.getInTime()))
            && (this.getProjectId() == null ? other.getProjectId() == null : this.getProjectId().equals(other.getProjectId()))
            && (this.getContractId() == null ? other.getContractId() == null : this.getContractId().equals(other.getContractId()))
            && (this.getOutTime() == null ? other.getOutTime() == null : this.getOutTime().equals(other.getOutTime()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getProductUnit() == null ? other.getProductUnit() == null : this.getProductUnit().equals(other.getProductUnit()))
            && (this.getRecordDescription() == null ? other.getRecordDescription() == null : this.getRecordDescription().equals(other.getRecordDescription()))
            && (this.getRecordAttachments() == null ? other.getRecordAttachments() == null : this.getRecordAttachments().equals(other.getRecordAttachments()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRecordId() == null) ? 0 : getRecordId().hashCode());
        result = prime * result + ((getProductId() == null) ? 0 : getProductId().hashCode());
        result = prime * result + ((getProductOrderId() == null) ? 0 : getProductOrderId().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getCount() == null) ? 0 : getCount().hashCode());
        result = prime * result + ((getProductBrand() == null) ? 0 : getProductBrand().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getProductBatchs() == null) ? 0 : getProductBatchs().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getInTime() == null) ? 0 : getInTime().hashCode());
        result = prime * result + ((getProjectId() == null) ? 0 : getProjectId().hashCode());
        result = prime * result + ((getContractId() == null) ? 0 : getContractId().hashCode());
        result = prime * result + ((getOutTime() == null) ? 0 : getOutTime().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getProductUnit() == null) ? 0 : getProductUnit().hashCode());
        result = prime * result + ((getRecordDescription() == null) ? 0 : getRecordDescription().hashCode());
        result = prime * result + ((getRecordAttachments() == null) ? 0 : getRecordAttachments().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", recordId=").append(recordId);
        sb.append(", productId=").append(productId);
        sb.append(", productOrderId=").append(productOrderId);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", count=").append(count);
        sb.append(", productBrand=").append(productBrand);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", productBatchs=").append(productBatchs);
        sb.append(", userId=").append(userId);
        sb.append(", inTime=").append(inTime);
        sb.append(", projectId=").append(projectId);
        sb.append(", contractId=").append(contractId);
        sb.append(", outTime=").append(outTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", productUnit=").append(productUnit);
        sb.append(", recordDescription=").append(recordDescription);
        sb.append(", recordAttachments=").append(recordAttachments);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}