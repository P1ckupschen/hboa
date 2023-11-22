package com.gdproj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Leave;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.LeaveMapper;
import com.gdproj.service.*;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.LeaveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【sys_leave】的数据库操作Service实现
* @createDate 2023-09-14 15:04:32
*/
@Service
public class LeaveServiceImpl extends ServiceImpl<LeaveMapper, Leave>
    implements LeaveService{

    @Autowired
    DeployeeService deployeeService;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    leaveCategoryService leaveCategoryService;

    @Autowired
    FlowService flowService;


    @Override
    public IPage<LeaveVo> getLeaveList(PageQueryDto pageDto) {
        Integer type = pageDto.getType();
        Integer departmentId = pageDto.getDepartmentId();
        String time = pageDto.getTime();
        String sort = pageDto.getSort();
        String title = pageDto.getTitle();
        Integer pageNum = pageDto.getPageNum();
        Integer pageSize = pageDto.getPageSize();

        Page<Leave> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Leave> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Leave::getIsDeleted,0);
        //排序
        if(sort.equals("+id")){
            queryWrapper.orderByAsc(Leave::getLeaveId);
        }else{
            queryWrapper.orderByDesc(Leave::getLeaveId);
        }
        //如果根据部门分类，有一定几率会与模糊人民冲突
        if(!ObjectUtil.isEmpty(departmentId) && title.isEmpty()){
            List<Integer> ids = deployeeService.getIdsByDepartmentId(pageDto.getDepartmentId());
            if(ObjectUtil.isEmpty(ids)){
                queryWrapper.in(Leave::getUserId,0);
            }else{
                queryWrapper.in(Leave::getUserId,ids);
            }
        }
        //设置时间 年 月 日
        //模糊查询时间
        if(time != null){
            queryWrapper.like(Leave::getStartTime,time);
        }

        //模糊查询人名
        if(!title.isEmpty()){
            //如果有模糊查询的时间 先通过查title 的用户ids
            List<Integer> ids = deployeeService.getIdsByTitle(title);
            if(!ObjectUtil.isEmpty(ids)){
                queryWrapper.in(Leave::getUserId, ids);
            }else{
                queryWrapper.in(Leave::getUserId,0);
            }
            //通过ids去找所有符合ids的对象 sign;
        }

        if(!ObjectUtil.isEmpty(type)){
            queryWrapper.eq(Leave::getCategoryId,type);
        }

        IPage<Leave> leavePage = page(page, queryWrapper);

        Page<LeaveVo> resultPage = new Page<>();

        List<LeaveVo> resultList = new ArrayList<>();

        //结果里的部门 和用户都返回成string；
        try {
            resultList = leavePage.getRecords().stream().map((item) -> {
                LeaveVo leavevo = BeanCopyUtils.copyBean(item, LeaveVo.class);

                //人员
                if(!ObjectUtil.isEmpty(item.getUserId())){
                    leavevo.setUsername(deployeeService.getNameByUserId(item.getUserId()));
                    leavevo.setDepartment(deployeeService.getDepartmentNameByUserId(item.getUserId()));
                    leavevo.setDepartmentId(deployeeService.getDepartmentIdByUserId(item.getUserId()));
                }else{
                    leavevo.setUsername("");
                    leavevo.setDepartment("");
                }

                //请假类型
                if(!ObjectUtil.isEmpty(item.getCategoryId())){
                    leavevo.setCategory(leaveCategoryService.getById(item.getCategoryId()).getCategoryName());
                }else{
                    leavevo.setCategory("");
                }

                //部门
//            leavevo.setDepartment(departmentService.getDepartmentNameByDepartmentId(item.getDepartmentId()));




                long btime = 0;
                double v = 0;
                //请假总时长 TODO如果向上取整
                if(ObjectUtil.isEmpty(item.getStartTime()) || ObjectUtil.isEmpty(item.getEndTime())){
                    btime = 0;
                }else{
                    btime = item.getEndTime().getTime() - item.getStartTime().getTime();
                    v = btime / 1000 / 60 / 60 /24.0;
                }

                int round = (int) Math.round(v);
                leavevo.setLeaveDays(String.valueOf(round));

                return leavevo;

            }).collect(Collectors.toList());
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }

        resultPage.setRecords(resultList);

        resultPage.setTotal(leavePage.getTotal());


        return resultPage;
    }

    @Override
    public boolean insertLeave(Leave insertLeave) {

        boolean f =false;
        boolean o =save(insertLeave);
        if(o){
            f = flowService.insertFlow(insertLeave);
        }else{
            throw new SystemException(AppHttpCodeEnum.INSERT_ERROR);
        }
        //同时更新flow 表 增加一条flow数据


        return o && f;
    }
}




