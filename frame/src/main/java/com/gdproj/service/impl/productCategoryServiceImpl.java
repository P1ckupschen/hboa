package com.gdproj.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.ProductCategory;
import com.gdproj.mapper.productCategoryMapper;
import com.gdproj.service.productCategoryService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.utils.analysisTree;
import com.gdproj.vo.CategoryVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_product_category】的数据库操作Service实现
* @createDate 2023-09-21 14:09:53
*/
@Service
public class productCategoryServiceImpl extends ServiceImpl<productCategoryMapper, ProductCategory>
    implements productCategoryService {


    @Override
    public IPage<CategoryVo> getProductCategoryListByPage(PageQueryDto pagedto) {

        List<ProductCategory> list = list();

        IPage<CategoryVo> page = new Page<>();

        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(list, CategoryVo.class);

        List<CategoryVo> categoryVos1 = analysisTree.builderTree(categoryVos, 0);

        System.out.println(categoryVos1);

        page.setRecords(categoryVos1);

        return page;
    }

    @Override
    public List<CategoryVo> getProductCategoryList() {

        List<ProductCategory> list = list();

        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(list, CategoryVo.class);

        List<CategoryVo> categoryVos1 = analysisTree.builderTree(categoryVos, 0);

        return categoryVos1;
    }
}




