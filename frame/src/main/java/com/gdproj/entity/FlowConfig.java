package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @TableName sys_flow_config
 */
@TableName(value ="sys_flow_config",autoResultMap = true)
public class FlowConfig implements Serializable {
    /**
     * 
     */
    @TableId(value = "type_id", type = IdType.AUTO)
    private Integer typeId;

    /**
     * 
     */
    @TableField(value = "type_name")
    private String typeName;

    /**
     * 
     */
    @TableField(value = "approval_flow",typeHandler = JacksonTypeHandler.class)
    private List<Integer> approvalFlow;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getTypeId() {
        return typeId;
    }

    /**
     * 
     */
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    /**
     * 
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * 
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * 
     */
    public List<Integer> getApprovalFlow() {
        return approvalFlow;
    }

    /**
     * 
     */
    public void setApprovalFlow(List<Integer> approvalFlow) {
        this.approvalFlow = approvalFlow;
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
        FlowConfig other = (FlowConfig) that;
        return (this.getTypeId() == null ? other.getTypeId() == null : this.getTypeId().equals(other.getTypeId()))
            && (this.getTypeName() == null ? other.getTypeName() == null : this.getTypeName().equals(other.getTypeName()))
            && (this.getApprovalFlow() == null ? other.getApprovalFlow() == null : this.getApprovalFlow().equals(other.getApprovalFlow()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTypeId() == null) ? 0 : getTypeId().hashCode());
        result = prime * result + ((getTypeName() == null) ? 0 : getTypeName().hashCode());
        result = prime * result + ((getApprovalFlow() == null) ? 0 : getApprovalFlow().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", typeId=").append(typeId);
        sb.append(", typeName=").append(typeName);
        sb.append(", approvalFlow=").append(approvalFlow);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}