package com.gdproj.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.ReportCategory;
import com.gdproj.mapper.reportCategoryMapper;
import com.gdproj.service.reportCategoryService;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【sys_report_category】的数据库操作Service实现
* @createDate 2023-09-19 09:39:07
*/
@Service
public class reportCategoryServiceImpl extends ServiceImpl<reportCategoryMapper, ReportCategory>
    implements reportCategoryService {

    @Override
    public IPage<ReportCategory> getReportCategoryList(pageDto pagedto) {

        Integer pageSize = pagedto.getPageSize();
        Integer pageNum = pagedto.getPageNum();

        Page<ReportCategory> page = new Page<>(pageNum, pageSize);

        IPage<ReportCategory> pageList = page(page);

        return pageList;
    }
}




