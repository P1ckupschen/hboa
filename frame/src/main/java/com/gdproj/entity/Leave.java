package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.gdproj.vo.FileVo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @TableName sys_leave
 */
@TableName(value ="sys_leave",autoResultMap = true)
public class Leave implements Serializable {
    /**
     *
     */
    @TableId(value = "leave_id", type = IdType.AUTO)
    private Integer leaveId;

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
    @TableField(value = "leave_pic",typeHandler = JacksonTypeHandler.class)
    private List<FileVo> leavePic;

    /**
     *
     */
    @TableField(value = "category_id")
    private Integer categoryId;

    /**
     *
     */
    @TableField(value = "is_deleted")
    @TableLogic
    private Integer isDeleted;

    /**
     *
     */
    @TableField(value = "leave_status")
    private Integer leaveStatus;

    /**
     *
     */
    @TableField(value = "leave_title")
    private String leaveTitle;

    /**
     *
     */
    @TableField(value = "type_id")
    private Integer typeId;

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @TableField(value = "created_time",fill = FieldFill.INSERT)
    private Date createdTime;

    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

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
    public List<FileVo> getLeavePic() {
        return leavePic;
    }

    /**
     *
     */
    public void setLeavePic(List<FileVo> leavePic) {
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
    public Integer getLeaveStatus() {
        return leaveStatus;
    }

    /**
     *
     */
    public void setLeaveStatus(Integer leaveStatus) {
        this.leaveStatus = leaveStatus;
    }

    /**
     *
     */
    public String getLeaveTitle() {
        return leaveTitle;
    }

    /**
     *
     */
    public void setLeaveTitle(String leaveTitle) {
        this.leaveTitle = leaveTitle;
    }

    /**
     *
     */
    public Integer getTypeId() {
        return typeId;
    }

    /**
     *
     */
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
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
                && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
                && (this.getDepartmentId() == null ? other.getDepartmentId() == null : this.getDepartmentId().equals(other.getDepartmentId()))
                && (this.getLeaveDays() == null ? other.getLeaveDays() == null : this.getLeaveDays().equals(other.getLeaveDays()))
                && (this.getStartTime() == null ? other.getStartTime() == null : this.getStartTime().equals(other.getStartTime()))
                && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
                && (this.getLeaveDescription() == null ? other.getLeaveDescription() == null : this.getLeaveDescription().equals(other.getLeaveDescription()))
                && (this.getLeavePic() == null ? other.getLeavePic() == null : this.getLeavePic().equals(other.getLeavePic()))
                && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
                && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
                && (this.getLeaveStatus() == null ? other.getLeaveStatus() == null : this.getLeaveStatus().equals(other.getLeaveStatus()))
                && (this.getLeaveTitle() == null ? other.getLeaveTitle() == null : this.getLeaveTitle().equals(other.getLeaveTitle()))
                && (this.getTypeId() == null ? other.getTypeId() == null : this.getTypeId().equals(other.getTypeId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getLeaveId() == null) ? 0 : getLeaveId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getDepartmentId() == null) ? 0 : getDepartmentId().hashCode());
        result = prime * result + ((getLeaveDays() == null) ? 0 : getLeaveDays().hashCode());
        result = prime * result + ((getStartTime() == null) ? 0 : getStartTime().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        result = prime * result + ((getLeaveDescription() == null) ? 0 : getLeaveDescription().hashCode());
        result = prime * result + ((getLeavePic() == null) ? 0 : getLeavePic().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getLeaveStatus() == null) ? 0 : getLeaveStatus().hashCode());
        result = prime * result + ((getLeaveTitle() == null) ? 0 : getLeaveTitle().hashCode());
        result = prime * result + ((getTypeId() == null) ? 0 : getTypeId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", leaveId=").append(leaveId);
        sb.append(", userId=").append(userId);
        sb.append(", departmentId=").append(departmentId);
        sb.append(", leaveDays=").append(leaveDays);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", leaveDescription=").append(leaveDescription);
        sb.append(", leavePic=").append(leavePic);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", leaveStatus=").append(leaveStatus);
        sb.append(", leaveTitle=").append(leaveTitle);
        sb.append(", typeId=").append(typeId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}