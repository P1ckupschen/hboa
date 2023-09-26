package com.gdproj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gdproj.entity.Stock;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Administrator
* @description 针对表【sys_stock】的数据库操作Mapper
* @createDate 2023-09-25 08:11:34
* @Entity generator.entity.Stock
*/
@Mapper
public interface StockMapper extends BaseMapper<Stock> {

}




