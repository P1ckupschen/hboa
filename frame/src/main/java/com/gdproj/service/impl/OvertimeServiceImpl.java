package com.gdproj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Overtime;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.OvertimeMapper;
import com.gdproj.service.*;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.overtimeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【sys_overtime】的数据库操作Service实现
* @createDate 2023-09-14 15:04:32
*/
@Service
public class OvertimeServiceImpl extends ServiceImpl<OvertimeMapper, Overtime>
    implements OvertimeService {

    @Autowired
    DepartmentService departmentService;

    @Autowired
    DeployeeService deployeeService;

    @Autowired
    overtimeCategoryService overtimecategoryService;

    @Autowired
    FlowService flowService;

    @Override
    public IPage<overtimeVo> getOverTimeList(pageDto pageDto) {

        Integer type = pageDto.getType();
        Integer departmentId = pageDto.getDepartmentId();
        String time = pageDto.getTime();
        String sort = pageDto.getSort();
        String title = pageDto.getTitle();
        Integer pageNum = pageDto.getPageNum();
        Integer pageSize = pageDto.getPageSize();

        Page<Overtime> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Overtime> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Overtime::getIsDeleted,0);
        //排序
        if(sort.equals("+id")){
            queryWrapper.orderByAsc(Overtime::getOvertimeId);
        }else{
            queryWrapper.orderByDesc(Overtime::getOvertimeId);
        }
        //如果根据部门分类，有一定几率会与模糊人民冲突
        if(!ObjectUtil.isEmpty(departmentId) && title.isEmpty()){
            List<Integer> ids = deployeeService.getIdsByDepartmentId(pageDto.getDepartmentId());
            if(ObjectUtil.isEmpty(ids)){
                queryWrapper.in(Overtime::getExecutorId,0);
            }else{
                queryWrapper.in(Overtime::getExecutorId,ids);
            }
        }
        //模糊查询时间
        if(time != null){
            queryWrapper.like(Overtime::getStartTime,time);
        }

        //模糊查询人名
        if(!title.isEmpty()){
            //如果有模糊查询的时间 先通过查title 的用户ids
            List<Integer> ids = deployeeService.getIdsByTitle(title);
            queryWrapper.in(Overtime::getExecutorId,ids);
            //通过ids去找所有符合ids的对象 sign;
        }

        if(!ObjectUtil.isEmpty(type)){
            queryWrapper.eq(Overtime::getCategoryId,type);
        }

        IPage<Overtime> overtimePage = page(page, queryWrapper);

        Page<overtimeVo> resultPage = new Page<>();
        //结果里的部门 和用户都返回成string；
        List<overtimeVo> resultList =  new ArrayList<>();
        try {
            resultList = overtimePage.getRecords().stream().map((item) -> {
                overtimeVo overtimevo = BeanCopyUtils.copyBean(item, overtimeVo.class);

                //人员
                overtimevo.setUsername(deployeeService.getNameByUserId(item.getExecutorId()));

                //加班类型
                overtimevo.setCategory(overtimecategoryService.getById(item.getCategoryId()).getCategoryName());

                //部门
                overtimevo.setDepartment(deployeeService.getDepartmentNameByUserId(item.getExecutorId()));

                overtimevo.setDepartmentId(deployeeService.getDepartmentIdByUserId(item.getExecutorId()));

                long btime = 0;

                double v = 0;
                //加班总时长
                if(ObjectUtil.isEmpty(item.getStartTime()) || ObjectUtil.isEmpty(item.getEndTime())){
                    btime = 0;
                }else{
                    btime = item.getEndTime().getTime() - item.getStartTime().getTime();
                    v = btime / 1000 / 60 / 60 ;
                }

                int round = (int) Math.round(v);
                overtimevo.setOvertimeHours(round);
                return overtimevo;

            }).collect(Collectors.toList());
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }

        resultPage.setRecords(resultList);

        resultPage.setTotal(overtimePage.getTotal());


        return resultPage;
    }

    @Override
    public boolean insertOvertime(Overtime insertOvertime) {

        //插入overtime数据
        boolean f =false;
        boolean o =save(insertOvertime);
        if(o){
            f = flowService.insertFlow(insertOvertime);
        }else{
            throw new SystemException(AppHttpCodeEnum.INSERT_ERROR);
        }
        //同时更新flow 表 增加一条flow数据

        return o && f;

    }
}




