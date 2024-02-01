package com.gdproj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Product;
import com.gdproj.entity.ProductCategory;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.ProductMapper;
import com.gdproj.service.ProductService;
import com.gdproj.service.RecordService;
import com.gdproj.service.productCategoryService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.ProductVo;
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
    public List<ProductVo> getProductForSelect() {
        List<Product> list = list();

        List<ProductVo> collect = list.stream().map((item) -> {
            ProductVo vo = new ProductVo();
            vo.setProductId(item.getProductId());
            vo.setProductName(item.getProductName());
            vo.setProductTotal(recordService.getCountByProductId(vo.getProductId()));
            return vo;
        }).collect(Collectors.toList());

        return collect;

    }

    @Override
    public IPage<ProductVo> getProductList(PageQueryDto pageDto) {
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
//            queryWrapper.eq(Product::getProductId,title);
//            System.out.println(title);
//            //如果有模糊查询的时间 先通过查title 的用户ids
            List<Integer> ids = getIdsByTitle(title);
//            System.out.println(ids);
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

        Page<ProductVo> resultPage = new Page<>();

        List<ProductVo> resultList = new ArrayList<>();
        //结果里的部门 和用户都返回成string；
        try {
            resultList = productPage.getRecords().stream().map((item) -> {

                ProductVo vo = BeanCopyUtils.copyBean(item, ProductVo.class);

                //类型名称
                if(!ObjectUtil.isEmpty(item.getCategoryId())){
                    ProductCategory one = categoryService.getById(item.getCategoryId());
                    if(!ObjectUtil.isEmpty(one)){
                        vo.setCategory(one.getCategoryName());
                    }
                }else{
                    vo.setCategory("");
                }

                //total 计算
                Integer count = recordService.getCountByProductId(vo.getProductId());
                vo.setCount(count);

                //record 记录产品不需要  但是库存状态需要
                vo.setRecordIn(recordService.getRecordInListByProductId(item.getProductId()));
                //产品出库记录
                vo.setRecordOut(recordService.getRecordOutListByProductId(item.getProductId()));

                return vo;

            }).collect(Collectors.toList());
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }
        List<ProductVo> productVos = addOrderId(resultList, pageNum, pageSize);
        resultPage.setRecords(productVos);

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

        ProductCategory categoryServiceById = categoryService.getById(oneById.getCategoryId());
        if(ObjectUtil.isEmpty(categoryServiceById)){
            return "";
        }else{
            return categoryServiceById.getCategoryName();
        }

    }



    public List<Integer> getIdsByTitle(String title){

        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(Product::getProductName,title);

        List<Product> list = list(queryWrapper);

        return list.stream().map(Product::getProductId).collect(Collectors.toList());
    }
    private List<ProductVo> addOrderId(List<ProductVo> list, Integer pageNum, Integer pageSize){
        if (!ObjectUtil.isEmpty(pageNum) && !ObjectUtil.isEmpty(pageSize)) {
            for (int i = 0 ; i < list.size() ; i++){
                list.get(i).setOrderId((pageNum - 1) * pageSize + i + 1);
            }
        }
        return list;
    }
}




