package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * 用户和角色关联表
 * @TableName sys_user_role
 */
@TableName(value ="sys_user_role")
public class UserRole implements Serializable {
    /**
     * 用户ID
     */
    @TableField(value = "deployee_id")
    private Integer deployeeId;

    /**
     * 角色ID
     */
    @TableField(value = "role_id")
    private Integer roleId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    public Integer getDeployeeId() {
        return deployeeId;
    }

    /**
     * 用户ID
     */
    public void setDeployeeId(Integer deployeeId) {
        this.deployeeId = deployeeId;
    }

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
        UserRole other = (UserRole) that;
        return (this.getDeployeeId() == null ? other.getDeployeeId() == null : this.getDeployeeId().equals(other.getDeployeeId()))
                && (this.getRoleId() == null ? other.getRoleId() == null : this.getRoleId().equals(other.getRoleId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDeployeeId() == null) ? 0 : getDeployeeId().hashCode());
        result = prime * result + ((getRoleId() == null) ? 0 : getRoleId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", deployeeId=").append(deployeeId);
        sb.append(", roleId=").append(roleId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}