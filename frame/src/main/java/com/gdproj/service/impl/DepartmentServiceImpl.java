package com.gdproj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.entity.Department;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.DepartmentMapper;
import com.gdproj.service.DepartmentService;
import com.gdproj.vo.selectVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【sys_department】的数据库操作Service实现
* @createDate 2023-09-14 11:01:16
*/
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department>
    implements DepartmentService {

    @Override
    public String getDepartmentNameByDepartmentId(Integer departmentId) {

        LambdaQueryWrapper<Department> lambdaQueryWrapper =new LambdaQueryWrapper<>();

        lambdaQueryWrapper.eq(Department::getDepartmentId,departmentId);

        Department one = getOne(lambdaQueryWrapper);

        if (ObjectUtil.isEmpty(one)){
            throw new SystemException(AppHttpCodeEnum.DEPARTMENT_NULL);
        }else{
            return one.getDepartmentName();
        }

    }

    @Override
    public List<selectVo> getListForSelect() {
        List<Department> list = list();

        List<selectVo> collect = list.stream().map((item) -> {
            selectVo vo = new selectVo();
            vo.setId(item.getDepartmentId());
            vo.setName(item.getDepartmentName());
            return vo;
        }).collect(Collectors.toList());
        return collect;
    }
}




