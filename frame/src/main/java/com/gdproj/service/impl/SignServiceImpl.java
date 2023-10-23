package com.gdproj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Sign;
import com.gdproj.mapper.SignMapper;
import com.gdproj.service.DepartmentService;
import com.gdproj.service.DeployeeService;
import com.gdproj.service.SignService;
import com.gdproj.utils.BaiduMapUtil;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.isSignVo;
import com.gdproj.vo.signVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
        if(!ObjectUtil.isEmpty(pagedto.getDepartmentId()) && title.isEmpty()){
            List<Integer> ids = deployeeService.getIdsByDepartmentId(pagedto.getDepartmentId());
            if(ObjectUtil.isEmpty(ids)){
                queryWrapper.in(Sign::getUserId,0);
            }else{
                queryWrapper.in(Sign::getUserId,ids);
            }
//            queryWrapper.in(Sign::getDepartmentId,pagedto.getDepartmentId());
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
            queryWrapper.in(Sign::getUserId,ids);
            //通过ids去找所有符合ids的对象 sign;
        }


        IPage<Sign> signPage = signMapper.selectPage(page, queryWrapper);

        Page<signVo> resultPage = new Page<>();
        //结果里的部门 和用户都返回成string；
        List<signVo> resultList = signPage.getRecords().stream().map((item) -> {
            signVo signvo = BeanCopyUtils.copyBean(item, signVo.class);

            signvo.setUsername(deployeeService.getNameByUserId(item.getUserId()));
            //部门
            signvo.setDepartment(departmentService.getDepartmentNameByDepartmentId(deployeeService.getById(item.getUserId()).getDepartmentId()));
            signvo.setDepartmentId(deployeeService.getDepartmentIdByUserId(item.getUserId()));
            long workTime = 0L;
            if( !ObjectUtil.isEmpty(signvo.getEndTime())){
                workTime = (signvo.getEndTime().getTime() - signvo.getInTime().getTime()) / 1000 / 60 / 60;
            }

            signvo.setTWorkTime((int) workTime);
            if(signvo.getTWorkTime() < item.getWorkTime()){
                signvo.setSignStatus(0);
            }
            //设置是否完成考勤 判断时长 判断是否迟到 是否早退
            DateFormat timeInstance = DateFormat.getTimeInstance();

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

    @Override
    public boolean insertSign(Sign sign) {


        LambdaQueryWrapper<Sign> queryWrapper = new LambdaQueryWrapper<>();
        //格式化当前日期
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(calendar.getTime());
        //日期筛选
        queryWrapper.like(Sign::getInTime,format);
        //用户筛选
        queryWrapper.eq(Sign::getUserId,sign.getUserId());
        //拿到当前用户当天的打卡记录
        Sign one = getOne(queryWrapper);
        //先判断是否今天已打过卡
        isSignVo vo = getSingInfoByUserIdAndDate(sign.getUserId());
        //如果早上签过到
        if(vo.getIsSignIn() == 1){
            one.setEndTime(calendar.getTime());
            if(hour < 17){
                one.setIsEarly(1);
            }
            return updateById(one);
        }else{
            //如果早上没有签过到 那就是早上第一次签到
            sign.setInTime(calendar.getTime());
            if(hour > 8 || (hour == 8 && minute >= 30)){
                sign.setIsLate(1);
            }else{
                sign.setIsLate(0);
            }
            //只有用户和签到时间
            sign.setSignAddr(BaiduMapUtil.getAddress("221.136.212.195"));
            return save(sign);
        }

    }

    @Override
    public isSignVo getSingInfoByUserIdAndDate(Integer userId) {

      LambdaQueryWrapper<Sign> queryWrapper = new LambdaQueryWrapper<>();
      queryWrapper.eq(Sign::getUserId,userId);
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(calendar.getTime());
        //拿到当前日期
      queryWrapper.like(Sign::getInTime,format);
//            每天的打卡记录只有一条
        Sign one = getOne(queryWrapper);
        isSignVo vo = new isSignVo();
        if(!ObjectUtil.isEmpty(one)){
//            有打卡记录 并且签到时间不为空
            if(!ObjectUtil.isEmpty(one.getInTime())){
                vo.setIsSignIn(1);
                vo.setSignInTime(one.getInTime());
            }else{
//            有打卡记录 但是签到时间为空
                vo.setIsSignIn(0);
                vo.setIsSignOut(0);
            }
//            有打卡记录 并且签退时间不为空
            if(!ObjectUtil.isEmpty(one.getEndTime())){
                vo.setIsSignOut(1);
                vo.setSingOutTime(one.getEndTime());
            }else{
//            有打卡记录 但是签退时间为空
                vo.setIsSignOut(0);
            }
        }else{
//            没有打卡记录
            vo.setIsSignIn(0);
            vo.setIsSignOut(0);
        }
        return vo;
    }

}




