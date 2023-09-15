package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName sys_leave
 */
@TableName(value ="sys_leave")
public class Leave implements Serializable {
    /**
     * 
     */
    @TableId(value = "leave_id", type = IdType.AUTO)
    private Integer leaveId;

    /**
     * 
     */
    @TableField(value = "leave_user")
    private Integer leaveUser;

    /**
     * 
     */
    @TableField(value = "leave_department")
    private Integer leaveDepartment;

    /**
     * 
     */
    @TableField(value = "leave_days")
    private String leaveDays;

    /**
     * 
     */
    @TableField(value = "start_time")
    private Date startTime;

    /**
     * 
     */
    @TableField(value = "end_time")
    private Date endTime;

    /**
     * 
     */
    @TableField(value = "leave_description")
    private String leaveDescription;

    /**
     * 
     */
    @TableField(value = "leave_pic")
    private String leavePic;

    /**
     * 
     */
    @TableField(value = "category_id")
    private Integer categoryId;

    /**
     * 
     */
    @TableField(value = "is_deleted")
    private String isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getLeaveId() {
        return leaveId;
    }

    /**
     * 
     */
    public void setLeaveId(Integer leaveId) {
        this.leaveId = leaveId;
    }

    /**
     * 
     */
    public Integer getLeaveUser() {
        return leaveUser;
    }

    /**
     * 
     */
    public void setLeaveUser(Integer leaveUser) {
        this.leaveUser = leaveUser;
    }

    /**
     * 
     */
    public Integer getLeaveDepartment() {
        return leaveDepartment;
    }

    /**
     * 
     */
    public void setLeaveDepartment(Integer leaveDepartment) {
        this.leaveDepartment = leaveDepartment;
    }

    /**
     * 
     */
    public String getLeaveDays() {
        return leaveDays;
    }

    /**
     * 
     */
    public void setLeaveDays(String leaveDays) {
        this.leaveDays = leaveDays;
    }

    /**
     * 
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
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
     * 
     */
    public String getLeaveDescription() {
        return leaveDescription;
    }

    /**
     * 
     */
    public void setLeaveDescription(String leaveDescription) {
        this.leaveDescription = leaveDescription;
    }

    /**
     * 
     */
    public String getLeavePic() {
        return leavePic;
    }

    /**
     * 
     */
    public void setLeavePic(String leavePic) {
        this.leavePic = leavePic;
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
    public String getIsDeleted() {
        return isDeleted;
    }

    /**
     * 
     */
    public void setIsDeleted(String isDeleted) {
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
        Leave other = (Leave) that;
        return (this.getLeaveId() == null ? other.getLeaveId() == null : this.getLeaveId().equals(other.getLeaveId()))
            && (this.getLeaveUser() == null ? other.getLeaveUser() == null : this.getLeaveUser().equals(other.getLeaveUser()))
            && (this.getLeaveDepartment() == null ? other.getLeaveDepartment() == null : this.getLeaveDepartment().equals(other.getLeaveDepartment()))
            && (this.getLeaveDays() == null ? other.getLeaveDays() == null : this.getLeaveDays().equals(other.getLeaveDays()))
            && (this.getStartTime() == null ? other.getStartTime() == null : this.getStartTime().equals(other.getStartTime()))
            && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
            && (this.getLeaveDescription() == null ? other.getLeaveDescription() == null : this.getLeaveDescription().equals(other.getLeaveDescription()))
            && (this.getLeavePic() == null ? other.getLeavePic() == null : this.getLeavePic().equals(other.getLeavePic()))
            && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getLeaveId() == null) ? 0 : getLeaveId().hashCode());
        result = prime * result + ((getLeaveUser() == null) ? 0 : getLeaveUser().hashCode());
        result = prime * result + ((getLeaveDepartment() == null) ? 0 : getLeaveDepartment().hashCode());
        result = prime * result + ((getLeaveDays() == null) ? 0 : getLeaveDays().hashCode());
        result = prime * result + ((getStartTime() == null) ? 0 : getStartTime().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        result = prime * result + ((getLeaveDescription() == null) ? 0 : getLeaveDescription().hashCode());
        result = prime * result + ((getLeavePic() == null) ? 0 : getLeavePic().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", leaveId=").append(leaveId);
        sb.append(", leaveUser=").append(leaveUser);
        sb.append(", leaveDepartment=").append(leaveDepartment);
        sb.append(", leaveDays=").append(leaveDays);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", leaveDescription=").append(leaveDescription);
        sb.append(", leavePic=").append(leavePic);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}