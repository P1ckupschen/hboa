package com.gdproj.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Template;
import com.gdproj.mapper.TemplateMapper;
import com.gdproj.service.TemplateService;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【sys_template】的数据库操作Service实现
* @createDate 2023-10-06 09:40:12
*/
@Service
public class TemplateServiceImpl extends ServiceImpl<TemplateMapper, Template>
    implements TemplateService {

    @Override
    public IPage<Template> getTemplateList(pageDto pageDto) {

        Page<Template> page = new Page<>(pageDto.getPageNum(), pageDto.getPageSize());
        IPage<Template> listPage = page(page);
        return listPage;
    }
}




