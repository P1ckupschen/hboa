package com.gdproj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Flow;
import com.gdproj.entity.Leave;
import com.gdproj.entity.Overtime;
import com.gdproj.entity.flowConfig;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.FlowMapper;
import com.gdproj.service.*;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.flowVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【sys_flow】的数据库操作Service实现
* @createDate 2023-10-17 15:09:38
*/
@Service
public class FlowServiceImpl extends ServiceImpl<FlowMapper, Flow>
    implements FlowService {

    @Autowired
    flowConfigService configService;

    @Autowired
    OvertimeService overtimeService;

    @Autowired
    LeaveService leaveService;

    @Autowired
    DeployeeService deployeeService;

    @Override
    public boolean insertFlow(Overtime insertOvertime) {


        flowConfig config = configService.getById(insertOvertime.getTypeId());

        List<Integer> approvalFlow = config.getApprovalFlow();

        //审批流里的所有下级用户
        Flow flow = new Flow();

        flow.setTypeId(insertOvertime.getTypeId());
        flow.setCurrentUserId( approvalFlow.get(0));
        flow.setFlowTitle(insertOvertime.getOvertimeTitle());
        flow.setRunId(insertOvertime.getOvertimeId());
        flow.setCurrentStepId(0);
        System.out.println(approvalFlow.size());
        flow.setTotalLevel(approvalFlow.size());

        List<Flow> list = list();

        return save(flow);

    }

    @Override
    public boolean approveFlow(flowVo vo) {

        Flow flow = BeanCopyUtils.copyBean(vo, Flow.class);

        Flow dataInSql = getById(flow.getFlowId());

        if(!ObjectUtil.isEmpty(dataInSql)){
            if(dataInSql.getCurrentStepId() == flow.getCurrentStepId()){
                //与数据库原来的数据比对，如果flowstatus == 传过来的状态值说明不通过   TODO 考虑==topstatus的状况
                System.out.println("审批不通过");
            }
            if(dataInSql.getCurrentStepId() < flow.getCurrentStepId()){
                System.out.println("审批通过");
                //与数据库原来的数据比对，如果flowstatus < 传过来的状态值说明 传过来时 +1了
                //说明通过 更新currentuserid
                flowConfig config = configService.getById(flow.getTypeId());
                List<Integer> approvalFlow = config.getApprovalFlow();
                if(approvalFlow.contains(flow.getCurrentUserId()) && flow.getTotalLevel() >= flow.getCurrentStepId()){
                    int index = approvalFlow.indexOf(flow.getCurrentUserId());
                    flow.setCurrentUserId(approvalFlow.get(index+1));
                }else{
                    //如果最后一级也审批过了 那么userid变为0 说明已通过
                    flow.setCurrentUserId(0);
                }
            }
        }

        return updateById(flow);

    }

    @Override
    public IPage<flowVo> getFlowList(pageDto pageDto) {
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

        Page<Flow> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Flow> queryWrapper = new LambdaQueryWrapper<>();
        //排序
        if (sort.equals("+id")) {
            queryWrapper.orderByAsc(Flow::getFlowId);
        } else {
            queryWrapper.orderByDesc(Flow::getFlowId);
        }

        //查询名称？
        if (!title.isEmpty()) {
            queryWrapper.eq(Flow::getFlowId,title);
        }

        //如果有类型的话 类型
        if (!ObjectUtil.isEmpty(type)) {
            //传过来的是productCategoryId,需要去产品表下找属于这个产品类型的产品 id数组
            queryWrapper.eq(Flow::getTypeId,type);
        }
        IPage<Flow> recordPage = page(page, queryWrapper);

        Page<flowVo> resultPage = new Page<>();

        List<flowVo> resultList = new ArrayList<>();
        try {

            resultList = recordPage.getRecords().stream().map((item) -> {

                flowVo vo = BeanCopyUtils.copyBean(item, flowVo.class);
                //类型名称?
                setFlowVoProperty(vo,item);

                return vo;
            }).collect(Collectors.toList());
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }

        resultPage.setRecords(resultList);

        resultPage.setTotal(recordPage.getTotal());

        return resultPage;
    }

    private void setFlowVoProperty(flowVo vo,Flow item) {
        Integer typeId = item.getTypeId();
        if(typeId == 1){
            //加班申请
            Overtime overtime = overtimeService.getById(item.getRunId());
            vo.setApplicantName(deployeeService.getNameByUserId(overtime.getApplicantId()));
            //判断当前的状态是什么
            if(vo.getTotalLevel() >= vo.getCurrentStepId()){
                vo.setCurrentStep(item.getCurrentStepId() + ":" + deployeeService.getNameByUserId(vo.getCurrentUserId()) +"审批中");
            }else{
                vo.setCurrentStep("已完成");
            }
            vo.setTypeName(configService.getById(vo.getTypeId()).getTypeName());
            vo.setRunStatus(overtime.getOvertimeStatus()+ "");
            vo.setFlowTitle(overtime.getOvertimeTitle());

        } else if (typeId == 2) {
            //请假申请
            Leave leave = leaveService.getById(item.getRunId());
            vo.setApplicantName(deployeeService.getNameByUserId(leave.getUserId()));
            //判断当前的状态是什么
            if(vo.getTotalLevel() >= vo.getCurrentStepId()){
                vo.setCurrentStep(item.getCurrentStepId() + ":" + deployeeService.getNameByUserId(vo.getCurrentUserId()) +"审批中");
            }else{
                vo.setCurrentStep("已完成");
            }
            vo.setTypeName(configService.getById(vo.getTypeId()).getTypeName());
            vo.setRunStatus(leave.getLeaveStatus()+ "");
            vo.setFlowTitle(leave.getLeaveTitle());
        } else if (typeId == 3) {
            //出库申请
        } else if (typeId == 4) {
            //日常领用申请
        } else if (typeId == 5) {
            //付款申请
        } else if (typeId == 6) {
            //报销申请
        } else if (typeId == 7) {
            //采购申请
        }

    }

    @Override
    public boolean insertFlow(Leave insertLeave) {

        flowConfig config = configService.getById(insertLeave.getTypeId());

        List<Integer> approvalFlow = config.getApprovalFlow();

        //审批流里的所有下级用户
        Flow flow = new Flow();

        flow.setTypeId(insertLeave.getTypeId());
        flow.setCurrentUserId( approvalFlow.get(0));
        flow.setFlowTitle(insertLeave.getLeaveTitle());
        flow.setRunId(insertLeave.getLeaveId());
        flow.setCurrentStepId(0);
        flow.setTotalLevel(approvalFlow.size());

        List<Flow> list = list();

        boolean f = save(flow);

        return f;

    }
}




