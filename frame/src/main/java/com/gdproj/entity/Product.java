package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.gdproj.handler.jsonAndListTypeHandler;
import com.gdproj.vo.fileVo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 
 * @TableName sys_product
 */
@TableName(value ="sys_product",autoResultMap = true)
public class Product implements Serializable {
    /**
     * 
     */
    @TableId(value = "product_id", type = IdType.AUTO)
    private Integer productId;

    /**
     * 
     */
    @TableField(value = "product_name")
    private String productName;

    /**
     * 
     */
    @TableField(value = "product_order_id")
    private Integer productOrderId;

    /**
     * 
     */
    @TableField(value = "product_factory_code")
    private String productFactoryCode;

    /**
     * 1是入库2是出库
     */
    @TableField(value = "category_id")
    private Integer categoryId;

    /**
     * 
     */
    @TableField(value = "product_brand")
    private String productBrand;

    /**
     * 
     */
    @TableField(value = "product_unit")
    private String productUnit;

    /**
     * 
     */
    @TableField(value = "product_modelname")
    private String productModelname;

    /**
     * 
     */
    @TableField(value = "product_cost")
    private BigDecimal productCost;

    /**
     * 
     */
    @TableField(value = "product_pic",typeHandler = jsonAndListTypeHandler.class)
    private List<fileVo> productPic;

    /**
     * 
     */
    @TableField(value = "product_total")
    private Integer productTotal;

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
    @TableField(value = "product_description")
    private String productDescription;

    /**
     * 
     */
    @TableField(value = "is_deleted")
    @TableLogic
    private Integer isDeleted;

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 
     */
    @TableField(value = "product_price")
    private BigDecimal productPrice;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

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
    public String getProductName() {
        return productName;
    }

    /**
     * 
     */
    public void setProductName(String productName) {
        this.productName = productName;
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
    public String getProductFactoryCode() {
        return productFactoryCode;
    }

    /**
     * 
     */
    public void setProductFactoryCode(String productFactoryCode) {
        this.productFactoryCode = productFactoryCode;
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
    public String getProductModelname() {
        return productModelname;
    }

    /**
     * 
     */
    public void setProductModelname(String productModelname) {
        this.productModelname = productModelname;
    }

    /**
     * 
     */
    public BigDecimal getProductCost() {
        return productCost;
    }

    /**
     * 
     */
    public void setProductCost(BigDecimal productCost) {
        this.productCost = productCost;
    }

    /**
     * 
     */
    public List<fileVo> getProductPic() {
        return productPic;
    }

    /**
     * 
     */
    public void setProductPic(List<fileVo> productPic) {
        this.productPic = productPic;
    }

    /**
     * 
     */
    public Integer getProductTotal() {
        return productTotal;
    }

    /**
     * 
     */
    public void setProductTotal(Integer productTotal) {
        this.productTotal = productTotal;
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
    public String getProductDescription() {
        return productDescription;
    }

    /**
     * 
     */
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
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
    public BigDecimal getProductPrice() {
        return productPrice;
    }

    /**
     * 
     */
    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
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
        Product other = (Product) that;
        return (this.getProductId() == null ? other.getProductId() == null : this.getProductId().equals(other.getProductId()))
            && (this.getProductName() == null ? other.getProductName() == null : this.getProductName().equals(other.getProductName()))
            && (this.getProductOrderId() == null ? other.getProductOrderId() == null : this.getProductOrderId().equals(other.getProductOrderId()))
            && (this.getProductFactoryCode() == null ? other.getProductFactoryCode() == null : this.getProductFactoryCode().equals(other.getProductFactoryCode()))
            && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
            && (this.getProductBrand() == null ? other.getProductBrand() == null : this.getProductBrand().equals(other.getProductBrand()))
            && (this.getProductUnit() == null ? other.getProductUnit() == null : this.getProductUnit().equals(other.getProductUnit()))
            && (this.getProductModelname() == null ? other.getProductModelname() == null : this.getProductModelname().equals(other.getProductModelname()))
            && (this.getProductCost() == null ? other.getProductCost() == null : this.getProductCost().equals(other.getProductCost()))
            && (this.getProductPic() == null ? other.getProductPic() == null : this.getProductPic().equals(other.getProductPic()))
            && (this.getProductTotal() == null ? other.getProductTotal() == null : this.getProductTotal().equals(other.getProductTotal()))
            && (this.getRecordIn() == null ? other.getRecordIn() == null : this.getRecordIn().equals(other.getRecordIn()))
            && (this.getRecordOut() == null ? other.getRecordOut() == null : this.getRecordOut().equals(other.getRecordOut()))
            && (this.getProductDescription() == null ? other.getProductDescription() == null : this.getProductDescription().equals(other.getProductDescription()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getProductPrice() == null ? other.getProductPrice() == null : this.getProductPrice().equals(other.getProductPrice()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getProductId() == null) ? 0 : getProductId().hashCode());
        result = prime * result + ((getProductName() == null) ? 0 : getProductName().hashCode());
        result = prime * result + ((getProductOrderId() == null) ? 0 : getProductOrderId().hashCode());
        result = prime * result + ((getProductFactoryCode() == null) ? 0 : getProductFactoryCode().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getProductBrand() == null) ? 0 : getProductBrand().hashCode());
        result = prime * result + ((getProductUnit() == null) ? 0 : getProductUnit().hashCode());
        result = prime * result + ((getProductModelname() == null) ? 0 : getProductModelname().hashCode());
        result = prime * result + ((getProductCost() == null) ? 0 : getProductCost().hashCode());
        result = prime * result + ((getProductPic() == null) ? 0 : getProductPic().hashCode());
        result = prime * result + ((getProductTotal() == null) ? 0 : getProductTotal().hashCode());
        result = prime * result + ((getRecordIn() == null) ? 0 : getRecordIn().hashCode());
        result = prime * result + ((getRecordOut() == null) ? 0 : getRecordOut().hashCode());
        result = prime * result + ((getProductDescription() == null) ? 0 : getProductDescription().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getProductPrice() == null) ? 0 : getProductPrice().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", productId=").append(productId);
        sb.append(", productName=").append(productName);
        sb.append(", productOrderId=").append(productOrderId);
        sb.append(", productFactoryCode=").append(productFactoryCode);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", productBrand=").append(productBrand);
        sb.append(", productUnit=").append(productUnit);
        sb.append(", productModelname=").append(productModelname);
        sb.append(", productCost=").append(productCost);
        sb.append(", productPic=").append(productPic);
        sb.append(", productTotal=").append(productTotal);
        sb.append(", recordIn=").append(recordIn);
        sb.append(", recordOut=").append(recordOut);
        sb.append(", productDescription=").append(productDescription);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", productPrice=").append(productPrice);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}