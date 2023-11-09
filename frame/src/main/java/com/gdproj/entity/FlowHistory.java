package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName sys_flow_history
 */
@TableName(value ="sys_flow_history")
public class FlowHistory implements Serializable {
    /**
     * 
     */
    @TableId(value = "flow_history_id", type = IdType.AUTO)
    private Integer flowHistoryId;

    /**
     * 
     */
    @TableField(value = "flow_id")
    private Integer flowId;

    /**
     * 表类型的id
     */
    @TableField(value = "type_id")
    private Integer typeId;

    /**
     * 
     */
    @TableField(value = "flow_title")
    private String flowTitle;

    /**
     * 
     */
    @TableField(value = "current_step_id")
    private Integer currentStepId;

    /**
     * 具体到表内的id
     */
    @TableField(value = "run_id")
    private Integer runId;

    /**
     * 
     */
    @TableField(value = "current_user_id")
    private Integer currentUserId;

    /**
     * 
     */
    @TableField(value = "is_deleted")
    private Integer isDeleted;

    /**
     * 审核级数
     */
    @TableField(value = "total_level")
    private Integer totalLevel;

    /**
     * 
     */
    @TableField(value = "created_time",fill= FieldFill.INSERT)
    private Date createdTime;

    /**
     * 
     */
    @TableField(value = "update_time",fill=FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 0待审批 1已审批 2审批不通过
     */
    @TableField(value = "flow_status")
    private Integer flowStatus;

    /**
     * 反馈意见
     */
    @TableField(value = "flow_feedback")
    private String flowFeedback;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getFlowHistoryId() {
        return flowHistoryId;
    }

    /**
     * 
     */
    public void setFlowHistoryId(Integer flowHistoryId) {
        this.flowHistoryId = flowHistoryId;
    }

    /**
     * 
     */
    public Integer getFlowId() {
        return flowId;
    }

    /**
     * 
     */
    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }

    /**
     * 表类型的id
     */
    public Integer getTypeId() {
        return typeId;
    }

    /**
     * 表类型的id
     */
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    /**
     * 
     */
    public String getFlowTitle() {
        return flowTitle;
    }

    /**
     * 
     */
    public void setFlowTitle(String flowTitle) {
        this.flowTitle = flowTitle;
    }

    /**
     * 
     */
    public Integer getCurrentStepId() {
        return currentStepId;
    }

    /**
     * 
     */
    public void setCurrentStepId(Integer currentStepId) {
        this.currentStepId = currentStepId;
    }

    /**
     * 具体到表内的id
     */
    public Integer getRunId() {
        return runId;
    }

    /**
     * 具体到表内的id
     */
    public void setRunId(Integer runId) {
        this.runId = runId;
    }

    /**
     * 
     */
    public Integer getCurrentUserId() {
        return currentUserId;
    }

    /**
     * 
     */
    public void setCurrentUserId(Integer currentUserId) {
        this.currentUserId = currentUserId;
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
     * 审核级数
     */
    public Integer getTotalLevel() {
        return totalLevel;
    }

    /**
     * 审核级数
     */
    public void setTotalLevel(Integer totalLevel) {
        this.totalLevel = totalLevel;
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

    /**
     * 0待审批 1已审批 2审批不通过
     */
    public Integer getFlowStatus() {
        return flowStatus;
    }

    /**
     * 0待审批 1已审批 2审批不通过
     */
    public void setFlowStatus(Integer flowStatus) {
        this.flowStatus = flowStatus;
    }

    /**
     * 反馈意见
     */
    public String getFlowFeedback() {
        return flowFeedback;
    }

    /**
     * 反馈意见
     */
    public void setFlowFeedback(String flowFeedback) {
        this.flowFeedback = flowFeedback;
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
        FlowHistory other = (FlowHistory) that;
        return (this.getFlowHistoryId() == null ? other.getFlowHistoryId() == null : this.getFlowHistoryId().equals(other.getFlowHistoryId()))
            && (this.getFlowId() == null ? other.getFlowId() == null : this.getFlowId().equals(other.getFlowId()))
            && (this.getTypeId() == null ? other.getTypeId() == null : this.getTypeId().equals(other.getTypeId()))
            && (this.getFlowTitle() == null ? other.getFlowTitle() == null : this.getFlowTitle().equals(other.getFlowTitle()))
            && (this.getCurrentStepId() == null ? other.getCurrentStepId() == null : this.getCurrentStepId().equals(other.getCurrentStepId()))
            && (this.getRunId() == null ? other.getRunId() == null : this.getRunId().equals(other.getRunId()))
            && (this.getCurrentUserId() == null ? other.getCurrentUserId() == null : this.getCurrentUserId().equals(other.getCurrentUserId()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getTotalLevel() == null ? other.getTotalLevel() == null : this.getTotalLevel().equals(other.getTotalLevel()))
            && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getFlowStatus() == null ? other.getFlowStatus() == null : this.getFlowStatus().equals(other.getFlowStatus()))
            && (this.getFlowFeedback() == null ? other.getFlowFeedback() == null : this.getFlowFeedback().equals(other.getFlowFeedback()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getFlowHistoryId() == null) ? 0 : getFlowHistoryId().hashCode());
        result = prime * result + ((getFlowId() == null) ? 0 : getFlowId().hashCode());
        result = prime * result + ((getTypeId() == null) ? 0 : getTypeId().hashCode());
        result = prime * result + ((getFlowTitle() == null) ? 0 : getFlowTitle().hashCode());
        result = prime * result + ((getCurrentStepId() == null) ? 0 : getCurrentStepId().hashCode());
        result = prime * result + ((getRunId() == null) ? 0 : getRunId().hashCode());
        result = prime * result + ((getCurrentUserId() == null) ? 0 : getCurrentUserId().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getTotalLevel() == null) ? 0 : getTotalLevel().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getFlowStatus() == null) ? 0 : getFlowStatus().hashCode());
        result = prime * result + ((getFlowFeedback() == null) ? 0 : getFlowFeedback().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", flowHistoryId=").append(flowHistoryId);
        sb.append(", flowId=").append(flowId);
        sb.append(", typeId=").append(typeId);
        sb.append(", flowTitle=").append(flowTitle);
        sb.append(", currentStepId=").append(currentStepId);
        sb.append(", runId=").append(runId);
        sb.append(", currentUserId=").append(currentUserId);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", totalLevel=").append(totalLevel);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", flowStatus=").append(flowStatus);
        sb.append(", flowFeedback=").append(flowFeedback);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}