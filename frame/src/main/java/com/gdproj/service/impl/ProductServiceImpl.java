package com.gdproj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.*;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.ProductMapper;
import com.gdproj.service.ProductService;
import com.gdproj.service.RecordService;
import com.gdproj.service.productCategoryService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.productVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Autowired
    ProductMapper productMapper;

    @Autowired
    RecordService recordService;

    @Autowired
    productCategoryService categoryService;

    @Override
    public List<productVo> getProductForSelect() {
        List<Product> list = list();

        List<productVo> collect = list.stream().map((item) -> {
            productVo vo = new productVo();
            vo.setProductId(item.getProductId());
            vo.setProductName(item.getProductName());
            vo.setProductTotal(recordService.getCountByProductId(vo.getProductId()));
            return vo;
        }).collect(Collectors.toList());

        return collect;

    }

    @Override
    public IPage<productVo> getProductList(pageDto pageDto) {
        Integer type = pageDto.getType();
        Integer departmentId = pageDto.getDepartmentId();
        String time = pageDto.getTime();
        String sort = pageDto.getSort();
        String title = pageDto.getTitle();
        Integer pageNum = pageDto.getPageNum();
        Integer pageSize = pageDto.getPageSize();

        Page<Product> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Product::getIsDeleted,0);
        //排序
        if(sort.equals("+id")){
            queryWrapper.orderByAsc(Product::getProductId);
        }else{
            queryWrapper.orderByDesc(Product::getProductId);
        }


        //模糊查询产品名
        if(!title.isEmpty()){
            //如果有模糊查询的时间 先通过查title 的用户ids
            List<Integer> ids = getIdsByTitle(title);
            if(!ObjectUtil.isEmpty(ids)){
                queryWrapper.in(Product::getProductId, ids);
            }else{
                queryWrapper.in(Product::getProductId,0);
            }
            //通过ids去找所有符合ids的对象 sign;
        }

        //产品类型
        if(!ObjectUtil.isEmpty(type)){
            queryWrapper.eq(Product::getCategoryId,type);
        }

        IPage<Product> productPage = productMapper.selectPage(page, queryWrapper);

        Page<productVo> resultPage = new Page<>();

        List<productVo> resultList = new ArrayList<>();
        //结果里的部门 和用户都返回成string；
        try {
            resultList = productPage.getRecords().stream().map((item) -> {

                productVo vo = BeanCopyUtils.copyBean(item, productVo.class);

                //类型名称
                vo.setCategory(categoryService.getById(item.getCategoryId()).getCategoryName());

                //total 计算
                Integer count = recordService.getCountByProductId(vo.getProductId());
                vo.setProductTotal(count);

                //record 记录产品不需要  但是库存状态需要

                return vo;

            }).collect(Collectors.toList());
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }

        resultPage.setRecords(resultList);

        resultPage.setTotal(productPage.getTotal());

        return resultPage;

    }

    @Override
    public List<Integer> getIdsByCategoryId(Integer type) {

        LambdaQueryWrapper<Product> queryWrapper =new LambdaQueryWrapper<>();

        queryWrapper.in(Product::getCategoryId,type);

        List<Product> list = list(queryWrapper);

        return list.stream().map(Product::getProductId).collect(Collectors.toList());
    }

    @Override
    public String getCategoryNameById(Integer productId) {

        Product oneById = getById(productId);

        productCategory categoryServiceById = categoryService.getById(oneById.getCategoryId());

        return categoryServiceById.getCategoryName();
    }


    public List<Integer> getIdsByTitle(String title){

        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(Product::getProductName,title);

        List<Product> list = list(queryWrapper);

        List<Integer> collect = list.stream().map((item) -> {
            return item.getProductId();
        }).collect(Collectors.toList());

        return collect;
    }
}




