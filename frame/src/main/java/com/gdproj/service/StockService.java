package com.gdproj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Stock;
import com.gdproj.vo.stockSelectVo;
import com.gdproj.vo.StockVo;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_stock】的数据库操作Service
* @createDate 2023-09-25 08:11:34
*/
public interface StockService extends IService<Stock> {

    IPage<StockVo> getStockList(PageQueryDto pageDto);

    List<stockSelectVo> getStockListForSelect();
}
