package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.gdproj.vo.DailyUseContentVo;
import com.gdproj.vo.FileVo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @TableName sys_schedule
 */
@TableName(value ="sys_schedule" ,autoResultMap = true)
public class Schedule implements Serializable {
    /**
     * 
     */
    @TableId(value = "schedule_id", type = IdType.AUTO)
    private Integer scheduleId;

    /**
     * 
     */
    @TableField(value = "project_id")
    private Integer projectId;

    /**
     * 时间段内容
     */
    @TableField(value = "schedule_content")
    private String scheduleContent;

    /**
     * 
     */
    @TableField(value = "is_completed")
    private Integer isCompleted;

    /**
     * 
     */
    @TableField(value = "is_late")
    private Integer isLate;

    /**
     * 
     */
    @TableField(value = "plan_start_time")
    private Date planStartTime;

    /**
     * 
     */
    @TableField(value = "plan_end_time")
    private Date planEndTime;

    /**
     * 
     */
    @TableField(value = "true_end_time")
    private Date trueEndTime;

    /**
     * 
     */
    @TableField(value = "is_deleted")
    @TableLogic
    private Integer isDeleted;

    /**
     * 存json工具清单
     */
    @TableField(value = "tool_list" ,typeHandler = JacksonTypeHandler.class)
    private List<DailyUseContentVo> toolList;

    /**
     * 时间段汇报
     */
    @TableField(value = "schedule_report")
    private String scheduleReport;

    /**
     * 
     */
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    private Date createdTime;

    public String getScheduleName() {
        return scheduleName;
    }

    public void setScheduleName(String scheduleName) {
        this.scheduleName = scheduleName;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    @TableField(value = "type_id")
    private Integer typeId;

    @TableField(value = "schedule_name")
    private String scheduleName;

    /**
     * 
     */
    @TableField(value = "update_time" ,fill =  FieldFill.INSERT_UPDATE)
    private Date updateTime;

    public List<FileVo> getScheduleAttachments() {
        return scheduleAttachments;
    }

    public void setScheduleAttachments( List<FileVo> scheduleAttachments) {
        this.scheduleAttachments = scheduleAttachments;
    }

    @TableField(value = "schedule_attachments" ,typeHandler = JacksonTypeHandler.class)
    private  List<FileVo> scheduleAttachments;

    public Date getTrueStartTime() {
        return trueStartTime;
    }

    public void setTrueStartTime(Date trueStartTime) {
        this.trueStartTime = trueStartTime;
    }

    @TableField(value = "true_start_time")
    private Date trueStartTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getScheduleId() {
        return scheduleId;
    }

    /**
     * 
     */
    public void setScheduleId(Integer scheduleId) {
        this.scheduleId = scheduleId;
    }

    /**
     * 
     */
    public Integer getProjectId() {
        return projectId;
    }

    /**
     * 
     */
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    /**
     * 时间段内容
     */
    public String getScheduleContent() {
        return scheduleContent;
    }

    /**
     * 时间段内容
     */
    public void setScheduleContent(String scheduleContent) {
        this.scheduleContent = scheduleContent;
    }

    /**
     * 
     */
    public Integer getIsCompleted() {
        return isCompleted;
    }

    /**
     * 
     */
    public void setIsCompleted(Integer isCompleted) {
        this.isCompleted = isCompleted;
    }

    /**
     * 
     */
    public Integer getIsLate() {
        return isLate;
    }

    /**
     * 
     */
    public void setIsLate(Integer isLate) {
        this.isLate = isLate;
    }

    /**
     * 
     */
    public Date getPlanStartTime() {
        return planStartTime;
    }

    /**
     * 
     */
    public void setPlanStartTime(Date planStartTime) {
        this.planStartTime = planStartTime;
    }

    /**
     * 
     */
    public Date getPlanEndTime() {
        return planEndTime;
    }

    /**
     * 
     */
    public void setPlanEndTime(Date planEndTime) {
        this.planEndTime = planEndTime;
    }

    /**
     * 
     */
    public Date getTrueEndTime() {
        return trueEndTime;
    }

    /**
     * 
     */
    public void setTrueEndTime(Date trueEndTime) {
        this.trueEndTime = trueEndTime;
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
     * 存json工具清单
     */
    public List<DailyUseContentVo> getToolList() {
        return toolList;
    }

    /**
     * 存json工具清单
     */
    public void setToolList(List<DailyUseContentVo> toolList) {
        this.toolList = toolList;
    }

    /**
     * 时间段汇报
     */
    public String getScheduleReport() {
        return scheduleReport;
    }

    /**
     * 时间段汇报
     */
    public void setScheduleReport(String scheduleReport) {
        this.scheduleReport = scheduleReport;
    }

    /**
     * 
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * 
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    /**
     * 
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
        Schedule other = (Schedule) that;
        return (this.getScheduleId() == null ? other.getScheduleId() == null : this.getScheduleId().equals(other.getScheduleId()))
            && (this.getProjectId() == null ? other.getProjectId() == null : this.getProjectId().equals(other.getProjectId()))
            && (this.getScheduleContent() == null ? other.getScheduleContent() == null : this.getScheduleContent().equals(other.getScheduleContent()))
            && (this.getIsCompleted() == null ? other.getIsCompleted() == null : this.getIsCompleted().equals(other.getIsCompleted()))
            && (this.getIsLate() == null ? other.getIsLate() == null : this.getIsLate().equals(other.getIsLate()))
            && (this.getPlanStartTime() == null ? other.getPlanStartTime() == null : this.getPlanStartTime().equals(other.getPlanStartTime()))
            && (this.getPlanEndTime() == null ? other.getPlanEndTime() == null : this.getPlanEndTime().equals(other.getPlanEndTime()))
            && (this.getTrueEndTime() == null ? other.getTrueEndTime() == null : this.getTrueEndTime().equals(other.getTrueEndTime()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getToolList() == null ? other.getToolList() == null : this.getToolList().equals(other.getToolList()))
            && (this.getScheduleReport() == null ? other.getScheduleReport() == null : this.getScheduleReport().equals(other.getScheduleReport()))
            && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getScheduleId() == null) ? 0 : getScheduleId().hashCode());
        result = prime * result + ((getProjectId() == null) ? 0 : getProjectId().hashCode());
        result = prime * result + ((getScheduleContent() == null) ? 0 : getScheduleContent().hashCode());
        result = prime * result + ((getIsCompleted() == null) ? 0 : getIsCompleted().hashCode());
        result = prime * result + ((getIsLate() == null) ? 0 : getIsLate().hashCode());
        result = prime * result + ((getPlanStartTime() == null) ? 0 : getPlanStartTime().hashCode());
        result = prime * result + ((getPlanEndTime() == null) ? 0 : getPlanEndTime().hashCode());
        result = prime * result + ((getTrueEndTime() == null) ? 0 : getTrueEndTime().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getToolList() == null) ? 0 : getToolList().hashCode());
        result = prime * result + ((getScheduleReport() == null) ? 0 : getScheduleReport().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", scheduleId=").append(scheduleId);
        sb.append(", projectId=").append(projectId);
        sb.append(", scheduleContent=").append(scheduleContent);
        sb.append(", isCompleted=").append(isCompleted);
        sb.append(", isLate=").append(isLate);
        sb.append(", planStartTime=").append(planStartTime);
        sb.append(", planEndTime=").append(planEndTime);
        sb.append(", trueEndTime=").append(trueEndTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", toolList=").append(toolList);
        sb.append(", scheduleReport=").append(scheduleReport);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}