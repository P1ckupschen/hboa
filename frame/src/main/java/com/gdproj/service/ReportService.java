package com.gdproj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Report;
import com.gdproj.vo.reportVo;

/**
* @author Administrator
* @description 针对表【sys_report】的数据库操作Service
* @createDate 2023-09-19 09:39:07
*/
public interface ReportService extends IService<Report> {

    IPage<reportVo> getReportList(pageDto pageDto);
}