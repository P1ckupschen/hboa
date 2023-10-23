package com.gdproj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Task;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.TaskMapper;
import com.gdproj.service.DeployeeService;
import com.gdproj.service.TaskService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.taskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【sys_task】的数据库操作Service实现
* @createDate 2023-09-19 14:13:15
*/
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task>
    implements TaskService {

    @Autowired
    DeployeeService deployeeService;

    @Autowired
    TaskMapper taskMapper;


    @Override
    public IPage<taskVo> getTaskList(pageDto pageDto) {
        Integer type = pageDto.getType();
        Integer departmentId = pageDto.getDepartmentId();
        String time = pageDto.getTime();
        String sort = pageDto.getSort();
        String title = pageDto.getTitle();
        Integer pageNum = pageDto.getPageNum();
        Integer pageSize = pageDto.getPageSize();

        Page<Task> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Task> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Task::getIsDeleted,0);
        //排序
        if(sort.equals("+id")){
            queryWrapper.orderByAsc(Task::getTaskId);
        }else{
            queryWrapper.orderByDesc(Task::getTaskId);
        }
        //如果根据部门分类，有一定几率会与模糊人民冲突
        if(!Objects.isNull(departmentId) && title.isEmpty()){
            //如果没有对象没有部门id属性就找到对应id的部门所以的员工的userid
            List<Integer> userIds = deployeeService.getIdsByDepartmentId(departmentId);
            if(ObjectUtil.isEmpty(userIds)){
                queryWrapper.in(Task::getExecutorId,0);
            }else{
                queryWrapper.in(Task::getExecutorId,userIds);
            }
        }
        //设置时间 年 月 日
        //模糊查询时间
        if(time != null){
            queryWrapper.like(Task::getTaskTime,time);
        }

        //模糊查询人名
        if(!title.isEmpty()){
            //如果有模糊查询的时间 先通过查title 的用户ids
            List<Integer> ids = deployeeService.getIdsByTitle(title);
            queryWrapper.in(Task::getCreatedUser,ids);
            //通过ids去找所有符合ids的对象 sign;
        }

        //如果有类型的话

        if(!ObjectUtil.isEmpty(type)){
            queryWrapper.eq(Task::getCategoryId,type);
        }

        IPage<Task> taskPage = taskMapper.selectPage(page, queryWrapper);

        Page<taskVo> resultPage = new Page<>();

        List<taskVo> resultList = new ArrayList<>();
        //结果里的部门 和用户都返回成string；
        try {
            resultList = taskPage.getRecords().stream().map((item) -> {

                taskVo taskVo = BeanCopyUtils.copyBean(item, taskVo.class);
                //创建人
                taskVo.setUsername(deployeeService.getNameByUserId(item.getCreatedUser()));

                //执行人
                taskVo.setExecutorUsername(deployeeService.getNameByUserId(item.getExecutorId()));

                //指派人
                taskVo.setAssignedUsername(deployeeService.getNameByUserId(item.getApplicantId()));

                return taskVo;

            }).collect(Collectors.toList());
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }

        resultPage.setRecords(resultList);

        resultPage.setTotal(taskPage.getTotal());

        return resultPage;

    }
}




