package com.gdproj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Task;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.TaskMapper;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.DeployeeService;
import com.gdproj.service.TaskService;
import com.gdproj.utils.AesUtil;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.utils.JwtUtils;
import com.gdproj.vo.PageVo;
import com.gdproj.vo.TaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
    public IPage<TaskVo> getTaskList(PageQueryDto pageDto) {
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
        //设置时间 年 月 日
        //模糊查询时间
        if(time != null){
            queryWrapper.like(Task::getTaskTime,time);
        }
        //模糊查询任务名称
        if(!title.isEmpty()){
            queryWrapper.like(Task::getTaskName,title);
        }
        //如果有类型的话
        if(!ObjectUtil.isEmpty(type)){
            queryWrapper.eq(Task::getCategoryId,type);
        }

        IPage<Task> taskPage = taskMapper.selectPage(page, queryWrapper);

        Page<TaskVo> resultPage = new Page<>();

        List<TaskVo> resultList = new ArrayList<>();

        //结果里的部门 和用户都返回成string；
        try {
            resultList = taskPage.getRecords().stream().map((item) -> {

                TaskVo taskVo = BeanCopyUtils.copyBean(item, TaskVo.class);
                if(!ObjectUtil.isEmpty(item.getCreatedUser())){
                    taskVo.setUsername(deployeeService.getNameByUserId(item.getCreatedUser()));
                }

                //执行人
                if(!ObjectUtil.isEmpty(item.getExecutorIds())){
                    List<String> names = new ArrayList<>();
                    for(Integer id : item.getExecutorIds()){
                        names.add(deployeeService.getNameByUserId(id));
                    }
                    taskVo.setExecutorUsername(names);
                }

                //创建人
                if(!ObjectUtil.isEmpty(item.getApplicantId())){
                    taskVo.setAssignedUsername(deployeeService.getNameByUserId(item.getApplicantId()));
                }

                if(!ObjectUtil.isEmpty(item.getTaskContacts())){
                    taskVo.setTaskContacts(AesUtil.decrypt(item.getTaskContacts(),AesUtil.key128));
                }

                return taskVo;

            }).collect(Collectors.toList());
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }
        List<TaskVo> taskVos = addOrderId(resultList, pageNum, pageSize);
        resultPage.setRecords(taskVos);

        resultPage.setTotal(taskPage.getTotal());

        return resultPage;

    }

    @Override
    public ResponseResult getTaskListByCurrentId(PageQueryDto queryDto, HttpServletRequest request) {

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

        Page<Task> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Task> queryWrapper = new LambdaQueryWrapper<>();
        //排序
        if (sort.equals("+id")) {
            queryWrapper.orderByAsc(Task::getTaskId);
        } else {
            queryWrapper.orderByDesc(Task::getTaskId);
        }

        //查询名称？
        if(!title.isEmpty()){
            queryWrapper.like(Task::getTaskName,title);
        }

        //如果有类型的话 类型
        if (!ObjectUtil.isEmpty(type)) {
            //传过来的是productCategoryId,需要去产品表下找属于这个产品类型的产品 id数组
            queryWrapper.eq(Task::getCategoryId, type);
        }

        String authorization = request.getHeader("Authorization");
        System.out.println(authorization);
        String token = authorization.split(" ")[1];
        String id = JwtUtils.getMemberIdByJwtToken(token);
        // TODO 如何使用 lamdbaquerywrapper 判断数据库字段中的数组 是否含有某个元素
//        if(!ObjectUtil.isEmpty(authorization)){
//            System.out.println(authorization);
//            String token = authorization.split(" ")[1];
//            String id = JwtUtils.getMemberIdByJwtToken(token);
//            queryWrapper.eq(Task::getCreatedUser,id).or().apply("FIND_IN_SET({0}, executor_ids)", id);
//            TODO
//        }else{
//            throw new SystemException(AppHttpCodeEnum.TOKEN_PARSE_ERRPE);
//        }

        IPage<Task> recordPage = page(page, queryWrapper);
        //相同的typeId 和runId的只显示最早的createdTime的数据
        Integer i1 = Integer.valueOf(id);
        recordPage.setRecords(recordPage.getRecords().stream().filter(item->item.getExecutorIds().contains(i1) || Objects.equals(item.getCreatedUser(), i1)).collect(Collectors.toList()));
        PageVo<List<TaskVo>> result = new PageVo<>();

        List<TaskVo> resultList = new ArrayList<>();
        try {
            resultList = recordPage.getRecords().stream().map((item) -> {
                TaskVo vo = BeanCopyUtils.copyBean(item, TaskVo.class);
                //类型名称?
                if(!ObjectUtil.isEmpty(item.getCreatedUser())){
                    vo.setUsername(deployeeService.getNameByUserId(item.getCreatedUser()));
                }
                //执行人
                if(!ObjectUtil.isEmpty(item.getExecutorIds())){
                    ArrayList<String> names = new ArrayList<>();
                    item.getExecutorIds().forEach((i) -> {
                        if(!ObjectUtil.isEmpty(i)){
                            String name = deployeeService.getNameByUserId(i);
                            names.add(name+ " ");
                        }
                    });
                    vo.setExecutorUsername(names);
                }

                //创建人
                if(!ObjectUtil.isEmpty(item.getApplicantId())){
                    vo.setAssignedUsername(deployeeService.getNameByUserId(item.getApplicantId()));
                }

                if(!ObjectUtil.isEmpty(item.getTaskContacts())){
                    vo.setTaskContacts(AesUtil.decrypt(item.getTaskContacts(),AesUtil.key128));
                }
                return vo;
            }).collect(Collectors.toList());
            List<TaskVo> taskVos = addOrderId(resultList, pageNum, pageSize);
            result.setData(taskVos);
            result.setTotal( recordPage.getRecords().size());
            return ResponseResult.okResult(result);
        } catch (Exception e) {
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }
    }
    private List<TaskVo> addOrderId(List<TaskVo> list, Integer pageNum, Integer pageSize){
        if (!ObjectUtil.isEmpty(pageNum) && !ObjectUtil.isEmpty(pageSize)) {
            for (int i = 0 ; i < list.size() ; i++){
                list.get(i).setOrderId((pageNum - 1) * pageSize + i + 1);
            }
        }
        return list;
    }
}




