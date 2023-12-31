package com.gdproj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.ProjectCategory;

/**
* @author Administrator
* @description 针对表【sys_project_category】的数据库操作Service
* @createDate 2023-10-07 14:54:52
*/
public interface projectCategoryService extends IService<ProjectCategory> {

    IPage<ProjectCategory> getProjectCategoryList(PageQueryDto pagedto);
}
