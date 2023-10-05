package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.gdproj.handler.jsonAndListTypeHandler;
import com.gdproj.vo.fileVo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * @TableName sys_client
 */
@TableName(value ="sys_client",autoResultMap = true)
public class Client implements Serializable {
    /**
     * 
     */
    @TableId(value = "client_id", type = IdType.AUTO)
    private Integer clientId;

    /**
     * 
     */
    @TableField(value = "client_name")
    private String clientName;

    /**
     * 
     */
    @TableField(value = "client_order_id")
    private Integer clientOrderId;

    /**
     * 客户经理与员工绑定
     */
    @TableField(value = "client_manager")
    private Integer clientManager;

    /**
     * 
     */
    @TableField(value = "client_from")
    private String clientFrom;

    /**
     * 
     */
    @TableField(value = "client_status")
    private Integer clientStatus;

    /**
     * 
     */
    @TableField(value = "categroy_id")
    private Integer categroyId;

    /**
     * 
     */
    @TableField(value = "client_company")
    private String clientCompany;

    /**
     * 
     */
    @TableField(value = "client_location")
    private String clientLocation;

    /**
     * 
     */
    @TableField(value = "client_phone")
    private String clientPhone;

    /**
     * 
     */
    @TableField(value = "client_fax")
    private String clientFax;

    /**
     * 
     */
    @TableField(value = "client_email")
    private String clientEmail;

    /**
     * 
     */
    @TableField(value = "client_addr")
    private String clientAddr;

    /**
     * 
     */
    @TableField(value = "created_time")
    private Date createdTime;

    /**
     * 存json 联系人名 方式
     */
    @TableField(value = "client_contacts",typeHandler = FastjsonTypeHandler.class)
    private List<Map<String,String>> clientContacts;

    /**
     * 
     */
    @TableField(value = "client_description")
    private String clientDescription;

    /**
     * 
     */
    @TableField(value = "created_user")
    private Integer createdUser;

    /**
     * 
     */
    @TableField(value = "is_deleted")
    @TableLogic
    private Integer isDeleted;

    /**
     * 
     */
    @TableField(value = "client_attachments",typeHandler = JacksonTypeHandler.class)
    private List<fileVo> clientAttachments;

    /**
     * 
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 
     */
    @TableField(value = "project_id")
    private Integer projectId;

    /**
     * 
     */
    @TableField(value = "contract_id")
    private Integer contractId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getClientId() {
        return clientId;
    }

    /**
     * 
     */
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    /**
     * 
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * 
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * 
     */
    public Integer getClientOrderId() {
        return clientOrderId;
    }

    /**
     * 
     */
    public void setClientOrderId(Integer clientOrderId) {
        this.clientOrderId = clientOrderId;
    }

    /**
     * 客户经理与员工绑定
     */
    public Integer getClientManager() {
        return clientManager;
    }

    /**
     * 客户经理与员工绑定
     */
    public void setClientManager(Integer clientManager) {
        this.clientManager = clientManager;
    }

    /**
     *
     */
    public String getClientFrom() {
        return clientFrom;
    }

    /**
     *
     */
    public void setClientFrom(String clientFrom) {
        this.clientFrom = clientFrom;
    }

    /**
     *
     */
    public Integer getClientStatus() {
        return clientStatus;
    }

    /**
     *
     */
    public void setClientStatus(Integer clientStatus) {
        this.clientStatus = clientStatus;
    }

    /**
     *
     */
    public Integer getCategroyId() {
        return categroyId;
    }

    /**
     *
     */
    public void setCategroyId(Integer categroyId) {
        this.categroyId = categroyId;
    }

    /**
     *
     */
    public String getClientCompany() {
        return clientCompany;
    }

    /**
     *
     */
    public void setClientCompany(String clientCompany) {
        this.clientCompany = clientCompany;
    }

    /**
     *
     */
    public String getClientLocation() {
        return clientLocation;
    }

    /**
     *
     */
    public void setClientLocation(String clientLocation) {
        this.clientLocation = clientLocation;
    }

    /**
     * 
     */
    public String getClientPhone() {
        return clientPhone;
    }

    /**
     * 
     */
    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    /**
     * 
     */
    public String getClientFax() {
        return clientFax;
    }

    /**
     * 
     */
    public void setClientFax(String clientFax) {
        this.clientFax = clientFax;
    }

    /**
     * 
     */
    public String getClientEmail() {
        return clientEmail;
    }

    /**
     * 
     */
    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    /**
     * 
     */
    public String getClientAddr() {
        return clientAddr;
    }

    /**
     * 
     */
    public void setClientAddr(String clientAddr) {
        this.clientAddr = clientAddr;
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
     * 存json 联系人名 方式
     */
    public List<Map<String,String>> getClientContacts() {
        return clientContacts;
    }

    /**
     * 存json 联系人名 方式
     */
    public void setClientContacts(List<Map<String,String>> clientContacts) {
        this.clientContacts = clientContacts;
    }

    /**
     * 
     */
    public String getClientDescription() {
        return clientDescription;
    }

    /**
     * 
     */
    public void setClientDescription(String clientDescription) {
        this.clientDescription = clientDescription;
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
    public List<fileVo> getClientAttachments() {
        return clientAttachments;
    }

    /**
     * 
     */
    public void setClientAttachments(List<fileVo> clientAttachments) {
        this.clientAttachments = clientAttachments;
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
    public Integer getContractId() {
        return contractId;
    }

    /**
     * 
     */
    public void setContractId(Integer contractId) {
        this.contractId = contractId;
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
        Client other = (Client) that;
        return (this.getClientId() == null ? other.getClientId() == null : this.getClientId().equals(other.getClientId()))
            && (this.getClientName() == null ? other.getClientName() == null : this.getClientName().equals(other.getClientName()))
            && (this.getClientOrderId() == null ? other.getClientOrderId() == null : this.getClientOrderId().equals(other.getClientOrderId()))
            && (this.getClientManager() == null ? other.getClientManager() == null : this.getClientManager().equals(other.getClientManager()))
            && (this.getClientFrom() == null ? other.getClientFrom() == null : this.getClientFrom().equals(other.getClientFrom()))
            && (this.getClientStatus() == null ? other.getClientStatus() == null : this.getClientStatus().equals(other.getClientStatus()))
            && (this.getCategroyId() == null ? other.getCategroyId() == null : this.getCategroyId().equals(other.getCategroyId()))
            && (this.getClientCompany() == null ? other.getClientCompany() == null : this.getClientCompany().equals(other.getClientCompany()))
            && (this.getClientLocation() == null ? other.getClientLocation() == null : this.getClientLocation().equals(other.getClientLocation()))
            && (this.getClientPhone() == null ? other.getClientPhone() == null : this.getClientPhone().equals(other.getClientPhone()))
            && (this.getClientFax() == null ? other.getClientFax() == null : this.getClientFax().equals(other.getClientFax()))
            && (this.getClientEmail() == null ? other.getClientEmail() == null : this.getClientEmail().equals(other.getClientEmail()))
            && (this.getClientAddr() == null ? other.getClientAddr() == null : this.getClientAddr().equals(other.getClientAddr()))
            && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
            && (this.getClientContacts() == null ? other.getClientContacts() == null : this.getClientContacts().equals(other.getClientContacts()))
            && (this.getClientDescription() == null ? other.getClientDescription() == null : this.getClientDescription().equals(other.getClientDescription()))
            && (this.getCreatedUser() == null ? other.getCreatedUser() == null : this.getCreatedUser().equals(other.getCreatedUser()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getClientAttachments() == null ? other.getClientAttachments() == null : this.getClientAttachments().equals(other.getClientAttachments()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getProjectId() == null ? other.getProjectId() == null : this.getProjectId().equals(other.getProjectId()))
            && (this.getContractId() == null ? other.getContractId() == null : this.getContractId().equals(other.getContractId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getClientId() == null) ? 0 : getClientId().hashCode());
        result = prime * result + ((getClientName() == null) ? 0 : getClientName().hashCode());
        result = prime * result + ((getClientOrderId() == null) ? 0 : getClientOrderId().hashCode());
        result = prime * result + ((getClientManager() == null) ? 0 : getClientManager().hashCode());
        result = prime * result + ((getClientFrom() == null) ? 0 : getClientFrom().hashCode());
        result = prime * result + ((getClientStatus() == null) ? 0 : getClientStatus().hashCode());
        result = prime * result + ((getCategroyId() == null) ? 0 : getCategroyId().hashCode());
        result = prime * result + ((getClientCompany() == null) ? 0 : getClientCompany().hashCode());
        result = prime * result + ((getClientLocation() == null) ? 0 : getClientLocation().hashCode());
        result = prime * result + ((getClientPhone() == null) ? 0 : getClientPhone().hashCode());
        result = prime * result + ((getClientFax() == null) ? 0 : getClientFax().hashCode());
        result = prime * result + ((getClientEmail() == null) ? 0 : getClientEmail().hashCode());
        result = prime * result + ((getClientAddr() == null) ? 0 : getClientAddr().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getClientContacts() == null) ? 0 : getClientContacts().hashCode());
        result = prime * result + ((getClientDescription() == null) ? 0 : getClientDescription().hashCode());
        result = prime * result + ((getCreatedUser() == null) ? 0 : getCreatedUser().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getClientAttachments() == null) ? 0 : getClientAttachments().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getProjectId() == null) ? 0 : getProjectId().hashCode());
        result = prime * result + ((getContractId() == null) ? 0 : getContractId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", clientId=").append(clientId);
        sb.append(", clientName=").append(clientName);
        sb.append(", clientOrderId=").append(clientOrderId);
        sb.append(", clientManager=").append(clientManager);
        sb.append(", clientFrom=").append(clientFrom);
        sb.append(", clientStatus=").append(clientStatus);
        sb.append(", categroyId=").append(categroyId);
        sb.append(", clientCompany=").append(clientCompany);
        sb.append(", clientLocation=").append(clientLocation);
        sb.append(", clientPhone=").append(clientPhone);
        sb.append(", clientFax=").append(clientFax);
        sb.append(", clientEmail=").append(clientEmail);
        sb.append(", clientAddr=").append(clientAddr);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", clientContacts=").append(clientContacts);
        sb.append(", clientDescription=").append(clientDescription);
        sb.append(", createdUser=").append(createdUser);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", clientAttachments=").append(clientAttachments);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", projectId=").append(projectId);
        sb.append(", contractId=").append(contractId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}