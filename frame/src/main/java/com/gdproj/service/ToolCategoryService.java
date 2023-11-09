package com.gdproj.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.ToolCategory;

/**
* @author Administrator
* @description 针对表【sys_dailyuse_category】的数据库操作Service
* @createDate 2023-10-31 09:44:02
*/
public interface ToolCategoryService extends IService<ToolCategory> {

    IPage<ToolCategory> getDailyUseCategoryList(PageQueryDto pagedto);
}
