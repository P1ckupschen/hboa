package com.gdproj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.DailyUse;
import com.gdproj.entity.DailyUseRecord;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.DailyUseMapper;
import com.gdproj.service.*;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.DailyUseContentVo;
import com.gdproj.vo.DailyUseRecordVo;
import com.gdproj.vo.DailyUseVo;
import com.gdproj.vo.ToolVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【sys_dailyuse】的数据库操作Service实现
* @createDate 2023-10-31 09:27:07
*/
@Service
public class DailyUseServiceImpl extends ServiceImpl<DailyUseMapper, DailyUse>
    implements DailyUseService {

    @Autowired
    DeployeeService deployeeService;

    @Autowired
    DailyUseRecordService recordService;
    @Autowired
    FlowService flowService;
    @Autowired
    ToolService toolService;


    @Override
    public IPage<DailyUseVo> getDailyUseList(PageQueryDto pageDto) {


        //类型
        Integer type = pageDto.getType();
        //部门
        Integer departmentId = pageDto.getDepartmentId();
        //时间
        String time = pageDto.getTime();
        //排序
        String sort = pageDto.getSort();
        //搜索框如果是产品搜索产品名称或者选择产品id
        //如果是人 搜素人名或者人id
        //如果是物 搜索id
        String title = pageDto.getTitle();
        Integer pageNum = pageDto.getPageNum();
        Integer pageSize = pageDto.getPageSize();

        Page<DailyUse> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<DailyUse> queryWrapper = new LambdaQueryWrapper<>();
        //排序
        if (sort.equals("+id")) {
            queryWrapper.orderByAsc(DailyUse::getDailyuseId);
        } else {
            queryWrapper.orderByDesc(DailyUse::getDailyuseId);
        }

        //查询名称？
        if (!title.isEmpty()) {
            List<Integer> ids = deployeeService.getIdsByTitle(title);
            if(!ObjectUtil.isEmpty(ids)){
                queryWrapper.in(DailyUse::getUserId,ids);
            }else{
                queryWrapper.eq(DailyUse::getUserId,0);
            }

        }

        if(!ObjectUtil.isEmpty(time)){
            queryWrapper.like(DailyUse::getCreatedTime,time);
        }

        //如果有类型的话 类型
        if (!ObjectUtil.isEmpty(type)) {
            //传过来的是productCategoryId,需要去产品表下找属于这个产品类型的产品 id数组
            queryWrapper.eq(DailyUse::getCategoryId,type);
        }
        IPage<DailyUse> recordPage = page(page, queryWrapper);

        Page<DailyUseVo> resultPage = new Page<>();

        List<DailyUseVo> resultList = new ArrayList<>();
        try {

            resultList = recordPage.getRecords().stream().map((item) -> {

                DailyUseVo vo = BeanCopyUtils.copyBean(item, DailyUseVo.class);

                if(item.getCategoryId() == 1){
                    vo.setCategory("入库");
                }else if(item.getCategoryId() == 2){
                    vo.setCategory("出库");
                }
                //TODO 内嵌属性content 字段需要统一
                String Content = JSONUtil.toJsonStr(item.getDailyuseContent());
                List<DailyUseContentVo> contentVoList = JSONUtil.toList(Content, DailyUseContentVo.class);
                vo.setDailyuseContent(contentVoList);
                vo.setUsername(deployeeService.getNameByUserId(item.getUserId()));

                return vo;
            }).collect(Collectors.toList());

            resultPage.setRecords(resultList);

            resultPage.setTotal(recordPage.getTotal());

            return resultPage;
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }
    }

    @Override
    public boolean insertDailyUse(DailyUse dailyUse) {
        Integer categoryId = dailyUse.getCategoryId();
        dailyUse.setDailyuseStatus(1);
        boolean f = false;
        boolean o = save(dailyUse);
        //如果为入库申请则无需审批
        if(dailyUse.getCategoryId() == 1){
            if(!ObjectUtil.isEmpty(dailyUse.getDailyuseContent())){
                List<DailyUseRecord> recordList = recordService.transferDailyUseContentToRecord(dailyUse);
                return recordService.saveBatch(recordList);
            }else{
                return true;
            }
        }else{
            if (o) {
                f = flowService.insertFlow(dailyUse);
            }else{
                throw new SystemException(AppHttpCodeEnum.INSERT_ERROR);
            }
            return o && f;
        }
    }

    @Override
    public IPage<DailyUseRecordVo> getDailyUseRecordList(PageQueryDto pageDto) {
        Integer type = pageDto.getType();
        Integer departmentId = pageDto.getDepartmentId();
        String time = pageDto.getTime();
        String sort = pageDto.getSort();
        String title = pageDto.getTitle();
        Integer pageNum = pageDto.getPageNum();
        Integer pageSize = pageDto.getPageSize();

        Page<DailyUseRecord> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<DailyUseRecord> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(DailyUseRecord::getIsDeleted, 0);
        //排序
        if (sort.equals("+id")) {
            queryWrapper.orderByAsc(DailyUseRecord::getRecordId);
        } else {
            queryWrapper.orderByDesc(DailyUseRecord::getRecordId);
        }
        //如果根据部门分类，有一定几率会与模糊人民冲突
        if (!Objects.isNull(departmentId) && title.isEmpty()) {
            //如果没有对象没有部门id属性就找到对应id的部门所以的员工的userid
            List<Integer> userIds = deployeeService.getIdsByDepartmentId(departmentId);
            if(!ObjectUtil.isEmpty(userIds)){
                queryWrapper.in(DailyUseRecord::getUserId, userIds);
            }else{
                queryWrapper.in(DailyUseRecord::getUserId,0);
            }
        }
        //设置时间 年 月 日
        //模糊查询时间
        if (time != null) {
            queryWrapper.like(DailyUseRecord::getRecordTime, time);
        }

        //查询产品名称？
        if (!title.isEmpty()) {
            queryWrapper.in(DailyUseRecord::getToolId, title);
        }

        //如果有类型的话

        if (!ObjectUtil.isEmpty(type)) {
            queryWrapper.eq(DailyUseRecord::getCategoryId, type);
        }

        IPage<DailyUseRecord> recordPage = recordService.page(page, queryWrapper);

        Page<DailyUseRecordVo> resultPage = new Page<>();

        List<DailyUseRecordVo> resultList = new ArrayList<>();
        try {

            resultList = recordPage.getRecords().stream().map((item) -> {

                DailyUseRecordVo vo = BeanCopyUtils.copyBean(item, DailyUseRecordVo.class);
                //创建人
                vo.setUsername(deployeeService.getNameByUserId(item.getUserId()));

                //部门
                vo.setDepartment(deployeeService.getDepartmentNameByUserId(item.getUserId()));

                //产品名称 对应的产品类型
                vo.setTool(BeanCopyUtils.copyBean(toolService.getById(item.getToolId()), ToolVo.class));

                //产品类型
                if (vo.getCategoryId() == 1) {
                    vo.setCategory("入库");
                } else {
                    vo.setCategory("出库");
                }

                return vo;

            }).collect(Collectors.toList());
            resultPage.setRecords(resultList);

            resultPage.setTotal(recordPage.getTotal());

            return resultPage;
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }

    }
}




