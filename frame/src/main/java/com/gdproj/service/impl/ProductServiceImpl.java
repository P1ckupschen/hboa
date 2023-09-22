package com.gdproj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.entity.Deployee;
import com.gdproj.entity.Product;
import com.gdproj.mapper.ProductMapper;
import com.gdproj.service.ProductService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.productVo;
import com.gdproj.vo.userVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【sys_product】的数据库操作Service实现
* @createDate 2023-09-21 14:09:53
*/
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product>
    implements ProductService {

    @Override
    public List<productVo> getProductForSelect() {
        List<Product> list = list();

        List<productVo> collect = list.stream().map((item) -> {
            productVo vo = new productVo();
            vo.setProductId(item.getProductId());
            vo.setProductName(item.getProductName());
            return vo;
        }).collect(Collectors.toList());

        return collect;

    }
}




