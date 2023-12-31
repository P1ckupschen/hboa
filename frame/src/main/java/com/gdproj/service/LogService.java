package com.gdproj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Log;
import com.gdproj.result.ResponseResult;

/**
* @author Administrator
* @description 针对表【sys_log】的数据库操作Service
* @createDate 2023-10-16 13:24:39
*/
public interface LogService extends IService<Log> {


    void insertLogWhenOperating(Log log);

    ResponseResult getLogList(PageQueryDto queryDto);
}
