package com.gdproj.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.SelectPage;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.gdproj.dto.pageDto;
import com.gdproj.entity.Leave;
import com.gdproj.entity.Sign;
import com.gdproj.mapper.SignMapper;
import com.gdproj.service.DepartmentService;
import com.gdproj.service.DeployeeService;
import com.gdproj.service.SignService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.signVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【sys_sign(考勤
)】的数据库操作Service实现
* @createDate 2023-09-13 11:09:17
*/
@Service
public class SignServiceImpl extends ServiceImpl<SignMapper, Sign>
    implements SignService {

    @Autowired
    SignMapper signMapper;
    @Autowired
    DeployeeService deployeeService;

    @Autowired
    DepartmentService departmentService;

    @Override
    public IPage<signVo> getSignList(pageDto pagedto) {

        LambdaQueryWrapper<Sign> queryWrapper = new LambdaQueryWrapper<>();

        Page<Sign> page = new Page<>(pagedto.getPageNum(), pagedto.getPageSize());

        String title = pagedto.getTitle();
        String time = pagedto.getTime();
        List<Sign> signList = new ArrayList<>();
        //设置排序
        if(pagedto.getSort().equals("+id")){
            queryWrapper.orderByAsc(Sign::getSignId);
        }else{
            queryWrapper.orderByDesc(Sign::getSignId);
        }


        //如果根据部门分类，有一定几率会与模糊人民冲突
        if(!Objects.isNull(pagedto.getDepartmentId()) && title.isEmpty()){
//            List<Integer> ids = deployeeService.getIdsByDepartmentId(pagedto.getType());
//            queryWrapper.in(Sign::getDepartmentId,ids);
            queryWrapper.in(Sign::getDepartmentId,pagedto.getDepartmentId());
        }

        //设置时间 年 月 日
        //模糊查询时间

        if(time != null){
            queryWrapper.like(Sign::getInTime,time);
        }

        //模糊查询人名
        if(!title.isEmpty()){
            //如果有模糊查询的时间 先通过查title 的用户ids
            List<Integer> ids = deployeeService.getIdsByTitle(title);
            queryWrapper.in(Sign::getSignUser,ids);
            //通过ids去找所有符合ids的对象 sign;
        }


        IPage<Sign> signPage = signMapper.selectPage(page, queryWrapper);

        Page<signVo> resultPage = new Page<>();
        //结果里的部门 和用户都返回成string；
        List<signVo> resultList = signPage.getRecords().stream().map((item) -> {
            signVo signvo = BeanCopyUtils.copyBean(item, signVo.class);
            //用户
            signvo.setSignUser(deployeeService.getNameByUserId(item.getSignUser()));
            //部门
            signvo.setDepartmentId(departmentService.getDepartmentNameByDepartmentId(item.getDepartmentId()));
            long workTime = (signvo.getEndTime().getTime() - signvo.getInTime().getTime()) / 1000 / 60 / 60;
            signvo.setTWorkTime( (int) workTime);
            //设置是否完成考勤 判断时长 判断是否迟到 是否早退
            DateFormat timeInstance = DateFormat.getTimeInstance();

            System.out.println(timeInstance.format(item.getEndTime()));
            if( workTime >= signvo.getWorkTime() && signvo.getSignAddr() == "XXXX附近"  ){
                signvo.setSignStatus(1);
            }else{
                signvo.setSignStatus(0);
            }
            return signvo;

        }).collect(Collectors.toList());

        resultPage.setRecords(resultList);

        resultPage.setTotal(signPage.getTotal());

        return resultPage;

    }
}




