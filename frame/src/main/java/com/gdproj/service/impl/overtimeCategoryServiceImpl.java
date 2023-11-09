package com.gdproj.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.OvertimeCategory;
import com.gdproj.mapper.overtimeCategoryMapper;
import com.gdproj.service.overtimeCategoryService;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【sys_overtime_category】的数据库操作Service实现
* @createDate 2023-09-14 15:04:32
*/
@Service
public class overtimeCategoryServiceImpl extends ServiceImpl<overtimeCategoryMapper, OvertimeCategory>
    implements overtimeCategoryService {

    @Override
    public IPage<OvertimeCategory> getOvertimeCategoryList(PageQueryDto pagedto) {

        Integer pageSize = pagedto.getPageSize();
        Integer pageNum = pagedto.getPageNum();

        Page<OvertimeCategory> page = new Page<>(pageNum, pageSize);

        IPage<OvertimeCategory> pageList = page(page);

        return pageList;



    }
}




