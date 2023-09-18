package com.gdproj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Department;
import com.gdproj.entity.Leave;
import com.gdproj.entity.Notify;
import com.gdproj.mapper.NotifyMapper;
import com.gdproj.service.DepartmentService;
import com.gdproj.service.DeployeeService;
import com.gdproj.service.NotifyService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.leaveVo;
import com.gdproj.vo.notifyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【sys_notify】的数据库操作Service实现
* @createDate 2023-09-15 15:27:55
*/
@Service
public class NotifyServiceImpl extends ServiceImpl<NotifyMapper, Notify>
    implements NotifyService {

    @Autowired
    DeployeeService deployeeService;

    @Autowired
    NotifyMapper notifyMapper;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    notifyCategoryServiceImpl notifyCategoryService;


    @Override
    public IPage<notifyVo> getNotifyList(pageDto pageDto) {

        Integer type = pageDto.getType();
        String time = pageDto.getTime();
        String sort = pageDto.getSort();
        String title = pageDto.getTitle();
        Integer pageNum = pageDto.getPageNum();
        Integer pageSize = pageDto.getPageSize();

        Page<Notify> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Notify> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Notify::getIsDeleted,0);
        //排序
        if(sort.equals("+id")){
            queryWrapper.orderByAsc(Notify::getNotifyId);
        }else{
            queryWrapper.orderByDesc(Notify::getNotifyId);
        }
        //如果根据部门分类，有一定几率会与模糊人民冲突
        if(!Objects.isNull(type) && title.isEmpty()){
            //如果没有对象没有部门id属性就找到对应id的部门所以的员工的userid
            List<Integer> userIds = deployeeService.getIdsByDepartmentId(type);
            queryWrapper.in(Notify::getUserId,userIds);
        }
        //设置时间 年 月 日
        //模糊查询时间
        if(time != null){
            queryWrapper.like(Notify::getCreatedTime,time);
        }

        //模糊查询人名
        if(!title.isEmpty()){
            //如果有模糊查询的时间 先通过查title 的用户ids
            List<Integer> ids = deployeeService.getIdsByTitle(title);
            queryWrapper.in(Notify::getUserId,ids);
            //通过ids去找所有符合ids的对象 sign;
        }

        IPage<Notify> notifyPage = notifyMapper.selectPage(page, queryWrapper);

        Page<notifyVo> resultPage = new Page<>();
        //结果里的部门 和用户都返回成string；
        List<notifyVo> resultList = notifyPage.getRecords().stream().map((item) -> {

            notifyVo notifyVo = BeanCopyUtils.copyBean(item, notifyVo.class);
            //人员
            notifyVo.setUsername(deployeeService.getNameByUserId(item.getUserId()));

            //公告类型
            notifyVo.setCategory(notifyCategoryService.getById(item.getCategoryId()).getCategoryName());

            //如果没有对象没有部门id属性就找到对应id的部门所以的员工的userid
            notifyVo.setDepartment(deployeeService.getDepartmentNameByUserId(item.getUserId()));

            notifyVo.setDepartmentId(deployeeService.getDepartmentIdByUserId(item.getUserId()));

            //公告审核人
            notifyVo.setExaminerName(deployeeService.getNameByUserId(item.getExaminerId()));

            return notifyVo;

        }).collect(Collectors.toList());

        resultPage.setRecords(resultList);

        resultPage.setTotal(notifyPage.getTotal());

        return resultPage;
    }

}




