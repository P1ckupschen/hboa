package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gdproj.handler.jsonAndListTypeHandler;
import com.gdproj.vo.fileVo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @TableName sys_overtime
 */
@TableName(value ="sys_overtime",autoResultMap = true)
public class Overtime implements Serializable {
    /**
     * 
     */
    @TableId(value = "overtime_id", type = IdType.AUTO)
    private Integer overtimeId;

    /**
     * 
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 
     */
    @TableField(value = "department_id")
    private Integer departmentId;

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
    @TableField(value = "overtime_pic",typeHandler = jsonAndListTypeHandler.class)
    private List<fileVo> overtimePic;

    /**
     * 
     */
    @TableField(value = "is_deleted")
    private String isDeleted;

    /**
     * 
     */
    @TableField(value = "overtime_status")
    private Integer overtimeStatus;

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
    public Integer getUserId() {
        return userId;
    }

    /**
     * 
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
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
    public List<fileVo> getOvertimePic() {
        return overtimePic;
    }

    /**
     * 
     */
    public void setOvertimePic(List<fileVo> overtimePic) {
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

    /**
     * 
     */
    public Integer getOvertimeStatus() {
        return overtimeStatus;
    }

    /**
     * 
     */
    public void setOvertimeStatus(Integer overtimeStatus) {
        this.overtimeStatus = overtimeStatus;
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
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getDepartmentId() == null ? other.getDepartmentId() == null : this.getDepartmentId().equals(other.getDepartmentId()))
            && (this.getOvertimeDays() == null ? other.getOvertimeDays() == null : this.getOvertimeDays().equals(other.getOvertimeDays()))
            && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
            && (this.getStartTime() == null ? other.getStartTime() == null : this.getStartTime().equals(other.getStartTime()))
            && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
            && (this.getOvertimeDescription() == null ? other.getOvertimeDescription() == null : this.getOvertimeDescription().equals(other.getOvertimeDescription()))
            && (this.getOvertimePic() == null ? other.getOvertimePic() == null : this.getOvertimePic().equals(other.getOvertimePic()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getOvertimeStatus() == null ? other.getOvertimeStatus() == null : this.getOvertimeStatus().equals(other.getOvertimeStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOvertimeId() == null) ? 0 : getOvertimeId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getDepartmentId() == null) ? 0 : getDepartmentId().hashCode());
        result = prime * result + ((getOvertimeDays() == null) ? 0 : getOvertimeDays().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getStartTime() == null) ? 0 : getStartTime().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        result = prime * result + ((getOvertimeDescription() == null) ? 0 : getOvertimeDescription().hashCode());
        result = prime * result + ((getOvertimePic() == null) ? 0 : getOvertimePic().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getOvertimeStatus() == null) ? 0 : getOvertimeStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", overtimeId=").append(overtimeId);
        sb.append(", userId=").append(userId);
        sb.append(", departmentId=").append(departmentId);
        sb.append(", overtimeDays=").append(overtimeDays);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", overtimeDescription=").append(overtimeDescription);
        sb.append(", overtimePic=").append(overtimePic);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", overtimeStatus=").append(overtimeStatus);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}