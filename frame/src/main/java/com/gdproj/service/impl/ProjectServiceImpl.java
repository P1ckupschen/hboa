package com.gdproj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Project;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.ProjectMapper;
import com.gdproj.service.DeployeeService;
import com.gdproj.service.ProjectService;
import com.gdproj.service.projectCategoryService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.ProjectVo;
import com.gdproj.vo.SelectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【sys_project】的数据库操作Service实现
* @createDate 2023-10-05 08:41:52
*/
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project>
    implements ProjectService {

    @Autowired
    DeployeeService deployeeService;

    @Autowired
    projectCategoryService categoryService;

    @Override
    public List<SelectVo> getListForSelect() {

        List<Project> list = list();

        List<SelectVo> collect = list.stream().map((item) -> {
            SelectVo vo = new SelectVo();
            vo.setId(item.getProjectId());
            vo.setName(item.getProjectName());
            return vo;
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public IPage<ProjectVo> getProjectList(pageDto pageDto) {

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

        Page<Project> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Project> queryWrapper = new LambdaQueryWrapper<>();
        //排序
        if (sort.equals("+id")) {
            queryWrapper.orderByAsc(Project::getProjectId);
        } else {
            queryWrapper.orderByDesc(Project::getProjectId);
        }

        //查询名称？
        if (!title.isEmpty()) {
            queryWrapper.eq(Project::getProjectId,title);
        }

        //如果有类型的话 类型
        if (!ObjectUtil.isEmpty(type)) {
            //传过来的是productCategoryId,需要去产品表下找属于这个产品类型的产品 id数组
            queryWrapper.eq(Project::getCategoryId,type);
        }
        IPage<Project> recordPage = page(page, queryWrapper);

        Page<ProjectVo> resultPage = new Page<>();

        List<ProjectVo> resultList = new ArrayList<>();
        try {

            resultList = recordPage.getRecords().stream().map((item) -> {

                ProjectVo vo = BeanCopyUtils.copyBean(item, ProjectVo.class);
                //类型名称?
                vo.setCategory(categoryService.getById(item.getCategoryId()).getCategoryName());
                vo.setSupervisorName(deployeeService.getNameByUserId(item.getSupervisorId()));
                //设置是否延期
                if( !ObjectUtil.isNull(item.getCompletedTime()) && item.getCompletedTime().getTime() < item.getEndTime().getTime()){
                    vo.setIsLate(1);
                }else{
                    vo.setIsLate(0);
                }
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
    public List<ProjectVo> getProjectListByClientId(Integer clientId) {

        LambdaQueryWrapper<Project> queryWrapper =new LambdaQueryWrapper<>();

        queryWrapper.eq(Project::getProjectClient,clientId);

        List<Project> list = list(queryWrapper);

        List<ProjectVo> collect = list.stream().map((item) -> {
            ProjectVo vo = BeanCopyUtils.copyBean(item, ProjectVo.class);
            //类型名称?
            vo.setCategory(categoryService.getById(item.getCategoryId()).getCategoryName());
            vo.setSupervisorName(deployeeService.getNameByUserId(item.getSupervisorId()));
            //设置是否延期
            if (!ObjectUtil.isNull(item.getCompletedTime()) && item.getCompletedTime().getTime() < item.getEndTime().getTime()) {
                vo.setIsLate(1);
            } else {
                vo.setIsLate(0);
            }
            return vo;
        }).collect(Collectors.toList());

        return collect;
    }
}




