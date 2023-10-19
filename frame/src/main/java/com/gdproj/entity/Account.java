package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.gdproj.vo.fileVo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @TableName sys_front_account
 */
@TableName(value ="sys_front_account",autoResultMap = true)
public class Account implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField(value = "username")
    private String username;

    /**
     * 
     */
    @TableField(value = "password")
    private String password;

    /**
     * 
     */
    @TableField(value = "login_time")
    private Date loginTime;

    /**
     * 
     */
    @TableField(value = "avatar",typeHandler = JacksonTypeHandler.class)
    private List<fileVo> avatar;

    /**
     * 
     */
    @TableField(value = "user_status")
    private Integer userStatus;

    /**
     * 
     */
    @TableField(value = "is_deleted")
    @TableLogic
    private Integer isDeleted;

    /**
     * 
     */
    @TableField(value = "deployee_id")
    private Integer deployeeId;

    /**
     * 如果没有绑定员工,此字段当做name（例如外包）
     */
    @TableField(value = "name")
    private String name;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(Integer id) {
        this.id = id;
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
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * 
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * 
     */
    public List<fileVo> getAvatar() {
        return avatar;
    }

    /**
     * 
     */
    public void setAvatar(List<fileVo> avatar) {
        this.avatar = avatar;
    }

    /**
     * 
     */
    public Integer getUserStatus() {
        return userStatus;
    }

    /**
     * 
     */
    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
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
     * 如果没有绑定员工,此字段当做name（例如外包）
     */
    public String getName() {
        return name;
    }

    /**
     * 如果没有绑定员工,此字段当做name（例如外包）
     */
    public void setName(String name) {
        this.name = name;
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
        Account other = (Account) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getLoginTime() == null ? other.getLoginTime() == null : this.getLoginTime().equals(other.getLoginTime()))
            && (this.getAvatar() == null ? other.getAvatar() == null : this.getAvatar().equals(other.getAvatar()))
            && (this.getUserStatus() == null ? other.getUserStatus() == null : this.getUserStatus().equals(other.getUserStatus()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getDeployeeId() == null ? other.getDeployeeId() == null : this.getDeployeeId().equals(other.getDeployeeId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getLoginTime() == null) ? 0 : getLoginTime().hashCode());
        result = prime * result + ((getAvatar() == null) ? 0 : getAvatar().hashCode());
        result = prime * result + ((getUserStatus() == null) ? 0 : getUserStatus().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getDeployeeId() == null) ? 0 : getDeployeeId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", loginTime=").append(loginTime);
        sb.append(", avatar=").append(avatar);
        sb.append(", userStatus=").append(userStatus);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", deployeeId=").append(deployeeId);
        sb.append(", name=").append(name);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}