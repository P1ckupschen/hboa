package com.gdproj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Department;
import com.gdproj.vo.DepartmentVo;
import com.gdproj.vo.SelectVo;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_department】的数据库操作Service
* @createDate 2023-09-14 11:01:16
*/
public interface DepartmentService extends IService<Department> {

    String getDepartmentNameByDepartmentId(Integer departmentId);

    List<SelectVo> getListForSelect();

    IPage<DepartmentVo> getDepartmentList(PageQueryDto pageDto);
}
