package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName sys_deployee
 */
@TableName(value ="sys_deployee")
public class Deployee implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer deployeeId;

    /**
     * 
     */
    private String deployeeName;

    /**
     * 
     */
    private String deployeeJob;

    /**
     * 
     */
    private String deployeePhone;

    /**
     * 
     */
    private String deployeeAddr;

    /**
     * 
     */
    private Integer departmentId;

    /**
     * 
     */
    private String deployeeStatus;

    /**
     * 
     */
    private Integer deployeeRole;

    /**
     * 
     */
    private Date entryTime;

    /**
     * 
     */
    private String isContract;

    /**
     * 
     */
    private String isEnsurance;

    /**
     * 
     */
    private String contract;

    /**
     * 
     */
    private String idCard;

    /**
     * 
     */
    private String idDeleted;

    /**
     * 
     */

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
    public String getDeployeeStatus() {
        return deployeeStatus;
    }

    /**
     * 
     */
    public void setDeployeeStatus(String deployeeStatus) {
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
    public String getIsContract() {
        return isContract;
    }

    /**
     * 
     */
    public void setIsContract(String isContract) {
        this.isContract = isContract;
    }

    /**
     * 
     */
    public String getIsEnsurance() {
        return isEnsurance;
    }

    /**
     * 
     */
    public void setIsEnsurance(String isEnsurance) {
        this.isEnsurance = isEnsurance;
    }

    /**
     * 
     */
    public String getContract() {
        return contract;
    }

    /**
     * 
     */
    public void setContract(String contract) {
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
    public String getIdDeleted() {
        return idDeleted;
    }

    /**
     * 
     */
    public void setIdDeleted(String idDeleted) {
        this.idDeleted = idDeleted;
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
            && (this.getIdDeleted() == null ? other.getIdDeleted() == null : this.getIdDeleted().equals(other.getIdDeleted()));
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
        result = prime * result + ((getIdDeleted() == null) ? 0 : getIdDeleted().hashCode());
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
        sb.append(", idDeleted=").append(idDeleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}