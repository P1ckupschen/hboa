package com.gdproj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.*;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.FlowMapper;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.*;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.utils.JwtUtils;
import com.gdproj.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
    RecordService recordService;

    @Autowired
    DailyUseService dailyUseService;

    @Autowired
    DailyUseRecordService dailyUseRecordService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    ReimbursementService reimbursementService;

    @Autowired
    PurchaseService purchaseService;

    @Autowired
    DeployeeService deployeeService;

    @Autowired
    ProductService productService;




    @Override
    public boolean approveFlow(FlowVo vo) {

        //不同表 分主表 从表  主表更新数据 从表插入历史
        Flow flow = BeanCopyUtils.copyBean(vo, Flow.class);
        Flow dataInSql = getById(flow.getFlowId());
        FlowConfig config = configService.getById(flow.getTypeId());
        List<Integer> approvalFlow = config.getApprovalFlow();
        boolean isSuccess = false;
        if (!ObjectUtil.isEmpty(approvalFlow)) {
            //如果前端修改的是flow_status 1通过或2不通过
            if (vo.getFlowStatus() == 1) {
                //通过 保存一条 通过的记录
                FlowHistory history = BeanCopyUtils.copyBean(flow, FlowHistory.class);
                historyService.save(history);
                flow.setFlowStatus(0);
                flow.setCurrentStepId(flow.getCurrentStepId() + 1);
                if (flow.getTotalLevel() >= flow.getCurrentStepId()) {
                    int index = approvalFlow.indexOf(flow.getCurrentUserId());
                    flow.setCurrentUserId(approvalFlow.get(index + 1));
                } else {
                    //说明最后一级也通过了
                    flow.setCurrentUserId(0);
                    flow.setFlowStatus(1);
                    //并且把所有
                    boolean isPass = setPassStatus(flow.getTypeId(), flow.getRunId());
                    if (flow.getTypeId() == 3) {
                        //如果typeId == 3 那么为出库申请，在最后一级都通过的情况下，出库申请为所有的材料生成record 插入record表
                        //warehouseId 就是 runId  遍历这条warehouse记录的warehouseContent，product_id 和 count
                        boolean w = recordService.insertRecordByWarehouseId(flow.getRunId());
                    }
                    if (flow.getTypeId() == 4) {
                        boolean w = dailyUseRecordService.insertRecordByDailyUseId(flow.getRunId());
                    }
                    //  TODO 判断W
                    if (!isPass ) {
                        throw new SystemException(AppHttpCodeEnum.SET_PASS_ERROR);
                    }
                }
                updateById(flow);
                //再保存一条到下一级的新的数据
                FlowHistory newHistory = BeanCopyUtils.copyBean(flow, FlowHistory.class);
                return historyService.save(newHistory);
            } else {
                //不通过 保存一条 不通过的记录
                FlowHistory history = BeanCopyUtils.copyBean(flow, FlowHistory.class);
                historyService.save(history);
                updateById(flow);
                //并且将各类表里的那条记录状态设为不通过；
                boolean isNoPass = setNoPassStatus(flow.getTypeId(), flow.getRunId());
                if (!isNoPass) {
                    throw new SystemException(AppHttpCodeEnum.SET_NO_PASS_ERROR);
                }
                return isNoPass;
            }
        } else {
            throw new SystemException(AppHttpCodeEnum.FLOW_CONFIG_CONTENT_NULL);
        }

    }

    private boolean setPassStatus(Integer typeId, Integer runId) {
        if (typeId == 1) {
            //加班申请
            LambdaUpdateWrapper<Overtime> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Overtime::getOvertimeId, runId);
            updateWrapper.set(Overtime::getOvertimeStatus, 1);
            return overtimeService.update(updateWrapper);

        } else if (typeId == 2) {
            //请假申请
            LambdaUpdateWrapper<Leave> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Leave::getLeaveId, runId);
            updateWrapper.set(Leave::getLeaveStatus, 1);
            return leaveService.update(updateWrapper);
        } else if (typeId == 3) {
            //出库申请
            LambdaUpdateWrapper<Warehouse> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Warehouse::getWarehouseId, runId);
            updateWrapper.set(Warehouse::getWarehouseStatus, 1);
            return warehouseService.update(updateWrapper);
        } else if (typeId == 4) {
            //日常领用申请
            LambdaUpdateWrapper<DailyUse> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(DailyUse::getDailyuseId, runId);
            updateWrapper.set(DailyUse::getDailyuseStatus, 1);
            return dailyUseService.update(updateWrapper);
        } else if (typeId == 5) {
            //付款申请
            LambdaUpdateWrapper<Payment> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Payment::getPaymentId, runId);
            updateWrapper.set(Payment::getPaymentStatus, 1);
            return paymentService.update(updateWrapper);
        } else if (typeId == 6) {
            //报销申请
            LambdaUpdateWrapper<Reimbursement> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Reimbursement::getReimbursementId, runId);
            updateWrapper.set(Reimbursement::getReimbursementStatus, 1);
            return reimbursementService.update(updateWrapper);
        } else if (typeId == 7) {
            //采购申请
            LambdaUpdateWrapper<Purchase> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Purchase::getPurchaseId, runId);
            updateWrapper.set(Purchase::getPurchaseStatus, 1);
            return purchaseService.update(updateWrapper);
        } else {
            throw new SystemException(AppHttpCodeEnum.FLOW_TYPE_ERROR);
//            return false;
        }
    }

    private boolean setNoPassStatus(Integer typeId, Integer runId) {

        if (typeId == 1) {
            //加班申请
            LambdaUpdateWrapper<Overtime> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Overtime::getOvertimeId, runId);
            updateWrapper.set(Overtime::getOvertimeStatus, 2);
            return overtimeService.update(updateWrapper);
        } else if (typeId == 2) {
            //请假申请
            LambdaUpdateWrapper<Leave> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Leave::getLeaveId, runId);
            updateWrapper.set(Leave::getLeaveStatus, 2);
            return leaveService.update(updateWrapper);
        } else if (typeId == 3) {
            //出库申请
            LambdaUpdateWrapper<Warehouse> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Warehouse::getWarehouseId, runId);
            updateWrapper.set(Warehouse::getWarehouseStatus, 2);
            return warehouseService.update(updateWrapper);
        } else if (typeId == 4) {
            //日常领用申请
            LambdaUpdateWrapper<DailyUse> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(DailyUse::getDailyuseId, runId);
            updateWrapper.set(DailyUse::getDailyuseStatus, 2);
            return dailyUseService.update(updateWrapper);
        } else if (typeId == 5) {
            //付款申请
            LambdaUpdateWrapper<Payment> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Payment::getPaymentId, runId);
            updateWrapper.set(Payment::getPaymentStatus, 2);
            return paymentService.update(updateWrapper);
        } else if (typeId == 6) {
            //报销申请
            LambdaUpdateWrapper<Reimbursement> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Reimbursement::getReimbursementId, runId);
            updateWrapper.set(Reimbursement::getReimbursementStatus, 2);
            return reimbursementService.update(updateWrapper);
        } else if (typeId == 7) {
            //采购申请
            LambdaUpdateWrapper<Purchase> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Purchase::getPurchaseId, runId);
            updateWrapper.set(Purchase::getPurchaseStatus, 2);
            return purchaseService.update(updateWrapper);
        } else {
            throw new SystemException(AppHttpCodeEnum.FLOW_TYPE_ERROR);
        }

    }

    @Override
    public IPage<FlowVo> getFlowList(PageQueryDto pageDto, HttpServletRequest request) {

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

        Page<FlowVo> resultPage = new Page<>();

        List<FlowVo> resultList = new ArrayList<>();
        try {

            resultList = recordPage.getRecords().stream().map((item) -> {
                FlowVo vo = BeanCopyUtils.copyBean(item, FlowVo.class);
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

    private void setFlowVoProperty(FlowVo vo, Flow item) {
        Integer typeId = item.getTypeId();
        if (typeId == 1) {
            //加班申请
            Overtime overtime = overtimeService.getById(item.getRunId());
            if (!ObjectUtil.isEmpty(overtime)) {
                vo.setApplicantName(deployeeService.getNameByUserId(overtime.getApplicantId()));
                //判断当前的状态是什么
                if (vo.getTotalLevel() >= vo.getCurrentStepId()) {
                    if (vo.getFlowStatus() == 1) {
                        vo.setCurrentStep(item.getCurrentStepId() + ":" + deployeeService.getNameByUserId(vo.getCurrentUserId()) + "审批通过");
                    } else if (vo.getFlowStatus() == 2) {
                        vo.setCurrentStep(item.getCurrentStepId() + ":" + deployeeService.getNameByUserId(vo.getCurrentUserId()) + "审批不通过");
                    } else {
                        vo.setCurrentStep(item.getCurrentStepId() + ":" + deployeeService.getNameByUserId(vo.getCurrentUserId()) + "审批中");
                    }
                } else {
                    vo.setCurrentStep("已完成");
                }
                vo.setTypeName(configService.getById(vo.getTypeId()).getTypeName());
                vo.setRunStatus(overtime.getOvertimeStatus() + "");
                vo.setFlowTitle(overtime.getOvertimeTitle());
            } else {
                //                说明当前流程对应的申请被删除，当前申请删除
                removeById(item.getFlowId());
            }

        } else if (typeId == 2) {
            //请假申请
            Leave leave = leaveService.getById(item.getRunId());
            if (!ObjectUtil.isEmpty(leave)) {
                vo.setApplicantName(deployeeService.getNameByUserId(leave.getUserId()));
                //判断当前的状态是什么
                if (vo.getTotalLevel() >= vo.getCurrentStepId()) {
                    if (vo.getFlowStatus() == 1) {
                        vo.setCurrentStep(item.getCurrentStepId() + ":" + deployeeService.getNameByUserId(vo.getCurrentUserId()) + "审批通过");
                    } else if (vo.getFlowStatus() == 2) {
                        vo.setCurrentStep(item.getCurrentStepId() + ":" + deployeeService.getNameByUserId(vo.getCurrentUserId()) + "审批不通过");
                    } else {
                        vo.setCurrentStep(item.getCurrentStepId() + ":" + deployeeService.getNameByUserId(vo.getCurrentUserId()) + "审批中");
                    }
                } else {
                    vo.setCurrentStep("已完成");
                }
                vo.setTypeName(configService.getById(vo.getTypeId()).getTypeName());
                vo.setRunStatus(leave.getLeaveStatus() + "");
                vo.setFlowTitle(leave.getLeaveTitle());
            } else {
//                说明当前流程对应的申请被删除，当前申请删除
                removeById(item.getFlowId());
            }

        } else if (typeId == 3) {
            //出库申请
            Warehouse warehouse = warehouseService.getById(item.getRunId());
            if (!ObjectUtil.isEmpty(warehouse)) {
                vo.setApplicantName(deployeeService.getNameByUserId(warehouse.getUserId()));
                //判断当前的状态是什么
                if (vo.getTotalLevel() >= vo.getCurrentStepId()) {
                    if (vo.getFlowStatus() == 1) {
                        vo.setCurrentStep(item.getCurrentStepId() + ":" + deployeeService.getNameByUserId(vo.getCurrentUserId()) + "审批通过");
                    } else if (vo.getFlowStatus() == 2) {
                        vo.setCurrentStep(item.getCurrentStepId() + ":" + deployeeService.getNameByUserId(vo.getCurrentUserId()) + "审批不通过");
                    } else {
                        vo.setCurrentStep(item.getCurrentStepId() + ":" + deployeeService.getNameByUserId(vo.getCurrentUserId()) + "审批中");
                    }
                } else {
                    vo.setCurrentStep("已完成");
                }
                vo.setTypeName(configService.getById(vo.getTypeId()).getTypeName());
                vo.setRunStatus(warehouse.getWarehouseStatus() + "");
                vo.setFlowTitle(warehouse.getWarehouseTitle());
            } else {
                //                说明当前流程对应的申请被删除，当前申请删除
                removeById(item.getFlowId());
            }
        } else if (typeId == 4) {
            //日常领用申请
            //出库申请
            DailyUse dailyUse = dailyUseService.getById(item.getRunId());
            if (!ObjectUtil.isEmpty(dailyUse)) {
                vo.setApplicantName(deployeeService.getNameByUserId(dailyUse.getUserId()));
                //判断当前的状态是什么
                if (vo.getTotalLevel() >= vo.getCurrentStepId()) {
                    if (vo.getFlowStatus() == 1) {
                        vo.setCurrentStep(item.getCurrentStepId() + ":" + deployeeService.getNameByUserId(vo.getCurrentUserId()) + "审批通过");
                    } else if (vo.getFlowStatus() == 2) {
                        vo.setCurrentStep(item.getCurrentStepId() + ":" + deployeeService.getNameByUserId(vo.getCurrentUserId()) + "审批不通过");
                    } else {
                        vo.setCurrentStep(item.getCurrentStepId() + ":" + deployeeService.getNameByUserId(vo.getCurrentUserId()) + "审批中");
                    }
                } else {
                    vo.setCurrentStep("已完成");
                }
                vo.setTypeName(configService.getById(vo.getTypeId()).getTypeName());
                vo.setRunStatus(dailyUse.getDailyuseStatus() + "");
                vo.setFlowTitle(dailyUse.getDailyuseTitle());
            } else {
                //                说明当前流程对应的申请被删除，当前申请删除
                removeById(item.getFlowId());
            }
        } else if (typeId == 5) {
            //付款申请
            Payment payment = paymentService.getById(item.getRunId());
            if (!ObjectUtil.isEmpty(payment)) {
                vo.setApplicantName(deployeeService.getNameByUserId(payment.getUserId()));
                //判断当前的状态是什么
                if (vo.getTotalLevel() >= vo.getCurrentStepId()) {
                    if (vo.getFlowStatus() == 1) {
                        vo.setCurrentStep(item.getCurrentStepId() + ":" + deployeeService.getNameByUserId(vo.getCurrentUserId()) + "审批通过");
                    } else if (vo.getFlowStatus() == 2) {
                        vo.setCurrentStep(item.getCurrentStepId() + ":" + deployeeService.getNameByUserId(vo.getCurrentUserId()) + "审批不通过");
                    } else {
                        vo.setCurrentStep(item.getCurrentStepId() + ":" + deployeeService.getNameByUserId(vo.getCurrentUserId()) + "审批中");
                    }
                } else {
                    vo.setCurrentStep("已完成");
                }
                vo.setTypeName(configService.getById(vo.getTypeId()).getTypeName());
                vo.setRunStatus(payment.getPaymentStatus() + "");
                vo.setFlowTitle(payment.getPaymentTitle());
            } else {
                //                说明当前流程对应的申请被删除，当前申请删除
                removeById(item.getFlowId());
            }
        } else if (typeId == 6) {
            //报销申请
            Reimbursement reimbursement = reimbursementService.getById(item.getRunId());
            if (!ObjectUtil.isEmpty(reimbursement)) {
                vo.setApplicantName(deployeeService.getNameByUserId(reimbursement.getUserId()));
                //判断当前的状态是什么
                if (vo.getTotalLevel() >= vo.getCurrentStepId()) {
                    if (vo.getFlowStatus() == 1) {
                        vo.setCurrentStep(item.getCurrentStepId() + ":" + deployeeService.getNameByUserId(vo.getCurrentUserId()) + "审批通过");
                    } else if (vo.getFlowStatus() == 2) {
                        vo.setCurrentStep(item.getCurrentStepId() + ":" + deployeeService.getNameByUserId(vo.getCurrentUserId()) + "审批不通过");
                    } else {
                        vo.setCurrentStep(item.getCurrentStepId() + ":" + deployeeService.getNameByUserId(vo.getCurrentUserId()) + "审批中");
                    }
                } else {
                    vo.setCurrentStep("已完成");
                }
                vo.setTypeName(configService.getById(vo.getTypeId()).getTypeName());
                vo.setRunStatus(reimbursement.getReimbursementStatus() + "");
                vo.setFlowTitle(reimbursement.getReimbursementTitle());
            } else {
                //                说明当前流程对应的申请被删除，当前申请删除
                removeById(item.getFlowId());
            }
        } else if (typeId == 7) {
            //采购申请
            Purchase purchase = purchaseService.getById(item.getRunId());
            if (!ObjectUtil.isEmpty(purchase)) {
                vo.setApplicantName(deployeeService.getNameByUserId(purchase.getUserId()));
                //判断当前的状态是什么
                if (vo.getTotalLevel() >= vo.getCurrentStepId()) {
                    if (vo.getFlowStatus() == 1) {
                        vo.setCurrentStep(item.getCurrentStepId() + ":" + deployeeService.getNameByUserId(vo.getCurrentUserId()) + "审批通过");
                    } else if (vo.getFlowStatus() == 2) {
                        vo.setCurrentStep(item.getCurrentStepId() + ":" + deployeeService.getNameByUserId(vo.getCurrentUserId()) + "审批不通过");
                    } else {
                        vo.setCurrentStep(item.getCurrentStepId() + ":" + deployeeService.getNameByUserId(vo.getCurrentUserId()) + "审批中");
                    }
                } else {
                    vo.setCurrentStep("已完成");
                }
                vo.setTypeName(configService.getById(vo.getTypeId()).getTypeName());
                vo.setRunStatus(purchase.getPurchaseStatus() + "");
                vo.setFlowTitle(purchase.getPurchaseTitle());
            } else {
//                说明当前流程对应的申请被删除，当前申请删除
                removeById(item.getFlowId());
            }
        }

    }

    /**
     * 加班 typeId =1
     */
    @Override
    public boolean insertFlow(Overtime insertOvertime) {


        FlowConfig config = configService.getById(insertOvertime.getTypeId());

        List<Integer> approvalFlow = config.getApprovalFlow();

        //审批流里的所有下级用户
        Flow flow = new Flow();
        flow.setTypeId(insertOvertime.getTypeId());
        flow.setCurrentUserId(approvalFlow.get(0));
        flow.setFlowTitle(insertOvertime.getOvertimeTitle());
        flow.setRunId(insertOvertime.getOvertimeId());
        flow.setCurrentStepId(1);
        flow.setTotalLevel(approvalFlow.size());
        flow.setCreatedUserId(insertOvertime.getApplicantId());
        return save(flow);

    }

    /**
     * 请假 typeId =2
     */
    @Override
    public boolean insertFlow(Leave insertLeave) {

        FlowConfig config = configService.getById(insertLeave.getTypeId());

        List<Integer> approvalFlow = config.getApprovalFlow();

        //审批流里的所有下级用户
        Flow flow = new Flow();

        flow.setTypeId(insertLeave.getTypeId());
        flow.setCurrentUserId(approvalFlow.get(0));
        flow.setFlowTitle(insertLeave.getLeaveTitle());
        flow.setRunId(insertLeave.getLeaveId());
        flow.setCurrentStepId(1);
        flow.setTotalLevel(approvalFlow.size());
        flow.setCreatedUserId(insertLeave.getUserId());
        return save(flow);


    }

    /**
     * 出库 typeId = 3
     */
    @Override
    public boolean insertFlow(Warehouse warehouse) {

        if (warehouse.getCategoryId() == 2) {
            //如果为出库申请则需审批
            FlowConfig config = configService.getById(warehouse.getTypeId());
            List<Integer> approvalFlow = config.getApprovalFlow();
            //审批流里的所有下级用户
            Flow flow = new Flow();
            flow.setTypeId(warehouse.getTypeId());
            flow.setCurrentUserId(approvalFlow.get(0));
            flow.setFlowTitle(warehouse.getWarehouseTitle());
            flow.setRunId(warehouse.getWarehouseId());
            flow.setCurrentStepId(1);
            flow.setTotalLevel(approvalFlow.size());
            flow.setCreatedUserId(warehouse.getUserId());
            return save(flow);
        } else {
            return false;
        }

    }

    /**
     * 日常领用 typeId = 4
     */
    @Override
    public boolean insertFlow(DailyUse dailyUse) {

        if (dailyUse.getCategoryId() == 2) {
            //如果为日常领用的出库申请则需审批
            FlowConfig config = configService.getById(dailyUse.getTypeId());
            List<Integer> approvalFlow = config.getApprovalFlow();
            //审批流里的所有下级用户
            Flow flow = new Flow();
            flow.setTypeId(dailyUse.getTypeId());
            flow.setCurrentUserId(approvalFlow.get(0));
            flow.setFlowTitle(dailyUse.getDailyuseTitle());
            flow.setRunId(dailyUse.getDailyuseId());
            flow.setCurrentStepId(1);
            flow.setTotalLevel(approvalFlow.size());
            flow.setCreatedUserId(dailyUse.getUserId());
            return save(flow);
        } else {
            return false;
        }
    }


    /**
     * 支付 typeId = 5
     */
    @Override
    public boolean insertFlow(Payment payment) {


        FlowConfig config = configService.getById(payment.getTypeId());

        List<Integer> approvalFlow = config.getApprovalFlow();

        //审批流里的所有下级用户
        Flow flow = new Flow();
        flow.setTypeId(payment.getTypeId());
        flow.setCurrentUserId(approvalFlow.get(0));
        flow.setFlowTitle(payment.getPaymentTitle());
        flow.setRunId(payment.getPaymentId());
        flow.setCurrentStepId(1);
        flow.setTotalLevel(approvalFlow.size());
        flow.setCreatedUserId(payment.getUserId());
        return save(flow);

    }

    /**
     * 报销 typeId = 6
     */
    @Override
    public boolean insertFlow(Reimbursement reimbursement) {

        FlowConfig config = configService.getById(reimbursement.getTypeId());

        List<Integer> approvalFlow = config.getApprovalFlow();

        //审批流里的所有下级用户
        Flow flow = new Flow();
        flow.setTypeId(reimbursement.getTypeId());
        flow.setCurrentUserId(approvalFlow.get(0));
        flow.setFlowTitle(reimbursement.getReimbursementTitle());
        flow.setRunId(reimbursement.getReimbursementId());
        flow.setCurrentStepId(1);
        flow.setTotalLevel(approvalFlow.size());
        flow.setCreatedUserId(reimbursement.getUserId());
        return save(flow);

    }


    /**
     * 报销 typeId = 7
     */
    @Override
    public boolean insertFlow(Purchase purchase) {

        FlowConfig config = configService.getById(purchase.getTypeId());

        List<Integer> approvalFlow = config.getApprovalFlow();

        //审批流里的所有下级用户
        Flow flow = new Flow();
        flow.setTypeId(purchase.getTypeId());
        flow.setCurrentUserId(approvalFlow.get(0));
        flow.setFlowTitle(purchase.getPurchaseTitle());
        flow.setRunId(purchase.getPurchaseId());
        flow.setCurrentStepId(1);
        flow.setTotalLevel(approvalFlow.size());
        flow.setCreatedUserId(purchase.getUserId());
        return save(flow);
    }

    @Override
    public ResponseResult getFlowListByCurrentUser(PageQueryDto queryDto, HttpServletRequest request) {

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

        // TODO 根据当前操作用户是谁 只能查询属于关于自己的内容吗？
        String authorization = request.getHeader("Authorization");
        if(!ObjectUtil.isEmpty(authorization)){
            System.out.println(authorization);
            String token = authorization.split(" ")[1];

            String id = JwtUtils.getMemberIdByJwtToken(token);
            queryWrapper.eq(Flow::getCurrentUserId,id).or().eq(Flow::getCreatedUserId,id);

        }else{
            throw new SystemException(AppHttpCodeEnum.TOKEN_PARSE_ERRPE);
        }

        IPage<Flow> recordPage = page(page, queryWrapper);
        //相同的typeId 和runId的只显示最早的createdTime的数据

        PageVo<List<FlowVo>> result = new PageVo<>();

        List<FlowVo> resultList = new ArrayList<>();
        try {
            resultList = recordPage.getRecords().stream().map((item) -> {
                FlowVo vo = BeanCopyUtils.copyBean(item, FlowVo.class);
                //类型名称?
                setFlowVoProperty(vo, item);
                return vo;
            }).collect(Collectors.toList());
            result.setData(resultList);
            result.setTotal((int) recordPage.getTotal());
            return ResponseResult.okResult(result);
        } catch (Exception e) {
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }
    }

    @Override
    public ResponseResult getFlowDetail(Integer typeId, Integer runId) {
        if (typeId == 1) {
            //加班申请
            LambdaQueryWrapper<Overtime> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Overtime::getOvertimeId, runId);
            Overtime one = overtimeService.getOne(queryWrapper);
            OvertimeVo vo = BeanCopyUtils.copyBean(one, OvertimeVo.class);
//            OvertimeVo vo = overtimeService.setProperyToVo(one);
            return ResponseResult.okResult(vo);
        } else if (typeId == 2) {
            //请假申请
            LambdaQueryWrapper<Leave> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Leave::getLeaveId, runId);
            Leave one = leaveService.getOne(queryWrapper);
            LeaveVo vo = BeanCopyUtils.copyBean(one, LeaveVo.class);
            return ResponseResult.okResult(vo);
        } else if (typeId == 3) {
            //出库申请
            LambdaQueryWrapper<Warehouse> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Warehouse::getWarehouseId, runId);
            Warehouse one = warehouseService.getOne(queryWrapper);
            WarehouseVo vo = BeanCopyUtils.copyBean(one, WarehouseVo.class);
            return ResponseResult.okResult(vo);
        } else if (typeId == 4) {
            //日常领用申请
            LambdaQueryWrapper<DailyUse> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DailyUse::getDailyuseId, runId);
            DailyUse one = dailyUseService.getOne(queryWrapper);
            DailyUseVo vo = BeanCopyUtils.copyBean(one, DailyUseVo.class);
            return ResponseResult.okResult(vo);
        } else if (typeId == 5) {
            //付款申请
            LambdaQueryWrapper<Payment> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Payment::getPaymentId, runId);
            Payment one = paymentService.getOne(queryWrapper);
            PaymentVo vo = BeanCopyUtils.copyBean(one, PaymentVo.class);
            return ResponseResult.okResult(vo);
        } else if (typeId == 6) {
            //报销申请
            LambdaQueryWrapper<Reimbursement> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Reimbursement::getReimbursementId, runId);
            Reimbursement one = reimbursementService.getOne(queryWrapper);
            ReimbursementVo vo = BeanCopyUtils.copyBean(one, ReimbursementVo.class);
            return ResponseResult.okResult(vo);
        } else if (typeId == 7) {
            //采购申请
            LambdaQueryWrapper<Purchase> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Purchase::getPurchaseId, runId);
            Purchase one = purchaseService.getOne(queryWrapper);
            PurchaseVo vo = BeanCopyUtils.copyBean(one, PurchaseVo.class);
            return ResponseResult.okResult(vo);
        } else {
            throw new SystemException(AppHttpCodeEnum.FLOW_TYPE_ERROR);
        }
    }

    @Override
    public ResponseResult deleteFlow(Integer id) {

        boolean b = removeById(id);
        if(b){
            return ResponseResult.okResult(b);
        }else{
            return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);
        }
    }

}




