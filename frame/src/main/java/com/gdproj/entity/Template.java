package com.gdproj.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.gdproj.vo.FileVo;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @TableName sys_template
 */
@TableName(value ="sys_template",autoResultMap = true)
public class Template implements Serializable {
    /**
     *
     */
    @TableId(value = "template_id", type = IdType.AUTO)
    private Integer templateId;

    /**
     *
     */
    @TableField(value = "template_name")
    private String templateName;

    /**
     *
     */
    @TableField(value = "template_content",typeHandler = JacksonTypeHandler.class)
    private List<FileVo> templateContent;

    /**
     *
     */
    @TableField(value = "is_deleted")
    @TableLogic
    private Integer isDeleted;

    /**
     *
     */
    @TableField(value = "template_pic",typeHandler = JacksonTypeHandler.class)
    private List<FileVo> templatePic;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public Integer getTemplateId() {
        return templateId;
    }

    /**
     *
     */
    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    /**
     *
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     *
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    /**
     *
     */
    public List<FileVo> getTemplateContent() {
        return templateContent;
    }

    /**
     *
     */
    public void setTemplateContent(List<FileVo> templateContent) {
        this.templateContent = templateContent;
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
    public List<FileVo> getTemplatePic() {
        return templatePic;
    }

    /**
     *
     */
    public void setTemplatePic(List<FileVo> templatePic) {
        this.templatePic = templatePic;
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
        Template other = (Template) that;
        return (this.getTemplateId() == null ? other.getTemplateId() == null : this.getTemplateId().equals(other.getTemplateId()))
                && (this.getTemplateName() == null ? other.getTemplateName() == null : this.getTemplateName().equals(other.getTemplateName()))
                && (this.getTemplateContent() == null ? other.getTemplateContent() == null : this.getTemplateContent().equals(other.getTemplateContent()))
                && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
                && (this.getTemplatePic() == null ? other.getTemplatePic() == null : this.getTemplatePic().equals(other.getTemplatePic()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getTemplateId() == null) ? 0 : getTemplateId().hashCode());
        result = prime * result + ((getTemplateName() == null) ? 0 : getTemplateName().hashCode());
        result = prime * result + ((getTemplateContent() == null) ? 0 : getTemplateContent().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getTemplatePic() == null) ? 0 : getTemplatePic().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", templateId=").append(templateId);
        sb.append(", templateName=").append(templateName);
        sb.append(", templateContent=").append(templateContent);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", templatePic=").append(templatePic);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}