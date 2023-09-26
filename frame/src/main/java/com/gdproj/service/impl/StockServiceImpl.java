package com.gdproj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Product;
import com.gdproj.entity.Stock;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.ProductMapper;
import com.gdproj.mapper.StockMapper;
import com.gdproj.service.ProductService;
import com.gdproj.service.RecordService;
import com.gdproj.service.StockService;
import com.gdproj.service.productCategoryService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.productVo;
import com.gdproj.vo.stockVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【sys_stock】的数据库操作Service实现
* @createDate 2023-09-25 08:11:34
*/
@Service
public class StockServiceImpl extends ServiceImpl<StockMapper, Stock>
    implements StockService {

    @Autowired
    ProductService productService;


    @Autowired
    RecordService recordService;
    @Autowired
    StockMapper stockMapper;

    @Autowired
    productCategoryService categoryService;

    @Override
    public IPage<stockVo> getStockList(pageDto pageDto) {

        //类型
        Integer type = pageDto.getType();
        //部门
        Integer departmentId = pageDto.getDepartmentId();
        //时间
        String time = pageDto.getTime();
        //排序
        String sort = pageDto.getSort();
        //搜索框如果是产品搜索产品名称或者选择产品id
        //如果是人 搜素人名或者人id
        //如果是物 搜索id
        String title = pageDto.getTitle();
        Integer pageNum = pageDto.getPageNum();
        Integer pageSize = pageDto.getPageSize();

        Page<Stock> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Stock> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Stock::getIsDeleted, 0);
        //排序
        if (sort.equals("+id")) {
            queryWrapper.orderByAsc(Stock::getMaterialId);
        } else {
            queryWrapper.orderByDesc(Stock::getMaterialId);
        }

        //查询产品名称？
        if (!title.isEmpty()) {
            queryWrapper.eq(Stock::getProductId,title);
        }

        //如果有类型的话
        if (!ObjectUtil.isEmpty(type)) {
            //传过来的是productCategoryId,需要去产品表下找属于这个产品类型的产品 id数组
            List<Integer> ids = productService.getIdsByCategoryId(type);
            //querywrapper
            queryWrapper.in(Stock::getProductId, ids);
        }

        IPage<Stock> recordPage = stockMapper.selectPage(page, queryWrapper);

        Page<stockVo> resultPage = new Page<>();

        List<stockVo> resultList = new ArrayList<>();
        try {

            resultList = recordPage.getRecords().stream().map((item) -> {

                stockVo vo = BeanCopyUtils.copyBean(item, stockVo.class);

                //产品名称 对应的产品类型
                vo.setProductName(productService.getById(item.getProductId()).getProductName());
                vo.setProductBrand(productService.getById(item.getProductId()).getProductBrand());
                vo.setProductUnit(productService.getById(item.getProductId()).getProductUnit());

                vo.setProduct(productService.getById(item.getProductId()));
                //产品类型名称
                vo.setCategory(productService.getCategoryNameById(item.getProductId()));
                vo.setCount(recordService.getCountByProductId(item.getProductId()));

                //产品入库记录
                vo.setRecordIn(recordService.getRecordInListByProductId(item.getProductId()));

                //产品出库记录
                vo.setRecordOut(recordService.getRecordOutListByProductId(item.getProductId()));
                return vo;
            }).collect(Collectors.toList());
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }

        resultPage.setRecords(resultList);

        resultPage.setTotal(recordPage.getTotal());

        return resultPage;

    }



}




