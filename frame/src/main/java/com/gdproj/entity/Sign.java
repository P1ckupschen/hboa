package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

/**
 * 考勤

 * @TableName sys_sign
 */
@TableName(value ="sys_sign")
public class Sign implements Serializable {
    /**
     *
     */
    @TableId(value = "sign_id", type = IdType.AUTO)
    private Integer signId;

    /**
     *
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     *
     */
    @TableField(value = "in_time")
    private Date inTime;

    /**
     *
     */
    @TableField(value = "sign_ip")
    private String signIp;

    /**
     *
     */
    @TableField(value = "sign_addr")
    private String signAddr;

    /**
     *
     */
    @TableField(value = "end_time")
    private Date endTime;

    /**
     *
     */
    @TableField(value = "sign_status")
    private Integer signStatus;

    /**
     *
     */
    @TableField(value = "work_time")
    private Integer workTime;

    /**
     *
     */
    @TableField(value = "t_work_time")
    private Integer tWorkTime;

    /**
     *
     */
    @TableField(value = "is_late")
    private Integer isLate;

    /**
     *
     */
    @TableField(value = "is_out")
    private Integer isOut;

    /**
     *
     */
    @TableField(value = "is_away")
    private Integer isAway;

    /**
     *
     */
    @TableField(value = "is_early")
    private Integer isEarly;

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
    public Integer getSignId() {
        return signId;
    }

    /**
     *
     */
    public void setSignId(Integer signId) {
        this.signId = signId;
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
    public String getSignIp() {
        return signIp;
    }

    /**
     *
     */
    public void setSignIp(String signIp) {
        this.signIp = signIp;
    }

    /**
     *
     */
    public String getSignAddr() {
        return signAddr;
    }

    /**
     *
     */
    public void setSignAddr(String signAddr) {
        this.signAddr = signAddr;
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
    public Integer getSignStatus() {
        return signStatus;
    }

    /**
     *
     */
    public void setSignStatus(Integer signStatus) {
        this.signStatus = signStatus;
    }

    /**
     *
     */
    public Integer getWorkTime() {
        return workTime;
    }

    /**
     *
     */
    public void setWorkTime(Integer workTime) {
        this.workTime = workTime;
    }

    /**
     *
     */
    public Integer gettWorkTime() {
        return tWorkTime;
    }

    /**
     *
     */
    public void settWorkTime(Integer tWorkTime) {
        this.tWorkTime = tWorkTime;
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
    public Integer getIsOut() {
        return isOut;
    }

    /**
     *
     */
    public void setIsOut(Integer isOut) {
        this.isOut = isOut;
    }

    /**
     *
     */
    public Integer getIsAway() {
        return isAway;
    }

    /**
     *
     */
    public void setIsAway(Integer isAway) {
        this.isAway = isAway;
    }

    /**
     *
     */
    public Integer getIsEarly() {
        return isEarly;
    }

    /**
     *
     */
    public void setIsEarly(Integer isEarly) {
        this.isEarly = isEarly;
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
        Sign other = (Sign) that;
        return (this.getSignId() == null ? other.getSignId() == null : this.getSignId().equals(other.getSignId()))
                && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
                && (this.getInTime() == null ? other.getInTime() == null : this.getInTime().equals(other.getInTime()))
                && (this.getSignIp() == null ? other.getSignIp() == null : this.getSignIp().equals(other.getSignIp()))
                && (this.getSignAddr() == null ? other.getSignAddr() == null : this.getSignAddr().equals(other.getSignAddr()))
                && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
                && (this.getSignStatus() == null ? other.getSignStatus() == null : this.getSignStatus().equals(other.getSignStatus()))
                && (this.getWorkTime() == null ? other.getWorkTime() == null : this.getWorkTime().equals(other.getWorkTime()))
                && (this.gettWorkTime() == null ? other.gettWorkTime() == null : this.gettWorkTime().equals(other.gettWorkTime()))
                && (this.getIsLate() == null ? other.getIsLate() == null : this.getIsLate().equals(other.getIsLate()))
                && (this.getIsOut() == null ? other.getIsOut() == null : this.getIsOut().equals(other.getIsOut()))
                && (this.getIsAway() == null ? other.getIsAway() == null : this.getIsAway().equals(other.getIsAway()))
                && (this.getIsEarly() == null ? other.getIsEarly() == null : this.getIsEarly().equals(other.getIsEarly()))
                && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSignId() == null) ? 0 : getSignId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getInTime() == null) ? 0 : getInTime().hashCode());
        result = prime * result + ((getSignIp() == null) ? 0 : getSignIp().hashCode());
        result = prime * result + ((getSignAddr() == null) ? 0 : getSignAddr().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        result = prime * result + ((getSignStatus() == null) ? 0 : getSignStatus().hashCode());
        result = prime * result + ((getWorkTime() == null) ? 0 : getWorkTime().hashCode());
        result = prime * result + ((gettWorkTime() == null) ? 0 : gettWorkTime().hashCode());
        result = prime * result + ((getIsLate() == null) ? 0 : getIsLate().hashCode());
        result = prime * result + ((getIsOut() == null) ? 0 : getIsOut().hashCode());
        result = prime * result + ((getIsAway() == null) ? 0 : getIsAway().hashCode());
        result = prime * result + ((getIsEarly() == null) ? 0 : getIsEarly().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", signId=").append(signId);
        sb.append(", userId=").append(userId);
        sb.append(", inTime=").append(inTime);
        sb.append(", signIp=").append(signIp);
        sb.append(", signAddr=").append(signAddr);
        sb.append(", endTime=").append(endTime);
        sb.append(", signStatus=").append(signStatus);
        sb.append(", workTime=").append(workTime);
        sb.append(", tWorkTime=").append(tWorkTime);
        sb.append(", isLate=").append(isLate);
        sb.append(", isOut=").append(isOut);
        sb.append(", isAway=").append(isAway);
        sb.append(", isEarly=").append(isEarly);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}