package com.gdproj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Notify;
import com.gdproj.vo.notifyVo;

/**
* @author Administrator
* @description 针对表【sys_notify】的数据库操作Service
* @createDate 2023-09-15 15:27:55
*/
public interface NotifyService extends IService<Notify> {

    IPage<notifyVo> getNotifyList(pageDto pageDto);
}