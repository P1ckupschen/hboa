package com.gdproj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.MonthSignExcelEntity;
import com.gdproj.entity.Sign;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.SignMapper;
import com.gdproj.service.DepartmentService;
import com.gdproj.service.DeployeeService;
import com.gdproj.service.SignService;
import com.gdproj.utils.BaiduMapUtil;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.DeployeeVo;
import com.gdproj.vo.IsSignVo;
import com.gdproj.vo.MonthSignVo;
import com.gdproj.vo.SignVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【sys_sign(考勤
 * )】的数据库操作Service实现
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
    public IPage<SignVo> getSignList(PageQueryDto pagedto) {

        LambdaQueryWrapper<Sign> queryWrapper = new LambdaQueryWrapper<>();

        Page<Sign> page = new Page<>(pagedto.getPageNum(), pagedto.getPageSize());

        String title = pagedto.getTitle();
        String time = pagedto.getTime();
        List<Sign> signList = new ArrayList<>();
        //设置排序
        if (pagedto.getSort().equals("+id")) {
            queryWrapper.orderByAsc(Sign::getSignId);
        } else {
            queryWrapper.orderByDesc(Sign::getSignId);
        }


        //如果根据部门分类，有一定几率会与模糊人民冲突
        if (!ObjectUtil.isEmpty(pagedto.getDepartmentId()) && title.isEmpty()) {
            List<Integer> ids = deployeeService.getIdsByDepartmentId(pagedto.getDepartmentId());
            if (ObjectUtil.isEmpty(ids)) {
                queryWrapper.in(Sign::getUserId, 0);
            } else {
                queryWrapper.in(Sign::getUserId, ids);
            }
//            queryWrapper.in(Sign::getDepartmentId,pagedto.getDepartmentId());
        }

        //设置时间 年 月 日
        //模糊查询时间

        if (time != null) {
            queryWrapper.like(Sign::getInTime, time);
        }

        //模糊查询人名
        if (!title.isEmpty()) {
            //如果有模糊查询的时间 先通过查title 的用户ids
            List<Integer> ids = deployeeService.getIdsByTitle(title);
            if(!ObjectUtil.isEmpty(ids)){
                queryWrapper.in(Sign::getUserId, ids);
            }else{
                queryWrapper.in(Sign::getUserId,0);
            }
            //通过ids去找所有符合ids的对象 sign;
        }


        IPage<Sign> signPage = signMapper.selectPage(page, queryWrapper);

        Page<SignVo> resultPage = new Page<>();
        //结果里的部门 和用户都返回成string；
        List<SignVo> resultList = signPage.getRecords().stream().map((item) -> {
            SignVo signvo = BeanCopyUtils.copyBean(item, SignVo.class);
            signvo.setUsername(deployeeService.getNameByUserId(item.getUserId()));
            //部门
            signvo.setDepartment(departmentService.getDepartmentNameByDepartmentId(deployeeService.getById(item.getUserId()).getDepartmentId()));
            signvo.setDepartmentId(deployeeService.getDepartmentIdByUserId(item.getUserId()));
            long workTime = 0L;
            if (!ObjectUtil.isEmpty(signvo.getEndTime())) {
                workTime = (signvo.getEndTime().getTime() - signvo.getInTime().getTime()) / 1000 / 60 / 60;
            }

            signvo.setTWorkTime((int) workTime);
            if (signvo.getTWorkTime() < item.getWorkTime()) {
                signvo.setSignStatus(0);
            }
            //如果 迟到
            //设置是否完成考勤 判断时长 判断是否迟到 是否早退

            DateFormat timeInstance = DateFormat.getTimeInstance();

            if (workTime >= signvo.getWorkTime() && signvo.getSignAddr() == "XXXX附近") {
                signvo.setSignStatus(1);
            } else {
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
        queryWrapper.like(Sign::getInTime, format);
        //用户筛选
        queryWrapper.eq(Sign::getUserId, sign.getUserId());
        //拿到当前用户当天的打卡记录
        Sign one = getOne(queryWrapper);
        //先判断是否今天已打过卡
        IsSignVo vo = getSignInfoByUserIdAndDate(sign.getUserId());
        //如果早上签过到
        if (vo.getIsSignIn() == 1) {
            one.setEndTime(calendar.getTime());
            //早上签到时间-下午签退时间；
            one.settWorkTime(Math.round(one.getEndTime().getTime() - one.getInTime().getTime()));
            if (hour < 17) {
                one.setIsEarly(1);
            }
            return updateById(one);
        } else {
            //如果早上没有签过到 那就是早上第一次签到
            sign.setInTime(calendar.getTime());
            if (hour > 8 || (hour == 8 && minute >= 30)) {
                sign.setIsLate(1);
            } else {
                sign.setIsLate(0);
            }
            //只有用户和签到时间
            sign.setSignAddr(BaiduMapUtil.getAddress("221.136.212.195"));
            return save(sign);
        }

    }

    @Override
    public IsSignVo getSignInfoByUserIdAndDate(Integer userId) {

        LambdaQueryWrapper<Sign> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Sign::getUserId, userId);
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(calendar.getTime());
        //拿到当前日期
        queryWrapper.like(Sign::getInTime, format);
//            每天的打卡记录只有一条
        Sign one = getOne(queryWrapper);
        IsSignVo vo = new IsSignVo();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

            Date shouldInTime = sdf.parse("8:30");
            Date shouldOutTime = sdf.parse("17:30");
            vo.setShouldInTime(shouldInTime);
            vo.setShouldOutTime(shouldOutTime);
        } catch (Exception e) {
            throw new SystemException(AppHttpCodeEnum.DATE_FORMAT_ERROR);
        }


        if (!ObjectUtil.isEmpty(one)) {
//            有打卡记录 并且签到时间不为空
            if (!ObjectUtil.isEmpty(one.getInTime())) {
                vo.setIsSignIn(1);
                vo.setSignInTime(one.getInTime());
            } else {
//            有打卡记录 但是签到时间为空
                vo.setIsSignIn(0);
                vo.setIsSignOut(0);
            }
//            有打卡记录 并且签退时间不为空
            if (!ObjectUtil.isEmpty(one.getEndTime())) {
                vo.setIsSignOut(1);
                vo.setSignOutTime(one.getEndTime());
            } else {
//            有打卡记录 但是签退时间为空
                vo.setIsSignOut(0);
            }
        } else {
//            没有打卡记录
            vo.setIsSignIn(0);
            vo.setIsSignOut(0);
        }
        return vo;
    }

    @Override
    public IPage<MonthSignVo> getMonthSignList(PageQueryDto pageDto) {

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


        Page<MonthSignVo> resultPage = new Page<>();

        try {
            //获取time 的年 月 日
            Calendar cal = Calendar.getInstance();
            Date date = transferStringToDate(time);
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            int day = cal.get(Calendar.DAY_OF_MONTH);
            //首先获取员工列表，根据分页数据d
            IPage<DeployeeVo> deployeeListPage = deployeeService.getDeployeeList(pageDto);
            List<DeployeeVo> deployeeList = deployeeListPage.getRecords();
            List<MonthSignVo> resultList = deployeeList.stream().map((item) -> countMonthDataByDeployee(item, year, month, day)).collect(Collectors.toList());
            resultPage.setRecords(resultList);
            resultPage.setTotal(deployeeListPage.getTotal());
            return resultPage;
        } catch (Exception e) {
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }
    }

    private Date transferStringToDate(String time) {
        try {
            if(ObjectUtil.isEmpty(time)){
                int m = Calendar.getInstance().get(Calendar.MONTH) + 1;
                int y =Calendar.getInstance().get(Calendar.YEAR);
                int d =Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                time = y + "-" + m + "-" + d;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(time);
        }catch (ParseException e){
            throw new SystemException(AppHttpCodeEnum.DATE_FORMAT_ERROR);
        } catch (Exception e) {
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }
    }

    @Override
    public void exportMonthSignExcel(PageQueryDto pageDto, HttpServletResponse response) {
        String fileName = "admin/src/main/resources/signExcel/" + System.currentTimeMillis() + ".xlsx";
        IPage<MonthSignVo> monthSignList = getMonthSignList(pageDto);
        String time = pageDto.getTime();
        List<MonthSignVo> records = monthSignList.getRecords();

        Date date = transferStringToDate(time);

        List<MonthSignExcelEntity> collect = records.stream().map((item) -> {
            MonthSignExcelEntity excelEntity = BeanCopyUtils.copyBean(item, MonthSignExcelEntity.class);
            excelEntity.setUserId(item.getDeployee().getDeployeeId());
            excelEntity.setUsername(item.getDeployee().getDeployeeName());
            //设置早退记录
            excelEntity.setEarlyHistory(transferEarlyHistoryToString(item.getEarlyHistory()));
            //设置迟到记录
            excelEntity.setLateHistory(transferLateHistoryToString(item.getLateHistory()));
            //设置考勤时期
            excelEntity.setStage(setSignStage(date));
            return excelEntity;
        }).collect(Collectors.toList());
        EasyExcel.write(fileName, MonthSignExcelEntity.class).sheet("月度考勤统计").doWrite(collect);
        downloadExcel(fileName,response);
    }

    private void downloadExcel(String fileName, HttpServletResponse response) {
        try {
            File file = new File(fileName);
            FileInputStream fis = new FileInputStream(file);
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());


            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + "sign.xlsx");
            // 设置响应头
            response.setContentType("application/octet-stream");

            //创建存放文件内容的数组
            byte[] buff = new byte[1024];
            //所读取的内容使用n来接收
            int n;
            //当没有读取完时,继续读取,循环
            while ((n = fis.read(buff)) != -1) {
                //将字节数组的数据全部写入到输出流中
                outputStream.write(buff, 0, n);
            }
            //强制将缓存区的数据进行输出
            outputStream.flush();
            //关流
            outputStream.close();
            fis.close();

        } catch (IOException e) {
            e.printStackTrace();
            throw new SystemException(AppHttpCodeEnum.DOWNLOAD_EXCEL_ERROR);
        }

    }


    private String setSignStage(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int m = cal.get(Calendar.MONTH) + 1;
        int d =cal.get(Calendar.DAY_OF_MONTH);
        return (m-1) + "月" + d + "日" + "~" + m + "月" + d + "日";
    }

    private String transferLateHistoryToString(List<SignVo> lateHistory) {
        List<String> collect1 = lateHistory.stream().map((i) -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = simpleDateFormat.format(i.getInTime());
            return format+ " 早上打卡迟到";
        }).collect(Collectors.toList());
        return collect1.toString();
    }

    private String transferEarlyHistoryToString(List<SignVo> earlyHistory) {
        List<String> collect1 = earlyHistory.stream().map((i) -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = simpleDateFormat.format(i.getEndTime());
            return format+ " 下午打卡早退";
        }).collect(Collectors.toList());
        return collect1.toString();
    }

    private MonthSignVo countMonthDataByDeployee(DeployeeVo deployee, int year, int month, int day) {

        MonthSignVo vo = new MonthSignVo();
        //年份
        vo.setYear(year);
        //月份
        vo.setMonth(month);
        //从查询的当天 day开始 到一个月后的day；
        LambdaQueryWrapper<Sign> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.between(Sign::getInTime, year + "-" + (month - 1) + "-" + day + " 00:00:00", year + "-" + month + "-" + day + " 00:00:00");
        queryWrapper.eq(Sign::getUserId, deployee.getDeployeeId());
        //获得这一个月内的这个用户的签到记录
        List<Sign> list = list(queryWrapper);

        //设置考勤人
        vo.setDeployee(deployee);
        vo.setUserId(deployee.getUserId());
        //固定上班时间 一个月27天; TODO  固定上班时间 设置为 27 天
        vo.setShouldAttendanceDays(27);
        //统计上班status == 1的list的size();
        //实际上班天数
        vo.setAttendanceDays((int) list.stream().filter((item) -> item.getSignStatus() == 1).count());
        //出勤率 百分比  实际上班天数 / 固定上班时间  算出百分比
        vo.setAttendanceRate((double) Math.round(vo.getAttendanceDays() / (double)vo.getShouldAttendanceDays() *100.0) / 100.0);
        //迟到天数
        vo.setLateDays(getLateHistoryByMonthList(list).size());
        //迟到记录
        vo.setLateHistory(getLateHistoryByMonthList(list));
        //早退天数
        vo.setEarlyDays(getEarlyHistoryByMonthList(list).size());
        //早退记录
        vo.setEarlyHistory(getEarlyHistoryByMonthList(list));
        return vo;
    }

    private List<SignVo> getEarlyHistoryByMonthList(List<Sign> list) {
        List<Sign> collect = list.stream().filter((item) -> item.getIsEarly() == 1).collect(Collectors.toList());
        return BeanCopyUtils.copyBeanList(collect, SignVo.class);
    }

    private List<SignVo> getLateHistoryByMonthList(List<Sign> list) {
        List<Sign> collect = list.stream().filter((item) -> item.getIsLate() == 1).collect(Collectors.toList());
        return BeanCopyUtils.copyBeanList(collect, SignVo.class);
    }


}




