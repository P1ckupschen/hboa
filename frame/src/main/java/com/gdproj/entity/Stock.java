package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.gdproj.handler.jsonAndListTypeHandler;
import com.gdproj.vo.fileVo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @TableName sys_stock
 */
@TableName(value ="sys_stock",autoResultMap = true)
public class Stock implements Serializable {
    /**
     * 
     */
    @TableId(value = "material_id", type = IdType.AUTO)
    private Integer materialId;

    /**
     * 
     */
    @TableField(value = "count")
    private Integer count;

    /**
     * 
     */
    @TableField(value = "record_in",typeHandler = jsonAndListTypeHandler.class)
    private List<String> recordIn;

    /**
     * 
     */
    @TableField(value = "record_out",typeHandler = jsonAndListTypeHandler.class)
    private List<String> recordOut;

    /**
     * 
     */
    @TableField(value = "material_description")
    private String materialDescription;

    /**
     * 
     */
    @TableField(value = "material_pic",typeHandler = jsonAndListTypeHandler.class)
    private List<fileVo> materialPic;

    /**
     * 
     */
    @TableField(value = "is_deleted")
    @TableLogic
    private Integer isDeleted;

    /**
     * 
     */
    @TableField(value = "product_id")
    private Integer productId;

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
    public Integer getMaterialId() {
        return materialId;
    }

    /**
     * 
     */
    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
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
    public List<String> getRecordIn() {
        return recordIn;
    }

    /**
     * 
     */
    public void setRecordIn(List<String> recordIn) {
        this.recordIn = recordIn;
    }

    /**
     * 
     */
    public List<String> getRecordOut() {
        return recordOut;
    }

    /**
     * 
     */
    public void setRecordOut(List<String> recordOut) {
        this.recordOut = recordOut;
    }

    /**
     * 
     */
    public String getMaterialDescription() {
        return materialDescription;
    }

    /**
     * 
     */
    public void setMaterialDescription(String materialDescription) {
        this.materialDescription = materialDescription;
    }

    /**
     * 
     */
    public List<fileVo> getMaterialPic() {
        return materialPic;
    }

    /**
     * 
     */
    public void setMaterialPic(List<fileVo> materialPic) {
        this.materialPic = materialPic;
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
        Stock other = (Stock) that;
        return (this.getMaterialId() == null ? other.getMaterialId() == null : this.getMaterialId().equals(other.getMaterialId()))
            && (this.getCount() == null ? other.getCount() == null : this.getCount().equals(other.getCount()))
            && (this.getRecordIn() == null ? other.getRecordIn() == null : this.getRecordIn().equals(other.getRecordIn()))
            && (this.getRecordOut() == null ? other.getRecordOut() == null : this.getRecordOut().equals(other.getRecordOut()))
            && (this.getMaterialDescription() == null ? other.getMaterialDescription() == null : this.getMaterialDescription().equals(other.getMaterialDescription()))
            && (this.getMaterialPic() == null ? other.getMaterialPic() == null : this.getMaterialPic().equals(other.getMaterialPic()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getProductId() == null ? other.getProductId() == null : this.getProductId().equals(other.getProductId()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMaterialId() == null) ? 0 : getMaterialId().hashCode());
        result = prime * result + ((getCount() == null) ? 0 : getCount().hashCode());
        result = prime * result + ((getRecordIn() == null) ? 0 : getRecordIn().hashCode());
        result = prime * result + ((getRecordOut() == null) ? 0 : getRecordOut().hashCode());
        result = prime * result + ((getMaterialDescription() == null) ? 0 : getMaterialDescription().hashCode());
        result = prime * result + ((getMaterialPic() == null) ? 0 : getMaterialPic().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getProductId() == null) ? 0 : getProductId().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", materialId=").append(materialId);
        sb.append(", count=").append(count);
        sb.append(", recordIn=").append(recordIn);
        sb.append(", recordOut=").append(recordOut);
        sb.append(", materialDescription=").append(materialDescription);
        sb.append(", materialPic=").append(materialPic);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", productId=").append(productId);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}