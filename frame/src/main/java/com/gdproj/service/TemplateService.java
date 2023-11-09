package com.gdproj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Template;

/**
* @author Administrator
* @description 针对表【sys_template】的数据库操作Service
* @createDate 2023-10-06 09:40:12
*/
public interface TemplateService extends IService<Template> {

    IPage<Template> getTemplateList(PageQueryDto pageDto);
}
