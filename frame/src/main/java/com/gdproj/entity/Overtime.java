package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
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
     * 申请人
     */
    @TableField(value = "applicant_id")
    private Integer applicantId;

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
    @TableField(value = "overtime_pic",typeHandler = JacksonTypeHandler.class)
    private List<fileVo> overtimePic;

    /**
     *
     */
    @TableField(value = "is_deleted")
    @TableLogic
    private Integer isDeleted;

    /**
     *
     */
    @TableField(value = "overtime_status")
    private Integer overtimeStatus;

    /**
     *
     */
    @TableField(value = "type_id")
    private Integer typeId;

    /**
     *
     */
    @TableField(value = "overtime_title")
    private String overtimeTitle;

    /**
     * 加班人
     */
    @TableField(value = "executor_id")
    private Integer executorId;

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
     * 申请人
     */
    public Integer getApplicantId() {
        return applicantId;
    }

    /**
     * 申请人
     */
    public void setApplicantId(Integer applicantId) {
        this.applicantId = applicantId;
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
    public Integer getOvertimeStatus() {
        return overtimeStatus;
    }

    /**
     *
     */
    public void setOvertimeStatus(Integer overtimeStatus) {
        this.overtimeStatus = overtimeStatus;
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

    /**
     *
     */
    public String getOvertimeTitle() {
        return overtimeTitle;
    }

    /**
     *
     */
    public void setOvertimeTitle(String overtimeTitle) {
        this.overtimeTitle = overtimeTitle;
    }

    /**
     * 加班人
     */
    public Integer getExecutorId() {
        return executorId;
    }

    /**
     * 加班人
     */
    public void setExecutorId(Integer executorId) {
        this.executorId = executorId;
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
                && (this.getApplicantId() == null ? other.getApplicantId() == null : this.getApplicantId().equals(other.getApplicantId()))
                && (this.getDepartmentId() == null ? other.getDepartmentId() == null : this.getDepartmentId().equals(other.getDepartmentId()))
                && (this.getOvertimeDays() == null ? other.getOvertimeDays() == null : this.getOvertimeDays().equals(other.getOvertimeDays()))
                && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
                && (this.getStartTime() == null ? other.getStartTime() == null : this.getStartTime().equals(other.getStartTime()))
                && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
                && (this.getOvertimeDescription() == null ? other.getOvertimeDescription() == null : this.getOvertimeDescription().equals(other.getOvertimeDescription()))
                && (this.getOvertimePic() == null ? other.getOvertimePic() == null : this.getOvertimePic().equals(other.getOvertimePic()))
                && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
                && (this.getOvertimeStatus() == null ? other.getOvertimeStatus() == null : this.getOvertimeStatus().equals(other.getOvertimeStatus()))
                && (this.getTypeId() == null ? other.getTypeId() == null : this.getTypeId().equals(other.getTypeId()))
                && (this.getOvertimeTitle() == null ? other.getOvertimeTitle() == null : this.getOvertimeTitle().equals(other.getOvertimeTitle()))
                && (this.getExecutorId() == null ? other.getExecutorId() == null : this.getExecutorId().equals(other.getExecutorId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOvertimeId() == null) ? 0 : getOvertimeId().hashCode());
        result = prime * result + ((getApplicantId() == null) ? 0 : getApplicantId().hashCode());
        result = prime * result + ((getDepartmentId() == null) ? 0 : getDepartmentId().hashCode());
        result = prime * result + ((getOvertimeDays() == null) ? 0 : getOvertimeDays().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getStartTime() == null) ? 0 : getStartTime().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        result = prime * result + ((getOvertimeDescription() == null) ? 0 : getOvertimeDescription().hashCode());
        result = prime * result + ((getOvertimePic() == null) ? 0 : getOvertimePic().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getOvertimeStatus() == null) ? 0 : getOvertimeStatus().hashCode());
        result = prime * result + ((getTypeId() == null) ? 0 : getTypeId().hashCode());
        result = prime * result + ((getOvertimeTitle() == null) ? 0 : getOvertimeTitle().hashCode());
        result = prime * result + ((getExecutorId() == null) ? 0 : getExecutorId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", overtimeId=").append(overtimeId);
        sb.append(", applicantId=").append(applicantId);
        sb.append(", departmentId=").append(departmentId);
        sb.append(", overtimeDays=").append(overtimeDays);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", overtimeDescription=").append(overtimeDescription);
        sb.append(", overtimePic=").append(overtimePic);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", overtimeStatus=").append(overtimeStatus);
        sb.append(", typeId=").append(typeId);
        sb.append(", overtimeTitle=").append(overtimeTitle);
        sb.append(", executorId=").append(executorId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}