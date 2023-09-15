package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName sys_custom
 */
@TableName(value ="sys_custom")
public class Custom implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer custom_id;

    /**
     * 
     */
    private String custom_name;

    /**
     * 
     */
    private Integer custom_order_id;

    /**
     * 
     */
    private String custom_manger;

    /**
     * 
     */
    private String custom_from;

    /**
     * 
     */
    private String custom_status;

    /**
     * 
     */
    private String custom_category;

    /**
     * 
     */
    private String custom_attribute;

    /**
     * 
     */
    private String custom_industry;

    /**
     * 
     */
    private String custom_location;

    /**
     * 
     */
    private String custom_phone;

    /**
     * 
     */
    private String custom_fax;

    /**
     * 
     */
    private String custom_web;

    /**
     * 
     */
    private String custom_legal_person;

    /**
     * 
     */
    private String custom_postal;

    /**
     * 
     */
    private String custom_email;

    /**
     * 
     */
    private String custom_addr;

    /**
     * 
     */
    private Double custom_size;

    /**
     * 
     */
    private String custom_annual_sales;

    /**
     * 
     */
    private Date custom_created_time;

    /**
     * 
     */
    private String custom_contacts1;

    /**
     * 
     */
    private String custom_contacts1_phone;

    /**
     * 
     */
    private String custom_contacts2;

    /**
     * 
     */
    private String custom_contacts2_phone;

    /**
     * 
     */
    private String custom_description;

    /**
     * 
     */
    private String custom_created_user;

    /**
     * 
     */
    private Integer custom_uncontacted;

    /**
     * 
     */
    private String is_deleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getCustom_id() {
        return custom_id;
    }

    /**
     * 
     */
    public void setCustom_id(Integer custom_id) {
        this.custom_id = custom_id;
    }

    /**
     * 
     */
    public String getCustom_name() {
        return custom_name;
    }

    /**
     * 
     */
    public void setCustom_name(String custom_name) {
        this.custom_name = custom_name;
    }

    /**
     * 
     */
    public Integer getCustom_order_id() {
        return custom_order_id;
    }

    /**
     * 
     */
    public void setCustom_order_id(Integer custom_order_id) {
        this.custom_order_id = custom_order_id;
    }

    /**
     * 
     */
    public String getCustom_manger() {
        return custom_manger;
    }

    /**
     * 
     */
    public void setCustom_manger(String custom_manger) {
        this.custom_manger = custom_manger;
    }

    /**
     * 
     */
    public String getCustom_from() {
        return custom_from;
    }

    /**
     * 
     */
    public void setCustom_from(String custom_from) {
        this.custom_from = custom_from;
    }

    /**
     * 
     */
    public String getCustom_status() {
        return custom_status;
    }

    /**
     * 
     */
    public void setCustom_status(String custom_status) {
        this.custom_status = custom_status;
    }

    /**
     * 
     */
    public String getCustom_category() {
        return custom_category;
    }

    /**
     * 
     */
    public void setCustom_category(String custom_category) {
        this.custom_category = custom_category;
    }

    /**
     * 
     */
    public String getCustom_attribute() {
        return custom_attribute;
    }

    /**
     * 
     */
    public void setCustom_attribute(String custom_attribute) {
        this.custom_attribute = custom_attribute;
    }

    /**
     * 
     */
    public String getCustom_industry() {
        return custom_industry;
    }

    /**
     * 
     */
    public void setCustom_industry(String custom_industry) {
        this.custom_industry = custom_industry;
    }

    /**
     * 
     */
    public String getCustom_location() {
        return custom_location;
    }

    /**
     * 
     */
    public void setCustom_location(String custom_location) {
        this.custom_location = custom_location;
    }

    /**
     * 
     */
    public String getCustom_phone() {
        return custom_phone;
    }

    /**
     * 
     */
    public void setCustom_phone(String custom_phone) {
        this.custom_phone = custom_phone;
    }

    /**
     * 
     */
    public String getCustom_fax() {
        return custom_fax;
    }

    /**
     * 
     */
    public void setCustom_fax(String custom_fax) {
        this.custom_fax = custom_fax;
    }

    /**
     * 
     */
    public String getCustom_web() {
        return custom_web;
    }

    /**
     * 
     */
    public void setCustom_web(String custom_web) {
        this.custom_web = custom_web;
    }

    /**
     * 
     */
    public String getCustom_legal_person() {
        return custom_legal_person;
    }

    /**
     * 
     */
    public void setCustom_legal_person(String custom_legal_person) {
        this.custom_legal_person = custom_legal_person;
    }

    /**
     * 
     */
    public String getCustom_postal() {
        return custom_postal;
    }

    /**
     * 
     */
    public void setCustom_postal(String custom_postal) {
        this.custom_postal = custom_postal;
    }

    /**
     * 
     */
    public String getCustom_email() {
        return custom_email;
    }

    /**
     * 
     */
    public void setCustom_email(String custom_email) {
        this.custom_email = custom_email;
    }

    /**
     * 
     */
    public String getCustom_addr() {
        return custom_addr;
    }

    /**
     * 
     */
    public void setCustom_addr(String custom_addr) {
        this.custom_addr = custom_addr;
    }

    /**
     * 
     */
    public Double getCustom_size() {
        return custom_size;
    }

    /**
     * 
     */
    public void setCustom_size(Double custom_size) {
        this.custom_size = custom_size;
    }

    /**
     * 
     */
    public String getCustom_annual_sales() {
        return custom_annual_sales;
    }

    /**
     * 
     */
    public void setCustom_annual_sales(String custom_annual_sales) {
        this.custom_annual_sales = custom_annual_sales;
    }

    /**
     * 
     */
    public Date getCustom_created_time() {
        return custom_created_time;
    }

    /**
     * 
     */
    public void setCustom_created_time(Date custom_created_time) {
        this.custom_created_time = custom_created_time;
    }

    /**
     * 
     */
    public String getCustom_contacts1() {
        return custom_contacts1;
    }

    /**
     * 
     */
    public void setCustom_contacts1(String custom_contacts1) {
        this.custom_contacts1 = custom_contacts1;
    }

    /**
     * 
     */
    public String getCustom_contacts1_phone() {
        return custom_contacts1_phone;
    }

    /**
     * 
     */
    public void setCustom_contacts1_phone(String custom_contacts1_phone) {
        this.custom_contacts1_phone = custom_contacts1_phone;
    }

    /**
     * 
     */
    public String getCustom_contacts2() {
        return custom_contacts2;
    }

    /**
     * 
     */
    public void setCustom_contacts2(String custom_contacts2) {
        this.custom_contacts2 = custom_contacts2;
    }

    /**
     * 
     */
    public String getCustom_contacts2_phone() {
        return custom_contacts2_phone;
    }

    /**
     * 
     */
    public void setCustom_contacts2_phone(String custom_contacts2_phone) {
        this.custom_contacts2_phone = custom_contacts2_phone;
    }

    /**
     * 
     */
    public String getCustom_description() {
        return custom_description;
    }

    /**
     * 
     */
    public void setCustom_description(String custom_description) {
        this.custom_description = custom_description;
    }

    /**
     * 
     */
    public String getCustom_created_user() {
        return custom_created_user;
    }

    /**
     * 
     */
    public void setCustom_created_user(String custom_created_user) {
        this.custom_created_user = custom_created_user;
    }

    /**
     * 
     */
    public Integer getCustom_uncontacted() {
        return custom_uncontacted;
    }

    /**
     * 
     */
    public void setCustom_uncontacted(Integer custom_uncontacted) {
        this.custom_uncontacted = custom_uncontacted;
    }

    /**
     * 
     */
    public String getIs_deleted() {
        return is_deleted;
    }

    /**
     * 
     */
    public void setIs_deleted(String is_deleted) {
        this.is_deleted = is_deleted;
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
        Custom other = (Custom) that;
        return (this.getCustom_id() == null ? other.getCustom_id() == null : this.getCustom_id().equals(other.getCustom_id()))
            && (this.getCustom_name() == null ? other.getCustom_name() == null : this.getCustom_name().equals(other.getCustom_name()))
            && (this.getCustom_order_id() == null ? other.getCustom_order_id() == null : this.getCustom_order_id().equals(other.getCustom_order_id()))
            && (this.getCustom_manger() == null ? other.getCustom_manger() == null : this.getCustom_manger().equals(other.getCustom_manger()))
            && (this.getCustom_from() == null ? other.getCustom_from() == null : this.getCustom_from().equals(other.getCustom_from()))
            && (this.getCustom_status() == null ? other.getCustom_status() == null : this.getCustom_status().equals(other.getCustom_status()))
            && (this.getCustom_category() == null ? other.getCustom_category() == null : this.getCustom_category().equals(other.getCustom_category()))
            && (this.getCustom_attribute() == null ? other.getCustom_attribute() == null : this.getCustom_attribute().equals(other.getCustom_attribute()))
            && (this.getCustom_industry() == null ? other.getCustom_industry() == null : this.getCustom_industry().equals(other.getCustom_industry()))
            && (this.getCustom_location() == null ? other.getCustom_location() == null : this.getCustom_location().equals(other.getCustom_location()))
            && (this.getCustom_phone() == null ? other.getCustom_phone() == null : this.getCustom_phone().equals(other.getCustom_phone()))
            && (this.getCustom_fax() == null ? other.getCustom_fax() == null : this.getCustom_fax().equals(other.getCustom_fax()))
            && (this.getCustom_web() == null ? other.getCustom_web() == null : this.getCustom_web().equals(other.getCustom_web()))
            && (this.getCustom_legal_person() == null ? other.getCustom_legal_person() == null : this.getCustom_legal_person().equals(other.getCustom_legal_person()))
            && (this.getCustom_postal() == null ? other.getCustom_postal() == null : this.getCustom_postal().equals(other.getCustom_postal()))
            && (this.getCustom_email() == null ? other.getCustom_email() == null : this.getCustom_email().equals(other.getCustom_email()))
            && (this.getCustom_addr() == null ? other.getCustom_addr() == null : this.getCustom_addr().equals(other.getCustom_addr()))
            && (this.getCustom_size() == null ? other.getCustom_size() == null : this.getCustom_size().equals(other.getCustom_size()))
            && (this.getCustom_annual_sales() == null ? other.getCustom_annual_sales() == null : this.getCustom_annual_sales().equals(other.getCustom_annual_sales()))
            && (this.getCustom_created_time() == null ? other.getCustom_created_time() == null : this.getCustom_created_time().equals(other.getCustom_created_time()))
            && (this.getCustom_contacts1() == null ? other.getCustom_contacts1() == null : this.getCustom_contacts1().equals(other.getCustom_contacts1()))
            && (this.getCustom_contacts1_phone() == null ? other.getCustom_contacts1_phone() == null : this.getCustom_contacts1_phone().equals(other.getCustom_contacts1_phone()))
            && (this.getCustom_contacts2() == null ? other.getCustom_contacts2() == null : this.getCustom_contacts2().equals(other.getCustom_contacts2()))
            && (this.getCustom_contacts2_phone() == null ? other.getCustom_contacts2_phone() == null : this.getCustom_contacts2_phone().equals(other.getCustom_contacts2_phone()))
            && (this.getCustom_description() == null ? other.getCustom_description() == null : this.getCustom_description().equals(other.getCustom_description()))
            && (this.getCustom_created_user() == null ? other.getCustom_created_user() == null : this.getCustom_created_user().equals(other.getCustom_created_user()))
            && (this.getCustom_uncontacted() == null ? other.getCustom_uncontacted() == null : this.getCustom_uncontacted().equals(other.getCustom_uncontacted()))
            && (this.getIs_deleted() == null ? other.getIs_deleted() == null : this.getIs_deleted().equals(other.getIs_deleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getCustom_id() == null) ? 0 : getCustom_id().hashCode());
        result = prime * result + ((getCustom_name() == null) ? 0 : getCustom_name().hashCode());
        result = prime * result + ((getCustom_order_id() == null) ? 0 : getCustom_order_id().hashCode());
        result = prime * result + ((getCustom_manger() == null) ? 0 : getCustom_manger().hashCode());
        result = prime * result + ((getCustom_from() == null) ? 0 : getCustom_from().hashCode());
        result = prime * result + ((getCustom_status() == null) ? 0 : getCustom_status().hashCode());
        result = prime * result + ((getCustom_category() == null) ? 0 : getCustom_category().hashCode());
        result = prime * result + ((getCustom_attribute() == null) ? 0 : getCustom_attribute().hashCode());
        result = prime * result + ((getCustom_industry() == null) ? 0 : getCustom_industry().hashCode());
        result = prime * result + ((getCustom_location() == null) ? 0 : getCustom_location().hashCode());
        result = prime * result + ((getCustom_phone() == null) ? 0 : getCustom_phone().hashCode());
        result = prime * result + ((getCustom_fax() == null) ? 0 : getCustom_fax().hashCode());
        result = prime * result + ((getCustom_web() == null) ? 0 : getCustom_web().hashCode());
        result = prime * result + ((getCustom_legal_person() == null) ? 0 : getCustom_legal_person().hashCode());
        result = prime * result + ((getCustom_postal() == null) ? 0 : getCustom_postal().hashCode());
        result = prime * result + ((getCustom_email() == null) ? 0 : getCustom_email().hashCode());
        result = prime * result + ((getCustom_addr() == null) ? 0 : getCustom_addr().hashCode());
        result = prime * result + ((getCustom_size() == null) ? 0 : getCustom_size().hashCode());
        result = prime * result + ((getCustom_annual_sales() == null) ? 0 : getCustom_annual_sales().hashCode());
        result = prime * result + ((getCustom_created_time() == null) ? 0 : getCustom_created_time().hashCode());
        result = prime * result + ((getCustom_contacts1() == null) ? 0 : getCustom_contacts1().hashCode());
        result = prime * result + ((getCustom_contacts1_phone() == null) ? 0 : getCustom_contacts1_phone().hashCode());
        result = prime * result + ((getCustom_contacts2() == null) ? 0 : getCustom_contacts2().hashCode());
        result = prime * result + ((getCustom_contacts2_phone() == null) ? 0 : getCustom_contacts2_phone().hashCode());
        result = prime * result + ((getCustom_description() == null) ? 0 : getCustom_description().hashCode());
        result = prime * result + ((getCustom_created_user() == null) ? 0 : getCustom_created_user().hashCode());
        result = prime * result + ((getCustom_uncontacted() == null) ? 0 : getCustom_uncontacted().hashCode());
        result = prime * result + ((getIs_deleted() == null) ? 0 : getIs_deleted().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", custom_id=").append(custom_id);
        sb.append(", custom_name=").append(custom_name);
        sb.append(", custom_order_id=").append(custom_order_id);
        sb.append(", custom_manger=").append(custom_manger);
        sb.append(", custom_from=").append(custom_from);
        sb.append(", custom_status=").append(custom_status);
        sb.append(", custom_category=").append(custom_category);
        sb.append(", custom_attribute=").append(custom_attribute);
        sb.append(", custom_industry=").append(custom_industry);
        sb.append(", custom_location=").append(custom_location);
        sb.append(", custom_phone=").append(custom_phone);
        sb.append(", custom_fax=").append(custom_fax);
        sb.append(", custom_web=").append(custom_web);
        sb.append(", custom_legal_person=").append(custom_legal_person);
        sb.append(", custom_postal=").append(custom_postal);
        sb.append(", custom_email=").append(custom_email);
        sb.append(", custom_addr=").append(custom_addr);
        sb.append(", custom_size=").append(custom_size);
        sb.append(", custom_annual_sales=").append(custom_annual_sales);
        sb.append(", custom_created_time=").append(custom_created_time);
        sb.append(", custom_contacts1=").append(custom_contacts1);
        sb.append(", custom_contacts1_phone=").append(custom_contacts1_phone);
        sb.append(", custom_contacts2=").append(custom_contacts2);
        sb.append(", custom_contacts2_phone=").append(custom_contacts2_phone);
        sb.append(", custom_description=").append(custom_description);
        sb.append(", custom_created_user=").append(custom_created_user);
        sb.append(", custom_uncontacted=").append(custom_uncontacted);
        sb.append(", is_deleted=").append(is_deleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}