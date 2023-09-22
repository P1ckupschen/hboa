package com.gdproj.service.impl;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Record;
import com.gdproj.mapper.RecordMapper;
import com.gdproj.service.DeployeeService;
import com.gdproj.service.ProductService;
import com.gdproj.service.RecordService;
import com.gdproj.service.productCategoryService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.recordVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    RecordMapper recordMapper;

    @Autowired
    DeployeeService deployeeService;

    @Autowired
    ProductService productService;

    @Autowired
    productCategoryService categoryService;


    @Override
    public IPage<recordVo> getRecordList(pageDto pageDto) {


        Integer type = pageDto.getType();
        Integer departmentId = pageDto.getDepartmentId();
        String time = pageDto.getTime();
        String sort = pageDto.getSort();
        String title = pageDto.getTitle();
        Integer pageNum = pageDto.getPageNum();
        Integer pageSize = pageDto.getPageSize();

        Page<Record> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Record> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Record::getIsDeleted,0);
        //排序
        if(sort.equals("+id")){
            queryWrapper.orderByAsc(Record::getRecordId);
        }else{
            queryWrapper.orderByDesc(Record::getRecordId);
        }
        //如果根据部门分类，有一定几率会与模糊人民冲突
        if(!Objects.isNull(departmentId) && title.isEmpty()){
            //如果没有对象没有部门id属性就找到对应id的部门所以的员工的userid
            List<Integer> userIds = deployeeService.getIdsByDepartmentId(departmentId);
            queryWrapper.in(Record::getUserId,userIds);
        }
        //设置时间 年 月 日
        //模糊查询时间
        if(time != null){
            queryWrapper.like(Record::getInTime,time);
        }

        //查询产品名称？
        if(!title.isEmpty()){
            queryWrapper.in(Record::getProductId,title);
        }

        //如果有类型的话

        if(!ObjectUtil.isEmpty(type)){
            queryWrapper.eq(Record::getCategoryId,type);
        }

        IPage<Record> recordPage = recordMapper.selectPage(page, queryWrapper);

        Page<recordVo> resultPage = new Page<>();
        //结果里的部门 和用户都返回成string；
        List<recordVo> resultList = recordPage.getRecords().stream().map((item) -> {

            recordVo vo = BeanCopyUtils.copyBean(item, recordVo.class);
            //创建人
            vo.setUsername(deployeeService.getNameByUserId(item.getUserId()));

            //部门
            vo.setDepartment(deployeeService.getDepartmentNameByUserId(item.getUserId()));

            //产品名称 对应的产品类型
            vo.setProductName(productService.getById(item.getProductId()).getProductName());
            vo.setProductBrand(productService.getById(item.getProductId()).getProductBrand());
            vo.setProductUnit(productService.getById(item.getProductId()).getProductUnit());




            //产品类型
            if(vo.getCategoryId() == 1){
                vo.setCategory("入库");
            }else{
                vo.setCategory("出库");
            }

            return vo;

        }).collect(Collectors.toList());

        resultPage.setRecords(resultList);

        resultPage.setTotal(recordPage.getTotal());

        return resultPage;

    }
}




