package com.gdproj.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.ContractCategory;
import com.gdproj.mapper.contractCategoryMapper;
import com.gdproj.service.contractCategoryService;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【sys_contract_category】的数据库操作Service实现
* @createDate 2023-10-05 09:13:46
*/
@Service
public class contractCategoryServiceImpl extends ServiceImpl<contractCategoryMapper, ContractCategory>
    implements contractCategoryService {

    @Override
    public IPage<ContractCategory> getContractCategoryList(PageQueryDto pagedto) {

        Integer pageSize = pagedto.getPageSize();
        Integer pageNum = pagedto.getPageNum();

        Page<ContractCategory> page = new Page<>(pageNum, pageSize);

        IPage<ContractCategory> pageList = page(page);

        return pageList;
    }
}




