package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.gdproj.vo.FileVo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @TableName sys_contract
 */
@TableName(value ="sys_contract",autoResultMap = true)
public class Contract implements Serializable {
    /**
     *
     */
    @TableId(value = "contract_id", type = IdType.AUTO)
    private Integer contractId;

    /**
     *
     */
    @TableField(value = "contract_title")
    private String contractTitle;

    /**
     *
     */
    @TableField(value = "contract_order_id")
    private Integer contractOrderId;

    /**
     *
     */
    @TableField(value = "category_id")
    private Integer categoryId;

    /**
     * 合同对象
     */
    @TableField(value = "contract_client")
    private Integer contractClient;

    /**
     * 合同金额
     */
    @TableField(value = "contract_amount")
    private Long contractAmount;

    /**
     * 金额大写

     */
    @TableField(value = "contract_amount_cp")
    private String contractAmountCp;

    /**
     *
     */
    @TableField(value = "a_address")
    private String aAddress;

    /**
     *
     */
    @TableField(value = "a_linkman")
    private String aLinkman;

    /**
     *
     */
    @TableField(value = "a_phone")
    private String aPhone;

    /**
     *
     */
    @TableField(value = "a_sign_time")
    private Date aSignTime;

    /**
     *
     */
    @TableField(value = "a_user")
    private String aUser;

    /**
     *
     */
    @TableField(value = "b_address")
    private String bAddress;

    /**
     *
     */
    @TableField(value = "b_linkman")
    private String bLinkman;

    /**
     *
     */
    @TableField(value = "b_phone")
    private String bPhone;

    /**
     *
     */
    @TableField(value = "b_sign_time")
    private Date bSignTime;

    /**
     *
     */
    @TableField(value = "b_user")
    private String bUser;

    /**
     *
     */
    @TableField(value = "contract_status")
    private Integer contractStatus;

    /**
     *
     */
    @TableField(value = "contract_remark")
    private String contractRemark;

    /**
     *
     */
    @TableField(value = "created_time",fill = FieldFill.INSERT)
    private Date createdTime;

    /**
     *
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * id
     */
    @TableField(value = "created_user")
    private Integer createdUser;

    /**
     * id
     */
    @TableField(value = "followed_user")
    private Integer followedUser;

    /**
     * 附件
     */
    @TableField(value = "contract_attachments" , typeHandler = JacksonTypeHandler.class)
    private List<FileVo> contractAttachments;

    /**
     *
     */
    @TableField(value = "contract_content")
    private String contractContent;

    /**
     * 父合同
     */
    @TableField(value = "parent_id")
    private Integer parentId;

    /**
     *
     */
    @TableField(value = "project_id")
    private Integer projectId;

    /**
     *
     */
    @TableField(value = "warranty_amount")
    private BigDecimal warrantyAmount;

    /**
     *
     */
    @TableField(value = "warranty_year")
    private Integer warrantyYear;

    /**
     *
     */
    @TableField(value = "warranty_start_time")
    private Date warrantyStartTime;

    /**
     *
     */
    @TableField(value = "warranty_end_time")
    private Date warrantyEndTime;

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
     *
     */
    public String getContractTitle() {
        return contractTitle;
    }

    /**
     *
     */
    public void setContractTitle(String contractTitle) {
        this.contractTitle = contractTitle;
    }

    /**
     *
     */
    public Integer getContractOrderId() {
        return contractOrderId;
    }

    /**
     *
     */
    public void setContractOrderId(Integer contractOrderId) {
        this.contractOrderId = contractOrderId;
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
     * 合同对象
     */
    public Integer getContractClient() {
        return contractClient;
    }

    /**
     * 合同对象
     */
    public void setContractClient(Integer contractClient) {
        this.contractClient = contractClient;
    }

    /**
     * 合同金额
     */
    public Long getContractAmount() {
        return contractAmount;
    }

    /**
     * 合同金额
     */
    public void setContractAmount(Long contractAmount) {
        this.contractAmount = contractAmount;
    }

    /**
     * 金额大写

     */
    public String getContractAmountCp() {
        return contractAmountCp;
    }

    /**
     * 金额大写

     */
    public void setContractAmountCp(String contractAmountCp) {
        this.contractAmountCp = contractAmountCp;
    }

    /**
     *
     */
    public String getaAddress() {
        return aAddress;
    }

    /**
     *
     */
    public void setaAddress(String aAddress) {
        this.aAddress = aAddress;
    }

    /**
     *
     */
    public String getaLinkman() {
        return aLinkman;
    }

    /**
     *
     */
    public void setaLinkman(String aLinkman) {
        this.aLinkman = aLinkman;
    }

    /**
     *
     */
    public String getaPhone() {
        return aPhone;
    }

    /**
     *
     */
    public void setaPhone(String aPhone) {
        this.aPhone = aPhone;
    }

    /**
     *
     */
    public Date getaSignTime() {
        return aSignTime;
    }

    /**
     *
     */
    public void setaSignTime(Date aSignTime) {
        this.aSignTime = aSignTime;
    }

    /**
     *
     */
    public String getaUser() {
        return aUser;
    }

    /**
     *
     */
    public void setaUser(String aUser) {
        this.aUser = aUser;
    }

    /**
     *
     */
    public String getbAddress() {
        return bAddress;
    }

    /**
     *
     */
    public void setbAddress(String bAddress) {
        this.bAddress = bAddress;
    }

    /**
     *
     */
    public String getbLinkman() {
        return bLinkman;
    }

    /**
     *
     */
    public void setbLinkman(String bLinkman) {
        this.bLinkman = bLinkman;
    }

    /**
     *
     */
    public String getbPhone() {
        return bPhone;
    }

    /**
     *
     */
    public void setbPhone(String bPhone) {
        this.bPhone = bPhone;
    }

    /**
     *
     */
    public Date getbSignTime() {
        return bSignTime;
    }

    /**
     *
     */
    public void setbSignTime(Date bSignTime) {
        this.bSignTime = bSignTime;
    }

    /**
     *
     */
    public String getbUser() {
        return bUser;
    }

    /**
     *
     */
    public void setbUser(String bUser) {
        this.bUser = bUser;
    }

    /**
     *
     */
    public Integer getContractStatus() {
        return contractStatus;
    }

    /**
     *
     */
    public void setContractStatus(Integer contractStatus) {
        this.contractStatus = contractStatus;
    }

    /**
     *
     */
    public String getContractRemark() {
        return contractRemark;
    }

    /**
     *
     */
    public void setContractRemark(String contractRemark) {
        this.contractRemark = contractRemark;
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
     * id
     */
    public Integer getCreatedUser() {
        return createdUser;
    }

    /**
     * id
     */
    public void setCreatedUser(Integer createdUser) {
        this.createdUser = createdUser;
    }

    /**
     * id
     */
    public Integer getFollowedUser() {
        return followedUser;
    }

    /**
     * id
     */
    public void setFollowedUser(Integer followedUser) {
        this.followedUser = followedUser;
    }

    /**
     * 附件
     */
    public List<FileVo> getContractAttachments() {
        return contractAttachments;
    }

    /**
     * 附件
     */
    public void setContractAttachments(List<FileVo> contractAttachments) {
        this.contractAttachments = contractAttachments;
    }

    /**
     *
     */
    public String getContractContent() {
        return contractContent;
    }

    /**
     *
     */
    public void setContractContent(String contractContent) {
        this.contractContent = contractContent;
    }

    /**
     * 父合同
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 父合同
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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
    public Integer getWarrantyYear() {
        return warrantyYear;
    }

    /**
     *
     */
    public void setWarrantyYear(Integer warrantyYear) {
        this.warrantyYear = warrantyYear;
    }

    /**
     *
     */
    public Date getWarrantyStartTime() {
        return warrantyStartTime;
    }

    /**
     *
     */
    public void setWarrantyStartTime(Date warrantyStartTime) {
        this.warrantyStartTime = warrantyStartTime;
    }

    /**
     *
     */
    public Date getWarrantyEndTime() {
        return warrantyEndTime;
    }

    /**
     *
     */
    public void setWarrantyEndTime(Date warrantyEndTime) {
        this.warrantyEndTime = warrantyEndTime;
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
        Contract other = (Contract) that;
        return (this.getContractId() == null ? other.getContractId() == null : this.getContractId().equals(other.getContractId()))
                && (this.getContractTitle() == null ? other.getContractTitle() == null : this.getContractTitle().equals(other.getContractTitle()))
                && (this.getContractOrderId() == null ? other.getContractOrderId() == null : this.getContractOrderId().equals(other.getContractOrderId()))
                && (this.getCategoryId() == null ? other.getCategoryId() == null : this.getCategoryId().equals(other.getCategoryId()))
                && (this.getContractClient() == null ? other.getContractClient() == null : this.getContractClient().equals(other.getContractClient()))
                && (this.getContractAmount() == null ? other.getContractAmount() == null : this.getContractAmount().equals(other.getContractAmount()))
                && (this.getContractAmountCp() == null ? other.getContractAmountCp() == null : this.getContractAmountCp().equals(other.getContractAmountCp()))
                && (this.getaAddress() == null ? other.getaAddress() == null : this.getaAddress().equals(other.getaAddress()))
                && (this.getaLinkman() == null ? other.getaLinkman() == null : this.getaLinkman().equals(other.getaLinkman()))
                && (this.getaPhone() == null ? other.getaPhone() == null : this.getaPhone().equals(other.getaPhone()))
                && (this.getaSignTime() == null ? other.getaSignTime() == null : this.getaSignTime().equals(other.getaSignTime()))
                && (this.getaUser() == null ? other.getaUser() == null : this.getaUser().equals(other.getaUser()))
                && (this.getbAddress() == null ? other.getbAddress() == null : this.getbAddress().equals(other.getbAddress()))
                && (this.getbLinkman() == null ? other.getbLinkman() == null : this.getbLinkman().equals(other.getbLinkman()))
                && (this.getbPhone() == null ? other.getbPhone() == null : this.getbPhone().equals(other.getbPhone()))
                && (this.getbSignTime() == null ? other.getbSignTime() == null : this.getbSignTime().equals(other.getbSignTime()))
                && (this.getbUser() == null ? other.getbUser() == null : this.getbUser().equals(other.getbUser()))
                && (this.getContractStatus() == null ? other.getContractStatus() == null : this.getContractStatus().equals(other.getContractStatus()))
                && (this.getContractRemark() == null ? other.getContractRemark() == null : this.getContractRemark().equals(other.getContractRemark()))
                && (this.getCreatedTime() == null ? other.getCreatedTime() == null : this.getCreatedTime().equals(other.getCreatedTime()))
                && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
                && (this.getCreatedUser() == null ? other.getCreatedUser() == null : this.getCreatedUser().equals(other.getCreatedUser()))
                && (this.getFollowedUser() == null ? other.getFollowedUser() == null : this.getFollowedUser().equals(other.getFollowedUser()))
                && (this.getContractAttachments() == null ? other.getContractAttachments() == null : this.getContractAttachments().equals(other.getContractAttachments()))
                && (this.getContractContent() == null ? other.getContractContent() == null : this.getContractContent().equals(other.getContractContent()))
                && (this.getParentId() == null ? other.getParentId() == null : this.getParentId().equals(other.getParentId()))
                && (this.getProjectId() == null ? other.getProjectId() == null : this.getProjectId().equals(other.getProjectId()))
                && (this.getWarrantyAmount() == null ? other.getWarrantyAmount() == null : this.getWarrantyAmount().equals(other.getWarrantyAmount()))
                && (this.getWarrantyYear() == null ? other.getWarrantyYear() == null : this.getWarrantyYear().equals(other.getWarrantyYear()))
                && (this.getWarrantyStartTime() == null ? other.getWarrantyStartTime() == null : this.getWarrantyStartTime().equals(other.getWarrantyStartTime()))
                && (this.getWarrantyEndTime() == null ? other.getWarrantyEndTime() == null : this.getWarrantyEndTime().equals(other.getWarrantyEndTime()))
                && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getContractId() == null) ? 0 : getContractId().hashCode());
        result = prime * result + ((getContractTitle() == null) ? 0 : getContractTitle().hashCode());
        result = prime * result + ((getContractOrderId() == null) ? 0 : getContractOrderId().hashCode());
        result = prime * result + ((getCategoryId() == null) ? 0 : getCategoryId().hashCode());
        result = prime * result + ((getContractClient() == null) ? 0 : getContractClient().hashCode());
        result = prime * result + ((getContractAmount() == null) ? 0 : getContractAmount().hashCode());
        result = prime * result + ((getContractAmountCp() == null) ? 0 : getContractAmountCp().hashCode());
        result = prime * result + ((getaAddress() == null) ? 0 : getaAddress().hashCode());
        result = prime * result + ((getaLinkman() == null) ? 0 : getaLinkman().hashCode());
        result = prime * result + ((getaPhone() == null) ? 0 : getaPhone().hashCode());
        result = prime * result + ((getaSignTime() == null) ? 0 : getaSignTime().hashCode());
        result = prime * result + ((getaUser() == null) ? 0 : getaUser().hashCode());
        result = prime * result + ((getbAddress() == null) ? 0 : getbAddress().hashCode());
        result = prime * result + ((getbLinkman() == null) ? 0 : getbLinkman().hashCode());
        result = prime * result + ((getbPhone() == null) ? 0 : getbPhone().hashCode());
        result = prime * result + ((getbSignTime() == null) ? 0 : getbSignTime().hashCode());
        result = prime * result + ((getbUser() == null) ? 0 : getbUser().hashCode());
        result = prime * result + ((getContractStatus() == null) ? 0 : getContractStatus().hashCode());
        result = prime * result + ((getContractRemark() == null) ? 0 : getContractRemark().hashCode());
        result = prime * result + ((getCreatedTime() == null) ? 0 : getCreatedTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getCreatedUser() == null) ? 0 : getCreatedUser().hashCode());
        result = prime * result + ((getFollowedUser() == null) ? 0 : getFollowedUser().hashCode());
        result = prime * result + ((getContractAttachments() == null) ? 0 : getContractAttachments().hashCode());
        result = prime * result + ((getContractContent() == null) ? 0 : getContractContent().hashCode());
        result = prime * result + ((getParentId() == null) ? 0 : getParentId().hashCode());
        result = prime * result + ((getProjectId() == null) ? 0 : getProjectId().hashCode());
        result = prime * result + ((getWarrantyAmount() == null) ? 0 : getWarrantyAmount().hashCode());
        result = prime * result + ((getWarrantyYear() == null) ? 0 : getWarrantyYear().hashCode());
        result = prime * result + ((getWarrantyStartTime() == null) ? 0 : getWarrantyStartTime().hashCode());
        result = prime * result + ((getWarrantyEndTime() == null) ? 0 : getWarrantyEndTime().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", contractId=").append(contractId);
        sb.append(", contractTitle=").append(contractTitle);
        sb.append(", contractOrderId=").append(contractOrderId);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", contractClient=").append(contractClient);
        sb.append(", contractAmount=").append(contractAmount);
        sb.append(", contractAmountCp=").append(contractAmountCp);
        sb.append(", aAddress=").append(aAddress);
        sb.append(", aLinkman=").append(aLinkman);
        sb.append(", aPhone=").append(aPhone);
        sb.append(", aSignTime=").append(aSignTime);
        sb.append(", aUser=").append(aUser);
        sb.append(", bAddress=").append(bAddress);
        sb.append(", bLinkman=").append(bLinkman);
        sb.append(", bPhone=").append(bPhone);
        sb.append(", bSignTime=").append(bSignTime);
        sb.append(", bUser=").append(bUser);
        sb.append(", contractStatus=").append(contractStatus);
        sb.append(", contractRemark=").append(contractRemark);
        sb.append(", createdTime=").append(createdTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", createdUser=").append(createdUser);
        sb.append(", followedUser=").append(followedUser);
        sb.append(", contractAttachments=").append(contractAttachments);
        sb.append(", contractContent=").append(contractContent);
        sb.append(", parentId=").append(parentId);
        sb.append(", projectId=").append(projectId);
        sb.append(", warrantyAmount=").append(warrantyAmount);
        sb.append(", warrantyYear=").append(warrantyYear);
        sb.append(", warrantyStartTime=").append(warrantyStartTime);
        sb.append(", warrantyEndTime=").append(warrantyEndTime);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}