package com.gdproj.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.*;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.OvertimeMapper;
import com.gdproj.service.*;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.utils.DownloadUtils;
import com.gdproj.vo.OvertimeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
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

    @Value("${ExcelFilePath}")
    String filePath;

    @Override
    public IPage<OvertimeVo> getOverTimeList(PageQueryDto pageDto) {

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
            if(!ObjectUtil.isEmpty(ids)){
                queryWrapper.in(Overtime::getExecutorId,ids);
            }else{
                queryWrapper.in(Overtime::getExecutorId,0);
            }
            //通过ids去找所有符合ids的对象 sign;
        }

        if(!ObjectUtil.isEmpty(type)){
            queryWrapper.eq(Overtime::getCategoryId,type);
        }

        IPage<Overtime> overtimePage = page(page, queryWrapper);

        Page<OvertimeVo> resultPage = new Page<>();
        //结果里的部门 和用户都返回成string；
        List<OvertimeVo> resultList =  new ArrayList<>();
        try {
            resultList = overtimePage.getRecords().stream().map((item) -> {
                OvertimeVo overtimevo = BeanCopyUtils.copyBean(item, OvertimeVo.class);

                //人员
//                if(!ObjectUtil.isEmpty( deployeeService.getById(item.getExecutorId())))
                if(!ObjectUtil.isEmpty(item.getExecutorId())){
                    overtimevo.setUsername(deployeeService.getNameByUserId(item.getExecutorId()));
                    overtimevo.setDepartment(deployeeService.getDepartmentNameByUserId(item.getExecutorId()));
                    overtimevo.setDepartmentId(deployeeService.getDepartmentIdByUserId(item.getExecutorId()));
                }else{
                    overtimevo.setUsername("");
                    overtimevo.setDepartment("");
                }

                //加班类型
                if(!ObjectUtil.isEmpty(item.getCategoryId())){
                    overtimevo.setCategory(overtimecategoryService.getById(item.getCategoryId()).getCategoryName());

                }else{
                    overtimevo.setCategory("");
                }

                long btime = 0;

                double v = 0;
                //加班总时长
                if(ObjectUtil.isEmpty(item.getStartTime()) || ObjectUtil.isEmpty(item.getEndTime())){
                    btime = 0;
                }else{
                    btime = item.getEndTime().getTime() - item.getStartTime().getTime();
                    System.out.println(btime);
                    v = btime / 1000 / 60 / 60 ;
                }
                System.out.println(v);
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

    @Override
    public void exportOvertimeExcel(List<Date> interval, HttpServletResponse response) {
        String fileName = filePath + System.currentTimeMillis() + ".xlsx";
        List<String> dateList = interval.stream().map((item) -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                return sdf.format(item);
            } catch (Exception e) {
                throw new SystemException(AppHttpCodeEnum.DATE_FORMAT_ERROR);
            }
        }).collect(Collectors.toList());
        List<Overtime> overtimeList = getOvertimeListByTimeInterval(dateList);
        // TODO 应该是depolyeeList？
        LambdaQueryWrapper<Deployee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Deployee::getDeployeeStatus , 1);
        List<Deployee> list = deployeeService.list(queryWrapper);
        List<OvertimeExcelEntity> collect = list.stream().map((item) -> {
            OvertimeExcelEntity oee = new OvertimeExcelEntity();
            oee.setUserId(item.getDeployeeId());
            oee.setUsername(item.getDeployeeName());
            oee.setStage(dateList.get(0) + "至" + dateList.get(1));
            oee.setOvertimeDays(countOvertimeDaysByIdAndInterval(item.getDeployeeId(), dateList));
            oee.setOvertimeHistory(getOvertimeListByIdAndInterval(item.getDeployeeId(), dateList));
            return oee;
        }).collect(Collectors.toList());
        EasyExcel.write(fileName, OvertimeExcelEntity.class).sheet("上班统计").doWrite(collect);
        DownloadUtils.downloadExcel(fileName,response);
    }

    private List<Overtime> getOvertimeListByTimeInterval(List<String> dateList) {
        LambdaQueryWrapper<Overtime> queryWrapper = new LambdaQueryWrapper<>();
        if(!ObjectUtil.isEmpty(dateList)){
            queryWrapper.between(Overtime::getStartTime, dateList.get(0), dateList.get(1));
            return list(queryWrapper);
        }else{
            throw new SystemException(AppHttpCodeEnum.DATE_FORMAT_ERROR);
        }
    }

    private String getOvertimeListByIdAndInterval(Integer deployeeId, List<String> dateList) {
        LambdaQueryWrapper<Overtime> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Overtime::getExecutorId,deployeeId);
        queryWrapper.eq(Overtime::getOvertimeStatus,1);
        if(!ObjectUtil.isEmpty(dateList)){
            queryWrapper.between(Overtime::getStartTime, dateList.get(0), dateList.get(1));
            List<Overtime> list = list(queryWrapper);
            List<String> collect = list.stream().map((item) -> {
                String start = DateUtil.format(item.getStartTime(), "yyyy-MM-dd HH:mm:ss");
                String end = DateUtil.format(item.getEndTime(), "yyyy-MM-dd HH:mm:ss");
                return start + "~" + end;
            }).collect(Collectors.toList());
            return collect.toString();
        }else{
            throw new SystemException(AppHttpCodeEnum.DATE_FORMAT_ERROR);
        }
    }

    private double countOvertimeDaysByIdAndInterval(Integer deployeeId, List<String> dateList) {
        LambdaQueryWrapper<Overtime> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Overtime::getExecutorId,deployeeId);
        queryWrapper.eq(Overtime::getOvertimeStatus,1);
        AtomicReference<Double> count = new AtomicReference<>((double) 0);
        //TODO 为0 ERROR
        if(!ObjectUtil.isEmpty(dateList)){
            queryWrapper.between(Overtime::getStartTime, dateList.get(0), dateList.get(1));
            List<Overtime> list = list(queryWrapper);
            for (Overtime item : list ) {
                System.out.println(item);
                long btime = 0;
                double onceOvertimeHours = 0;
                //请假总时长 TODO如果向上取整
                if(ObjectUtil.isEmpty(item.getStartTime()) || ObjectUtil.isEmpty(item.getEndTime())){
                    btime = 0;
                }else{
                    btime = item.getEndTime().getTime() - item.getStartTime().getTime();
                    onceOvertimeHours =  btime / 1000 / 60 / 60 ;
                }
                double floor = Math.floor(onceOvertimeHours);
                if(onceOvertimeHours - floor > 0.5 ){
                    count.set(count.get() + floor + 1);
                }else if(onceOvertimeHours - floor == 0){
                    count.set(count.get() + floor);
                }else{
                    count.set(count.get() + floor + 0.5);
                }
            }
            return count.get();
        }else{
            throw new SystemException(AppHttpCodeEnum.DATE_FORMAT_ERROR);
        }
    }


}




