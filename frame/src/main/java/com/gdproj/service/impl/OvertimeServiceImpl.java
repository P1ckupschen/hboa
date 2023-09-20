package com.gdproj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Leave;
import com.gdproj.entity.Overtime;
import com.gdproj.entity.Report;
import com.gdproj.service.DepartmentService;
import com.gdproj.service.DeployeeService;
import com.gdproj.service.OvertimeService;
import com.gdproj.mapper.OvertimeMapper;
import com.gdproj.service.overtimeCategoryService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.leaveVo;
import com.gdproj.vo.overtimeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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
    OvertimeMapper overtimeMapper;

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
        if(!Objects.isNull(departmentId) && title.isEmpty()){
            queryWrapper.in(Overtime::getDepartmentId,departmentId);
        }
        //设置时间 年 月 日
        //模糊查询时间
        if(time != null){
            queryWrapper.like(Overtime::getStartTime,time);
        }

        //模糊查询人名
        if(!title.isEmpty()){
            //如果有模糊查询的时间 先通过查title 的用户ids
            List<Integer> ids = deployeeService.getIdsByTitle(title);
            queryWrapper.in(Overtime::getUserId,ids);
            //通过ids去找所有符合ids的对象 sign;
        }

        if(!ObjectUtil.isEmpty(type)){
            queryWrapper.eq(Overtime::getCategoryId,type);
        }

        IPage<Overtime> overtimePage = overtimeMapper.selectPage(page, queryWrapper);

        Page<overtimeVo> resultPage = new Page<>();
        //结果里的部门 和用户都返回成string；
        List<overtimeVo> resultList = overtimePage.getRecords().stream().map((item) -> {
            overtimeVo overtimevo = BeanCopyUtils.copyBean(item, overtimeVo.class);

            //人员
            overtimevo.setUsername(deployeeService.getNameByUserId(item.getUserId()));

            //加班类型
            overtimevo.setCategory(overtimecategoryService.getById(item.getCategoryId()).getCategoryName());

            //部门
//            overtimevo.setDepartment(departmentService.getDepartmentNameByDepartmentId(item.getDepartmentId()));

            overtimevo.setDepartment(deployeeService.getDepartmentNameByUserId(item.getUserId()));

            overtimevo.setDepartmentId(deployeeService.getDepartmentIdByUserId(item.getUserId()));

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
            overtimevo.setOvertimeDays(round);
            return overtimevo;

        }).collect(Collectors.toList());

        resultPage.setRecords(resultList);

        resultPage.setTotal(overtimePage.getTotal());


        return resultPage;
    }
}




