package com.gdproj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.entity.Product;
import com.gdproj.vo.productVo;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_product】的数据库操作Service
* @createDate 2023-09-21 14:09:53
*/
public interface ProductService extends IService<Product> {

    List<productVo> getProductForSelect();
}