package com.gdproj.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.leaveCategory;
import com.gdproj.entity.overtimeCategory;
import com.gdproj.mapper.overtimeCategoryMapper;
import com.gdproj.service.overtimeCategoryService;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【sys_overtime_category】的数据库操作Service实现
* @createDate 2023-09-14 15:04:32
*/
@Service
public class overtimeCategoryServiceImpl extends ServiceImpl<overtimeCategoryMapper, overtimeCategory>
    implements overtimeCategoryService {

    @Override
    public IPage<overtimeCategory> getOvertimeCategoryList(pageDto pagedto) {

        Integer pageSize = pagedto.getPageSize();
        Integer pageNum = pagedto.getPageNum();

        Page<overtimeCategory> page = new Page<>(pageNum, pageSize);

        IPage<overtimeCategory> pageList = page(page);

        return pageList;



    }
}




