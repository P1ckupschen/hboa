package com.gdproj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Contract;
import com.gdproj.entity.ContractCategory;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.ContractMapper;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.ContractService;
import com.gdproj.service.DeployeeService;
import com.gdproj.service.contractCategoryService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.ContractVo;
import com.gdproj.vo.PageVo;
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
    public IPage<ContractVo> getContractList(PageQueryDto pageDto) {

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

        //排序
        if (sort.equals("+id")) {
            queryWrapper.orderByAsc(Contract::getContractId);
        } else {
            queryWrapper.orderByDesc(Contract::getContractId);
        }
        //查询产品名称？
        if (!title.isEmpty()) {
            queryWrapper.like(Contract::getContractTitle,title);
        }
//        如果有类型的话
        if (!ObjectUtil.isEmpty(type)) {
            //传过来的是合同类型id
            queryWrapper.eq(Contract::getCategoryId,type);
        }
        queryWrapper.eq(Contract::getIsPrivate,0);

        IPage<Contract> recordPage = contractMapper.selectPage(page, queryWrapper);

        Page<ContractVo> resultPage = new Page<>();

        List<ContractVo> resultList = new ArrayList<>();
        try {

            resultList = recordPage.getRecords().stream().map((item) -> {

                ContractVo vo = BeanCopyUtils.copyBean(item, ContractVo.class);
                //类型id
                if(!ObjectUtil.isEmpty(item.getCategoryId())){
                    ContractCategory category = categoryService.getById(item.getCategoryId());
                    if (!ObjectUtil.isEmpty(category)) {
                        vo.setCategory(category.getCategoryName());
                    }
                }
                //跟进人
                if(!ObjectUtil.isEmpty(item.getFollowedUser())){
                    vo.setFollowedName(deployeeService.getNameByUserId(item.getFollowedUser()));
                }else{
                    vo.setFollowedName("");
                }
//                //甲方手机号解密
//                if(!ObjectUtil.isEmpty(item.getaPhone())){
//                    vo.setAPhone(AesUtil.decrypt(item.getaPhone(),AesUtil.key128));
//                }
//                //乙方手机号解密
//                if(!ObjectUtil.isEmpty(item.getbPhone())) {
//                    vo.setBPhone(AesUtil.decrypt(item.getbPhone(), AesUtil.key128));
//                }
                return vo;
            }).collect(Collectors.toList());
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }
        List<ContractVo> contractVos = addOrderId(resultList, pageNum, pageSize);
        resultPage.setRecords(contractVos);

        resultPage.setTotal(recordPage.getTotal());

        return resultPage;

    }

    @Override
    public List<SelectVo> getListForSelect() {

        LambdaQueryWrapper<Contract> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(Contract::getIsPrivate,0);

        List<Contract> list = list(queryWrapper);

        return list.stream().map((item) -> {
            SelectVo vo = new SelectVo();
            vo.setId(item.getContractId());
            vo.setName(item.getContractTitle());
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public List<Contract> getListByClientId(Integer clientId) {

        LambdaQueryWrapper<Contract> queryWrapper =new LambdaQueryWrapper<>();

        queryWrapper.eq(Contract::getContractClient,clientId);
        List<Contract> list = list(queryWrapper);
        return list;
    }

    @Override
    public ResponseResult getContractListForBackend(PageQueryDto queryDto) {
        //类型
        Integer type = queryDto.getType();
        //部门
        Integer departmentId = queryDto.getDepartmentId();
        //时间
        String time = queryDto.getTime();
        //排序
        String sort = queryDto.getSort();
        //搜索框如果是产品搜索产品名称或者选择产品id
        //如果是人 搜素人名或者人id
        //如果是物 搜索id
        String title = queryDto.getTitle();
        Integer pageNum = queryDto.getPageNum();
        Integer pageSize = queryDto.getPageSize();

        Page<Contract> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Contract> queryWrapper = new LambdaQueryWrapper<>();

        //排序
        if (sort.equals("+id")) {
            queryWrapper.orderByAsc(Contract::getContractId);
        } else {
            queryWrapper.orderByDesc(Contract::getContractId);
        }
        //查询产品名称？
        if (!title.isEmpty()) {
            queryWrapper.like(Contract::getContractTitle,title);
        }
        //如果有类型的话
        if (!ObjectUtil.isEmpty(type)) {
            //传过来的是合同类型id
            queryWrapper.eq(Contract::getCategoryId,type);
        }

        IPage<Contract> recordPage = contractMapper.selectPage(page, queryWrapper);

        List<ContractVo> resultList = new ArrayList<>();

        try {
            resultList = recordPage.getRecords().stream().map(this::copyPropertyToVo).collect(Collectors.toList());
            PageVo<List<ContractVo>> result = new PageVo<>();
            List<ContractVo> contractVos = addOrderId(resultList,pageNum,pageSize);
            result.setData(contractVos);
            result.setTotal((int) recordPage.getTotal());
            return ResponseResult.okResult(result);
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }

    }


    @Override
    public IPage<ContractVo> getContractListForAdmin(PageQueryDto pageDto) {

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

        queryWrapper.eq(Contract::getIsPrivate , 1);
        //排序
        if (sort.equals("+id")) {
            queryWrapper.orderByAsc(Contract::getContractId);
        } else {
            queryWrapper.orderByDesc(Contract::getContractId);
        }
        //查询产品名称？
        if (!title.isEmpty()) {
            queryWrapper.like(Contract::getContractTitle,title);
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
                //类型id
                if(!ObjectUtil.isEmpty(item.getCategoryId())){
                    ContractCategory category = categoryService.getById(item.getCategoryId());
                    if(!ObjectUtil.isEmpty(category)){
                        vo.setCategory(category.getCategoryName());
                    }
                }
                //跟进人
                if(!ObjectUtil.isEmpty(item.getFollowedUser())){
                    vo.setFollowedName(deployeeService.getNameByUserId(item.getFollowedUser()));
                }else{
                    vo.setFollowedName("");
                }
//                //甲方手机号解密
//                if(!ObjectUtil.isEmpty(item.getaPhone())){
//                    vo.setAPhone(AesUtil.decrypt(item.getaPhone(),AesUtil.key128));
//                }
//                //乙方手机号解密
//                if(!ObjectUtil.isEmpty(item.getbPhone())) {
//                    vo.setBPhone(AesUtil.decrypt(item.getbPhone(), AesUtil.key128));
//                }
                return vo;
            }).collect(Collectors.toList());
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }
        List<ContractVo> contractVos = addOrderId(resultList,pageNum,pageSize);
        resultPage.setRecords(contractVos);

        resultPage.setTotal(recordPage.getTotal());

        return resultPage;

    }

    public ContractVo copyPropertyToVo(Contract contract){
        ContractVo vo = BeanCopyUtils.copyBean(contract, ContractVo.class);
        //类型id
        if(!ObjectUtil.isEmpty(contract.getCategoryId())){
            ContractCategory category = categoryService.getById(contract.getCategoryId());
            if(!ObjectUtil.isEmpty(category)) {
                vo.setCategory(category.getCategoryName());
            }
        }
        //跟进人
        if(!ObjectUtil.isEmpty(contract.getFollowedUser())){
            vo.setFollowedName(deployeeService.getNameByUserId(contract.getFollowedUser()));
        }else{
            vo.setFollowedName("");
        }
        return vo;
    }

    private List<ContractVo> addOrderId(List<ContractVo> list, Integer pageNum, Integer pageSize){
            if (!ObjectUtil.isEmpty(pageNum) && !ObjectUtil.isEmpty(pageSize)) {
                for (int i = 0 ; i < list.size() ; i++){
                    list.get(i).setOrderId((pageNum - 1) * pageSize + i + 1);
                }
            }
            return list;
    }
}




