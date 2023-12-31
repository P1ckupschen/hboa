package com.gdproj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.ProductCategory;
import com.gdproj.vo.CategoryVo;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_product_category】的数据库操作Service
* @createDate 2023-09-21 14:09:53
*/
public interface productCategoryService extends IService<ProductCategory> {

    IPage<CategoryVo> getProductCategoryListByPage(PageQueryDto pagedto);

    List<CategoryVo> getProductCategoryList();
}
