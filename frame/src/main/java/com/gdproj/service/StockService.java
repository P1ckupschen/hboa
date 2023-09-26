package com.gdproj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Stock;
import com.gdproj.vo.stockVo;

/**
* @author Administrator
* @description 针对表【sys_stock】的数据库操作Service
* @createDate 2023-09-25 08:11:34
*/
public interface StockService extends IService<Stock> {

    IPage<stockVo> getStockList(pageDto pageDto);
}
