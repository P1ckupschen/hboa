package com.gdproj.service.impl;

import cn.hutool.core.convert.NumberChineseFormatter;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Payment;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.PaymentMapper;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.DeployeeService;
import com.gdproj.service.FlowService;
import com.gdproj.service.PaymentService;
import com.gdproj.utils.AesUtil;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.PageVo;
import com.gdproj.vo.PaymentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【sys_payment】的数据库操作Service实现
* @createDate 2023-10-19 14:05:08
*/
@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, Payment>
    implements PaymentService {


    @Autowired
    FlowService flowService;

    @Autowired
    DeployeeService deployeeService;

    @Override
    public boolean insertPayment(Payment payment) {

        if(!ObjectUtil.isEmpty(payment.getPaymentAccount())){
            payment.setPaymentAccount(AesUtil.encrypt(payment.getPaymentAccount(),AesUtil.key128));
        }
        //插入overtime数据
        boolean f =false;
        payment.setUserName(deployeeService.getNameByUserId(payment.getUserId()));
        boolean o =save(payment);
        if(o){
            f = flowService.insertFlow(payment);
        }else{
            throw new SystemException(AppHttpCodeEnum.INSERT_ERROR);
        }
        //同时更新flow 表 增加一条flow数据
        return o && f;
    }

    @Override
    public ResponseResult getPaymentList(PageQueryDto queryDto) {

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

        Page<Payment> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Payment> queryWrapper = new LambdaQueryWrapper<>();
        //排序
        if (sort.equals("+id")) {
            queryWrapper.orderByAsc(Payment::getPaymentId);
        } else {
            queryWrapper.orderByDesc(Payment::getPaymentId);
        }

        if (!title.isEmpty()) {
            List<Integer> ids = deployeeService.getIdsByTitle(title);
            if(!ObjectUtil.isEmpty(ids)){
                queryWrapper.in(Payment::getUserId,ids);
            }else{
                queryWrapper.eq(Payment::getUserId,0);
            }
        }

        if(!ObjectUtil.isEmpty(time)){
            queryWrapper.like(Payment::getCreatedTime,time);
        }
        //如果有类型的话 类型
        if (!ObjectUtil.isEmpty(type)) {

            queryWrapper.eq(Payment::getCategoryId,type);
        }
        IPage<Payment> recordPage = page(page, queryWrapper);

        List<PaymentVo> collect = recordPage.getRecords().stream().map((item) -> {
            PaymentVo vo = BeanCopyUtils.copyBean(item, PaymentVo.class);
            if(ObjectUtil.isEmpty(item.getUserId())){

                throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
            }
            //金额大写
            if(!ObjectUtil.isEmpty(item.getPaymentAmount())){
                vo.setPaymentAmountCap(NumberChineseFormatter.format(item.getPaymentAmount().doubleValue(),true,true));
            }
            //账号解密
            if(!ObjectUtil.isEmpty(item.getPaymentAccount())){
                vo.setPaymentAccount(AesUtil.decrypt(item.getPaymentAccount(),AesUtil.key128));
            }

            return vo;
        }).collect(Collectors.toList());

        PageVo<List<PaymentVo>> pageList = new PageVo<>();

        List<PaymentVo> paymentVos = addOrderId(collect, pageNum, pageSize);
        pageList.setData(paymentVos);

        pageList.setTotal((int) recordPage.getTotal());

        return ResponseResult.okResult(pageList);

    }

    @Override
    public ResponseResult updatePayment(PaymentVo vo) {

        Payment payment = BeanCopyUtils.copyBean(vo, Payment.class);
        if(!ObjectUtil.isEmpty(payment.getPaymentAccount())){
            payment.setPaymentAccount(AesUtil.encrypt(payment.getPaymentAccount(),AesUtil.key128));
        }
        boolean b = updateById(payment);
        if(b){
            return ResponseResult.okResult(b);
        }else{
            throw  new SystemException(AppHttpCodeEnum.UPDATE_ERROR);
        }
    }

    @Override
    public ResponseResult deletePayment(Integer id) {
        boolean b = removeById(id);
        if(b){
            return ResponseResult.okResult(b);
        }else{
            throw new SystemException(AppHttpCodeEnum.DELETE_ERROR);
        }
    }

    private List<PaymentVo> addOrderId(List<PaymentVo> list, Integer pageNum, Integer pageSize){
        if (!ObjectUtil.isEmpty(pageNum) && !ObjectUtil.isEmpty(pageSize)) {
            for (int i = 0 ; i < list.size() ; i++){
                list.get(i).setOrderId((pageNum - 1) * pageSize + i + 1);
            }
        }
        return list;
    }


}




