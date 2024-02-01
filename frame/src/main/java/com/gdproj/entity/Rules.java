package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName sys_rules
 */
@TableName(value ="sys_rules")
public class Rules implements Serializable {
    /**
     * 
     */
    @TableId(value = "rules_id", type = IdType.AUTO)
    private Integer rulesId;

    /**
     * 
     */
    @TableField(value = "in_time")
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date inTime;

    /**
     * 
     */
    @TableField(value = "end_time")
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date endTime;

    /**
     * 迟到允许
     */
    @TableField(value = "in_allowance")
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date inAllowance;

    /**
     * 
     */
    @TableField(value = "end_allowance")
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+8")
    private Date endAllowance;

    /**
     * 
     */
    @TableField(value = "department_id")
    private Integer departmentId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getRulesId() {
        return rulesId;
    }

    /**
     * 
     */
    public void setRulesId(Integer rulesId) {
        this.rulesId = rulesId;
    }

    /**
     * 
     */
    public Date getInTime() {
        return inTime;
    }

    /**
     * 
     */
    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }

    /**
     * 
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 迟到允许
     */
    public Date getInAllowance() {
        return inAllowance;
    }

    /**
     * 迟到允许
     */
    public void setInAllowance(Date inAllowance) {
        this.inAllowance = inAllowance;
    }

    /**
     * 
     */
    public Date getEndAllowance() {
        return endAllowance;
    }

    /**
     * 
     */
    public void setEndAllowance(Date endAllowance) {
        this.endAllowance = endAllowance;
    }

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
        Rules other = (Rules) that;
        return (this.getRulesId() == null ? other.getRulesId() == null : this.getRulesId().equals(other.getRulesId()))
            && (this.getInTime() == null ? other.getInTime() == null : this.getInTime().equals(other.getInTime()))
            && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
            && (this.getInAllowance() == null ? other.getInAllowance() == null : this.getInAllowance().equals(other.getInAllowance()))
            && (this.getEndAllowance() == null ? other.getEndAllowance() == null : this.getEndAllowance().equals(other.getEndAllowance()))
            && (this.getDepartmentId() == null ? other.getDepartmentId() == null : this.getDepartmentId().equals(other.getDepartmentId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getRulesId() == null) ? 0 : getRulesId().hashCode());
        result = prime * result + ((getInTime() == null) ? 0 : getInTime().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        result = prime * result + ((getInAllowance() == null) ? 0 : getInAllowance().hashCode());
        result = prime * result + ((getEndAllowance() == null) ? 0 : getEndAllowance().hashCode());
        result = prime * result + ((getDepartmentId() == null) ? 0 : getDepartmentId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", rulesId=").append(rulesId);
        sb.append(", inTime=").append(inTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", inAllowance=").append(inAllowance);
        sb.append(", endAllowance=").append(endAllowance);
        sb.append(", departmentId=").append(departmentId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}