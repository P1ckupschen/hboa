package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

/**
 *
 * @TableName sys_department
 */
@TableName(value ="sys_department")
public class Department implements Serializable {
    /**
     *
     */
    @TableId(value = "department_id", type = IdType.AUTO)
    private Integer departmentId;

    /**
     *
     */
    @TableField(value = "department_name")
    private String departmentName;

    /**
     *
     */
    @TableField(value = "department_head")
    private Integer departmentHead;

    /**
     *
     */
    @TableField(value = "department_fax")
    private String departmentFax;

    /**
     *
     */
    @TableField(value = "department_phone")
    private String departmentPhone;

    /**
     *
     */
    @TableField(value = "parent_id")
    private Integer parentId;

    /**
     *
     */
    @TableField(value = "is_deleted")
    @TableLogic
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public Integer getDepartmentId() {
        return departmentId;
    }

    /**
     *
     */
    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    /**
     *
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     *
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     *
     */
    public Integer getDepartmentHead() {
        return departmentHead;
    }

    /**
     *
     */
    public void setDepartmentHead(Integer departmentHead) {
        this.departmentHead = departmentHead;
    }

    /**
     *
     */
    public String getDepartmentFax() {
        return departmentFax;
    }

    /**
     *
     */
    public void setDepartmentFax(String departmentFax) {
        this.departmentFax = departmentFax;
    }

    /**
     *
     */
    public String getDepartmentPhone() {
        return departmentPhone;
    }

    /**
     *
     */
    public void setDepartmentPhone(String departmentPhone) {
        this.departmentPhone = departmentPhone;
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
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     *
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
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
        Department other = (Department) that;
        return (this.getDepartmentId() == null ? other.getDepartmentId() == null : this.getDepartmentId().equals(other.getDepartmentId()))
                && (this.getDepartmentName() == null ? other.getDepartmentName() == null : this.getDepartmentName().equals(other.getDepartmentName()))
                && (this.getDepartmentHead() == null ? other.getDepartmentHead() == null : this.getDepartmentHead().equals(other.getDepartmentHead()))
                && (this.getDepartmentFax() == null ? other.getDepartmentFax() == null : this.getDepartmentFax().equals(other.getDepartmentFax()))
                && (this.getDepartmentPhone() == null ? other.getDepartmentPhone() == null : this.getDepartmentPhone().equals(other.getDepartmentPhone()))
                && (this.getParentId() == null ? other.getParentId() == null : this.getParentId().equals(other.getParentId()))
                && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDepartmentId() == null) ? 0 : getDepartmentId().hashCode());
        result = prime * result + ((getDepartmentName() == null) ? 0 : getDepartmentName().hashCode());
        result = prime * result + ((getDepartmentHead() == null) ? 0 : getDepartmentHead().hashCode());
        result = prime * result + ((getDepartmentFax() == null) ? 0 : getDepartmentFax().hashCode());
        result = prime * result + ((getDepartmentPhone() == null) ? 0 : getDepartmentPhone().hashCode());
        result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", departmentId=").append(departmentId);
        sb.append(", departmentName=").append(departmentName);
        sb.append(", departmentHead=").append(departmentHead);
        sb.append(", departmentFax=").append(departmentFax);
        sb.append(", departmentPhone=").append(departmentPhone);
        sb.append(", parentId=").append(parentId);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}