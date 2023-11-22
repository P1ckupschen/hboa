package com.gdproj.service.impl;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Product;
import com.gdproj.entity.Record;
import com.gdproj.entity.Warehouse;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.ProductMapper;
import com.gdproj.mapper.RecordMapper;
import com.gdproj.service.*;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.ProductVo;
import com.gdproj.vo.RecordVo;
import com.gdproj.vo.WarehouseContentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【sys_record】的数据库操作Service实现
 * @createDate 2023-09-20 15:15:25
 */
@Service
public class RecordServiceImpl extends ServiceImpl<RecordMapper, Record>
        implements RecordService {


    @Autowired
    DeployeeService deployeeService;

    @Autowired
//    @Lazy
    ProductService productService;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    productCategoryService categoryService;

    @Autowired
    WarehouseService warehouseService;

    @Autowired
    FlowService flowService;

    @Override
    public IPage<RecordVo> getRecordList(PageQueryDto pageDto) {


        Integer type = pageDto.getType();
        Integer departmentId = pageDto.getDepartmentId();
        String time = pageDto.getTime();
        String sort = pageDto.getSort();
        String title = pageDto.getTitle();
        Integer pageNum = pageDto.getPageNum();
        Integer pageSize = pageDto.getPageSize();

        Page<Record> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Record> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Record::getIsDeleted, 0);
        //排序
        if (sort.equals("+id")) {
            queryWrapper.orderByAsc(Record::getRecordId);
        } else {
            queryWrapper.orderByDesc(Record::getRecordId);
        }
        //如果根据部门分类，有一定几率会与模糊人民冲突
        if (!Objects.isNull(departmentId) && title.isEmpty()) {
            //如果没有对象没有部门id属性就找到对应id的部门所以的员工的userid
            List<Integer> userIds = deployeeService.getIdsByDepartmentId(departmentId);
            if(!ObjectUtil.isEmpty(userIds)){
                queryWrapper.in(Record::getProductId, userIds);
            }else{
                queryWrapper.in(Record::getProductId,0);
            }
        }
        //设置时间 年 月 日
        //模糊查询时间
        if (time != null) {
            queryWrapper.like(Record::getRecordTime, time);
        }

        //查询产品名称？
        if (!title.isEmpty()) {
            queryWrapper.in(Record::getProductId, title);
        }

        //如果有类型的话

        if (!ObjectUtil.isEmpty(type)) {
            queryWrapper.eq(Record::getCategoryId, type);
        }

        IPage<Record> recordPage = page(page, queryWrapper);

        Page<RecordVo> resultPage = new Page<>();

        List<RecordVo> resultList = new ArrayList<>();
        try {

            resultList = recordPage.getRecords().stream().map((item) -> {

                RecordVo vo = BeanCopyUtils.copyBean(item, RecordVo.class);
                //创建人
                if(!ObjectUtil.isEmpty(item.getUserId())){
                    vo.setUsername(deployeeService.getNameByUserId(item.getUserId()));
                    vo.setDepartment(deployeeService.getDepartmentNameByUserId(item.getUserId()));
                }else{
                    vo.setUsername("");
                    vo.setDepartment("");
                }
                if(!ObjectUtil.isEmpty(item.getProductId())){
                    vo.setProductName(productService.getById(item.getProductId()).getProductName());
                    vo.setProductBrand(productService.getById(item.getProductId()).getProductBrand());
                    vo.setProductUnit(productService.getById(item.getProductId()).getProductUnit());
                }else{
                    vo.setProductName("");
                    vo.setProductBrand("");
                    vo.setProductUnit("");
                }
                //产品名称 对应的产品类型


                //产品类型
                if (vo.getCategoryId() == 1) {
                    vo.setCategory("入库");
                } else {
                    vo.setCategory("出库");
                }

                return vo;

            }).collect(Collectors.toList());
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }


        resultPage.setRecords(resultList);

        resultPage.setTotal(recordPage.getTotal());

        return resultPage;

    }

    @Override
    public Integer getCountByProductId(Integer productId) {

        LambdaQueryWrapper<Record> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Record::getProductId, productId);
        
        List<Record> list = list(queryWrapper);
        int count = 0;
        for ( Record item : list) {
            if(item.getCategoryId() == 1){
                count += item.getCount();
            } else if (item.getCategoryId() == 2) {
                count -= item.getCount();
            } else if (count < 0) {
                count = 0;
                break;
            }
        }
        return count;

    }

    @Override
    public List<String> getRecordInListByProductId(Integer productId) {

        LambdaQueryWrapper<Record> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Record::getProductId,productId);

        List<Record> list = list(queryWrapper);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<String> recordList = new ArrayList<>();
        for ( Record item : list) {
            if(item.getCategoryId() == 1){
                recordList.add(simpleDateFormat.format(item.getRecordTime())+" "+"入库数量:  "+item.getCount());
            }
        }
        return recordList;
    }

    @Override
    public List<String> getRecordOutListByProductId(Integer productId) {

        LambdaQueryWrapper<Record> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Record::getProductId,productId);

        List<Record> list = list(queryWrapper);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<String> recordList = new ArrayList<>();
        for ( Record item : list) {
            if(item.getCategoryId() == 2){
                recordList.add(simpleDateFormat.format(item.getRecordTime())+" "+"出库数量:  "+item.getCount());
            }
        }
        return recordList;
    }


    public IPage<ProductVo> getStockList(PageQueryDto pageDto) {

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

        Page<Product> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Product::getIsDeleted, 0);
        //排序
        if (sort.equals("+id")) {
            queryWrapper.orderByAsc(Product::getProductId);
        } else {
            queryWrapper.orderByDesc(Product::getProductId);
        }

        //查询产品名称？
        if (!title.isEmpty()) {
            queryWrapper.eq(Product::getProductId,title);
        }

        //如果有类型的话
        if (!ObjectUtil.isEmpty(type)) {
            //传过来的是productCategoryId,需要去产品表下找属于这个产品类型的产品 id数组
            List<Integer> ids = productService.getIdsByCategoryId(type);
            //querywrapper
            queryWrapper.in(Product::getProductId, ids);
        }

        IPage<Product> recordPage = productMapper.selectPage(page, queryWrapper);

        Page<ProductVo> resultPage = new Page<>();

        List<ProductVo> resultList = new ArrayList<>();
        try {

            resultList = recordPage.getRecords().stream().map((item) -> {

                ProductVo vo = BeanCopyUtils.copyBean(item, ProductVo.class);
                //产品类型名称
                vo.setCategory(productService.getCategoryNameById(item.getProductId()));
                vo.setProductTotal(getCountByProductId(item.getProductId()));
                //产品入库记录
                vo.setRecordIn(getRecordInListByProductId(item.getProductId()));
                //产品出库记录
                vo.setRecordOut(getRecordOutListByProductId(item.getProductId()));
                return vo;
            }).collect(Collectors.toList());
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }

        resultPage.setRecords(resultList);

        resultPage.setTotal(recordPage.getTotal());

        return resultPage;

    }

    @Override
    public boolean insertRecord(Record updateRecord) {

        //根据record里的record_content里的产品id 遍历保存



        return false;
    }

    @Override
    public boolean insertRecordByWarehouseId(Integer runId) {

        Warehouse warehouse = warehouseService.getById(runId);
        List<Record> records = transferWarehouseContentToRecord(warehouse);

        return saveBatch(records);
    }

    @Override
    public List<Record> transferWarehouseContentToRecord(Warehouse warehouse) {
        String s = JSONUtil.toJsonStr(warehouse.getWarehouseContent());
        List<WarehouseContentVo> warehouseSelectVos = JSONUtil.toList(s, WarehouseContentVo.class);
        List<Record> collect = warehouseSelectVos.stream().map((item) -> {
            // 所以List 需要一个实体类声明 不能用泛型
            Integer productId = item.getStockId();
            Integer count = item.getCount();
            Record record = new Record();
            record.setProductName(item.getStockName());
            record.setCategoryId(1);
            //需要有projectId contractId;
            record.setIsDeleted(0);
            record.setContractId(warehouse.getContractId());
            record.setProjectId(warehouse.getProjectId());
            record.setProductId(productId);
            record.setCount(count);
            record.setProductUnit(item.getUnit());
            record.setRecordAttachments(warehouse.getWarehouseAttachments());
            record.setRecordDescription(warehouse.getWarehouseDescription());
            record.setRecordTime(warehouse.getCreatedTime());
            record.setRecordStatus(1);
            record.setUserId(warehouse.getUserId());
            //一条warehouse如何对应 其下的record 所以 record 需要一个字段 存放所属的warehouse
            record.setWarehouseId(warehouse.getWarehouseId());
            return record;
        }).collect(Collectors.toList());
        return collect;
    }

}




