package com.gdproj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Report;
import com.gdproj.entity.ReportCategory;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.ReportMapper;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.DeployeeService;
import com.gdproj.service.ReportService;
import com.gdproj.service.reportCategoryService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.ReportVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【sys_report】的数据库操作Service实现
* @createDate 2023-09-19 09:39:07
*/
@Service
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report>
    implements ReportService {

    @Autowired
    DeployeeService deployeeService;

    @Autowired
    reportCategoryService categoryService;

    @Autowired
    ReportMapper reportMapper;

    @Override
    public IPage<ReportVo> getReportList(PageQueryDto pageDto) {

        //类型
        Integer type = pageDto.getType();
        //部门Id
        Integer departmentId = pageDto.getDepartmentId();
        String time = pageDto.getTime();
        String sort = pageDto.getSort();
        String title = pageDto.getTitle();
        Integer pageNum = pageDto.getPageNum();
        Integer pageSize = pageDto.getPageSize();

        Page<Report> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Report> queryWrapper = new LambdaQueryWrapper<>();

        //排序
        if(sort.equals("+id")){
            queryWrapper.orderByAsc(Report::getReportId);
        }else{
            queryWrapper.orderByDesc(Report::getReportId);
        }
        //如果根据部门分类，有一定几率会与模糊人民冲突
        if(!Objects.isNull(departmentId) && title.isEmpty()){
            //如果没有对象没有部门id属性就找到对应id的部门所以的员工的userid
            List<Integer> userIds = deployeeService.getIdsByDepartmentId(departmentId);
            if(ObjectUtil.isEmpty(userIds)){
                queryWrapper.in(Report::getUserId,0);
            }else{
                queryWrapper.in(Report::getUserId,userIds);
            }
        }
        //设置时间 年 月 日
        //模糊查询时间
        if(time != null){
            queryWrapper.like(Report::getCreatedTime,time);
        }

        //模糊查询人名
        if(!title.isEmpty()){
            //如果有模糊查询的时间 先通过查title 的用户ids
            List<Integer> ids = deployeeService.getIdsByTitle(title);
            //通过ids去找所有符合ids的对象 sign;
            if(!ObjectUtil.isEmpty(ids)){
                queryWrapper.in(Report::getUserId, ids);
            }else{
                queryWrapper.in(Report::getUserId,0);
            }
        }

        //所属类型
        if(!ObjectUtil.isEmpty(type)){
            queryWrapper.eq(Report::getCategoryId,type);
        }
        IPage<Report> reportPage = reportMapper.selectPage(page, queryWrapper);

        Page<ReportVo> resultPage = new Page<>();

        List<ReportVo> resultList = new ArrayList<>();
        //结果里的部门 和用户都返回成string；
        try {
            resultList = reportPage.getRecords().stream().map((item) -> {
                ReportVo reportvo = BeanCopyUtils.copyBean(item, ReportVo.class);

                //人员
                if(!ObjectUtil.isEmpty(item.getUserId())){
                    reportvo.setUsername(deployeeService.getNameByUserId(item.getUserId()));
                    reportvo.setDepartment(deployeeService.getDepartmentNameByUserId(item.getUserId()));
                }else{
                    reportvo.setUsername("");
                    reportvo.setDepartment("");
                }

                //类型
                if(!ObjectUtil.isEmpty(item.getCategoryId())){
                    ReportCategory one = categoryService.getById(item.getCategoryId());
                    if(!ObjectUtil.isEmpty(one)){
                        reportvo.setCategory(one.getCategoryName());
                    }
                }else{
                    reportvo.setCategory("");
                }


                return reportvo;

            }).collect(Collectors.toList());
            List<ReportVo> reportVos = addOrderId(resultList, pageNum, pageSize);
            resultPage.setRecords(reportVos);
            resultPage.setTotal(reportPage.getTotal());

            return resultPage;
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }


    }
    @Override
    public IPage<ReportVo> getReportListByCurrentId(PageQueryDto pageDto, String id) {
        //类型
        Integer type = pageDto.getType();
        //部门Id
        Integer departmentId = pageDto.getDepartmentId();
        String time = pageDto.getTime();
        String sort = pageDto.getSort();
        String title = pageDto.getTitle();
        Integer pageNum = pageDto.getPageNum();
        Integer pageSize = pageDto.getPageSize();

        Page<Report> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Report> queryWrapper = new LambdaQueryWrapper<>();

        //排序
        if(sort.equals("+id")){
            queryWrapper.orderByAsc(Report::getReportId);
        }else{
            queryWrapper.orderByDesc(Report::getReportId);
        }
        //模糊查询时间
        if(time != null){
            queryWrapper.like(Report::getCreatedTime,time);
        }
        queryWrapper.eq(Report::getUserId,id);

        //所属类型
        if(!ObjectUtil.isEmpty(type)){
            queryWrapper.eq(Report::getCategoryId,type);
        }

        IPage<Report> reportPage = reportMapper.selectPage(page, queryWrapper);

        Page<ReportVo> resultPage = new Page<>();

        List<ReportVo> resultList = new ArrayList<>();
        //结果里的部门 和用户都返回成string；
        try {
            resultList = reportPage.getRecords().stream().map((item) -> {
                ReportVo reportvo = BeanCopyUtils.copyBean(item, ReportVo.class);
                //人员
                if(!ObjectUtil.isEmpty(item.getUserId())){
                    reportvo.setUsername(deployeeService.getNameByUserId(item.getUserId()));
                    reportvo.setDepartment(deployeeService.getDepartmentNameByUserId(item.getUserId()));
                }else{
                    reportvo.setUsername("");
                    reportvo.setDepartment("");
                }

                //类型
                if(!ObjectUtil.isEmpty(item.getCategoryId())){
                    ReportCategory one = categoryService.getById(item.getCategoryId());
                    if(!ObjectUtil.isEmpty(one)){
                        reportvo.setCategory(one.getCategoryName());
                    }
                }else{
                    reportvo.setCategory("");
                }
                return reportvo;

            }).collect(Collectors.toList());
            List<ReportVo> reportVos = addOrderId(resultList, pageNum, pageSize);
            resultPage.setRecords(reportVos);
            resultPage.setTotal(reportPage.getTotal());
            return resultPage;
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }
    }

    @Override
    public ResponseResult getDetailById(Integer id) {

        return  ResponseResult.okResult(copyPropertyToVo(getById(id)));
    }

    public ReportVo copyPropertyToVo(Report report){
        ReportVo vo = BeanCopyUtils.copyBean(report, ReportVo.class);
        try {
            //人员
            if(!ObjectUtil.isEmpty(report.getUserId())){
                vo.setUsername(deployeeService.getNameByUserId(report.getUserId()));
                vo.setDepartment(deployeeService.getDepartmentNameByUserId(report.getUserId()));
            }else{
                vo.setUsername("");
                vo.setDepartment("");
            }
            //类型
            if(!ObjectUtil.isEmpty(report.getCategoryId())){
                ReportCategory one = categoryService.getById(report.getCategoryId());
                if(!ObjectUtil.isEmpty(one)){
                    vo.setCategory(one.getCategoryName());
                }
            }else{
                vo.setCategory("");
            }
            return vo;
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }
    }

    private List<ReportVo> addOrderId(List<ReportVo> list, Integer pageNum, Integer pageSize){
        if (!ObjectUtil.isEmpty(pageNum) && !ObjectUtil.isEmpty(pageSize)) {
            for (int i = 0 ; i < list.size() ; i++){
                list.get(i).setOrderId((pageNum - 1) * pageSize + i + 1);
            }
        }
        return list;
    }
}




