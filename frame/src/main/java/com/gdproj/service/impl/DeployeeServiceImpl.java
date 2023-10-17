package com.gdproj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Department;
import com.gdproj.entity.Deployee;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.DeployeeMapper;
import com.gdproj.service.DepartmentService;
import com.gdproj.service.DeployeeService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.deployeeVo;
import com.gdproj.vo.userVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【sys_deployee】的数据库操作Service实现
* @createDate 2023-09-11 14:12:54
*/
@Service
public class DeployeeServiceImpl extends ServiceImpl<DeployeeMapper, Deployee>
    implements DeployeeService {


    @Autowired
    DepartmentService departmentService;

    @Override
    public List<Integer> getIdsByTime(String time) {

        return null;

    }

    @Override
    public List<Integer> getIdsByTitle(String title) {

        LambdaQueryWrapper<Deployee> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(Deployee::getDeployeeName,title);

        List<Deployee> list = list(queryWrapper);

        List<Integer> collect = list.stream().map(item -> item.getDeployeeId()).collect(Collectors.toList());

        return collect;
    }

    @Override
    public List<Integer> getIdsByDepartmentId(Integer type) {

        LambdaQueryWrapper<Deployee> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Deployee::getDepartmentId,type);

        List<Deployee> list = list(queryWrapper);

        List<Integer> collect = list.stream().map(item -> item.getDeployeeId()).collect(Collectors.toList());

        return collect;


    }

    @Override
    public String getNameByUserId(Integer userId) {

        LambdaQueryWrapper<Deployee> lambdaQueryWrapper =new LambdaQueryWrapper<>();

        lambdaQueryWrapper.eq(Deployee::getDeployeeId,userId);

        Deployee one = getOne(lambdaQueryWrapper);

        return one.getDeployeeName();
    }

    public String getDepartmentNameByUserId(Integer userId){

        LambdaQueryWrapper<Deployee> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Deployee::getDeployeeId,userId);

        Deployee one = getOne(queryWrapper);

        Department department = departmentService.getById(one.getDepartmentId());

        return  department.getDepartmentName();
    }

    @Override
    public Integer getDepartmentIdByUserId(Integer userId) {


        LambdaQueryWrapper<Deployee> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Deployee::getDeployeeId,userId);

        Deployee one = getOne(queryWrapper);

        Department department = departmentService.getById(one.getDepartmentId());

        return  department.getDepartmentId();

    }

    @Override
    public List<userVo> getListForSelect() {

        List<Deployee> list = list();

        List<userVo> collect = list.stream().map((item) -> {
            userVo userVo = new userVo();
            userVo.setUserId(item.getDeployeeId());
            userVo.setUsername(item.getDeployeeName());
            userVo.setDepartmentId(item.getDepartmentId());
            return userVo;
        }).collect(Collectors.toList());

        return collect;
    }

    @Override
    public IPage<deployeeVo> getDeployeeList(pageDto pageDto) {

        //类型
        Integer type = pageDto.getType();
        //部门
        Integer departmentId = pageDto.getDepartmentId();
        //时间
        String time = pageDto.getTime();
        //排序
        String sort = pageDto.getSort();
        //搜索框如果是产品搜索产品名称或者选择产品id
        String title = pageDto.getTitle();
        Integer pageNum = pageDto.getPageNum();
        Integer pageSize = pageDto.getPageSize();

        Page<Deployee> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Deployee> queryWrapper = new LambdaQueryWrapper<>();
        //排序
        if (sort.equals("+id")) {
            queryWrapper.orderByAsc(Deployee::getDeployeeId);
        } else {
            queryWrapper.orderByDesc(Deployee::getDeployeeId);
        }

        //查询名称？
        if (!title.isEmpty()) {
            queryWrapper.eq(Deployee::getDeployeeId,title);
        }
        //查询部门 是否需要找到子部门的所有员工
        if(!ObjectUtil.isNull(departmentId)){
            queryWrapper.eq(Deployee::getDepartmentId,departmentId);
        }

        //如果有类型的话 类型
        if (!ObjectUtil.isEmpty(type)) {
            queryWrapper.eq(Deployee::getDeployeeRole,type);
        }

        IPage<Deployee> recordPage = page(page, queryWrapper);

        Page<deployeeVo> resultPage = new Page<>();

        List<deployeeVo> resultList = new ArrayList<>();
        try {

            resultList = recordPage.getRecords().stream().map((item) -> {

                deployeeVo vo = BeanCopyUtils.copyBean(item, deployeeVo.class);
                //类型名称?
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




