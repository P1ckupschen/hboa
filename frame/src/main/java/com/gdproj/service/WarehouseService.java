package com.gdproj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Warehouse;
import com.gdproj.vo.WarehouseVo;

import javax.servlet.http.HttpServletRequest;

/**
* @author Administrator
* @description 针对表【sys_warehouse】的数据库操作Service
* @createDate 2023-10-20 11:10:47
*/
public interface WarehouseService extends IService<Warehouse> {

    boolean insertWarehouse(Warehouse warehouse);

    IPage<WarehouseVo> getWarehouseList(PageQueryDto pageDto);

    IPage<WarehouseVo> getMyWarehouseList(PageQueryDto pageDto, HttpServletRequest request);

    boolean updateWarehouse(Warehouse warehouse);
}
