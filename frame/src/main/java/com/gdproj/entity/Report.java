package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.gdproj.handler.jsonAndListTypeHandler;
import com.gdproj.vo.FileVo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @TableName sys_report
 */
@TableName(value ="sys_report", autoResultMap = true)
public class Report implements Serializable {
    /**
     * 
     */
    @TableId(value = "report_id", type = IdType.AUTO)
    private Integer reportId;

    /**
     * 
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 
     */
    @TableField(value = "created_time")
    private Date createdTime;

    /**
     * 
     */
    @TableField(value = "category_id")
    private Integer categoryId;

    /**
     * 
     */
    @TableField(value = "report_content")
    private String reportContent;

    /**
     * 
     */
    @TableField(value = "report_next_stage")
    private String reportNextStage;

    /**
     * 
     */
    @TableField(value = "report_pic",typeHandler = jsonAndListTypeHandler.class)
    private List<FileVo> reportPic;

    /**
     * 
     */
    @TableField(value = "report_file",typeHandler = jsonAndListTypeHandler.class)
    private List<FileVo> reportFile;

    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }

    @TableField(value="feed_back")
    private String feedBack;

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
    public Integer getReportId() {
        return reportId;
    }

    /**
     * 
     */
    public void setReportId(Integer reportId) {
        this.reportId = reportId;
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
    public String getReportContent() {
        return reportContent;
    }

    /**
     * 
     */
    public void setReportContent(String reportContent) {
        this.reportContent = reportContent;
    }

    /**
     * 
     */
    public String getReportNextStage() {
        return reportNextStage;
    }

    /**
     * 
     */
    public void setReportNextStage(String reportNextStage) {
        this.reportNextStage = reportNextStage;
    }

    /**
     * 
     */
    public List<FileVo> getReportPic() {
        return reportPic;
    }

    /**
     * 
     */
    public void setReportPic(List<FileVo> reportPic) {
        this.reportPic = reportPic;
    }

    /**
     * 
     */
    public List<FileVo> getReportFile() {
        return reportFile;
    }

    /**
     * 
     */
    public void setReportFile(List<FileVo> reportFile) {
        this.reportFile = reportFile;
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
        Report other = (Report) that;
        return (this.getReportId() == null ? other.getReportId() == null : this.getReportId().equals(other.getReportId()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
            && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
            && (this.getReportContent() == null ? other.getReportContent() == null : this.getReportContent().equals(other.getReportContent()))
            && (this.getReportNextStage() == null ? other.getReportNextStage() == null : this.getReportNextStage().equals(other.getReportNextStage()))
            && (this.getReportPic() == null ? other.getReportPic() == null : this.getReportPic().equals(other.getReportPic()))
            && (this.getReportFile() == null ? other.getReportFile() == null : this.getReportFile().equals(other.getReportFile()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getReportId() == null) ? 0 : getReportId().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getReportContent() == null) ? 0 : getReportContent().hashCode());
        result = prime * result + ((getReportNextStage() == null) ? 0 : getReportNextStage().hashCode());
        result = prime * result + ((getReportPic() == null) ? 0 : getReportPic().hashCode());
        result = prime * result + ((getReportFile() == null) ? 0 : getReportFile().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", reportId=").append(reportId);
        sb.append(", userId=").append(userId);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", reportContent=").append(reportContent);
        sb.append(", reportNextStage=").append(reportNextStage);
        sb.append(", reportPic=").append(reportPic);
        sb.append(", reportFile=").append(reportFile);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}