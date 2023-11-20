package com.gdproj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.ErrorLog;
import com.gdproj.result.ResponseResult;

/**
* @author Administrator
* @description 针对表【sys_error_log】的数据库操作Service
* @createDate 2023-11-13 15:44:28
*/
public interface ErrorLogService extends IService<ErrorLog> {

    ResponseResult getErrorLogList(PageQueryDto queryDto);
}
