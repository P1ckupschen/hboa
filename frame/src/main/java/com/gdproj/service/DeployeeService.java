package com.gdproj.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Deployee;
import com.gdproj.result.ResponseResult;
import com.gdproj.vo.deployeeVo;
import com.gdproj.vo.userVo;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_deployee】的数据库操作Service
* @createDate 2023-09-11 14:12:54
*/
public interface DeployeeService extends IService<Deployee> {



    List<Integer> getIdsByTime(String time);

    List<Integer> getIdsByTitle(String title);

    List<Integer> getIdsByDepartmentId(Integer type);

    String getNameByUserId(Integer userId);

    String getDepartmentNameByUserId(Integer userId);

    Integer getDepartmentIdByUserId(Integer userId);

    List<userVo> getListForSelect();

    IPage<deployeeVo> getDeployeeList(pageDto pageDto);
}
