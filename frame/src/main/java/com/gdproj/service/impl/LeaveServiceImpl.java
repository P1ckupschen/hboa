package com.gdproj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Leave;
import com.gdproj.entity.Sign;
import com.gdproj.service.DepartmentService;
import com.gdproj.service.DeployeeService;
import com.gdproj.service.leaveCategoryService;
import com.gdproj.service.LeaveService;
import com.gdproj.mapper.LeaveMapper;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.leaveVo;
import com.gdproj.vo.signVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
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
    LeaveMapper leaveMapper;

    @Override
    public IPage<leaveVo> getLeaveList(pageDto pageDto) {
        Integer type = pageDto.getType();
        String time = pageDto.getTime();
        String sort = pageDto.getSort();
        String title = pageDto.getTitle();
        Integer pageNum = pageDto.getPageNum();
        Integer pageSize = pageDto.getPageSize();

        Page<Leave> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Leave> queryWrapper = new LambdaQueryWrapper<>();

        //排序
        if(sort.equals("+id")){
            queryWrapper.orderByAsc(Leave::getLeaveId);
        }else{
            queryWrapper.orderByDesc(Leave::getLeaveId);
        }
        //如果根据部门分类，有一定几率会与模糊人民冲突
        if(!Objects.isNull(type) && title.isEmpty()){
            queryWrapper.in(Leave::getLeaveDepartment,type);
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
            queryWrapper.in(Leave::getLeaveUser,ids);
            //通过ids去找所有符合ids的对象 sign;
        }

        IPage<Leave> leavePage = leaveMapper.selectPage(page, queryWrapper);

        Page<leaveVo> resultPage = new Page<>();
        //结果里的部门 和用户都返回成string；
        List<leaveVo> resultList = leavePage.getRecords().stream().map((item) -> {
            leaveVo leavevo = BeanCopyUtils.copyBean(item, leaveVo.class);

            //人员
            leavevo.setLeaveUser(deployeeService.getNameByUserId(item.getLeaveUser()));

            //请假类型
            leavevo.setCategoryName(leaveCategoryService.getById(item.getCategoryId()).getCategoryName());

            //部门
            leavevo.setLeaveDepartment(departmentService.getDepartmentNameByDepartmentId(item.getLeaveDepartment()));

            return leavevo;

        }).collect(Collectors.toList());

        resultPage.setRecords(resultList);

        resultPage.setTotal(leavePage.getTotal());


        return resultPage;
    }
}




