package com.gdproj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Epiboly;
import com.gdproj.entity.Project;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.EpibolyMapper;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.EpibolyService;
import com.gdproj.service.ProjectService;
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
* @description 针对表【sys_epiboly】的数据库操作Service实现
* @createDate 2023-10-05 08:41:52
*/
@Service
public class EpibolyServiceImpl extends ServiceImpl<EpibolyMapper, Epiboly>
    implements EpibolyService {

    @Autowired
    ProjectService projectService;

    @Override
    public IPage<EpibolyVo> getEpibolyList(PageQueryDto pageDto) {
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

        Page<Epiboly> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Epiboly> queryWrapper = new LambdaQueryWrapper<>();
        //排序
        if (sort.equals("+id")) {
            queryWrapper.orderByAsc(Epiboly::getEpibolyId);
        } else {
            queryWrapper.orderByDesc(Epiboly::getEpibolyId);
        }

        //查询名称？
        if (!title.isEmpty()) {
            queryWrapper.eq(Epiboly::getEpibolyId,title);
        }

        //如果有类型的话 类型
//        if (!ObjectUtil.isEmpty(type)) {
//            //传过来的是productCategoryId,需要去产品表下找属于这个产品类型的产品 id数组
//            queryWrapper.eq(Epiboly::getCategoryId,type);
//        }
        IPage<Epiboly> recordPage = page(page, queryWrapper);

        Page<EpibolyVo> resultPage = new Page<>();

        List<EpibolyVo> resultList = new ArrayList<>();
        try {

            resultList = recordPage.getRecords().stream().map((item) -> {

                EpibolyVo vo = BeanCopyUtils.copyBean(item, EpibolyVo.class);

                String Content = JSONUtil.toJsonStr(item.getMaterialBill());

                List<stockSelectVo> contentVoList = JSONUtil.toList(Content, stockSelectVo.class);

                vo.setMaterialBill(contentVoList);

                Project projectInfo = projectService.getById(item.getProjectId());

                ProjectVo projectVo = BeanCopyUtils.copyBean(projectInfo, ProjectVo.class);

                vo.setProject(projectVo);
                //设置是否延期
                return vo;
            }).collect(Collectors.toList());

            resultPage.setRecords(resultList);

            resultPage.setTotal(recordPage.getTotal());

            return resultPage;
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }

    }

    @Override
    public List<SelectVo> getListForSelect() {

        List<Epiboly> list = list();

        List<SelectVo> collect = list.stream().map((item) -> {
            SelectVo vo = new SelectVo();
            vo.setId(item.getEpibolyId());
            vo.setName(item.getEpibolyName());
            return vo;
        }).collect(Collectors.toList());
        return collect;

    }

    @Override
    public ResponseResult getEpibolyListByCurrentId(PageQueryDto queryDto, HttpServletRequest request) {

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

        Page<Epiboly> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Epiboly> queryWrapper = new LambdaQueryWrapper<>();
        //排序
        if (sort.equals("+id")) {
            queryWrapper.orderByAsc(Epiboly::getEpibolyId);
        } else {
            queryWrapper.orderByDesc(Epiboly::getEpibolyId);
        }

        //查询名称？
        if (!title.isEmpty()) {
            queryWrapper.eq(Epiboly::getEpibolyName,title);
        }


        String authorization = request.getHeader("Authorization");
        if(!ObjectUtil.isEmpty(authorization)){
            System.out.println(authorization);
            String token = authorization.split(" ")[1];
            String id = JwtUtils.getMemberIdByJwtToken(token);
            queryWrapper.eq(Epiboly::getSupervisorId,id).or().eq(Epiboly::getCreatedUser,id);
        }else{
            throw new SystemException(AppHttpCodeEnum.TOKEN_PARSE_ERRPE);
        }

        IPage<Epiboly> recordPage = page(page, queryWrapper);
        //相同的typeId 和runId的只显示最早的createdTime的数据

        PageVo<List<EpibolyVo>> result = new PageVo<>();

        List<EpibolyVo> resultList = new ArrayList<>();
        try {
            resultList = recordPage.getRecords().stream().map((item) -> {
                EpibolyVo vo = BeanCopyUtils.copyBean(item, EpibolyVo.class);
                //类型名称?
                String Content = JSONUtil.toJsonStr(item.getMaterialBill());
                List<stockSelectVo> contentVoList = JSONUtil.toList(Content, stockSelectVo.class);

                vo.setMaterialBill(contentVoList);

                Project projectInfo = projectService.getById(item.getProjectId());

                ProjectVo projectVo = BeanCopyUtils.copyBean(projectInfo, ProjectVo.class);

                vo.setProject(projectVo);

                return vo;
            }).collect(Collectors.toList());
            result.setData(resultList);
            result.setTotal((int) recordPage.getTotal());
            return ResponseResult.okResult(result);
        } catch (Exception e) {
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }
    }
}




