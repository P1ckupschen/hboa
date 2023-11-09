package com.gdproj.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.ProjectCategory;
import com.gdproj.mapper.projectCategoryMapper;
import com.gdproj.service.projectCategoryService;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【sys_project_category】的数据库操作Service实现
* @createDate 2023-10-07 14:54:52
*/
@Service
public class projectCategoryServiceImpl extends ServiceImpl<projectCategoryMapper, ProjectCategory>
    implements projectCategoryService {

    @Override
    public IPage<ProjectCategory> getProjectCategoryList(PageQueryDto pagedto) {
        Page<ProjectCategory> page = new Page<>(pagedto.getPageNum(), pagedto.getPageSize());
        Page<ProjectCategory> pageList = page(page);
        return pageList;
    }
}




