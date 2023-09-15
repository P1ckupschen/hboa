package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName sys_overtime
 */
@TableName(value ="sys_overtime")
public class Overtime implements Serializable {
    /**
     * 
     */
    @TableId(value = "overtime_id", type = IdType.AUTO)
    private Integer overtimeId;

    /**
     * 
     */
    @TableField(value = "overtime_user")
    private Integer overtimeUser;

    /**
     * 
     */
    @TableField(value = "overtime_department")
    private Integer overtimeDepartment;

    /**
     * 
     */
    @TableField(value = "overtime_days")
    private Integer overtimeDays;

    /**
     * 
     */
    @TableField(value = "category_id")
    private Integer categoryId;

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
    @TableField(value = "overtime_description")
    private String overtimeDescription;

    /**
     * 
     */
    @TableField(value = "overtime_pic")
    private String overtimePic;

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
    public Integer getOvertimeId() {
        return overtimeId;
    }

    /**
     * 
     */
    public void setOvertimeId(Integer overtimeId) {
        this.overtimeId = overtimeId;
    }

    /**
     * 
     */
    public Integer getOvertimeUser() {
        return overtimeUser;
    }

    /**
     * 
     */
    public void setOvertimeUser(Integer overtimeUser) {
        this.overtimeUser = overtimeUser;
    }

    /**
     * 
     */
    public Integer getOvertimeDepartment() {
        return overtimeDepartment;
    }

    /**
     * 
     */
    public void setOvertimeDepartment(Integer overtimeDepartment) {
        this.overtimeDepartment = overtimeDepartment;
    }

    /**
     * 
     */
    public Integer getOvertimeDays() {
        return overtimeDays;
    }

    /**
     * 
     */
    public void setOvertimeDays(Integer overtimeDays) {
        this.overtimeDays = overtimeDays;
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
    public String getOvertimeDescription() {
        return overtimeDescription;
    }

    /**
     * 
     */
    public void setOvertimeDescription(String overtimeDescription) {
        this.overtimeDescription = overtimeDescription;
    }

    /**
     * 
     */
    public String getOvertimePic() {
        return overtimePic;
    }

    /**
     * 
     */
    public void setOvertimePic(String overtimePic) {
        this.overtimePic = overtimePic;
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
        Overtime other = (Overtime) that;
        return (this.getOvertimeId() == null ? other.getOvertimeId() == null : this.getOvertimeId().equals(other.getOvertimeId()))
            && (this.getOvertimeUser() == null ? other.getOvertimeUser() == null : this.getOvertimeUser().equals(other.getOvertimeUser()))
            && (this.getOvertimeDepartment() == null ? other.getOvertimeDepartment() == null : this.getOvertimeDepartment().equals(other.getOvertimeDepartment()))
            && (this.getOvertimeDays() == null ? other.getOvertimeDays() == null : this.getOvertimeDays().equals(other.getOvertimeDays()))
            && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
            && (this.getStartTime() == null ? other.getStartTime() == null : this.getStartTime().equals(other.getStartTime()))
            && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
            && (this.getOvertimeDescription() == null ? other.getOvertimeDescription() == null : this.getOvertimeDescription().equals(other.getOvertimeDescription()))
            && (this.getOvertimePic() == null ? other.getOvertimePic() == null : this.getOvertimePic().equals(other.getOvertimePic()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOvertimeId() == null) ? 0 : getOvertimeId().hashCode());
        result = prime * result + ((getOvertimeUser() == null) ? 0 : getOvertimeUser().hashCode());
        result = prime * result + ((getOvertimeDepartment() == null) ? 0 : getOvertimeDepartment().hashCode());
        result = prime * result + ((getOvertimeDays() == null) ? 0 : getOvertimeDays().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getStartTime() == null) ? 0 : getStartTime().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        result = prime * result + ((getOvertimeDescription() == null) ? 0 : getOvertimeDescription().hashCode());
        result = prime * result + ((getOvertimePic() == null) ? 0 : getOvertimePic().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", overtimeId=").append(overtimeId);
        sb.append(", overtimeUser=").append(overtimeUser);
        sb.append(", overtimeDepartment=").append(overtimeDepartment);
        sb.append(", overtimeDays=").append(overtimeDays);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", overtimeDescription=").append(overtimeDescription);
        sb.append(", overtimePic=").append(overtimePic);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}