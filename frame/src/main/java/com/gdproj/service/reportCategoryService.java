package com.gdproj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.ReportCategory;

/**
* @author Administrator
* @description 针对表【sys_report_category】的数据库操作Service
* @createDate 2023-09-19 09:39:07
*/
public interface reportCategoryService extends IService<ReportCategory> {

    IPage<ReportCategory> getReportCategoryList(PageQueryDto pagedto);
}
