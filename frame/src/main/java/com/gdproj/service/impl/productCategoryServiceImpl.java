package com.gdproj.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.productCategory;
import com.gdproj.mapper.productCategoryMapper;
import com.gdproj.service.productCategoryService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.utils.analysisTree;
import com.gdproj.vo.categoryVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_product_category】的数据库操作Service实现
* @createDate 2023-09-21 14:09:53
*/
@Service
public class productCategoryServiceImpl extends ServiceImpl<productCategoryMapper, productCategory>
    implements productCategoryService {


    @Override
    public IPage<categoryVo> getProductCategoryListByPage(pageDto pagedto) {

        List<productCategory> list = list();

        IPage<categoryVo> page = new Page<>();

        List<categoryVo> categoryVos = BeanCopyUtils.copyBeanList(list, categoryVo.class);

        List<categoryVo> categoryVos1 = analysisTree.builderTree(categoryVos, 0);

        System.out.println(categoryVos1);

        page.setRecords(categoryVos1);

        return page;
    }

    @Override
    public List<categoryVo> getProductCategoryList() {

        List<productCategory> list = list();

        List<categoryVo> categoryVos = BeanCopyUtils.copyBeanList(list, categoryVo.class);

        List<categoryVo> categoryVos1 = analysisTree.builderTree(categoryVos, 0);

        return categoryVos1;
    }
}




