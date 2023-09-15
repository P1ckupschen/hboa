package com.gdproj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.entity.Department;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.DepartmentMapper;
import com.gdproj.service.DepartmentService;
import org.springframework.stereotype.Service;

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
}




