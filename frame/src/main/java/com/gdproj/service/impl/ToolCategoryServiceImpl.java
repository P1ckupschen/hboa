package com.gdproj.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.ToolCategory;
import com.gdproj.mapper.ToolCategoryMapper;
import com.gdproj.service.ToolCategoryService;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【sys_dailyuse_category】的数据库操作Service实现
* @createDate 2023-10-31 09:44:02
*/
@Service
public class ToolCategoryServiceImpl extends ServiceImpl<ToolCategoryMapper, ToolCategory>
    implements ToolCategoryService {

    @Override
    public IPage<ToolCategory> getDailyUseCategoryList(pageDto pagedto) {

        Integer pageSize = pagedto.getPageSize();
        Integer pageNum = pagedto.getPageNum();
        
        Page<ToolCategory> page = new Page<>(pageNum, pageSize);

        IPage<ToolCategory> pageList = page(page);

        return pageList;
    }
}




