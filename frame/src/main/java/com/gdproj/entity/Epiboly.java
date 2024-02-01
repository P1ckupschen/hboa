package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.gdproj.vo.ContactVo;
import com.gdproj.vo.FileVo;
import com.gdproj.vo.stockSelectVo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 
 * @TableName sys_epiboly
 */
@TableName(value ="sys_epiboly",autoResultMap = true)
public class Epiboly implements Serializable {
    /**
     * 
     */
    @TableId(value = "epiboly_id", type = IdType.AUTO)
    private Integer epibolyId;

    /**
     * 
     */
    @TableField(value = "epiboly_order_id")
    private Integer epibolyOrderId;

    /**
     * 
     */
    @TableField(value = "epiboly_name")
    private String epibolyName;

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
    @TableField(value = "epiboly_team",typeHandler = JacksonTypeHandler.class)
    private List epibolyTeam;

    /**
     * 
     */
    @TableField(value = "epiboly_description")
    private String epibolyDescription;

    /**
     * 
     */
    @TableField(value = "epiboly_attachments",typeHandler = JacksonTypeHandler.class)
    private List<FileVo> epibolyAttachments;

    /**
     * 
     */
    @TableField(value = "created_time", fill = FieldFill.INSERT)
    private Date createdTime;

    /**
     * 
     */
    @TableField(value = "created_user")
    private Integer createdUser;

    /**
     * 
     */
    @TableField(value = "epiboly_status")
    private Integer epibolyStatus;

    /**
     * 
     */
    @TableField(value = "end_time")
    private Date endTime;

    /**
     * 进度
     */
    @TableField(value = "epiboly_process")
    private String epibolyProcess;

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    /**
     * 
     */
    @TableField(value = "contract_id")
    private Integer contractId;

    /**
     * json存客户信息
     */
    @TableField(value = "epiboly_client")
    private Integer epibolyClient;

    /**
     * 
     */
    @TableField(value = "epiboly_amount")
    private BigDecimal epibolyAmount;

    /**
     * 
     */
    @TableField(value = "is_completed")
    private Integer isCompleted;

    /**
     * 
     */
    @TableField(value = "epiboly_pic",typeHandler = JacksonTypeHandler.class)
    private List<FileVo> epibolyPic;

    /**
     * 
     */
    @TableField(value = "completed_time")
    private Date completedTime;

    /**
     * 完成的凭证
     */
    @TableField(value = "completed_voucher",typeHandler = JacksonTypeHandler.class)
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
    @TableField(value = "epiboly_arrangement",typeHandler = JacksonTypeHandler.class)
    private List epibolyArrangement;

    /**
     * 
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 材料清单
     */
    @TableField(value = "material_bill",typeHandler = JacksonTypeHandler.class)
    private List<stockSelectVo> materialBill;

    /**
     * 
     */
    @TableField(value = "project_id")
    private Integer projectId;

    /**
     * 
     */
    @TableField(value = "epiboly_company")
    private String epibolyCompany;

    /**
     * 联系人
     */
    @TableField(value = "epiboly_contacts",typeHandler = JacksonTypeHandler.class)
    private List<ContactVo> epibolyContacts;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getEpibolyId() {
        return epibolyId;
    }

    /**
     * 
     */
    public void setEpibolyId(Integer epibolyId) {
        this.epibolyId = epibolyId;
    }

    /**
     * 
     */
    public Integer getEpibolyOrderId() {
        return epibolyOrderId;
    }

    /**
     * 
     */
    public void setEpibolyOrderId(Integer epibolyOrderId) {
        this.epibolyOrderId = epibolyOrderId;
    }

    /**
     * 
     */
    public String getEpibolyName() {
        return epibolyName;
    }

    /**
     * 
     */
    public void setEpibolyName(String epibolyName) {
        this.epibolyName = epibolyName;
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
    public List getEpibolyTeam() {
        return epibolyTeam;
    }

    /**
     * json人员
     */
    public void setEpibolyTeam(List epibolyTeam) {
        this.epibolyTeam = epibolyTeam;
    }

    /**
     * 
     */
    public String getEpibolyDescription() {
        return epibolyDescription;
    }

    /**
     * 
     */
    public void setEpibolyDescription(String epibolyDescription) {
        this.epibolyDescription = epibolyDescription;
    }

    /**
     * 
     */
    public List<FileVo> getEpibolyAttachments() {
        return epibolyAttachments;
    }

    /**
     * 
     */
    public void setEpibolyAttachments(List<FileVo> epibolyAttachments) {
        this.epibolyAttachments = epibolyAttachments;
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
    public Integer getEpibolyStatus() {
        return epibolyStatus;
    }

    /**
     * 
     */
    public void setEpibolyStatus(Integer epibolyStatus) {
        this.epibolyStatus = epibolyStatus;
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
    public String getEpibolyProcess() {
        return epibolyProcess;
    }

    /**
     * 进度
     */
    public void setEpibolyProcess(String epibolyProcess) {
        this.epibolyProcess = epibolyProcess;
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

    /**
     * json存客户信息
     */
    public Integer getEpibolyClient() {
        return epibolyClient;
    }

    /**
     * json存客户信息
     */
    public void setEpibolyClient(Integer epibolyClient) {
        this.epibolyClient = epibolyClient;
    }

    /**
     * 
     */
    public BigDecimal getEpibolyAmount() {
        return epibolyAmount;
    }

    /**
     * 
     */
    public void setEpibolyAmount(BigDecimal epibolyAmount) {
        this.epibolyAmount = epibolyAmount;
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
    public List<FileVo> getEpibolyPic() {
        return epibolyPic;
    }

    /**
     * 
     */
    public void setEpibolyPic(List<FileVo> epibolyPic) {
        this.epibolyPic = epibolyPic;
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
    public List getEpibolyArrangement() {
        return epibolyArrangement;
    }

    /**
     * 项目安排
     */
    public void setEpibolyArrangement(List epibolyArrangement) {
        this.epibolyArrangement = epibolyArrangement;
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
     * 材料清单
     */
    public List<stockSelectVo> getMaterialBill() {
        return materialBill;
    }

    /**
     * 材料清单
     */
    public void setMaterialBill(List<stockSelectVo> materialBill) {
        this.materialBill = materialBill;
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
     * 
     */
    public String getEpibolyCompany() {
        return epibolyCompany;
    }

    /**
     * 
     */
    public void setEpibolyCompany(String epibolyCompany) {
        this.epibolyCompany = epibolyCompany;
    }

    /**
     * 联系人
     */
    public List<ContactVo> getEpibolyContacts() {
        return epibolyContacts;
    }

    /**
     * 联系人
     */
    public void setEpibolyContacts(List<ContactVo> epibolContacts) {
        this.epibolyContacts = epibolContacts;
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
        Epiboly other = (Epiboly) that;
        return (this.getEpibolyId() == null ? other.getEpibolyId() == null : this.getEpibolyId().equals(other.getEpibolyId()))
            && (this.getEpibolyOrderId() == null ? other.getEpibolyOrderId() == null : this.getEpibolyOrderId().equals(other.getEpibolyOrderId()))
            && (this.getEpibolyName() == null ? other.getEpibolyName() == null : this.getEpibolyName().equals(other.getEpibolyName()))
            && (this.getStartTime() == null ? other.getStartTime() == null : this.getStartTime().equals(other.getStartTime()))
            && (this.getSupervisorId() == null ? other.getSupervisorId() == null : this.getSupervisorId().equals(other.getSupervisorId()))
            && (this.getMonitorId() == null ? other.getMonitorId() == null : this.getMonitorId().equals(other.getMonitorId()))
            && (this.getExaminerId() == null ? other.getExaminerId() == null : this.getExaminerId().equals(other.getExaminerId()))
            && (this.getEpibolyTeam() == null ? other.getEpibolyTeam() == null : this.getEpibolyTeam().equals(other.getEpibolyTeam()))
            && (this.getEpibolyDescription() == null ? other.getEpibolyDescription() == null : this.getEpibolyDescription().equals(other.getEpibolyDescription()))
            && (this.getEpibolyAttachments() == null ? other.getEpibolyAttachments() == null : this.getEpibolyAttachments().equals(other.getEpibolyAttachments()))
            && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
            && (this.getCreatedUser() == null ? other.getCreatedUser() == null : this.getCreatedUser().equals(other.getCreatedUser()))
            && (this.getEpibolyStatus() == null ? other.getEpibolyStatus() == null : this.getEpibolyStatus().equals(other.getEpibolyStatus()))
            && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
            && (this.getEpibolyProcess() == null ? other.getEpibolyProcess() == null : this.getEpibolyProcess().equals(other.getEpibolyProcess()))
            && (this.getContractId() == null ? other.getContractId() == null : this.getContractId().equals(other.getContractId()))
            && (this.getEpibolyClient() == null ? other.getEpibolyClient() == null : this.getEpibolyClient().equals(other.getEpibolyClient()))
            && (this.getEpibolyAmount() == null ? other.getEpibolyAmount() == null : this.getEpibolyAmount().equals(other.getEpibolyAmount()))
            && (this.getIsCompleted() == null ? other.getIsCompleted() == null : this.getIsCompleted().equals(other.getIsCompleted()))
            && (this.getEpibolyPic() == null ? other.getEpibolyPic() == null : this.getEpibolyPic().equals(other.getEpibolyPic()))
            && (this.getCompletedTime() == null ? other.getCompletedTime() == null : this.getCompletedTime().equals(other.getCompletedTime()))
            && (this.getCompletedVoucher() == null ? other.getCompletedVoucher() == null : this.getCompletedVoucher().equals(other.getCompletedVoucher()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getEpibolyArrangement() == null ? other.getEpibolyArrangement() == null : this.getEpibolyArrangement().equals(other.getEpibolyArrangement()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getMaterialBill() == null ? other.getMaterialBill() == null : this.getMaterialBill().equals(other.getMaterialBill()))
            && (this.getProjectId() == null ? other.getProjectId() == null : this.getProjectId().equals(other.getProjectId()))
            && (this.getEpibolyCompany() == null ? other.getEpibolyCompany() == null : this.getEpibolyCompany().equals(other.getEpibolyCompany()))
            && (this.getEpibolyContacts() == null ? other.getEpibolyContacts() == null : this.getEpibolyContacts().equals(other.getEpibolyContacts()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getEpibolyId() == null) ? 0 : getEpibolyId().hashCode());
        result = prime * result + ((getEpibolyOrderId() == null) ? 0 : getEpibolyOrderId().hashCode());
        result = prime * result + ((getEpibolyName() == null) ? 0 : getEpibolyName().hashCode());
        result = prime * result + ((getStartTime() == null) ? 0 : getStartTime().hashCode());
        result = prime * result + ((getSupervisorId() == null) ? 0 : getSupervisorId().hashCode());
        result = prime * result + ((getMonitorId() == null) ? 0 : getMonitorId().hashCode());
        result = prime * result + ((getExaminerId() == null) ? 0 : getExaminerId().hashCode());
        result = prime * result + ((getEpibolyTeam() == null) ? 0 : getEpibolyTeam().hashCode());
        result = prime * result + ((getEpibolyDescription() == null) ? 0 : getEpibolyDescription().hashCode());
        result = prime * result + ((getEpibolyAttachments() == null) ? 0 : getEpibolyAttachments().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getCreatedUser() == null) ? 0 : getCreatedUser().hashCode());
        result = prime * result + ((getEpibolyStatus() == null) ? 0 : getEpibolyStatus().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        result = prime * result + ((getEpibolyProcess() == null) ? 0 : getEpibolyProcess().hashCode());
        result = prime * result + ((getContractId() == null) ? 0 : getContractId().hashCode());
        result = prime * result + ((getEpibolyClient() == null) ? 0 : getEpibolyClient().hashCode());
        result = prime * result + ((getEpibolyAmount() == null) ? 0 : getEpibolyAmount().hashCode());
        result = prime * result + ((getIsCompleted() == null) ? 0 : getIsCompleted().hashCode());
        result = prime * result + ((getEpibolyPic() == null) ? 0 : getEpibolyPic().hashCode());
        result = prime * result + ((getCompletedTime() == null) ? 0 : getCompletedTime().hashCode());
        result = prime * result + ((getCompletedVoucher() == null) ? 0 : getCompletedVoucher().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getEpibolyArrangement() == null) ? 0 : getEpibolyArrangement().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getMaterialBill() == null) ? 0 : getMaterialBill().hashCode());
        result = prime * result + ((getProjectId() == null) ? 0 : getProjectId().hashCode());
        result = prime * result + ((getEpibolyCompany() == null) ? 0 : getEpibolyCompany().hashCode());
        result = prime * result + ((getEpibolyContacts() == null) ? 0 : getEpibolyContacts().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", epibolyId=").append(epibolyId);
        sb.append(", epibolyOrderId=").append(epibolyOrderId);
        sb.append(", epibolyName=").append(epibolyName);
        sb.append(", startTime=").append(startTime);
        sb.append(", supervisorId=").append(supervisorId);
        sb.append(", monitorId=").append(monitorId);
        sb.append(", examinerId=").append(examinerId);
        sb.append(", epibolyTeam=").append(epibolyTeam);
        sb.append(", epibolyDescription=").append(epibolyDescription);
        sb.append(", epibolyAttachments=").append(epibolyAttachments);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", createdUser=").append(createdUser);
        sb.append(", epibolyStatus=").append(epibolyStatus);
        sb.append(", endTime=").append(endTime);
        sb.append(", epibolyProcess=").append(epibolyProcess);
        sb.append(", contractId=").append(contractId);
        sb.append(", epibolyClient=").append(epibolyClient);
        sb.append(", epibolyAmount=").append(epibolyAmount);
        sb.append(", isCompleted=").append(isCompleted);
        sb.append(", epibolyPic=").append(epibolyPic);
        sb.append(", completedTime=").append(completedTime);
        sb.append(", completedVoucher=").append(completedVoucher);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", epibolyArrangement=").append(epibolyArrangement);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", materialBill=").append(materialBill);
        sb.append(", projectId=").append(projectId);
        sb.append(", epibolyCompany=").append(epibolyCompany);
        sb.append(", epibolContacts=").append(epibolyContacts);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}