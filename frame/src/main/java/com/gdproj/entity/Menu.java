package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @TableName sys_menu
 */
@TableName(value ="sys_menu")
public class Menu implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer menuId;

    @TableField(exist = false)
    private List<Menu> children = null;

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }
    /**
     * 
     */
    private String menuName;

    /**
     * 
     */
    private Integer parentId;

    /**
     * 
     */
    private String path;

    /**
     * 
     */
    private String component;

    /**
     * 
     */
    private String menuType;

    /**
     * 
     */
    private String visible;

    /**
     * 
     */
    private String status;

    /**
     * 
     */
    private Date createdTime;

    /**
     * 
     */
    private Date updateTime;

    /**
     * 
     */
    private String remark;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getMenuId() {
        return menuId;
    }

    /**
     * 
     */
    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    /**
     * 
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * 
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    /**
     * 
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 
     */
    public String getPath() {
        return path;
    }

    /**
     * 
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 
     */
    public String getComponent() {
        return component;
    }

    /**
     * 
     */
    public void setComponent(String component) {
        this.component = component;
    }

    /**
     * 
     */
    public String getMenuType() {
        return menuType;
    }

    /**
     * 
     */
    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    /**
     * 
     */
    public String getVisible() {
        return visible;
    }

    /**
     * 
     */
    public void setVisible(String visible) {
        this.visible = visible;
    }

    /**
     * 
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     */
    public void setStatus(String status) {
        this.status = status;
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
    public String getRemark() {
        return remark;
    }

    /**
     * 
     */
    public void setRemark(String remark) {
        this.remark = remark;
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
        Menu other = (Menu) that;
        return (this.getMenuId() == null ? other.getMenuId() == null : this.getMenuId().equals(other.getMenuId()))
            && (this.getMenuName() == null ? other.getMenuName() == null : this.getMenuName().equals(other.getMenuName()))
            && (this.getParentId() == null ? other.getParentId() == null : this.getParentId().equals(other.getParentId()))
            && (this.getPath() == null ? other.getPath() == null : this.getPath().equals(other.getPath()))
            && (this.getComponent() == null ? other.getComponent() == null : this.getComponent().equals(other.getComponent()))
            && (this.getMenuType() == null ? other.getMenuType() == null : this.getMenuType().equals(other.getMenuType()))
            && (this.getVisible() == null ? other.getVisible() == null : this.getVisible().equals(other.getVisible()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMenuId() == null) ? 0 : getMenuId().hashCode());
        result = prime * result + ((getMenuName() == null) ? 0 : getMenuName().hashCode());
        result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
        result = prime * result + ((getPath() == null) ? 0 : getPath().hashCode());
        result = prime * result + ((getComponent() == null) ? 0 : getComponent().hashCode());
        result = prime * result + ((getMenuType() == null) ? 0 : getMenuType().hashCode());
        result = prime * result + ((getVisible() == null) ? 0 : getVisible().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", menuId=").append(menuId);
        sb.append(", menuName=").append(menuName);
        sb.append(", parentId=").append(parentId);
        sb.append(", path=").append(path);
        sb.append(", component=").append(component);
        sb.append(", menuType=").append(menuType);
        sb.append(", visible=").append(visible);
        sb.append(", status=").append(status);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}