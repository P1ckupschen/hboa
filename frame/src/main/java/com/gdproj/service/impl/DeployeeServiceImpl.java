package com.gdproj.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.gdproj.entity.Department;
import com.gdproj.entity.Deployee;
import com.gdproj.entity.User;
import com.gdproj.mapper.DeployeeMapper;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.DepartmentService;
import com.gdproj.service.DeployeeService;
import com.gdproj.utils.RSAUtil;
import net.sf.jsqlparser.statement.select.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}




