package com.gdproj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gdproj.entity.Product;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Administrator
* @description 针对表【sys_product】的数据库操作Mapper
* @createDate 2023-09-21 14:09:53
* @Entity generator.entity.Product
*/
@Mapper
public interface ProductMapper extends BaseMapper<Product> {

}




