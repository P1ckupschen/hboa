package com.gdproj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Contract;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.ContractMapper;
import com.gdproj.service.ContractService;
import com.gdproj.service.DeployeeService;
import com.gdproj.service.contractCategoryService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.ContractVo;
import com.gdproj.vo.SelectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【sys_contract】的数据库操作Service实现
* @createDate 2023-10-05 08:41:52
*/
@Service
public class ContractServiceImpl extends ServiceImpl<ContractMapper, Contract>
    implements ContractService {

    @Autowired
    ContractMapper contractMapper;

    @Autowired
    DeployeeService deployeeService;

    @Autowired
    contractCategoryService categoryService;

    @Override
    public IPage<ContractVo> getContractList(pageDto pageDto) {

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

        Page<Contract> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Contract> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Contract::getIsDeleted, 0);
        //排序
        if (sort.equals("+id")) {
            queryWrapper.orderByAsc(Contract::getContractId);
        } else {
            queryWrapper.orderByDesc(Contract::getContractId);
        }

        //查询产品名称？
        if (!title.isEmpty()) {
            queryWrapper.eq(Contract::getContractId,title);
        }

//        如果有类型的话
        if (!ObjectUtil.isEmpty(type)) {
            //传过来的是合同类型id
            queryWrapper.eq(Contract::getCategoryId,type);
        }

        IPage<Contract> recordPage = contractMapper.selectPage(page, queryWrapper);

        Page<ContractVo> resultPage = new Page<>();

        List<ContractVo> resultList = new ArrayList<>();
        try {

            resultList = recordPage.getRecords().stream().map((item) -> {

                ContractVo vo = BeanCopyUtils.copyBean(item, ContractVo.class);

                vo.setCategory(categoryService.getById(item.getCategoryId()).getCategoryName());

                vo.setFollowedName(deployeeService.getNameByUserId(item.getFollowedUser()));

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
    public List<SelectVo> getListForSelect() {

        List<Contract> list = list();

        List<SelectVo> collect = list.stream().map((item) -> {
            SelectVo vo = new SelectVo();
            vo.setId(item.getContractId());
            vo.setName(item.getContractTitle());
            return vo;
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<Contract> getListByClientId(Integer clientId) {

        LambdaQueryWrapper<Contract> queryWrapper =new LambdaQueryWrapper<>();

        queryWrapper.eq(Contract::getContractClient,clientId);
        List<Contract> list = list(queryWrapper);
        return list;
    }
}




