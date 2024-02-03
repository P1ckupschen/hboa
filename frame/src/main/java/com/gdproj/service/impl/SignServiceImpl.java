package com.gdproj.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Deployee;
import com.gdproj.entity.Rules;
import com.gdproj.entity.Sign;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.SignMapper;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.DepartmentService;
import com.gdproj.service.DeployeeService;
import com.gdproj.service.RulesService;
import com.gdproj.service.SignService;
import com.gdproj.utils.BMapApi;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.utils.JwtUtils;
import com.gdproj.vo.*;
import org.apache.poi.ss.usermodel.*;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
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

    @Autowired
    RulesService rulesService;

    @Value("${ExcelFilePath}")
    String filePath;


    @Override
    public PageVo<List<SignVo>> getSignList(PageQueryDto dto) {

        LambdaQueryWrapper<Sign> queryWrapper = new LambdaQueryWrapper<>();
        Page<Sign> page = new Page<>(dto.getPageNum(), dto.getPageSize());

        String keyword = dto.getTitle();
        Integer departmentId = dto.getDepartmentId();
        String time = dto.getTime();
//        List<Date> interval = dto.getInterval();

        if (!ObjectUtil.isEmpty(dto.getDepartmentId()) && keyword.isEmpty()) {
            List<Integer> ids = deployeeService.getIdsByDepartmentId(dto.getDepartmentId());
            if (ObjectUtil.isEmpty(ids)) {
                queryWrapper.in(Sign::getUserId, 0);
            } else {
                queryWrapper.in(Sign::getUserId, ids);
            }
        }

//        设置时间 年 月 日
//        模糊查询时间
        if (!ObjectUtil.isEmpty(time)) {
            //时间区间查询
            queryWrapper.like(Sign::getInTime,time);
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            queryWrapper.between(Sign::getInTime, sdf.format(interval.get(0)), sdf.format(interval.get(1)));
        }

//        模糊查询人名
        if (!ObjectUtil.isEmpty(keyword)) {
            //如果有模糊查询的时间 先通过查title 的用户ids
            List<Integer> ids = deployeeService.getIdsByTitle(keyword);
            if(!ObjectUtil.isEmpty(ids)){
                queryWrapper.in(Sign::getUserId, ids);
            }else{
                queryWrapper.in(Sign::getUserId,0);
            }
        }

        IPage<Sign> signPage = signMapper.selectPage(page, queryWrapper);

        //结果里的部门 和用户都返回成string；
        List<SignVo> resultList = signPage.getRecords().stream().map((item) -> {
            SignVo signvo = BeanCopyUtils.copyBean(item, SignVo.class);
            if(!ObjectUtil.isEmpty(item.getUserId())){
                signvo.setUsername(deployeeService.getNameByUserId(item.getUserId()));
                //部门
                signvo.setDepartment(departmentService.getDepartmentNameByDepartmentId(deployeeService.getById(item.getUserId()).getDepartmentId()));
                signvo.setDepartmentId(deployeeService.getDepartmentIdByUserId(item.getUserId()));
            }else{
                signvo.setUsername("");
                signvo.setDepartment("");
            }
            return signvo;
        }).collect(Collectors.toList());
        PageVo<List<SignVo>> result = new PageVo<>();
        List<SignVo> signVos = addOrderId(resultList, dto.getPageNum(), dto.getPageSize());
        result.setData(signVos);
        result.setTotal((int) signPage.getTotal());
        return result;

    }

    @Override
    public boolean insertSign(Sign sign) {

        LambdaQueryWrapper<Sign> queryWrapper = new LambdaQueryWrapper<>();
        //格式化当前日期
        Calendar calendar = Calendar.getInstance();
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
        BMapApi bMapApi = new BMapApi();
        // 签到规则
        Rules rule = rulesService.getById(1);

        //如果早上签过到
        if (vo.getIsSignIn() == 1) {
            one.setEndTime(calendar.getTime());
            //早上签到时间-下午签退时间；
            long tWorKHour = (one.getEndTime().getTime() - one.getInTime().getTime()) / 1000 / 3600;
            one.settWorkTime(Math.round(tWorKHour));
            one.setIsEarly(compareToHour(one.getEndTime(),rule.getEndAllowance(), -1));
            if(!ObjectUtil.isEmpty(sign.getLatitude()) && !ObjectUtil.isEmpty(sign.getLatitude())){
                GlobalCoordinates source = new GlobalCoordinates(sign.getLatitude(),sign.getLongitude());
//                double distanceMeter = GenUtil.getDistanceMeter(source, GenUtil.target, Ellipsoid.Sphere);

                JSONObject o = (JSONObject) bMapApi.reverseGeocoding(BigDecimal.valueOf(sign.getLongitude()), BigDecimal.valueOf(sign.getLatitude()));

                if(!ObjectUtil.isEmpty(o) && !ObjectUtil.isEmpty(o.get("result"))){
                    System.out.println(JSONUtil.parseObj(o.get("result")));
                    one.setEndAddr((String) JSONUtil.parseObj(o.get("result")).get("formatted_address"));
                }else{
                    one.setEndAddr("获取地址信息失败");
                }
            }
            return updateById(one);
        } else {
            //如果早上没有签过到 那就是早上第一次签到
            sign.setInTime(calendar.getTime());
            sign.setIsLate(compareToHour(sign.getInTime(),rule.getInAllowance(), 1));
            sign.setYear(String.valueOf(calendar.get(Calendar.YEAR)));
            sign.setMonth(String.valueOf(calendar.get(Calendar.MONTH)+1));
            sign.setDay(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
            if(!ObjectUtil.isEmpty(sign.getLatitude()) && !ObjectUtil.isEmpty(sign.getLatitude())){
                GlobalCoordinates source = new GlobalCoordinates(sign.getLatitude(),sign.getLongitude());
//                double distanceMeter = GenUtil.getDistanceMeter(source, GenUtil.target, Ellipsoid.Sphere);
                JSONObject o = (JSONObject) bMapApi.reverseGeocoding(BigDecimal.valueOf(sign.getLongitude()), BigDecimal.valueOf(sign.getLatitude()));
                if(!ObjectUtil.isEmpty(o) && !ObjectUtil.isEmpty(o.get("result"))){
                    System.out.println(JSONUtil.parseObj(o.get("result")));
                    sign.setSignAddr((String) JSONUtil.parseObj(o.get("result")).get("formatted_address"));
                }else{
                    sign.setSignAddr("获取地址信息失败");
                }
            }
            return save(sign);
        }

    }

    /**
     * 计算是否迟到早退
     * @param inTime yyyy-MM-dd HH:mm:ss
     * @param inAllowance HH:mm
     * @param i -1 是否提前走 1 是否迟到
     * */
    private Integer compareToHour(Date inTime, Date inAllowance,Integer i) {
        System.out.println(inTime);
        System.out.println(inAllowance);


        Calendar inTimeCal = Calendar.getInstance();
        inTimeCal.setTime(inTime);
        int inHour = inTimeCal.get(Calendar.HOUR_OF_DAY);
        int inMin = inTimeCal.get(Calendar.MINUTE);

        Calendar allowCal = Calendar.getInstance();
        allowCal.setTime(inAllowance);
        int allowHour = allowCal.get(Calendar.HOUR_OF_DAY);
        int allowMin = allowCal.get(Calendar.MINUTE);
        if(i == -1){
            if(inHour < allowHour || (inHour == allowHour && inMin < allowMin)){
                return 1;
            }else{
                return 0;
            }
        }else{
            if(inHour > allowHour || (inHour == allowHour && inMin >= allowMin)){
                return 1;
            }else{
                return 0;
            }
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
            //TODO 每日考勤时间设置 8:30 - 17:30
            Rules rule = rulesService.getById(1);
            vo.setShouldInTime(rule.getInTime());
            vo.setShouldOutTime(rule.getEndTime());
            System.out.println(vo);
        } catch (Exception e) {
            throw new SystemException(AppHttpCodeEnum.DATE_FORMAT_ERROR);
        }
        if (!ObjectUtil.isEmpty(one)) {
//            有打卡记录 并且签到时间不为空
            if (!ObjectUtil.isEmpty(one.getInTime())) {
                vo.setIsSignIn(1);
                vo.setSignAddr(one.getSignAddr());
                vo.setSignInTime(one.getInTime());
            } else {
//            有打卡记录 但是签到时间为空
                vo.setIsSignIn(0);
                vo.setIsSignOut(0);
            }
//            有打卡记录 并且签退时间不为空
            if (!ObjectUtil.isEmpty(one.getEndTime())) {
                vo.setIsSignOut(1);
                vo.setEndAddr(one.getEndAddr());
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

//    @Override
//    public IPage<MonthSignVo> getMonthSignList(PageQueryDto pageDto) {
//
//        //类型
//        Integer type = pageDto.getType();
//        //部门
//        Integer departmentId = pageDto.getDepartmentId();
//        //时间
//        String time = pageDto.getTime();
//        //排序
//        String sort = pageDto.getSort();
//        //搜索框如果是产品搜索产品名称或者选择产品id
//        //如果是人 搜素人名或者人id
//        //如果是物 搜索id
//        String title = pageDto.getTitle();
//        Integer pageNum = pageDto.getPageNum();
//        Integer pageSize = pageDto.getPageSize();
//
//
//        Page<MonthSignVo> resultPage = new Page<>();
//
//        try {
//            //获取time 的年 月 日
//            Calendar cal = Calendar.getInstance();
//            Date date = transferStringToDate(time);
//            cal.setTime(date);
//            int year = cal.get(Calendar.YEAR);
//            int month = cal.get(Calendar.MONTH) + 1;
//            int day = cal.get(Calendar.DAY_OF_MONTH);
//            //首先获取员工列表，根据分页数据d
//            IPage<DeployeeVo> deployeeListPage = deployeeService.getDeployeeList(pageDto);
//            List<DeployeeVo> deployeeList = deployeeListPage.getRecords();
//            deployeeList = deployeeList.stream().filter(item -> item.getDeployeeStatus() == 1 ).collect(Collectors.toList());
//            List<MonthSignVo> resultList = deployeeList.stream().map((item) -> countMonthDataByDeployee(item, year, month, day)).collect(Collectors.toList());
//            resultPage.setRecords(resultList);
//            resultPage.setTotal(deployeeListPage.getTotal());
//            return resultPage;
//        } catch (Exception e) {
//            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
//        }
//    }

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
    public ResponseResult getTodayList(PageQueryDto queryDto) {

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

        Calendar cal = Calendar.getInstance();
        Date date = transferStringToDate(time);
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        LambdaQueryWrapper<Sign> queryWrapper = new LambdaQueryWrapper<>();

        if(ObjectUtil.isEmpty(time)){
            queryWrapper.like(Sign::getInTime,year + "-" + month + "-" + day);
        }else{
            queryWrapper.like(Sign::getInTime,time);
        }
        Page<SignVo> resultPage = new Page<>();

        //并且筛选出离职员工
        IPage<DeployeeVo> deployeeListPage = deployeeService.getDeployeeList(queryDto);
        List<DeployeeVo> deployeeList = deployeeListPage.getRecords();
        List<Integer> collect = deployeeList.stream().filter(item -> item.getDeployeeStatus() == 1).map(DeployeeVo::getDeployeeId).collect(Collectors.toList());
        queryWrapper.in(Sign::getUserId,collect);
        Page<Sign> page = page(new Page<>(queryDto.getPageNum(), queryDto.getPageSize()), queryWrapper);

        List<SignVo> collect1 = page.getRecords().stream().map((item) -> {
            SignVo signvo = BeanCopyUtils.copyBean(item, SignVo.class);
            if (!ObjectUtil.isEmpty(item.getUserId())) {
                signvo.setUsername(deployeeService.getNameByUserId(item.getUserId()));
                //部门
                signvo.setDepartment(departmentService.getDepartmentNameByDepartmentId(deployeeService.getById(item.getUserId()).getDepartmentId()));
                signvo.setDepartmentId(deployeeService.getDepartmentIdByUserId(item.getUserId()));
            }

//            long workTime = 0L;
//            if (!ObjectUtil.isEmpty(signvo.getEndTime())) {
//                workTime = (signvo.getEndTime().getTime() - signvo.getInTime().getTime()) / 1000 / 60 / 60;
//            }
//
//            signvo.setTWorkTime((int) workTime);
//            if (signvo.getTWorkTime() < item.getWorkTime()) {
//                signvo.setSignStatus(0);
//            }

            //判断电脑打卡
//            if (workTime >= signvo.getWorkTime() && signvo.getSignAddr() == "XXXX附近") {
//                signvo.setSignStatus(1);
//            } else {
//                signvo.setSignStatus(0);
//            }
            return signvo;
        }).collect(Collectors.toList());
        List<SignVo> signVos = addOrderId(collect1, pageNum, pageSize);
        resultPage.setRecords(signVos);
            resultPage.setTotal(page.getTotal());
            return ResponseResult.okResult(resultPage);
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
//        vo.setShouldAttendanceDays(27);
        //统计上班status == 1的list的size();
        //实际上班天数
        vo.setAttendanceDays((int) list.stream().filter((item) -> item.getSignStatus() == 1).count());
        //出勤率 百分比  实际上班天数 / 固定上班时间  算出百分比
//        vo.setAttendanceRate((double) Math.round(vo.getAttendanceDays() / (double)vo.getShouldAttendanceDays() *100.0) / 100.0);
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

    @Override
    //TODO 如何设置 考勤记录导出？
    public void exportSignExcel(PageQueryDto dto, HttpServletResponse response) {

        Rules one = rulesService.getById(1);
        Date inAllowance = one.getInAllowance();
        Date endAllowance = one.getEndAllowance();
        String fileName = filePath + System.currentTimeMillis() + ".xlsx";


//        List<Date> interval = dto.getInterval();
        String time = dto.getTime();
        if(ObjectUtil.isEmpty(time)){
            throw new SystemException(AppHttpCodeEnum.NO_DATE_INPUT);
        }


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        Date parse = null;
        try {
            parse = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        int month = parse.getMonth();
        List<MonthSignVo> records = getMonthSignList(dto);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        AtomicInteger i = new AtomicInteger(0);
        ArrayList<Map<String, Object>> rows = CollUtil.newArrayList();
        AtomicReference<Integer> size = new AtomicReference<>();
        ExcelWriter writer = ExcelUtil.getWriter(fileName);
        List<Integer[]> contain = new ArrayList<>();
        records.forEach((item)->{
            Map<String, Object> row1 = new LinkedHashMap<>();
            row1.put("序号", i.incrementAndGet());
            row1.put("姓名",item.getDeployee().getDeployeeName());
//            row1.put("统计时间",sdf.format(interval.get(0))+ "至" +sdf.format(interval.get(1)));
            row1.put("统计时间",time);
            row1.put("实际出勤天数",item.getAttendanceDays());
            row1.put("迟到天数",item.getLateDays());
            row1.put("迟到记录",parseLateHistory(item.getLateHistory()));
            row1.put("早退天数",item.getEarlyDays());
            row1.put("早退记录",parseEarlyHistory(item.getEarlyHistory()));
            row1.put("时间段","上午");
            for(int j =1 ; j<= item.getInDetail().size() ; j++){
                String content = item.getInDetail().get(j);
                row1.put(j+"号",content);
                String[] splitResult = content.split("地点");
                if(!ObjectUtil.isEmpty(splitResult)) {
                    String prefix = splitResult[0];
                    if (!prefix.equals("-")) {
                        Date inTime;
                        try {
                            inTime = sdf1.parse(prefix);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        //判断是否迟到 如果迟到把x ， y坐标存下
                        if (compareToHour(inTime, inAllowance, 1) == 1) {
                            contain.add(new Integer[]{i.get() * 2, 8 + j});
                        }
                    }
                }
            }
            size.set(item.getInDetail().size() + 9);
            Map<String, Object> row2 = new LinkedHashMap<>();
            row2.put("序号", i.get());
            row2.put("姓名",item.getDeployee().getDeployeeName());
            row1.put("统计时间",time);
            row2.put("实际出勤天数",item.getAttendanceDays());
            row2.put("迟到天数",item.getLateDays());
            row2.put("迟到记录",item.getLateHistory());
            row2.put("早退天数",item.getEarlyDays());
            row2.put("早退记录",item.getEarlyHistory());
            row2.put("时间段","下午");
            for(int j =1 ; j<= item.getEndDetail().size() ; j++){
                String content = item.getEndDetail().get(j);
                row2.put(j+"号",content);
                //判断是否早退
                String[] splitResult = content.split("地点");
                if(!ObjectUtil.isEmpty(splitResult)){
                    String prefix = splitResult[0];
                    if(!prefix.equals("-")){
                        Date endTime;
                        try {
                            endTime = sdf1.parse(prefix);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                        //判断是否早退 如果早退把x ， y坐标存下
                        if(compareToHour(endTime,endAllowance,-1) == 1){

                            contain.add(new Integer[]{i.get()*2+1,8+j});
                        }
                    }
                }
            }
            rows.add(row1);
            rows.add(row2);
            int f = i.get()*2;
            int l = f + 1 ;
            writer.merge(f, l, 0, 0, i.get()-1, false);
            writer.merge(f, l, 1, 1, item.getDeployee().getDeployeeName(), false);
            writer.merge(f, l, 2, 2, "统计时间", false);
            writer.merge(f, l, 3, 3, item.getAttendanceDays(), false);
            writer.merge(f, l, 4, 4, item.getLateDays(), false);
            writer.merge(f, l, 5, 5, item.getLateHistory(), false);
            writer.merge(f, l, 6, 6, item.getEarlyDays(), false);
            writer.merge(f, l, 7, 7, item.getEarlyHistory(), false);
        });


        //  TODO 迟到或早退标红？
//
        writer.getSheet().setDefaultRowHeight((short) (60 * 256));
        writer.getSheet().setColumnWidth(2, 30 * 256);
        writer.getSheet().setColumnWidth(2, 30 * 256);
        for (int j = 9; j < size.get(); j++) {
            writer.getSheet().setColumnWidth(j,20*256);
        }
        writer.merge(size.get()-1,"浙江鸿邦科技公司"+(month+1)+"月份考勤统计");
        writer.write(rows, true);

        // 先write 再设置单元格格式
        contain.forEach((item) -> {
            setCellStyle(writer,item[1],item[0]);
        });
        writer.flush();
        // 关闭writer，释放内存
        writer.close();
        
        downloadExcel(fileName,response);


    }

    private List<String> parseEarlyHistory(List<SignVo> earlyHistory) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return  earlyHistory.stream().map((item)->{ return sdf.format(item.getEndTime());}).collect(Collectors.toList());
    }

    private List<String> parseLateHistory(List<SignVo> lateHistory) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return lateHistory.stream().map((item)->{ return sdf.format(item.getInTime());}).collect(Collectors.toList());
    }

    private CellStyle setCellStyle(ExcelWriter writer,Integer x , Integer y) {

        CellStyle cellStyle = writer.createCellStyle(x,y);
        // 内容水平居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 内容垂直居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 上边距
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setTopBorderColor(IndexedColors.RED.getIndex());
        // 右边栏
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setRightBorderColor(IndexedColors.RED.getIndex());
        // 底边栏
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBottomBorderColor(IndexedColors.RED.getIndex());
        // 左边栏
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setLeftBorderColor(IndexedColors.RED.getIndex());
        // 填充前景色(两个一起使用)
        cellStyle.setFillForegroundColor( IndexedColors.RED.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return cellStyle;
    }

    @Override
    public ResponseResult deleteSign(Integer id) {
        if(!ObjectUtil.isEmpty(id)){
            boolean b = removeById(id);
            if(b){
                return ResponseResult.okResult();
            }else{
                throw new SystemException(AppHttpCodeEnum.DELETE_ERROR);
            }
        }else{
            throw new SystemException(AppHttpCodeEnum.PRARM_NULL);
        }
    }

    @Override
    public ResponseResult getSignRules() {
        Rules one = rulesService.getById(1);
        Rules rules = new Rules();
        rules.setEndTime(one.getEndTime());
        rules.setInTime(one.getInTime());
        return ResponseResult.okResult(rules);
    }

    @Override
    public PageVo<List<SignVo>> getMySignList(PageQueryDto dto, HttpServletRequest request) {

        String id = JwtUtils.getMemberIdByJwtToken(request);
        LambdaQueryWrapper<Sign> queryWrapper = new LambdaQueryWrapper<>();
        Page<Sign> page = new Page<>(dto.getPageNum(), dto.getPageSize());

        String keyword = dto.getTitle();
        Integer departmentId = dto.getDepartmentId();
        String time = dto.getTime();
//        设置时间 年 月 日
//        模糊查询时间
        if (!ObjectUtil.isEmpty(time)) {
            //时间区间查询
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            queryWrapper.like(Sign::getInTime,time);
        }
        queryWrapper.eq(Sign::getUserId, id);


        IPage<Sign> signPage = signMapper.selectPage(page, queryWrapper);

        //结果里的部门 和用户都返回成string；
        List<SignVo> resultList = signPage.getRecords().stream().map((item) -> {
            SignVo signvo = BeanCopyUtils.copyBean(item, SignVo.class);
            if(!ObjectUtil.isEmpty(item.getUserId())){
                signvo.setUsername(deployeeService.getNameByUserId(item.getUserId()));
                //部门
                signvo.setDepartment(departmentService.getDepartmentNameByDepartmentId(deployeeService.getById(item.getUserId()).getDepartmentId()));
                signvo.setDepartmentId(deployeeService.getDepartmentIdByUserId(item.getUserId()));
            }else{
                signvo.setUsername("");
                signvo.setDepartment("");
            }
            return signvo;
        }).collect(Collectors.toList());
        PageVo<List<SignVo>> result = new PageVo<>();
        List<SignVo> signVos = addOrderId(resultList, dto.getPageNum(), dto.getPageSize());
        result.setData(signVos);
        result.setTotal((int) signPage.getTotal());
        return result;
    }

    public  List<MonthSignVo> getMonthSignList(PageQueryDto dto) {
//        List<Date> interval = dto.getInterval();
        String time = dto.getTime();
        String timeFormat = time+ "-01";
        String keyword = dto.getTitle();
        Integer departmentId = dto.getDepartmentId();

        LambdaQueryWrapper<Deployee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Deployee::getDeployeeStatus , 1);

//        if(ObjectUtil.isEmpty(interval)){
//
////            throw new SystemException(AppHttpCodeEnum.ARRAY_NULL_ERROR);
//        }
        if(!ObjectUtil.isEmpty(departmentId)){
            queryWrapper.eq(Deployee::getDepartmentId, departmentId);
        }
        if(!ObjectUtil.isEmpty(keyword)){
            queryWrapper.like(Deployee::getDeployeeName, keyword);
        }

        List<Deployee> list = deployeeService.list(queryWrapper);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(ObjectUtil.isEmpty(list)){
            throw new SystemException(AppHttpCodeEnum.NO_DEPLOYEE);
        }
        return list.stream().map((item) -> {
            MonthSignVo vo = new MonthSignVo();
            //从查询的当天 day开始 到一个月后的day；
            LambdaQueryWrapper<Sign> query = new LambdaQueryWrapper<>();
            query.like(Sign::getInTime,time);
//            query.between(Sign::getInTime, sdf.format(interval.get(0)), sdf.format(interval.get(1)));
            query.orderByAsc(Sign::getInTime);
            query.eq(Sign::getUserId, item.getDeployeeId());
            //获得这一个月内的这个用户的签到记录
            List<Sign> signList = list(query);

            DeployeeVo deployeeVo = BeanCopyUtils.copyBean(item, DeployeeVo.class);
            //设置考勤人
            vo.setDeployee(deployeeVo);
            vo.setUserId(item.getUserId());
            //实际上班天数
            vo.setAttendanceDays((int) signList.stream().filter((i) -> i.getSignStatus() == 1).count());
            //迟到天数
            vo.setLateDays(getLateHistoryByMonthList(signList).size());
            //迟到记录
            vo.setLateHistory(getLateHistoryByMonthList(signList));
            //早退天数
            vo.setEarlyDays(getEarlyHistoryByMonthList(signList).size());
            //早退记录
            vo.setEarlyHistory(getEarlyHistoryByMonthList(signList));
            //先找到这一个月多少天
            Calendar instance = Calendar.getInstance();

            try {
                instance.setTime(sdf.parse(timeFormat));
                instance.add(Calendar.MONTH,1);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
//            instance.setTime(interval.get(1));
            instance.add(Calendar.DATE, -1);
            int date =instance.get(Calendar.DATE);
            HashMap<Integer, String> inDetail = new HashMap<>();
            HashMap<Integer, String> endDetail = new HashMap<>();
            for(int i = 1 ; i<= date ;i++){
                //查询上午的打卡记录
                inDetail.put(i,"-");
                endDetail.put(i,"-");
                for (Sign sign : signList) {
                    if (Integer.toString(i).equals(sign.getDay())) {
                        inDetail.put(i, sdf1.format(sign.getInTime())+"地点："+sign.getSignAddr());
                        endDetail.put(i, sdf1.format(sign.getEndTime())+"地点："+sign.getEndAddr());
                    }
                }
            }

            vo.setInDetail(inDetail);
            vo.setEndDetail(endDetail);

            return vo;
        }).collect(Collectors.toList());


    }
    private List<SignVo> addOrderId(List<SignVo> list, Integer pageNum, Integer pageSize){
        if (!ObjectUtil.isEmpty(pageNum) && !ObjectUtil.isEmpty(pageSize)) {
            for (int i = 0 ; i < list.size() ; i++){
                list.get(i).setOrderId((pageNum - 1) * pageSize + i + 1);
            }
        }
        return list;
    }


}




