package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 菜单权限表
 * @TableName sys_menu
 */
@TableName(value ="sys_menu")
public class Menu implements Serializable {
    /**
     * 菜单ID
     */
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Long menuId;

    /**
     * 菜单名称
     */
    @TableField(value = "menu_name")
    private String menuName;

    /**
     * 父菜单ID
     */
    @TableField(value = "parent_id")
    private Long parentId;

    /**
     * 显示顺序
     */
    @TableField(value = "order_num")
    private Integer orderNum;

    /**
     * 路由地址
     */
    @TableField(value = "path")
    private String path;

    /**
     * 组件路径
     */
    @TableField(value = "component")
    private String component;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    @TableField(value = "menu_type")
    private String menuType;

    /**
     * 菜单状态（0显示 1隐藏）
     */
    @TableField(value = "visible")
    private String visible;

    /**
     * 菜单状态（0正常 1停用）
     */
    @TableField(value = "status")
    private String status;

    /**
     * 权限标识
     */
    @TableField(value = "perms")
    private String perms;

    /**
     * 创建者
     */
    @TableField(value = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(value = "created_time" , fill = FieldFill.INSERT)
    private Date createdTime;

    /**
     * 更新者
     */
    @TableField(value = "update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time" , fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    @TableField(exist = false)
    private List<Menu> children;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    public Long getMenuId() {
        return menuId;
    }

    /**
     * 菜单ID
     */
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    /**
     * 菜单名称
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * 菜单名称
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    /**
     * 父菜单ID
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 父菜单ID
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 显示顺序
     */
    public Integer getOrderNum() {
        return orderNum;
    }

    /**
     * 显示顺序
     */
    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    /**
     * 路由地址
     */
    public String getPath() {
        return path;
    }

    /**
     * 路由地址
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 组件路径
     */
    public String getComponent() {
        return component;
    }

    /**
     * 组件路径
     */
    public void setComponent(String component) {
        this.component = component;
    }

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    public String getMenuType() {
        return menuType;
    }

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    /**
     * 菜单状态（0显示 1隐藏）
     */
    public String getVisible() {
        return visible;
    }

    /**
     * 菜单状态（0显示 1隐藏）
     */
    public void setVisible(String visible) {
        this.visible = visible;
    }

    /**
     * 菜单状态（0正常 1停用）
     */
    public String getStatus() {
        return status;
    }

    /**
     * 菜单状态（0正常 1停用）
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 权限标识
     */
    public String getPerms() {
        return perms;
    }

    /**
     * 权限标识
     */
    public void setPerms(String perms) {
        this.perms = perms;
    }

    /**
     * 创建者
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 创建者
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 创建时间
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * 创建时间
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * 更新者
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * 更新者
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 备注
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
            && (this.getOrderNum() == null ? other.getOrderNum() == null : this.getOrderNum().equals(other.getOrderNum()))
            && (this.getPath() == null ? other.getPath() == null : this.getPath().equals(other.getPath()))
            && (this.getComponent() == null ? other.getComponent() == null : this.getComponent().equals(other.getComponent()))
            && (this.getMenuType() == null ? other.getMenuType() == null : this.getMenuType().equals(other.getMenuType()))
            && (this.getVisible() == null ? other.getVisible() == null : this.getVisible().equals(other.getVisible()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getPerms() == null ? other.getPerms() == null : this.getPerms().equals(other.getPerms()))
            && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
            && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
            && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
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
        result = prime * result + ((getOrderNum() == null) ? 0 : getOrderNum().hashCode());
        result = prime * result + ((getPath() == null) ? 0 : getPath().hashCode());
        result = prime * result + ((getComponent() == null) ? 0 : getComponent().hashCode());
        result = prime * result + ((getMenuType() == null) ? 0 : getMenuType().hashCode());
        result = prime * result + ((getVisible() == null) ? 0 : getVisible().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getPerms() == null) ? 0 : getPerms().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
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
        sb.append(", orderNum=").append(orderNum);
        sb.append(", path=").append(path);
        sb.append(", component=").append(component);
        sb.append(", menuType=").append(menuType);
        sb.append(", visible=").append(visible);
        sb.append(", status=").append(status);
        sb.append(", perms=").append(perms);
        sb.append(", createBy=").append(createBy);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updateBy=").append(updateBy);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }
}