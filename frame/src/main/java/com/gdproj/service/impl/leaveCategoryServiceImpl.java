package com.gdproj.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.LeaveCategory;
import com.gdproj.service.leaveCategoryService;
import com.gdproj.mapper.leaveCategoryMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【sys_leave_category】的数据库操作Service实现
* @createDate 2023-09-14 15:04:32
*/
@Service
public class leaveCategoryServiceImpl extends ServiceImpl<leaveCategoryMapper, LeaveCategory>
    implements leaveCategoryService{

    @Override
    public IPage<LeaveCategory> getLeaveCategoryList(pageDto pagedto) {

        Integer pageSize = pagedto.getPageSize();
        Integer pageNum = pagedto.getPageNum();

        Page<LeaveCategory> page = new Page<>(pageNum, pageSize);

        IPage<LeaveCategory> pageList = page(page);

        return pageList;
    }
}




