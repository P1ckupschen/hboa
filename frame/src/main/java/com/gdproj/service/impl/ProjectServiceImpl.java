package com.gdproj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Project;
import com.gdproj.entity.ProjectCategory;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.ProjectMapper;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.DeployeeService;
import com.gdproj.service.ProjectService;
import com.gdproj.service.projectCategoryService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.utils.JwtUtils;
import com.gdproj.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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


        LambdaQueryWrapper<Project> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(Project::getIsPrivate,0);
        List<Project> list = list(queryWrapper);

        return list.stream().map((item) -> {
            SelectVo vo = new SelectVo();
            vo.setId(item.getProjectId());
            vo.setName(item.getProjectName());
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public IPage<ProjectVo> getProjectList(PageQueryDto pageDto) {

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

        queryWrapper.eq(Project::getIsPrivate,0);
        //排序
        if (sort.equals("+id")) {
            queryWrapper.orderByAsc(Project::getProjectId);
        } else {
            queryWrapper.orderByDesc(Project::getProjectId);
        }

        //查询名称？
        if (!title.isEmpty()) {
            queryWrapper.like(Project::getProjectName,title);
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
                String Content = JSONUtil.toJsonStr(item.getMaterialBill());
                List<stockSelectVo> contentVoList = JSONUtil.toList(Content, stockSelectVo.class);

                vo.setMaterialBill(contentVoList);
                if(!ObjectUtil.isEmpty(item.getCategoryId())){
                    ProjectCategory one = categoryService.getById(item.getCategoryId());
                    if(!ObjectUtil.isEmpty(one)){
                        vo.setCategory(one.getCategoryName());
                    }
                }else{
                    vo.setCategory("");
                }
                if(!ObjectUtil.isEmpty(item.getSupervisorId())){
                    vo.setSupervisorName(deployeeService.getNameByUserId(item.getSupervisorId()));
                }else{
                    vo.setSupervisorName("");
                }
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
        List<ProjectVo> projectVos = addOrderId(resultList, pageNum, pageSize);
        resultPage.setRecords(projectVos);

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
            ProjectCategory one = categoryService.getById(item.getCategoryId());
            if(!ObjectUtil.isEmpty(one)){
                vo.setCategory(one.getCategoryName());
            }
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

    @Override
    public List<Integer> getIdsByName(String title) {

        LambdaQueryWrapper<Project> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Project::getProjectName,title);
        List<Project> list = list(queryWrapper);
        return list.stream().map(Project::getProjectId).collect(Collectors.toList());
    }

    @Override
    public ResponseResult getProjectListByCurrentId(PageQueryDto queryDto, HttpServletRequest request) {

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

        Page<Project> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Project> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Project::getProjectStatus,0);
        queryWrapper.eq(Project::getIsPrivate,0);
        //排序
        if (sort.equals("+id")) {
            queryWrapper.orderByAsc(Project::getProjectId);
        } else {
            queryWrapper.orderByDesc(Project::getProjectId);
        }

        //查询名称？
        if (!title.isEmpty()) {
            queryWrapper.like(Project::getProjectName,title);
        }

        //如果有类型的话 类型
        if (!ObjectUtil.isEmpty(type)) {
            //传过来的是productCategoryId,需要去产品表下找属于这个产品类型的产品 id数组
            queryWrapper.eq(Project::getCategoryId,type);
        }

        String authorization = request.getHeader("Authorization");
        if(!ObjectUtil.isEmpty(authorization)){
            System.out.println(authorization);
            String token = authorization.split(" ")[1];
            String id = JwtUtils.getMemberIdByJwtToken(token);
            queryWrapper.eq(Project::getSupervisorId,id).
                    or().eq(Project::getCreatedUser,id).
                    or().eq(Project::getMonitorId,id).
                    or().eq(Project::getExaminerId,id);
        }else{
            throw new SystemException(AppHttpCodeEnum.TOKEN_PARSE_ERRPE);
        }

        IPage<Project> recordPage = page(page, queryWrapper);
        //相同的typeId 和runId的只显示最早的createdTime的数据

        PageVo<List<ProjectVo>> result = new PageVo<>();

        List<ProjectVo> resultList = new ArrayList<>();
        try {
            resultList = recordPage.getRecords().stream().map((item) -> {
                ProjectVo vo = BeanCopyUtils.copyBean(item, ProjectVo.class);
                //类型名称?
                String Content = JSONUtil.toJsonStr(item.getMaterialBill());
                List<stockSelectVo> contentVoList = JSONUtil.toList(Content, stockSelectVo.class);

                vo.setMaterialBill(contentVoList);
                if(!ObjectUtil.isEmpty(item.getCategoryId())){
                    ProjectCategory one = categoryService.getById(item.getCategoryId());
                    if(!ObjectUtil.isEmpty(one)){
                        vo.setCategory(one.getCategoryName());
                    }
                }else{
                    vo.setCategory("");
                }
                if(!ObjectUtil.isEmpty(item.getSupervisorId())){
                    vo.setSupervisorName(deployeeService.getNameByUserId(item.getSupervisorId()));
                }else{
                    vo.setSupervisorName("");
                }
                //设置是否延期
                if( !ObjectUtil.isNull(item.getCompletedTime()) && item.getCompletedTime().getTime() < item.getEndTime().getTime()){
                    vo.setIsLate(1);
                }else{
                    vo.setIsLate(0);
                }
                return vo;
            }).collect(Collectors.toList());
            List<ProjectVo> projectVos = addOrderId(resultList, pageNum, pageSize);
            result.setData(projectVos);
            result.setTotal((int) recordPage.getTotal());
            return ResponseResult.okResult(result);
        } catch (Exception e) {
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }
    }

    @Override
    public ResponseResult getPrivateProjectList(PageQueryDto queryDto, HttpServletRequest request) {
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

        Page<Project> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Project> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Project::getIsPrivate,1);
        //排序
        if (sort.equals("+id")) {
            queryWrapper.orderByAsc(Project::getProjectId);
        } else {
            queryWrapper.orderByDesc(Project::getProjectId);
        }

        //查询名称？
        if (!title.isEmpty()) {
            queryWrapper.like(Project::getProjectName,title);
        }

        //如果有类型的话 类型
        if (!ObjectUtil.isEmpty(type)) {
            //传过来的是productCategoryId,需要去产品表下找属于这个产品类型的产品 id数组
            queryWrapper.eq(Project::getCategoryId,type);
        }

        IPage<Project> recordPage = page(page, queryWrapper);
        //相同的typeId 和runId的只显示最早的createdTime的数据

        PageVo<List<ProjectVo>> result = new PageVo<>();

        List<ProjectVo> resultList = new ArrayList<>();
        try {
            resultList = recordPage.getRecords().stream().map((item) -> {
                ProjectVo vo = BeanCopyUtils.copyBean(item, ProjectVo.class);
                //类型名称?
                String Content = JSONUtil.toJsonStr(item.getMaterialBill());
                List<stockSelectVo> contentVoList = JSONUtil.toList(Content, stockSelectVo.class);

                vo.setMaterialBill(contentVoList);
                if(!ObjectUtil.isEmpty(item.getCategoryId())){
                    ProjectCategory one = categoryService.getById(item.getCategoryId());
                    if(!ObjectUtil.isEmpty(one)){
                        vo.setCategory(one.getCategoryName());
                    }
                }else{
                    vo.setCategory("");
                }
                if(!ObjectUtil.isEmpty(item.getSupervisorId())){
                    vo.setSupervisorName(deployeeService.getNameByUserId(item.getSupervisorId()));
                }else{
                    vo.setSupervisorName("");
                }
                //设置是否延期
                if( !ObjectUtil.isNull(item.getCompletedTime()) && item.getCompletedTime().getTime() < item.getEndTime().getTime()){
                    vo.setIsLate(1);
                }else{
                    vo.setIsLate(0);
                }
                return vo;
            }).collect(Collectors.toList());
            List<ProjectVo> projectVos = addOrderId(resultList, pageNum, pageSize);
            result.setData(projectVos);
            result.setTotal((int) recordPage.getTotal());
            return ResponseResult.okResult(result);
        } catch (Exception e) {
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }
    }

    @Override
    public ResponseResult getProjectListForBackend(PageQueryDto queryDto) {
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
            queryWrapper.like(Project::getProjectName,title);
        }

        //如果有类型的话 类型
        if (!ObjectUtil.isEmpty(type)) {
            //传过来的是productCategoryId,需要去产品表下找属于这个产品类型的产品 id数组
            queryWrapper.eq(Project::getCategoryId,type);
        }

        IPage<Project> recordPage = page(page, queryWrapper);
        //相同的typeId 和runId的只显示最早的createdTime的数据

        PageVo<List<ProjectVo>> result = new PageVo<>();

        List<ProjectVo> resultList = new ArrayList<>();
        try {
            resultList = recordPage.getRecords().stream().map(this::copyPropertyToVo).collect(Collectors.toList());
            List<ProjectVo> projectVos = addOrderId(resultList, pageNum, pageSize);
            result.setData(projectVos);
            result.setTotal((int) recordPage.getTotal());
            return ResponseResult.okResult(result);
        } catch (Exception e) {
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }
    }

    public ProjectVo copyPropertyToVo(Project project){

        ProjectVo vo = BeanCopyUtils.copyBean(project, ProjectVo.class);
        //类型名称?
        String Content = JSONUtil.toJsonStr(project.getMaterialBill());
        List<stockSelectVo> contentVoList = JSONUtil.toList(Content, stockSelectVo.class);

        vo.setMaterialBill(contentVoList);
        if(!ObjectUtil.isEmpty(project.getCategoryId())){
            ProjectCategory one = categoryService.getById(project.getCategoryId());
            if(!ObjectUtil.isEmpty(one)){
                vo.setCategory(one.getCategoryName());
            }
        }else{
            vo.setCategory("");
        }
        if(!ObjectUtil.isEmpty(project.getSupervisorId())){
            vo.setSupervisorName(deployeeService.getNameByUserId(project.getSupervisorId()));
        }else{
            vo.setSupervisorName("");
        }
        //设置是否延期
        if( !ObjectUtil.isNull(project.getCompletedTime()) && project.getCompletedTime().getTime() < project.getEndTime().getTime()){
            vo.setIsLate(1);
        }else{
            vo.setIsLate(0);
        }

        return vo;
    }

    private List<ProjectVo> addOrderId(List<ProjectVo> list, Integer pageNum, Integer pageSize){
        if (!ObjectUtil.isEmpty(pageNum) && !ObjectUtil.isEmpty(pageSize)) {
            for (int i = 0 ; i < list.size() ; i++){
                list.get(i).setOrderId((pageNum - 1) * pageSize + i + 1);
            }
        }
        return list;
    }

}




