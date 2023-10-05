package com.gdproj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.contractCategory;

/**
* @author Administrator
* @description 针对表【sys_contract_category】的数据库操作Service
* @createDate 2023-10-05 09:13:46
*/
public interface contractCategoryService extends IService<contractCategory> {

    IPage<contractCategory> getContractCategoryList(pageDto pagedto);
}
