package com.gdproj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Project;
import com.gdproj.result.ResponseResult;
import com.gdproj.vo.ProjectVo;
import com.gdproj.vo.SelectVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_project】的数据库操作Service
* @createDate 2023-10-05 08:41:52
*/
public interface ProjectService extends IService<Project> {

    List<SelectVo> getListForSelect();

    IPage<ProjectVo> getProjectList(PageQueryDto pageDto);

    List<ProjectVo> getProjectListByClientId(Integer clientId);

    List<Integer> getIdsByName(String title);

    ResponseResult getProjectListByCurrentId(PageQueryDto queryDto, HttpServletRequest request);

    ResponseResult getPrivateProjectList(PageQueryDto queryDto, HttpServletRequest request);

    ResponseResult getProjectListForBackend(PageQueryDto queryDto);
}
