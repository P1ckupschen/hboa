package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.gdproj.vo.FileVo;
import com.gdproj.vo.stockSelectVo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @TableName sys_project
 */
@TableName(value ="sys_project" ,autoResultMap = true)
public class Project implements Serializable {
    /**
     *
     */
    @TableId(value = "project_id", type = IdType.AUTO)
    private Integer projectId;

    /**
     *
     */
    @TableField(value = "project_order_id")
    private Integer projectOrderId;

    /**
     *
     */
    @TableField(value = "project_name")
    private String projectName;

    /**
     *
     */
    @TableField(value = "start_time")
    private Date startTime;

    /**
     * 负责人
     */
    @TableField(value = "supervisor_id")
    private Integer supervisorId;

    /**
     *
     */
    @TableField(value = "project_urgency")
    private String projectUrgency;

    /**
     * 监控人
     */
    @TableField(value = "monitor_id")
    private Integer monitorId;

    /**
     * 审批人
     */
    @TableField(value = "examiner_id")
    private Integer examinerId;

    /**
     * json人员
     */
    @TableField(value = "project_team" , typeHandler = JacksonTypeHandler.class)
    private List<Integer> projectTeam;

    /**
     *
     */
    @TableField(value = "project_description")
    private String projectDescription;

    /**
     *
     */
    @TableField(value = "project_attachments", typeHandler = JacksonTypeHandler.class)
    private List<FileVo> projectAttachments;

    /**
     *
     */
    @TableField(value = "created_time" , fill = FieldFill.INSERT)
    private Date createdTime;

    /**
     *
     */
    @TableField(value = "created_user")
    private Integer createdUser;

    /**
     *
     */
    @TableField(value = "project_status")
    private Integer projectStatus;

    /**
     *
     */
    @TableField(value = "end_time")
    private Date endTime;

    /**
     * 进度
     */
    @TableField(value = "project_process")
    private Integer projectProcess;

    /**
     *
     */
    @TableField(value = "contract_id")
    private Integer contractId;

    /**
     * json存客户信息
     */
    @TableField(value = "project_client")
    private Integer projectClient;

    /**
     *
     */
    @TableField(value = "project_amount")
    private BigDecimal projectAmount;

    /**
     *
     */
    @TableField(value = "is_completed")
    private Integer isCompleted;

    /**
     *
     */
    @TableField(value = "warranty_year")
    private String warrantyYear;

    /**
     *
     */
    @TableField(value = "warranty_amount")
    private BigDecimal warrantyAmount;

    /**
     *
     */
    @TableField(value = "project_pic" ,typeHandler = JacksonTypeHandler.class)
    private List<FileVo> projectPic;

    /**
     *
     */
    @TableField(value = "completed_time")
    private Date completedTime;

    /**
     * 完成的凭证
     */
    @TableField(value = "completed_voucher" ,typeHandler = JacksonTypeHandler.class)
    private List<FileVo> completedVoucher;

    /**
     *
     */
    @TableField(value = "is_deleted")
    @TableLogic
    private Integer isDeleted;

    /**
     * 项目安排
     */
    @TableField(value = "project_arrangement")
    private String projectArrangement;

    /**
     *
     */
    @TableField(value = "category_id")
    private Integer categoryId;

    /**
     *
     */
    @TableField(value = "update_time" , fill =  FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 物料清单
     */
    @TableField(value = "material_bill" , typeHandler = JacksonTypeHandler.class)
    private List<stockSelectVo> materialBill;
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

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
     *
     */
    public Integer getProjectOrderId() {
        return projectOrderId;
    }

    /**
     *
     */
    public void setProjectOrderId(Integer projectOrderId) {
        this.projectOrderId = projectOrderId;
    }

    /**
     *
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     *
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
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
     * 负责人
     */
    public Integer getSupervisorId() {
        return supervisorId;
    }

    /**
     * 负责人
     */
    public void setSupervisorId(Integer supervisorId) {
        this.supervisorId = supervisorId;
    }

    /**
     *
     */
    public String getProjectUrgency() {
        return projectUrgency;
    }

    /**
     *
     */
    public void setProjectUrgency(String projectUrgency) {
        this.projectUrgency = projectUrgency;
    }

    /**
     * 监控人
     */
    public Integer getMonitorId() {
        return monitorId;
    }

    /**
     * 监控人
     */
    public void setMonitorId(Integer monitorId) {
        this.monitorId = monitorId;
    }

    /**
     * 审批人
     */
    public Integer getExaminerId() {
        return examinerId;
    }

    /**
     * 审批人
     */
    public void setExaminerId(Integer examinerId) {
        this.examinerId = examinerId;
    }

    /**
     * json人员
     */
    public List<Integer> getProjectTeam() {
        return projectTeam;
    }

    /**
     * json人员
     */
    public void setProjectTeam(List<Integer> projectTeam) {
        this.projectTeam = projectTeam;
    }

    /**
     *
     */
    public String getProjectDescription() {
        return projectDescription;
    }

    /**
     *
     */
    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    /**
     *
     */
    public List<FileVo> getProjectAttachments() {
        return projectAttachments;
    }

    /**
     *
     */
    public void setProjectAttachments(List<FileVo> projectAttachments) {
        this.projectAttachments = projectAttachments;
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
    public Integer getCreatedUser() {
        return createdUser;
    }

    /**
     *
     */
    public void setCreatedUser(Integer createdUser) {
        this.createdUser = createdUser;
    }

    /**
     *
     */
    public Integer getProjectStatus() {
        return projectStatus;
    }

    /**
     *
     */
    public void setProjectStatus(Integer projectStatus) {
        this.projectStatus = projectStatus;
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
     * 进度
     */
    public Integer getProjectProcess() {
        return projectProcess;
    }

    /**
     * 进度
     */
    public void setProjectProcess(Integer projectProcess) {
        this.projectProcess = projectProcess;
    }

    /**
     *
     */
    public Integer getContractId() {
        return contractId;
    }

    /**
     *
     */
    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    /**
     * json存客户信息
     */
    public Integer getProjectClient() {
        return projectClient;
    }

    /**
     * json存客户信息
     */
    public void setProjectClient(Integer projectClient) {
        this.projectClient = projectClient;
    }

    /**
     *
     */
    public BigDecimal getProjectAmount() {
        return projectAmount;
    }

    /**
     *
     */
    public void setProjectAmount(BigDecimal projectAmount) {
        this.projectAmount = projectAmount;
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
    public String getWarrantyYear() {
        return warrantyYear;
    }

    /**
     *
     */
    public void setWarrantyYear(String warrantyYear) {
        this.warrantyYear = warrantyYear;
    }

    /**
     *
     */
    public BigDecimal getWarrantyAmount() {
        return warrantyAmount;
    }

    /**
     *
     */
    public void setWarrantyAmount(BigDecimal warrantyAmount) {
        this.warrantyAmount = warrantyAmount;
    }

    /**
     *
     */
    public List<FileVo> getProjectPic() {
        return projectPic;
    }

    /**
     *
     */
    public void setProjectPic(List<FileVo> projectPic) {
        this.projectPic = projectPic;
    }

    /**
     *
     */
    public Date getCompletedTime() {
        return completedTime;
    }

    /**
     *
     */
    public void setCompletedTime(Date completedTime) {
        this.completedTime = completedTime;
    }

    /**
     * 完成的凭证
     */
    public List<FileVo> getCompletedVoucher() {
        return completedVoucher;
    }

    /**
     * 完成的凭证
     */
    public void setCompletedVoucher(List<FileVo> completedVoucher) {
        this.completedVoucher = completedVoucher;
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
     * 项目安排
     */
    public String getProjectArrangement() {
        return projectArrangement;
    }

    /**
     * 项目安排
     */
    public void setProjectArrangement(String projectArrangement) {
        this.projectArrangement = projectArrangement;
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
     * 物料清单
     */
    public List<stockSelectVo> getMaterialBill() {
        return materialBill;
    }

    /**
     * 物料清单
     */
    public void setMaterialBill(List<stockSelectVo> materialBill) {
        this.materialBill = materialBill;
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
        Project other = (Project) that;
        return (this.getProjectId() == null ? other.getProjectId() == null : this.getProjectId().equals(other.getProjectId()))
                && (this.getProjectOrderId() == null ? other.getProjectOrderId() == null : this.getProjectOrderId().equals(other.getProjectOrderId()))
                && (this.getProjectName() == null ? other.getProjectName() == null : this.getProjectName().equals(other.getProjectName()))
                && (this.getStartTime() == null ? other.getStartTime() == null : this.getStartTime().equals(other.getStartTime()))
                && (this.getSupervisorId() == null ? other.getSupervisorId() == null : this.getSupervisorId().equals(other.getSupervisorId()))
                && (this.getProjectUrgency() == null ? other.getProjectUrgency() == null : this.getProjectUrgency().equals(other.getProjectUrgency()))
                && (this.getMonitorId() == null ? other.getMonitorId() == null : this.getMonitorId().equals(other.getMonitorId()))
                && (this.getExaminerId() == null ? other.getExaminerId() == null : this.getExaminerId().equals(other.getExaminerId()))
                && (this.getProjectTeam() == null ? other.getProjectTeam() == null : this.getProjectTeam().equals(other.getProjectTeam()))
                && (this.getProjectDescription() == null ? other.getProjectDescription() == null : this.getProjectDescription().equals(other.getProjectDescription()))
                && (this.getProjectAttachments() == null ? other.getProjectAttachments() == null : this.getProjectAttachments().equals(other.getProjectAttachments()))
                && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
                && (this.getCreatedUser() == null ? other.getCreatedUser() == null : this.getCreatedUser().equals(other.getCreatedUser()))
                && (this.getProjectStatus() == null ? other.getProjectStatus() == null : this.getProjectStatus().equals(other.getProjectStatus()))
                && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
                && (this.getProjectProcess() == null ? other.getProjectProcess() == null : this.getProjectProcess().equals(other.getProjectProcess()))
                && (this.getContractId() == null ? other.getContractId() == null : this.getContractId().equals(other.getContractId()))
                && (this.getProjectClient() == null ? other.getProjectClient() == null : this.getProjectClient().equals(other.getProjectClient()))
                && (this.getProjectAmount() == null ? other.getProjectAmount() == null : this.getProjectAmount().equals(other.getProjectAmount()))
                && (this.getIsCompleted() == null ? other.getIsCompleted() == null : this.getIsCompleted().equals(other.getIsCompleted()))
                && (this.getWarrantyYear() == null ? other.getWarrantyYear() == null : this.getWarrantyYear().equals(other.getWarrantyYear()))
                && (this.getWarrantyAmount() == null ? other.getWarrantyAmount() == null : this.getWarrantyAmount().equals(other.getWarrantyAmount()))
                && (this.getProjectPic() == null ? other.getProjectPic() == null : this.getProjectPic().equals(other.getProjectPic()))
                && (this.getCompletedTime() == null ? other.getCompletedTime() == null : this.getCompletedTime().equals(other.getCompletedTime()))
                && (this.getCompletedVoucher() == null ? other.getCompletedVoucher() == null : this.getCompletedVoucher().equals(other.getCompletedVoucher()))
                && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
                && (this.getProjectArrangement() == null ? other.getProjectArrangement() == null : this.getProjectArrangement().equals(other.getProjectArrangement()))
                && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
                && (this.getMaterialBill() == null ? other.getMaterialBill() == null : this.getMaterialBill().equals(other.getMaterialBill()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getProjectId() == null) ? 0 : getProjectId().hashCode());
        result = prime * result + ((getProjectOrderId() == null) ? 0 : getProjectOrderId().hashCode());
        result = prime * result + ((getProjectName() == null) ? 0 : getProjectName().hashCode());
        result = prime * result + ((getStartTime() == null) ? 0 : getStartTime().hashCode());
        result = prime * result + ((getSupervisorId() == null) ? 0 : getSupervisorId().hashCode());
        result = prime * result + ((getProjectUrgency() == null) ? 0 : getProjectUrgency().hashCode());
        result = prime * result + ((getMonitorId() == null) ? 0 : getMonitorId().hashCode());
        result = prime * result + ((getExaminerId() == null) ? 0 : getExaminerId().hashCode());
        result = prime * result + ((getProjectTeam() == null) ? 0 : getProjectTeam().hashCode());
        result = prime * result + ((getProjectDescription() == null) ? 0 : getProjectDescription().hashCode());
        result = prime * result + ((getProjectAttachments() == null) ? 0 : getProjectAttachments().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getCreatedUser() == null) ? 0 : getCreatedUser().hashCode());
        result = prime * result + ((getProjectStatus() == null) ? 0 : getProjectStatus().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        result = prime * result + ((getProjectProcess() == null) ? 0 : getProjectProcess().hashCode());
        result = prime * result + ((getContractId() == null) ? 0 : getContractId().hashCode());
        result = prime * result + ((getProjectClient() == null) ? 0 : getProjectClient().hashCode());
        result = prime * result + ((getProjectAmount() == null) ? 0 : getProjectAmount().hashCode());
        result = prime * result + ((getIsCompleted() == null) ? 0 : getIsCompleted().hashCode());
        result = prime * result + ((getWarrantyYear() == null) ? 0 : getWarrantyYear().hashCode());
        result = prime * result + ((getWarrantyAmount() == null) ? 0 : getWarrantyAmount().hashCode());
        result = prime * result + ((getProjectPic() == null) ? 0 : getProjectPic().hashCode());
        result = prime * result + ((getCompletedTime() == null) ? 0 : getCompletedTime().hashCode());
        result = prime * result + ((getCompletedVoucher() == null) ? 0 : getCompletedVoucher().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getProjectArrangement() == null) ? 0 : getProjectArrangement().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getMaterialBill() == null) ? 0 : getMaterialBill().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", projectId=").append(projectId);
        sb.append(", projectOrderId=").append(projectOrderId);
        sb.append(", projectName=").append(projectName);
        sb.append(", startTime=").append(startTime);
        sb.append(", supervisorId=").append(supervisorId);
        sb.append(", projectUrgency=").append(projectUrgency);
        sb.append(", monitorId=").append(monitorId);
        sb.append(", examinerId=").append(examinerId);
        sb.append(", projectTeam=").append(projectTeam);
        sb.append(", projectDescription=").append(projectDescription);
        sb.append(", projectAttachments=").append(projectAttachments);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", createdUser=").append(createdUser);
        sb.append(", projectStatus=").append(projectStatus);
        sb.append(", endTime=").append(endTime);
        sb.append(", projectProcess=").append(projectProcess);
        sb.append(", contractId=").append(contractId);
        sb.append(", projectClient=").append(projectClient);
        sb.append(", projectAmount=").append(projectAmount);
        sb.append(", isCompleted=").append(isCompleted);
        sb.append(", warrantyYear=").append(warrantyYear);
        sb.append(", warrantyAmount=").append(warrantyAmount);
        sb.append(", projectPic=").append(projectPic);
        sb.append(", completedTime=").append(completedTime);
        sb.append(", completedVoucher=").append(completedVoucher);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", projectArrangement=").append(projectArrangement);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", materialBill=").append(materialBill);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}