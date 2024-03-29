package com.gdproj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Product;
import com.gdproj.vo.ProductVo;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_product】的数据库操作Service
* @createDate 2023-09-21 14:09:53
*/
public interface ProductService extends IService<Product> {

    List<ProductVo> getProductForSelect();

    IPage<ProductVo> getProductList(PageQueryDto pageDto);

    List<Integer> getIdsByCategoryId(Integer type);

    String getCategoryNameById(Integer productId);

}
