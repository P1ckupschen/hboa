package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.gdproj.vo.FileVo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 
 * @TableName sys_tool
 */
@TableName(value ="sys_tool",autoResultMap = true)
public class Tool implements Serializable {
    /**
     * 
     */
    @TableId(value = "tool_id", type = IdType.AUTO)
    private Integer toolId;

    /**
     * 
     */
    @TableField(value = "tool_name")
    private String toolName;

    /**
     * 
     */
    @TableField(value = "tool_order_id")
    private Integer toolOrderId;

    /**
     * 
     */
    @TableField(value = "tool_factory_code")
    private String toolFactoryCode;

    /**
     * 1是入库2是出库
     */
    @TableField(value = "category_id")
    private Integer categoryId;

    /**
     * 
     */
    @TableField(value = "tool_brand")
    private String toolBrand;

    /**
     * 
     */
    @TableField(value = "tool_unit")
    private String toolUnit;

    /**
     * 
     */
    @TableField(value = "tool_modelname")
    private String toolModelname;

    /**
     * 
     */
    @TableField(value = "tool_cost")
    private BigDecimal toolCost;

    /**
     * 
     */
    @TableField(value = "tool_pic",typeHandler = JacksonTypeHandler.class)
    private List<FileVo> toolPic;

    /**
     * 
     */
    @TableField(value = "tool_total")
    private Integer toolTotal;

    /**
     * 
     */
    @TableField(value = "record_in")
    private String recordIn;

    /**
     * 
     */
    @TableField(value = "record_out")
    private String recordOut;

    /**
     * 
     */
    @TableField(value = "tool_description")
    private String toolDescription;

    /**
     * 
     */
    @TableField(value = "is_deleted")
    @TableLogic
    private Integer isDeleted;

    /**
     * 
     */
    @TableField(value = "tool_price")
    private BigDecimal toolPrice;

    /**
     * 
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

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
     * 
     */
    public String getToolName() {
        return toolName;
    }

    /**
     * 
     */
    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    /**
     * 
     */
    public Integer getToolOrderId() {
        return toolOrderId;
    }

    /**
     * 
     */
    public void setToolOrderId(Integer toolOrderId) {
        this.toolOrderId = toolOrderId;
    }

    /**
     * 
     */
    public String getToolFactoryCode() {
        return toolFactoryCode;
    }

    /**
     * 
     */
    public void setToolFactoryCode(String toolFactoryCode) {
        this.toolFactoryCode = toolFactoryCode;
    }

    /**
     * 1是入库2是出库
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * 1是入库2是出库
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 
     */
    public String getToolBrand() {
        return toolBrand;
    }

    /**
     * 
     */
    public void setToolBrand(String toolBrand) {
        this.toolBrand = toolBrand;
    }

    /**
     * 
     */
    public String getToolUnit() {
        return toolUnit;
    }

    /**
     * 
     */
    public void setToolUnit(String toolUnit) {
        this.toolUnit = toolUnit;
    }

    /**
     * 
     */
    public String getToolModelname() {
        return toolModelname;
    }

    /**
     * 
     */
    public void setToolModelname(String toolModelname) {
        this.toolModelname = toolModelname;
    }

    /**
     * 
     */
    public BigDecimal getToolCost() {
        return toolCost;
    }

    /**
     * 
     */
    public void setToolCost(BigDecimal toolCost) {
        this.toolCost = toolCost;
    }

    /**
     * 
     */
    public List<FileVo> getToolPic() {
        return toolPic;
    }

    /**
     * 
     */
    public void setToolPic(List<FileVo> toolPic) {
        this.toolPic = toolPic;
    }

    /**
     * 
     */
    public Integer getToolTotal() {
        return toolTotal;
    }

    /**
     * 
     */
    public void setToolTotal(Integer toolTotal) {
        this.toolTotal = toolTotal;
    }

    /**
     * 
     */
    public String getRecordIn() {
        return recordIn;
    }

    /**
     * 
     */
    public void setRecordIn(String recordIn) {
        this.recordIn = recordIn;
    }

    /**
     * 
     */
    public String getRecordOut() {
        return recordOut;
    }

    /**
     * 
     */
    public void setRecordOut(String recordOut) {
        this.recordOut = recordOut;
    }

    /**
     * 
     */
    public String getToolDescription() {
        return toolDescription;
    }

    /**
     * 
     */
    public void setToolDescription(String toolDescription) {
        this.toolDescription = toolDescription;
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
    public BigDecimal getToolPrice() {
        return toolPrice;
    }

    /**
     * 
     */
    public void setToolPrice(BigDecimal toolPrice) {
        this.toolPrice = toolPrice;
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
        Tool other = (Tool) that;
        return (this.getToolId() == null ? other.getToolId() == null : this.getToolId().equals(other.getToolId()))
            && (this.getToolName() == null ? other.getToolName() == null : this.getToolName().equals(other.getToolName()))
            && (this.getToolOrderId() == null ? other.getToolOrderId() == null : this.getToolOrderId().equals(other.getToolOrderId()))
            && (this.getToolFactoryCode() == null ? other.getToolFactoryCode() == null : this.getToolFactoryCode().equals(other.getToolFactoryCode()))
            && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
            && (this.getToolBrand() == null ? other.getToolBrand() == null : this.getToolBrand().equals(other.getToolBrand()))
            && (this.getToolUnit() == null ? other.getToolUnit() == null : this.getToolUnit().equals(other.getToolUnit()))
            && (this.getToolModelname() == null ? other.getToolModelname() == null : this.getToolModelname().equals(other.getToolModelname()))
            && (this.getToolCost() == null ? other.getToolCost() == null : this.getToolCost().equals(other.getToolCost()))
            && (this.getToolPic() == null ? other.getToolPic() == null : this.getToolPic().equals(other.getToolPic()))
            && (this.getToolTotal() == null ? other.getToolTotal() == null : this.getToolTotal().equals(other.getToolTotal()))
            && (this.getRecordIn() == null ? other.getRecordIn() == null : this.getRecordIn().equals(other.getRecordIn()))
            && (this.getRecordOut() == null ? other.getRecordOut() == null : this.getRecordOut().equals(other.getRecordOut()))
            && (this.getToolDescription() == null ? other.getToolDescription() == null : this.getToolDescription().equals(other.getToolDescription()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getToolPrice() == null ? other.getToolPrice() == null : this.getToolPrice().equals(other.getToolPrice()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getToolId() == null) ? 0 : getToolId().hashCode());
        result = prime * result + ((getToolName() == null) ? 0 : getToolName().hashCode());
        result = prime * result + ((getToolOrderId() == null) ? 0 : getToolOrderId().hashCode());
        result = prime * result + ((getToolFactoryCode() == null) ? 0 : getToolFactoryCode().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getToolBrand() == null) ? 0 : getToolBrand().hashCode());
        result = prime * result + ((getToolUnit() == null) ? 0 : getToolUnit().hashCode());
        result = prime * result + ((getToolModelname() == null) ? 0 : getToolModelname().hashCode());
        result = prime * result + ((getToolCost() == null) ? 0 : getToolCost().hashCode());
        result = prime * result + ((getToolPic() == null) ? 0 : getToolPic().hashCode());
        result = prime * result + ((getToolTotal() == null) ? 0 : getToolTotal().hashCode());
        result = prime * result + ((getRecordIn() == null) ? 0 : getRecordIn().hashCode());
        result = prime * result + ((getRecordOut() == null) ? 0 : getRecordOut().hashCode());
        result = prime * result + ((getToolDescription() == null) ? 0 : getToolDescription().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getToolPrice() == null) ? 0 : getToolPrice().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", toolId=").append(toolId);
        sb.append(", toolName=").append(toolName);
        sb.append(", toolOrderId=").append(toolOrderId);
        sb.append(", toolFactoryCode=").append(toolFactoryCode);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", toolBrand=").append(toolBrand);
        sb.append(", toolUnit=").append(toolUnit);
        sb.append(", toolModelname=").append(toolModelname);
        sb.append(", toolCost=").append(toolCost);
        sb.append(", toolPic=").append(toolPic);
        sb.append(", toolTotal=").append(toolTotal);
        sb.append(", recordIn=").append(recordIn);
        sb.append(", recordOut=").append(recordOut);
        sb.append(", toolDescription=").append(toolDescription);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", toolPrice=").append(toolPrice);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}