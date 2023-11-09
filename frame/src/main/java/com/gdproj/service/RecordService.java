package com.gdproj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Record;
import com.gdproj.entity.Warehouse;
import com.gdproj.vo.ProductVo;
import com.gdproj.vo.RecordVo;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_record】的数据库操作Service
* @createDate 2023-09-20 15:15:25
*/
public interface RecordService extends IService<Record> {

    IPage<RecordVo> getRecordList(PageQueryDto pageDto);

    Integer getCountByProductId(Integer productId);

    List<String> getRecordInListByProductId(Integer productId);

    List<String> getRecordOutListByProductId(Integer productId);
    IPage<ProductVo> getStockList(PageQueryDto pageDto);

    boolean insertRecord(Record updateRecord);

    boolean insertRecordByWarehouseId(Integer runId);

    List<Record> transferWarehouseContentToRecord(Warehouse warehouse);
}
