package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
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
     * 出入库时间
     */
    @TableField(value = "record_time")
    private Date recordTime;

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
    private List<fileVo> recordAttachments;

    /**
     *
     */
    @TableField(value = "examiner_id")
    private Integer examinerId;

    /**
     *
     */
    @TableField(value = "record_status")
    private Integer recordStatus;

    /**
     *
     */
    @TableField(value = "product_id")
    private Integer productId;

    /**
     *
     */
    @TableField(value = "product_unit")
    private String productUnit;

    /**
     *
     */
    @TableField(value = "record_title")
    private String recordTitle;

    /**
     *
     */
    @TableField(value = "warehouse_id")
    private Integer warehouseId;

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
     * 出入库时间
     */
    public Date getRecordTime() {
        return recordTime;
    }

    /**
     * 出入库时间
     */
    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
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
    public List<fileVo> getRecordAttachments() {
        return recordAttachments;
    }

    /**
     *
     */
    public void setRecordAttachments(List<fileVo> recordAttachments) {
        this.recordAttachments = recordAttachments;
    }

    /**
     *
     */
    public Integer getExaminerId() {
        return examinerId;
    }

    /**
     *
     */
    public void setExaminerId(Integer examinerId) {
        this.examinerId = examinerId;
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
    public String getRecordTitle() {
        return recordTitle;
    }

    /**
     *
     */
    public void setRecordTitle(String recordTitle) {
        this.recordTitle = recordTitle;
    }

    /**
     *
     */
    public Integer getWarehouseId() {
        return warehouseId;
    }

    /**
     *
     */
    public void setWarehouseId(Integer warehouseId) {
        this.warehouseId = warehouseId;
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
                && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
                && (this.getCount() == null ? other.getCount() == null : this.getCount().equals(other.getCount()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
                && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
                && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
                && (this.getRecordTime() == null ? other.getRecordTime() == null : this.getRecordTime().equals(other.getRecordTime()))
                && (this.getProjectId() == null ? other.getProjectId() == null : this.getProjectId().equals(other.getProjectId()))
                && (this.getContractId() == null ? other.getContractId() == null : this.getContractId().equals(other.getContractId()))
                && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
                && (this.getRecordDescription() == null ? other.getRecordDescription() == null : this.getRecordDescription().equals(other.getRecordDescription()))
                && (this.getRecordAttachments() == null ? other.getRecordAttachments() == null : this.getRecordAttachments().equals(other.getRecordAttachments()))
                && (this.getExaminerId() == null ? other.getExaminerId() == null : this.getExaminerId().equals(other.getExaminerId()))
                && (this.getRecordStatus() == null ? other.getRecordStatus() == null : this.getRecordStatus().equals(other.getRecordStatus()))
                && (this.getProductId() == null ? other.getProductId() == null : this.getProductId().equals(other.getProductId()))
                && (this.getProductUnit() == null ? other.getProductUnit() == null : this.getProductUnit().equals(other.getProductUnit()))
                && (this.getRecordTitle() == null ? other.getRecordTitle() == null : this.getRecordTitle().equals(other.getRecordTitle()))
                && (this.getWarehouseId() == null ? other.getWarehouseId() == null : this.getWarehouseId().equals(other.getWarehouseId()));
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
        result = prime * result + ((getProjectId() == null) ? 0 : getProjectId().hashCode());
        result = prime * result + ((getContractId() == null) ? 0 : getContractId().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getRecordDescription() == null) ? 0 : getRecordDescription().hashCode());
        result = prime * result + ((getRecordAttachments() == null) ? 0 : getRecordAttachments().hashCode());
        result = prime * result + ((getExaminerId() == null) ? 0 : getExaminerId().hashCode());
        result = prime * result + ((getRecordStatus() == null) ? 0 : getRecordStatus().hashCode());
        result = prime * result + ((getProductId() == null) ? 0 : getProductId().hashCode());
        result = prime * result + ((getProductUnit() == null) ? 0 : getProductUnit().hashCode());
        result = prime * result + ((getRecordTitle() == null) ? 0 : getRecordTitle().hashCode());
        result = prime * result + ((getWarehouseId() == null) ? 0 : getWarehouseId().hashCode());
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
        sb.append(", projectId=").append(projectId);
        sb.append(", contractId=").append(contractId);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", recordDescription=").append(recordDescription);
        sb.append(", recordAttachments=").append(recordAttachments);
        sb.append(", examinerId=").append(examinerId);
        sb.append(", recordStatus=").append(recordStatus);
        sb.append(", productId=").append(productId);
        sb.append(", productUnit=").append(productUnit);
        sb.append(", recordTitle=").append(recordTitle);
        sb.append(", warehouseId=").append(warehouseId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}