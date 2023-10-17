package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.gdproj.vo.fileVo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @TableName sys_deployee
 */
@TableName(value ="sys_deployee",autoResultMap = true)
public class Deployee implements Serializable {
    /**
     *
     */
    @TableId(value = "deployee_id", type = IdType.AUTO)
    private Integer deployeeId;

    /**
     *
     */
    @TableField(value = "deployee_name")
    private String deployeeName;

    /**
     *
     */
    @TableField(value = "deployee_job")
    private String deployeeJob;

    /**
     *
     */
    @TableField(value = "deployee_phone")
    private String deployeePhone;

    /**
     *
     */
    @TableField(value = "deployee_addr")
    private String deployeeAddr;

    /**
     *
     */
    @TableField(value = "department_id")
    private Integer departmentId;

    /**
     *
     */
    @TableField(value = "deployee_status")
    private Integer deployeeStatus;

    /**
     *
     */
    @TableField(value = "deployee_role")
    private Integer deployeeRole;

    /**
     *
     */
    @TableField(value = "entry_time")
    private Date entryTime;

    /**
     *
     */
    @TableField(value = "is_contract")
    private Integer isContract;

    /**
     *
     */
    @TableField(value = "is_ensurance")
    private Integer isEnsurance;

    /**
     *
     */
    @TableField(value = "contract",typeHandler = JacksonTypeHandler.class)
    private List<fileVo> contract;

    /**
     *
     */
    @TableField(value = "id_card")
    private String idCard;

    /**
     *
     */
    @TableField(value = "is_deleted")
    @TableLogic
    private Integer isDeleted;

    /**
     *
     */
    @TableField(value = "password")
    private String password;

    /**
     *
     */
    @TableField(value = "username")
    private String username;

    /**
     *
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     *
     */
    @TableField(value = "update_time")
    private Date updateTime;

    /**
     *
     */
    @TableField(value = "ensurance",typeHandler = JacksonTypeHandler.class)
    private List<fileVo> ensurance;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public Integer getDeployeeId() {
        return deployeeId;
    }

    /**
     *
     */
    public void setDeployeeId(Integer deployeeId) {
        this.deployeeId = deployeeId;
    }

    /**
     *
     */
    public String getDeployeeName() {
        return deployeeName;
    }

    /**
     *
     */
    public void setDeployeeName(String deployeeName) {
        this.deployeeName = deployeeName;
    }

    /**
     *
     */
    public String getDeployeeJob() {
        return deployeeJob;
    }

    /**
     *
     */
    public void setDeployeeJob(String deployeeJob) {
        this.deployeeJob = deployeeJob;
    }

    /**
     *
     */
    public String getDeployeePhone() {
        return deployeePhone;
    }

    /**
     *
     */
    public void setDeployeePhone(String deployeePhone) {
        this.deployeePhone = deployeePhone;
    }

    /**
     *
     */
    public String getDeployeeAddr() {
        return deployeeAddr;
    }

    /**
     *
     */
    public void setDeployeeAddr(String deployeeAddr) {
        this.deployeeAddr = deployeeAddr;
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
    public Integer getDeployeeStatus() {
        return deployeeStatus;
    }

    /**
     *
     */
    public void setDeployeeStatus(Integer deployeeStatus) {
        this.deployeeStatus = deployeeStatus;
    }

    /**
     *
     */
    public Integer getDeployeeRole() {
        return deployeeRole;
    }

    /**
     *
     */
    public void setDeployeeRole(Integer deployeeRole) {
        this.deployeeRole = deployeeRole;
    }

    /**
     *
     */
    public Date getEntryTime() {
        return entryTime;
    }

    /**
     *
     */
    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    /**
     *
     */
    public Integer getIsContract() {
        return isContract;
    }

    /**
     *
     */
    public void setIsContract(Integer isContract) {
        this.isContract = isContract;
    }

    /**
     *
     */
    public Integer getIsEnsurance() {
        return isEnsurance;
    }

    /**
     *
     */
    public void setIsEnsurance(Integer isEnsurance) {
        this.isEnsurance = isEnsurance;
    }

    /**
     *
     */
    public List<fileVo> getContract() {
        return contract;
    }

    /**
     *
     */
    public void setContract(List<fileVo> contract) {
        this.contract = contract;
    }

    /**
     *
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     *
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
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
    public String getPassword() {
        return password;
    }

    /**
     *
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     */
    public void setUsername(String username) {
        this.username = username;
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
    public List<fileVo> getEnsurance() {
        return ensurance;
    }

    /**
     *
     */
    public void setEnsurance(List<fileVo> ensurance) {
        this.ensurance = ensurance;
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
        Deployee other = (Deployee) that;
        return (this.getDeployeeId() == null ? other.getDeployeeId() == null : this.getDeployeeId().equals(other.getDeployeeId()))
                && (this.getDeployeeName() == null ? other.getDeployeeName() == null : this.getDeployeeName().equals(other.getDeployeeName()))
                && (this.getDeployeeJob() == null ? other.getDeployeeJob() == null : this.getDeployeeJob().equals(other.getDeployeeJob()))
                && (this.getDeployeePhone() == null ? other.getDeployeePhone() == null : this.getDeployeePhone().equals(other.getDeployeePhone()))
                && (this.getDeployeeAddr() == null ? other.getDeployeeAddr() == null : this.getDeployeeAddr().equals(other.getDeployeeAddr()))
                && (this.getDepartmentId() == null ? other.getDepartmentId() == null : this.getDepartmentId().equals(other.getDepartmentId()))
                && (this.getDeployeeStatus() == null ? other.getDeployeeStatus() == null : this.getDeployeeStatus().equals(other.getDeployeeStatus()))
                && (this.getDeployeeRole() == null ? other.getDeployeeRole() == null : this.getDeployeeRole().equals(other.getDeployeeRole()))
                && (this.getEntryTime() == null ? other.getEntryTime() == null : this.getEntryTime().equals(other.getEntryTime()))
                && (this.getIsContract() == null ? other.getIsContract() == null : this.getIsContract().equals(other.getIsContract()))
                && (this.getIsEnsurance() == null ? other.getIsEnsurance() == null : this.getIsEnsurance().equals(other.getIsEnsurance()))
                && (this.getContract() == null ? other.getContract() == null : this.getContract().equals(other.getContract()))
                && (this.getIdCard() == null ? other.getIdCard() == null : this.getIdCard().equals(other.getIdCard()))
                && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
                && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
                && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
                && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
                && (this.getEnsurance() == null ? other.getEnsurance() == null : this.getEnsurance().equals(other.getEnsurance()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getDeployeeId() == null) ? 0 : getDeployeeId().hashCode());
        result = prime * result + ((getDeployeeName() == null) ? 0 : getDeployeeName().hashCode());
        result = prime * result + ((getDeployeeJob() == null) ? 0 : getDeployeeJob().hashCode());
        result = prime * result + ((getDeployeePhone() == null) ? 0 : getDeployeePhone().hashCode());
        result = prime * result + ((getDeployeeAddr() == null) ? 0 : getDeployeeAddr().hashCode());
        result = prime * result + ((getDepartmentId() == null) ? 0 : getDepartmentId().hashCode());
        result = prime * result + ((getDeployeeStatus() == null) ? 0 : getDeployeeStatus().hashCode());
        result = prime * result + ((getDeployeeRole() == null) ? 0 : getDeployeeRole().hashCode());
        result = prime * result + ((getEntryTime() == null) ? 0 : getEntryTime().hashCode());
        result = prime * result + ((getIsContract() == null) ? 0 : getIsContract().hashCode());
        result = prime * result + ((getIsEnsurance() == null) ? 0 : getIsEnsurance().hashCode());
        result = prime * result + ((getContract() == null) ? 0 : getContract().hashCode());
        result = prime * result + ((getIdCard() == null) ? 0 : getIdCard().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getEnsurance() == null) ? 0 : getEnsurance().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", deployeeId=").append(deployeeId);
        sb.append(", deployeeName=").append(deployeeName);
        sb.append(", deployeeJob=").append(deployeeJob);
        sb.append(", deployeePhone=").append(deployeePhone);
        sb.append(", deployeeAddr=").append(deployeeAddr);
        sb.append(", departmentId=").append(departmentId);
        sb.append(", deployeeStatus=").append(deployeeStatus);
        sb.append(", deployeeRole=").append(deployeeRole);
        sb.append(", entryTime=").append(entryTime);
        sb.append(", isContract=").append(isContract);
        sb.append(", isEnsurance=").append(isEnsurance);
        sb.append(", contract=").append(contract);
        sb.append(", idCard=").append(idCard);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", password=").append(password);
        sb.append(", username=").append(username);
        sb.append(", userId=").append(userId);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", ensurance=").append(ensurance);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}