package com.gdproj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.*;
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
    flowHistoryService historyService;

    @Autowired
    OvertimeService overtimeService;

    @Autowired
    LeaveService leaveService;

    @Autowired
    WarehouseService warehouseService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    ReimbursementService reimbursementService;

    @Autowired
    DeployeeService deployeeService;



    @Override
    public boolean approveFlow(flowVo vo) {
        //判断是否通过 通过增加一条记录 不通过也增加一条记录
        //1. 同一张表  TODO  查询sql？？？
//        Flow flow = BeanCopyUtils.copyBean(vo, Flow.class);
//        flow.setFlowId(null);
//        Flow dataInSql = getById(flow.getFlowId());
//        flowConfig config = configService.getById(flow.getTypeId());
//        List<Integer> approvalFlow = config.getApprovalFlow();
//        boolean isSuccess = false;
//        if(!ObjectUtil.isEmpty(approvalFlow)){
//            //如果前端修改的是flow_status 1通过或2不通过
//            if (vo.getFlowStatus() == 1) {
//                //通过 保存一条 通过的记录
//                save(flow);
//                //再保存一条到下一级的新的数据
//                Flow newFlow = BeanCopyUtils.copyBean(flow, Flow.class);
//                newFlow.setFlowId(null);
//                newFlow.setFlowStatus(0);
//                newFlow.setCurrentStepId(newFlow.getCurrentStepId()+1);
//                if(newFlow.getTotalLevel() >= newFlow.getCurrentStepId()) {
//                    int index = approvalFlow.indexOf(newFlow.getCurrentUserId());
//                    newFlow.setCurrentUserId(approvalFlow.get(index + 1));
//                }else{
//                    //说明最后一级也通过了
//                    newFlow.setCurrentUserId(0);
//                    newFlow.setFlowStatus(1);
//                    //并且把所有
//                    boolean isPass = setPassStatus(newFlow.getTypeId(),newFlow.getRunId());
//                    if(!isPass){
//                        throw new SystemException(AppHttpCodeEnum.SET_PASS_ERROR);
//                    }
//                }
//                return save(newFlow);
//            } else {
//                //不通过 保存一条 不通过的记录
//                save(flow);
//                //并且将各类表里的那条记录状态设为不通过；
//                boolean isNoPass = setNoPassStatus(flow.getTypeId(),flow.getRunId());
//                if(!isNoPass){
//                    throw new SystemException(AppHttpCodeEnum.SET_NO_PASS_ERROR);
//                }
//                return isNoPass;
//            }
//        }else{
//            throw new SystemException(AppHttpCodeEnum.FLOW_CONFIG_CONTENT_NULL);
//        }

        //2.不同表 分主表 从表  主表更新数据 从表插入历史
        Flow flow = BeanCopyUtils.copyBean(vo, Flow.class);
        Flow dataInSql = getById(flow.getFlowId());
        flowConfig config = configService.getById(flow.getTypeId());
        List<Integer> approvalFlow = config.getApprovalFlow();
        boolean isSuccess = false;
        if(!ObjectUtil.isEmpty(approvalFlow)){
            //如果前端修改的是flow_status 1通过或2不通过
            if (vo.getFlowStatus() == 1) {
                //通过 保存一条 通过的记录
                flowHistory history = BeanCopyUtils.copyBean(flow, flowHistory.class);
                historyService.save(history);
                flow.setFlowStatus(0);
                flow.setCurrentStepId(flow.getCurrentStepId()+1);
                if(flow.getTotalLevel() >= flow.getCurrentStepId()) {
                    int index = approvalFlow.indexOf(flow.getCurrentUserId());
                    flow.setCurrentUserId(approvalFlow.get(index + 1));
                }else{
                    //说明最后一级也通过了
                    flow.setCurrentUserId(0);
                    flow.setFlowStatus(1);
                    //并且把所有
                    boolean isPass = setPassStatus(flow.getTypeId(),flow.getRunId());
                    if(!isPass){
                        throw new SystemException(AppHttpCodeEnum.SET_PASS_ERROR);
                    }
                }
                updateById(flow);
                //再保存一条到下一级的新的数据
                flowHistory newHistory = BeanCopyUtils.copyBean(flow, flowHistory.class);
                return historyService.save(newHistory);
            } else {
                //不通过 保存一条 不通过的记录
                flowHistory history = BeanCopyUtils.copyBean(flow, flowHistory.class);
                historyService.save(history);
                updateById(flow);
                //并且将各类表里的那条记录状态设为不通过；
                boolean isNoPass = setNoPassStatus(flow.getTypeId(),flow.getRunId());
                if(!isNoPass){
                    throw new SystemException(AppHttpCodeEnum.SET_NO_PASS_ERROR);
                }
                return isNoPass;
            }
        }else{
            throw new SystemException(AppHttpCodeEnum.FLOW_CONFIG_CONTENT_NULL);
        }

    }

    private boolean setPassStatus(Integer typeId, Integer runId) {
        if (typeId == 1) {
            //加班申请
            LambdaUpdateWrapper<Overtime> updateWrapper =new LambdaUpdateWrapper<>();
            updateWrapper.eq(Overtime::getOvertimeId,runId);
            updateWrapper.set(Overtime::getOvertimeStatus,1);
            return overtimeService.update(updateWrapper);

        } else if (typeId == 2) {
            //请假申请
            LambdaUpdateWrapper<Leave> updateWrapper =new LambdaUpdateWrapper<>();
            updateWrapper.eq(Leave::getLeaveId,runId);
            updateWrapper.set(Leave::getLeaveStatus,1);
            return leaveService.update(updateWrapper);
        } else if (typeId == 3) {
            //出库申请
            LambdaUpdateWrapper<Warehouse> updateWrapper =new LambdaUpdateWrapper<>();
            updateWrapper.eq(Warehouse::getWarehouseId,runId);
            updateWrapper.set(Warehouse::getWarehouseStatus,1);
            return warehouseService.update(updateWrapper);
        } else if (typeId == 4) {
            //日常领用申请
            return false;
        } else if (typeId == 5) {
            //付款申请
            LambdaUpdateWrapper<Payment> updateWrapper =new LambdaUpdateWrapper<>();
            updateWrapper.eq(Payment::getPaymentId,runId);
            updateWrapper.set(Payment::getPaymentStatus,1);
            return paymentService.update(updateWrapper);
        } else if (typeId == 6) {
            //报销申请
            LambdaUpdateWrapper<Reimbursement> updateWrapper =new LambdaUpdateWrapper<>();
            updateWrapper.eq(Reimbursement::getReimbursementId,runId);
            updateWrapper.set(Reimbursement::getReimbursementStatus,1);
            return reimbursementService.update(updateWrapper);
        } else if (typeId == 7) {
            //采购申请
            return false;
        }else {
            return false;
        }
    }

    private boolean setNoPassStatus(Integer typeId, Integer runId) {

        if (typeId == 1) {
            //加班申请
            LambdaUpdateWrapper<Overtime> updateWrapper =new LambdaUpdateWrapper<>();
            updateWrapper.eq(Overtime::getOvertimeId,runId);
            updateWrapper.set(Overtime::getOvertimeStatus,2);
            return overtimeService.update(updateWrapper);
        } else if (typeId == 2) {
            //请假申请
            LambdaUpdateWrapper<Leave> updateWrapper =new LambdaUpdateWrapper<>();
            updateWrapper.eq(Leave::getLeaveId,runId);
            updateWrapper.set(Leave::getLeaveStatus,2);
            return leaveService.update(updateWrapper);
        } else if (typeId == 3) {
            //出库申请
            return false;
        } else if (typeId == 4) {
            //日常领用申请
            return false;
        } else if (typeId == 5) {
            //付款申请
            LambdaUpdateWrapper<Payment> updateWrapper =new LambdaUpdateWrapper<>();
            updateWrapper.eq(Payment::getPaymentId,runId);
            updateWrapper.set(Payment::getPaymentStatus,2);
            return paymentService.update(updateWrapper);
        } else if (typeId == 6) {
            //报销申请
            LambdaUpdateWrapper<Reimbursement> updateWrapper =new LambdaUpdateWrapper<>();
            updateWrapper.eq(Reimbursement::getReimbursementId,runId);
            updateWrapper.set(Reimbursement::getReimbursementStatus,2);
            return reimbursementService.update(updateWrapper);
        } else if (typeId == 7) {
            //采购申请
            return false;
        }else{
            return false;
        }

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
            queryWrapper.eq(Flow::getFlowId, title);
        }

        //如果有类型的话 类型
        if (!ObjectUtil.isEmpty(type)) {
            //传过来的是productCategoryId,需要去产品表下找属于这个产品类型的产品 id数组
            queryWrapper.eq(Flow::getTypeId, type);
        }



        IPage<Flow> recordPage = page(page, queryWrapper);
        //相同的typeId 和runId的只显示最早的createdTime的数据

        Page<flowVo> resultPage = new Page<>();

        List<flowVo> resultList = new ArrayList<>();
        try {

            resultList = recordPage.getRecords().stream().map((item) -> {

                flowVo vo = BeanCopyUtils.copyBean(item, flowVo.class);
                //类型名称?
                setFlowVoProperty(vo, item);

                return vo;
            }).collect(Collectors.toList());

            resultPage.setRecords(resultList);

            resultPage.setTotal(recordPage.getTotal());

            return resultPage;
        } catch (Exception e) {
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }


    }

    private void setFlowVoProperty(flowVo vo, Flow item) {
        Integer typeId = item.getTypeId();
        if (typeId == 1) {
            //加班申请
            Overtime overtime = overtimeService.getById(item.getRunId());
            vo.setApplicantName(deployeeService.getNameByUserId(overtime.getApplicantId()));
            //判断当前的状态是什么
            if (vo.getTotalLevel() >= vo.getCurrentStepId()) {
                if(vo.getFlowStatus() ==1){
                    vo.setCurrentStep(item.getCurrentStepId() + ":" + deployeeService.getNameByUserId(vo.getCurrentUserId()) + "审批通过");
                }else if(vo.getFlowStatus() == 2){
                    vo.setCurrentStep(item.getCurrentStepId() + ":" + deployeeService.getNameByUserId(vo.getCurrentUserId()) + "审批不通过");
                }else{
                    vo.setCurrentStep(item.getCurrentStepId() + ":" + deployeeService.getNameByUserId(vo.getCurrentUserId()) + "审批中");
                }
            } else {
                vo.setCurrentStep("已完成");
            }
            vo.setTypeName(configService.getById(vo.getTypeId()).getTypeName());
            vo.setRunStatus(overtime.getOvertimeStatus() + "");
            vo.setFlowTitle(overtime.getOvertimeTitle());

        } else if (typeId == 2) {
            //请假申请
            Leave leave = leaveService.getById(item.getRunId());
            vo.setApplicantName(deployeeService.getNameByUserId(leave.getUserId()));
            //判断当前的状态是什么
            if (vo.getTotalLevel() >= vo.getCurrentStepId()) {
                if(vo.getFlowStatus() ==1){
                    vo.setCurrentStep(item.getCurrentStepId() + ":" + deployeeService.getNameByUserId(vo.getCurrentUserId()) + "审批通过");
                }else if(vo.getFlowStatus() == 2){
                    vo.setCurrentStep(item.getCurrentStepId() + ":" + deployeeService.getNameByUserId(vo.getCurrentUserId()) + "审批不通过");
                }else{
                    vo.setCurrentStep(item.getCurrentStepId() + ":" + deployeeService.getNameByUserId(vo.getCurrentUserId()) + "审批中");
                }
            } else {
                vo.setCurrentStep("已完成");
            }
            vo.setTypeName(configService.getById(vo.getTypeId()).getTypeName());
            vo.setRunStatus(leave.getLeaveStatus() + "");
            vo.setFlowTitle(leave.getLeaveTitle());
        } else if (typeId == 3) {
            //出库申请
        } else if (typeId == 4) {
            //日常领用申请
        } else if (typeId == 5) {
            //付款申请
            Payment payment = paymentService.getById(item.getRunId());
            vo.setApplicantName(deployeeService.getNameByUserId(payment.getUserId()));
            //判断当前的状态是什么
            if (vo.getTotalLevel() >= vo.getCurrentStepId()) {
                if(vo.getFlowStatus() ==1){
                    vo.setCurrentStep(item.getCurrentStepId() + ":" + deployeeService.getNameByUserId(vo.getCurrentUserId()) + "审批通过");
                }else if(vo.getFlowStatus() == 2){
                    vo.setCurrentStep(item.getCurrentStepId() + ":" + deployeeService.getNameByUserId(vo.getCurrentUserId()) + "审批不通过");
                }else{
                    vo.setCurrentStep(item.getCurrentStepId() + ":" + deployeeService.getNameByUserId(vo.getCurrentUserId()) + "审批中");
                }
            } else {
                vo.setCurrentStep("已完成");
            }
            vo.setTypeName(configService.getById(vo.getTypeId()).getTypeName());
            vo.setRunStatus(payment.getPaymentStatus() + "");
            vo.setFlowTitle(payment.getPaymentTitle());
        } else if (typeId == 6) {
            //报销申请
            Reimbursement reimbursement = reimbursementService.getById(item.getRunId());
            vo.setApplicantName(deployeeService.getNameByUserId(reimbursement.getUserId()));
            //判断当前的状态是什么
            if (vo.getTotalLevel() >= vo.getCurrentStepId()) {
                if(vo.getFlowStatus() ==1){
                    vo.setCurrentStep(item.getCurrentStepId() + ":" + deployeeService.getNameByUserId(vo.getCurrentUserId()) + "审批通过");
                }else if(vo.getFlowStatus() == 2){
                    vo.setCurrentStep(item.getCurrentStepId() + ":" + deployeeService.getNameByUserId(vo.getCurrentUserId()) + "审批不通过");
                }else{
                    vo.setCurrentStep(item.getCurrentStepId() + ":" + deployeeService.getNameByUserId(vo.getCurrentUserId()) + "审批中");
                }
            } else {
                vo.setCurrentStep("已完成");
            }
            vo.setTypeName(configService.getById(vo.getTypeId()).getTypeName());
            vo.setRunStatus(reimbursement.getReimbursementStatus() + "");
            vo.setFlowTitle(reimbursement.getReimbursementTitle());
        } else if (typeId == 7) {
            //采购申请
        }

    }
    /**
     *
     * 加班 typeId =1
     * */
    @Override
    public boolean insertFlow(Overtime insertOvertime) {


        flowConfig config = configService.getById(insertOvertime.getTypeId());

        List<Integer> approvalFlow = config.getApprovalFlow();

        //审批流里的所有下级用户
        Flow flow = new Flow();
        flow.setTypeId(insertOvertime.getTypeId());
        flow.setCurrentUserId(approvalFlow.get(0));
        flow.setFlowTitle(insertOvertime.getOvertimeTitle());
        flow.setRunId(insertOvertime.getOvertimeId());
        flow.setCurrentStepId(1);
        flow.setTotalLevel(approvalFlow.size());

        List<Flow> list = list();

        return save(flow);

    }

    /**
     *
     * 请假 typeId =2
     * */
    @Override
    public boolean insertFlow(Leave insertLeave) {

        flowConfig config = configService.getById(insertLeave.getTypeId());

        List<Integer> approvalFlow = config.getApprovalFlow();

        //审批流里的所有下级用户
        Flow flow = new Flow();

        flow.setTypeId(insertLeave.getTypeId());
        flow.setCurrentUserId(approvalFlow.get(0));
        flow.setFlowTitle(insertLeave.getLeaveTitle());
        flow.setRunId(insertLeave.getLeaveId());
        flow.setCurrentStepId(1);
        flow.setTotalLevel(approvalFlow.size());

        List<Flow> list = list();

        boolean f = save(flow);

        return f;

    }

    /**
     *
     * 出库 typeId = 3
     * */
    @Override
    public boolean insertFlow(Warehouse warehouse) {

        //如果为入库申请则无需审批
        if(warehouse.getCategoryId() == 1){
            //并且遍历warehouse 的一个json属性 将所有入库产品都加入record
            List warehouseContent = warehouse.getWarehouseContent();
            return true;
        } else if (warehouse.getCategoryId() == 2) {
            //如果为出库申请则需审批
            flowConfig config = configService.getById(warehouse.getTypeId());
            List<Integer> approvalFlow = config.getApprovalFlow();
            //审批流里的所有下级用户
            Flow flow = new Flow();
            flow.setTypeId(warehouse.getTypeId());
            flow.setCurrentUserId(approvalFlow.get(0));
            flow.setFlowTitle(warehouse.getWarehouseTitle());
            flow.setRunId(warehouse.getWarehouseId());
            flow.setCurrentStepId(1);
            flow.setTotalLevel(approvalFlow.size());
            List<Flow> list = list();
            return save(flow);
        }else{
            return false;
        }

    }

    /**
     *
     * 支付 typeId = 5
     * */
    @Override
    public boolean insertFlow(Payment payment) {


        flowConfig config = configService.getById(payment.getTypeId());

        List<Integer> approvalFlow = config.getApprovalFlow();

        //审批流里的所有下级用户
        Flow flow = new Flow();
        flow.setTypeId(payment.getTypeId());
        flow.setCurrentUserId(approvalFlow.get(0));
        flow.setFlowTitle(payment.getPaymentTitle());
        flow.setRunId(payment.getPaymentId());
        flow.setCurrentStepId(1);
        flow.setTotalLevel(approvalFlow.size());

        List<Flow> list = list();

        return save(flow);

    }
    /**
     *
     * 报销 typeId = 6
     * */
    @Override
    public boolean insertFlow(Reimbursement reimbursement) {

        flowConfig config = configService.getById(reimbursement.getTypeId());

        List<Integer> approvalFlow = config.getApprovalFlow();

        //审批流里的所有下级用户
        Flow flow = new Flow();
        flow.setTypeId(reimbursement.getTypeId());
        flow.setCurrentUserId(approvalFlow.get(0));
        flow.setFlowTitle(reimbursement.getReimbursementTitle());
        flow.setRunId(reimbursement.getReimbursementId());
        flow.setCurrentStepId(1);
        flow.setTotalLevel(approvalFlow.size());

        List<Flow> list = list();

        return save(flow);

    }
}




