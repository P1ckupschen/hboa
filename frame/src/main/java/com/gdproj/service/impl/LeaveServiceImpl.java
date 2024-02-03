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
import com.gdproj.mapper.LeaveMapper;
import com.gdproj.service.*;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.utils.DownloadUtils;
import com.gdproj.utils.JwtUtils;
import com.gdproj.utils.commonUtils;
import com.gdproj.vo.LeaveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
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
    UserRoleService userRoleService;

    @Autowired
    FlowService flowService;

    @Value("${ExcelFilePath}")
    String filePath;


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
        if (!ObjectUtil.isEmpty(time)) {
            //时间区间查询
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            queryWrapper.like(Leave::getCreatedTime,time);
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
                    LeaveCategory one = leaveCategoryService.getById(item.getCategoryId());
                    if(!ObjectUtil.isEmpty(one)){
                        leavevo.setCategory(one.getCategoryName());
                    }
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

                double floor = Math.floor(v);
                if(v - floor > 0.5 ){
                    v = floor + 1.0;
                } else if( v - floor == 0 ){
                    v = floor;
                }else {
                    v = floor + 0.5;
                }
                leavevo.setLeaveDays(String.valueOf(v));

                return leavevo;

            }).collect(Collectors.toList());
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }
        List<LeaveVo> leaveVos = addOrderId(resultList, pageNum, pageSize);
        resultPage.setRecords(leaveVos);
        resultPage.setTotal(leavePage.getTotal());
        return resultPage;
    }

    @Override
    public boolean insertLeave(Leave insertLeave) {

        boolean f = false;
        boolean o = false;
        if (!ObjectUtil.isEmpty(insertLeave.getLeaveDescription()) && insertLeave.getLeaveDescription().length() > 10) {
            o = save(insertLeave);
        }
        if(o){
            f = flowService.insertFlow(insertLeave);
        }else{
            throw new SystemException(AppHttpCodeEnum.INSERT_ERROR);
        }
        //同时更新flow 表 增加一条flow数据


        return o && f;
    }

    @Override
    public void exportLeaveExcel(PageQueryDto queryDto, HttpServletResponse response) {

//        List<Date> interval = queryDto.getInterval();
        String time = queryDto.getTime();
        Integer departmentId = queryDto.getDepartmentId();
        String title = queryDto.getTitle();
        LambdaQueryWrapper<Deployee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Deployee::getDeployeeStatus , 1);
        if(!ObjectUtil.isEmpty(departmentId)){
            queryWrapper.eq(Deployee::getDepartmentId, departmentId);
        }
        if(!ObjectUtil.isEmpty(title)){
            queryWrapper.like(Deployee::getDeployeeName, title);
        }
        String fileName = filePath + System.currentTimeMillis() + ".xlsx";
//        List<String> dateList = interval.stream().map((item) -> {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            try {
//                return sdf.format(item);
//            } catch (Exception e) {
//                throw new SystemException(AppHttpCodeEnum.DATE_FORMAT_ERROR);
//            }
//        }).collect(Collectors.toList());
//        List<Leave> leaveList = getLeaveListByTimeInterval(dateList);
        // TODO 应该是depolyeeList？

        AtomicInteger i = new AtomicInteger(1);
        List<Deployee> list = deployeeService.list(queryWrapper);
        List<LeaveExcelEntity> collect = list.stream().map((item) -> {
            LeaveExcelEntity lee = new LeaveExcelEntity();
            lee.setOrderId(i.getAndIncrement());
            lee.setUsername(item.getDeployeeName());
            lee.setStage(time);
            lee.setLeaveDays(countLeaveDaysByIdAndInterval(item.getDeployeeId(), time));
            lee.setLeaveHistory(getLeaveListByIdAndInterval(item.getDeployeeId(), time));
            return lee;
        }).collect(Collectors.toList());

        EasyExcel.write(fileName, LeaveExcelEntity.class).sheet("请假统计").doWrite(collect);
        DownloadUtils.downloadExcel(fileName,response);
    }

    @Override
    public IPage<LeaveVo> getMyLeaveList(PageQueryDto pageDto, HttpServletRequest request) {
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

        String id = JwtUtils.getMemberIdByJwtToken(request);
        List<String> role = userRoleService.getRoleKeysByUserId(id);

        if(!ObjectUtil.isEmpty(role)){
            boolean is = commonUtils.checkRole(role);
            if(!is){
                queryWrapper.eq(Leave::getUserId, JwtUtils.getMemberIdByJwtToken(request));
            }
        }

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
        if (!ObjectUtil.isEmpty(time)) {
            //时间区间查询
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            queryWrapper.like(Leave::getCreatedTime,time);
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
                    LeaveCategory one = leaveCategoryService.getById(item.getCategoryId());
                    if(!ObjectUtil.isEmpty(one)){
                        leavevo.setCategory(one.getCategoryName());
                    }
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

                double floor = Math.floor(v);
                if(v - floor > 0.5 ){
                    v = floor + 1.0;
                } else if( v - floor == 0 ){
                    v = floor;
                }else {
                    v = floor + 0.5;
                }
                leavevo.setLeaveDays(String.valueOf(v));

                return leavevo;

            }).collect(Collectors.toList());
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }
        List<LeaveVo> leaveVos = addOrderId(resultList, pageNum, pageSize);
        resultPage.setRecords(leaveVos);
        resultPage.setTotal(leavePage.getTotal());
        return resultPage;
    }

    @Override
    public boolean updateLeave(Leave info) {
        info.setLeaveStatus(0);
        boolean update = updateById(info);
        if(update){
            Integer typeId = info.getTypeId();
            Integer Id = info.getLeaveId();
            LambdaQueryWrapper<Flow> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Flow::getTypeId,typeId);
            queryWrapper.eq(Flow::getRunId,Id);
            Flow one = flowService.getOne(queryWrapper);
            one.setFlowTitle(info.getLeaveTitle());
            Flow flow = flowService.resetProperty(one);
            return flowService.updateById(flow);
        }else{
            return false;
        }
    }

    private double countLeaveDaysByIdAndInterval(Integer userId,  String time) {
        LambdaQueryWrapper<Leave> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Leave::getUserId,userId);
        queryWrapper.eq(Leave::getLeaveStatus,1);
        AtomicReference<Double> count = new AtomicReference<>((double) 0);
        //TODO 为0 ERROR
        if(!ObjectUtil.isEmpty(time)){
            queryWrapper.like(Leave::getStartTime, time);
            List<Leave> list = list(queryWrapper);
            for (Leave item : list ) {
                System.out.println(item);
                long btime = 0;
                double onceLeaveTime = 0;
                //请假总时长 TODO如果向上取整
                if(ObjectUtil.isEmpty(item.getStartTime()) || ObjectUtil.isEmpty(item.getEndTime())){
                    btime = 0;
                }else{
                    btime = item.getEndTime().getTime() - item.getStartTime().getTime();
                    onceLeaveTime = btime / 1000 / 60 / 60 /24.0;
                }
                double floor = Math.floor(onceLeaveTime);
                if(onceLeaveTime - floor > 0.5 ){
                    count.set(count.get() + floor + 1);
                }else if(onceLeaveTime - floor == 0){
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

    private String getLeaveListByIdAndInterval(Integer userId, String time) {
        LambdaQueryWrapper<Leave> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Leave::getUserId,userId);
        queryWrapper.eq(Leave::getLeaveStatus,1);
        if(!ObjectUtil.isEmpty(time)){
            queryWrapper.like(Leave::getStartTime,time);
            List<Leave> list = list(queryWrapper);
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

    private List<Leave> getLeaveListByTimeInterval(List<String> dateList) {
        LambdaQueryWrapper<Leave> queryWrapper = new LambdaQueryWrapper<>();
        if(!ObjectUtil.isEmpty(dateList)){
            queryWrapper.between(Leave::getStartTime, dateList.get(0), dateList.get(1));
            return list(queryWrapper);
        }else{
            throw new SystemException(AppHttpCodeEnum.DATE_FORMAT_ERROR);
        }
    }


    private List<LeaveVo> addOrderId(List<LeaveVo> list, Integer pageNum, Integer pageSize){
        if (!ObjectUtil.isEmpty(pageNum) && !ObjectUtil.isEmpty(pageSize)) {
            for (int i = 0 ; i < list.size() ; i++){
                list.get(i).setOrderId((pageNum - 1) * pageSize + i + 1);
            }
        }
        return list;
    }
}




