package com.gdproj.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.projectCategory;
import com.gdproj.mapper.projectCategoryMapper;
import com.gdproj.service.projectCategoryService;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【sys_project_category】的数据库操作Service实现
* @createDate 2023-10-07 14:54:52
*/
@Service
public class projectCategoryServiceImpl extends ServiceImpl<projectCategoryMapper, projectCategory>
    implements projectCategoryService {

    @Override
    public IPage<projectCategory> getProjectCategoryList(pageDto pagedto) {
        Page<projectCategory> page = new Page<>(pagedto.getPageNum(), pagedto.getPageSize());
        Page<projectCategory> pageList = page(page);
        return pageList;
    }
}




