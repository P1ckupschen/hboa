package com.gdproj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Report;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.ReportMapper;
import com.gdproj.service.DeployeeService;
import com.gdproj.service.ReportService;
import com.gdproj.service.reportCategoryService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.reportVo;
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
    public IPage<reportVo> getReportList(pageDto pageDto) {

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

        queryWrapper.eq(Report::getIsDeleted,0);
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
            queryWrapper.in(Report::getUserId,userIds);
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
            queryWrapper.in(Report::getUserId,ids);
            //通过ids去找所有符合ids的对象 sign;
        }

        //所属类型
        if(!ObjectUtil.isEmpty(type)){
            queryWrapper.eq(Report::getCategoryId,type);
        }
        IPage<Report> reportPage = reportMapper.selectPage(page, queryWrapper);

        Page<reportVo> resultPage = new Page<>();

        List<reportVo> resultList = new ArrayList<>();
        //结果里的部门 和用户都返回成string；
        try {
            resultList = reportPage.getRecords().stream().map((item) -> {
                reportVo reportvo = BeanCopyUtils.copyBean(item, reportVo.class);

                //人员
                reportvo.setUsername(deployeeService.getNameByUserId(item.getUserId()));

                //类型
                reportvo.setCategory(categoryService.getById(item.getCategoryId()).getCategoryName());

                //部门
                reportvo.setDepartment(deployeeService.getDepartmentNameByUserId(item.getUserId()));

                return reportvo;

            }).collect(Collectors.toList());

            resultPage.setRecords(resultList);
            resultPage.setTotal(reportPage.getTotal());

            return resultPage;
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }


    }
}




