package com.gdproj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.NotifyCategory;

/**
* @author Administrator
* @description 针对表【sys_notify_category】的数据库操作Service
* @createDate 2023-09-15 15:27:55
*/
public interface notifyCategoryService extends IService<NotifyCategory> {

    IPage<NotifyCategory> getNotifyCategoryList(PageQueryDto pagedto);
}
