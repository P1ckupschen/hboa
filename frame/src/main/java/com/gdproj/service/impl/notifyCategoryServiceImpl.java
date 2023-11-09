package com.gdproj.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.NotifyCategory;
import com.gdproj.mapper.notifyCategoryMapper;
import com.gdproj.service.notifyCategoryService;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【sys_notify_category】的数据库操作Service实现
* @createDate 2023-09-15 15:27:55
*/
@Service
public class notifyCategoryServiceImpl extends ServiceImpl<notifyCategoryMapper, NotifyCategory>
    implements notifyCategoryService {

    @Override
    public IPage<NotifyCategory> getNotifyCategoryList(PageQueryDto pagedto) {

        Integer pageSize = pagedto.getPageSize();
        Integer pageNum = pagedto.getPageNum();

        Page<NotifyCategory> page = new Page<>(pageNum, pageSize);

        IPage<NotifyCategory> pageList = page(page);

        return pageList;
    }
}




