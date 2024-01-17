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
import com.gdproj.entity.Schedule;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.mapper.ScheduleMapper;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.DeployeeService;
import com.gdproj.service.EpibolyService;
import com.gdproj.service.ProjectService;
import com.gdproj.service.ScheduleService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.DailyUseContentVo;
import com.gdproj.vo.PageVo;
import com.gdproj.vo.ScheduleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【sys_schedule】的数据库操作Service实现
* @createDate 2023-12-12 14:39:34
*/
@Service
public class ScheduleServiceImpl extends ServiceImpl<ScheduleMapper, Schedule>
    implements ScheduleService {

    @Autowired
    ProjectService projectService;
    @Autowired
    EpibolyService epibolyService;

    @Autowired
    DeployeeService deployeeService;
    @Override
    public ResponseResult getScheduleList(PageQueryDto queryDto) {
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

        Page<Schedule> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Schedule> queryWrapper = new LambdaQueryWrapper<>();
        //排序
//        if (sort.equals("+id")) {
//            queryWrapper.orderByAsc(Schedule::getScheduleId);
//        } else {
//            queryWrapper.orderByDesc(Schedule::getScheduleId);
//        }

        if (!title.isEmpty()) {
            List<Integer> ids = projectService.getIdsByName(title);
            if(!ObjectUtil.isEmpty(ids)){
                queryWrapper.in(Schedule::getProjectId,ids);
            }else{
                queryWrapper.eq(Schedule::getProjectId,0);
            }
        }

        if(!ObjectUtil.isEmpty(time)){
            queryWrapper.like(Schedule::getPlanStartTime,time);
        }
        queryWrapper.orderByDesc(Schedule::getPlanStartTime);
        IPage<Schedule> recordPage = page(page, queryWrapper);

        List<ScheduleVo> collect = recordPage.getRecords().stream().map((item) -> {

            ScheduleVo vo = BeanCopyUtils.copyBean(item, ScheduleVo.class);
            String Content = JSONUtil.toJsonStr(item.getToolList());
            List<DailyUseContentVo> contentVoList = JSONUtil.toList(Content, DailyUseContentVo.class);
            vo.setToolList(contentVoList);
            if(item.getTypeId() == 1){
                Project one = projectService.getById(item.getProjectId());
                Integer supervisorId = one.getSupervisorId();
                vo.setSupervisorName(deployeeService.getNameByUserId(supervisorId));

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String start = "";
                String end = "";
                if(!ObjectUtil.isEmpty(item.getTrueStartTime())){
                    start = sdf.format(item.getTrueStartTime());
                }
                if(!ObjectUtil.isEmpty(item.getTrueEndTime())){
                    end = sdf.format(item.getTrueEndTime());
                }
                vo.setTimeStage(start+"--"+end);
                vo.setProjectName(projectService.getById(item.getProjectId()).getProjectName());
            }else{
                Epiboly one = epibolyService.getById(item.getProjectId());
                Integer supervisorId = one.getSupervisorId();
                vo.setSupervisorName(deployeeService.getNameByUserId(supervisorId));

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String start = "";
                String end = "";
                if(!ObjectUtil.isEmpty(item.getTrueStartTime())){
                    start = sdf.format(item.getTrueStartTime());
                }
                if(!ObjectUtil.isEmpty(item.getTrueEndTime())){
                    end = sdf.format(item.getTrueEndTime());
                }
                vo.setTimeStage(start+"--"+end);
                vo.setProjectName(epibolyService.getById(item.getProjectId()).getEpibolyName());
            }
            return vo;
        }).collect(Collectors.toList());

        PageVo<List<ScheduleVo>> pageList = new PageVo<>();

        pageList.setData(collect);

        pageList.setTotal((int) recordPage.getTotal());

        return ResponseResult.okResult(pageList);
    }

    @Override
    public ResponseResult insertSchedule(ScheduleVo vo) {

        Schedule schedule = BeanCopyUtils.copyBean(vo, Schedule.class);
        boolean save = save(schedule);
        if(save){
            return ResponseResult.okResult(save);
        }else{
            return ResponseResult.errorResult(AppHttpCodeEnum.INSERT_ERROR);
        }
    }

    @Override
    public ResponseResult updateSchedule(ScheduleVo vo) {
        Schedule schedule = BeanCopyUtils.copyBean(vo, Schedule.class);
        boolean update = updateById(schedule);
        if(update){
            return ResponseResult.okResult(update);
        }else{
            return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
        }
    }

    @Override
    public ResponseResult deleteSchedule(Integer id) {
        boolean remove = removeById(id);
        if(remove){
            return ResponseResult.okResult(remove);
        }else{
            return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);
        }
    }

    @Override
    public ResponseResult getScheduleByid(Integer id , Integer typeId) {

        LambdaQueryWrapper<Schedule> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Schedule::getPlanStartTime);
        if(typeId == 1){
            //为项目
            queryWrapper.eq(Schedule::getProjectId,id);
            List<Schedule> list = list(queryWrapper);
            List<ScheduleVo> collect = list.stream().map((info) -> {
                ScheduleVo vo = BeanCopyUtils.copyBean(info, ScheduleVo.class);

                String Content = JSONUtil.toJsonStr(info.getToolList());
                List<DailyUseContentVo> contentVoList = JSONUtil.toList(Content, DailyUseContentVo.class);
                vo.setToolList(contentVoList);
                Project one = projectService.getById(info.getProjectId());
                Integer supervisorId = one.getSupervisorId();
                vo.setSupervisorName(deployeeService.getNameByUserId(supervisorId));

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String start = "";
                String end = "";
                if (!ObjectUtil.isEmpty(info.getTrueStartTime())) {
                    start = sdf.format(info.getTrueStartTime());
                }
                if (!ObjectUtil.isEmpty(info.getTrueEndTime())) {
                    end = sdf.format(info.getTrueEndTime());
                }
                vo.setTimeStage(start + "--" + end);
                vo.setProjectName(projectService.getById(info.getProjectId()).getProjectName());
                return vo;
            }).collect(Collectors.toList());

            return ResponseResult.okResult(collect);

        }else{
            //为外包
            queryWrapper.eq(Schedule::getProjectId,id);
            List<Schedule> list = list(queryWrapper);
            List<ScheduleVo> collect = list.stream().map((info) -> {
                ScheduleVo vo = BeanCopyUtils.copyBean(info, ScheduleVo.class);

                String Content = JSONUtil.toJsonStr(info.getToolList());
                List<DailyUseContentVo> contentVoList = JSONUtil.toList(Content, DailyUseContentVo.class);
                vo.setToolList(contentVoList);
                Epiboly one = epibolyService.getById(info.getProjectId());
                Integer supervisorId = one.getSupervisorId();
                vo.setSupervisorName(deployeeService.getNameByUserId(supervisorId));

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String start = "";
                String end = "";
                if (!ObjectUtil.isEmpty(info.getTrueStartTime())) {
                    start = sdf.format(info.getTrueStartTime());
                }
                if (!ObjectUtil.isEmpty(info.getTrueEndTime())) {
                    end = sdf.format(info.getTrueEndTime());
                }
                vo.setTimeStage(start + "--" + end);
                vo.setProjectName(epibolyService.getById(info.getProjectId()).getEpibolyName());
                return vo;
            }).collect(Collectors.toList());
            return ResponseResult.okResult(collect);

        }

    }
}




