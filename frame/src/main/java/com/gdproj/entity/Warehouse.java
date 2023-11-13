package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.gdproj.vo.FileVo;
import com.gdproj.vo.WarehouseContentVo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @TableName sys_warehouse
 */
@TableName(value ="sys_warehouse",autoResultMap = true)
public class Warehouse implements Serializable {
    /**
     *
     */
    @TableId(value = "warehouse_id", type = IdType.AUTO)
    private Integer warehouseId;

    /**
     *
     */
    @TableField(value = "warehouse_title")
    private String warehouseTitle;

    /**
     *
     */
    @TableField(value = "warehouse_content",typeHandler = JacksonTypeHandler.class)
    private List<WarehouseContentVo> warehouseContent;

    /**
     *
     */
    @TableField(value = "created_time",fill = FieldFill.INSERT)
    private Date createdTime;

    /**
     *
     */
    @TableField(value = "update_time",fill=FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     *
     */
    @TableField(value = "category_id")
    private Integer categoryId;

    /**
     *
     */
    @TableField(value = "warehouse_feedback")
    private String warehouseFeedback;

    /**
     *
     */
    @TableField(value = "warehouse_description")
    private String warehouseDescription;

    /**
     *
     */
    @TableField(value = "warehouse_status")
    private Integer warehouseStatus;

    /**
     *
     */
    @TableField(value = "warehouse_attachments",typeHandler = JacksonTypeHandler.class)
    private List<FileVo> warehouseAttachments;

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
     *
     */
    @TableField(value = "contract_id")
    private Integer contractId;

    /**
     *
     */
    @TableField(value = "project_id")
    private Integer projectId;

    @TableField(value = "record_time")
    private Date recordTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

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

    /**
     *
     */
    public String getWarehouseTitle() {
        return warehouseTitle;
    }

    /**
     *
     */
    public void setWarehouseTitle(String warehouseTitle) {
        this.warehouseTitle = warehouseTitle;
    }

    /**
     *
     */
    public List<WarehouseContentVo> getWarehouseContent() {
        return warehouseContent;
    }

    /**
     *
     */
    public void setWarehouseContent(List<WarehouseContentVo> warehouseContent) {
        this.warehouseContent = warehouseContent;
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
    public String getWarehouseFeedback() {
        return warehouseFeedback;
    }

    /**
     *
     */
    public void setWarehouseFeedback(String warehouseFeedback) {
        this.warehouseFeedback = warehouseFeedback;
    }

    /**
     *
     */
    public String getWarehouseDescription() {
        return warehouseDescription;
    }

    /**
     *
     */
    public void setWarehouseDescription(String warehouseDescription) {
        this.warehouseDescription = warehouseDescription;
    }

    /**
     *
     */
    public Integer getWarehouseStatus() {
        return warehouseStatus;
    }

    /**
     *
     */
    public void setWarehouseStatus(Integer warehouseStatus) {
        this.warehouseStatus = warehouseStatus;
    }

    /**
     *
     */
    public List<FileVo> getWarehouseAttachments() {
        return warehouseAttachments;
    }

    /**
     *
     */
    public void setWarehouseAttachments(List<FileVo> warehouseAttachments) {
        this.warehouseAttachments = warehouseAttachments;
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
    public Integer getProjectId() {
        return projectId;
    }

    /**
     *
     */
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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
        Warehouse other = (Warehouse) that;
        return (this.getWarehouseId() == null ? other.getWarehouseId() == null : this.getWarehouseId().equals(other.getWarehouseId()))
                && (this.getWarehouseTitle() == null ? other.getWarehouseTitle() == null : this.getWarehouseTitle().equals(other.getWarehouseTitle()))
                && (this.getWarehouseContent() == null ? other.getWarehouseContent() == null : this.getWarehouseContent().equals(other.getWarehouseContent()))
                && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
                && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
                && (this.getWarehouseFeedback() == null ? other.getWarehouseFeedback() == null : this.getWarehouseFeedback().equals(other.getWarehouseFeedback()))
                && (this.getWarehouseDescription() == null ? other.getWarehouseDescription() == null : this.getWarehouseDescription().equals(other.getWarehouseDescription()))
                && (this.getWarehouseStatus() == null ? other.getWarehouseStatus() == null : this.getWarehouseStatus().equals(other.getWarehouseStatus()))
                && (this.getWarehouseAttachments() == null ? other.getWarehouseAttachments() == null : this.getWarehouseAttachments().equals(other.getWarehouseAttachments()))
                && (this.getTypeId() == null ? other.getTypeId() == null : this.getTypeId().equals(other.getTypeId()))
                && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
                && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
                && (this.getContractId() == null ? other.getContractId() == null : this.getContractId().equals(other.getContractId()))
                && (this.getProjectId() == null ? other.getProjectId() == null : this.getProjectId().equals(other.getProjectId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getWarehouseId() == null) ? 0 : getWarehouseId().hashCode());
        result = prime * result + ((getWarehouseTitle() == null) ? 0 : getWarehouseTitle().hashCode());
        result = prime * result + ((getWarehouseContent() == null) ? 0 : getWarehouseContent().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getWarehouseFeedback() == null) ? 0 : getWarehouseFeedback().hashCode());
        result = prime * result + ((getWarehouseDescription() == null) ? 0 : getWarehouseDescription().hashCode());
        result = prime * result + ((getWarehouseStatus() == null) ? 0 : getWarehouseStatus().hashCode());
        result = prime * result + ((getWarehouseAttachments() == null) ? 0 : getWarehouseAttachments().hashCode());
        result = prime * result + ((getTypeId() == null) ? 0 : getTypeId().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getContractId() == null) ? 0 : getContractId().hashCode());
        result = prime * result + ((getProjectId() == null) ? 0 : getProjectId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", warehouseId=").append(warehouseId);
        sb.append(", warehouseTitle=").append(warehouseTitle);
        sb.append(", warehouseContent=").append(warehouseContent);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", warehouseFeedback=").append(warehouseFeedback);
        sb.append(", warehouseDescription=").append(warehouseDescription);
        sb.append(", warehouseStatus=").append(warehouseStatus);
        sb.append(", warehouseAttachments=").append(warehouseAttachments);
        sb.append(", typeId=").append(typeId);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", userId=").append(userId);
        sb.append(", contractId=").append(contractId);
        sb.append(", projectId=").append(projectId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }
}