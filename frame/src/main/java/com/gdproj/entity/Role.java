package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色信息表
 * @TableName sys_role
 */
@TableName(value ="sys_role")
public class Role implements Serializable {
    /**
     * 角色ID
     */
    @TableId(value = "role_id", type = IdType.AUTO)
    private Integer roleId;

    /**
     * 角色名称
     */
    @TableField(value = "role_name")
    private String roleName;

    /**
     * 角色权限字符串
     */
    @TableField(value = "role_key")
    private String roleKey;

    /**
     * 显示顺序
     */
    @TableField(value = "role_sort")
    private Integer roleSort;

    /**
     * 角色状态（0正常 1停用）
     */
    @TableField(value = "status")
    private Integer status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @TableField(value = "is_deleted")
    @TableLogic
    private Integer isDeleted;

    /**
     * 创建者
     */
    @TableField(value = "create_user")
    private String createUser;

    /**
     * 创建时间
     */
    @TableField(value = "created_time" ,fill = FieldFill.INSERT)
    private Date createdTime;

    /**
     * 更新者
     */
    @TableField(value = "update_user")
    private String updateUser;

    /**
     * 更新时间
     */
    @TableField(value = "update_time" ,fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * 角色ID
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * 角色名称
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * 角色名称
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * 角色权限字符串
     */
    public String getRoleKey() {
        return roleKey;
    }

    /**
     * 角色权限字符串
     */
    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey;
    }

    /**
     * 显示顺序
     */
    public Integer getRoleSort() {
        return roleSort;
    }

    /**
     * 显示顺序
     */
    public void setRoleSort(Integer roleSort) {
        this.roleSort = roleSort;
    }

    /**
     * 角色状态（0正常 1停用）
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 角色状态（0正常 1停用）
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * 创建者
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 创建者
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
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
    public String getUpdateUser() {
        return updateUser;
    }

    /**
     * 更新者
     */
    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
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
        Role other = (Role) that;
        return (this.getRoleId() == null ? other.getRoleId() == null : this.getRoleId().equals(other.getRoleId()))
                && (this.getRoleName() == null ? other.getRoleName() == null : this.getRoleName().equals(other.getRoleName()))
                && (this.getRoleKey() == null ? other.getRoleKey() == null : this.getRoleKey().equals(other.getRoleKey()))
                && (this.getRoleSort() == null ? other.getRoleSort() == null : this.getRoleSort().equals(other.getRoleSort()))
                && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
                && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
                && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
                && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
                && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
                && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRoleId() == null) ? 0 : getRoleId().hashCode());
        result = prime * result + ((getRoleName() == null) ? 0 : getRoleName().hashCode());
        result = prime * result + ((getRoleKey() == null) ? 0 : getRoleKey().hashCode());
        result = prime * result + ((getRoleSort() == null) ? 0 : getRoleSort().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
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
        sb.append(", roleId=").append(roleId);
        sb.append(", roleName=").append(roleName);
        sb.append(", roleKey=").append(roleKey);
        sb.append(", roleSort=").append(roleSort);
        sb.append(", status=").append(status);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", createUser=").append(createUser);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", remark=").append(remark);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}