package com.gdproj.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Sign;
import com.gdproj.result.ResponseResult;
import com.gdproj.vo.IsSignVo;
import com.gdproj.vo.MonthSignVo;
import com.gdproj.vo.SignVo;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_sign(考勤
)】的数据库操作Service
* @createDate 2023-09-13 11:09:17
*/
public interface SignService extends IService<Sign> {

    IPage<SignVo> getSignList(PageQueryDto pagedto);

    boolean insertSign(Sign sign);

    IsSignVo getSignInfoByUserIdAndDate(Integer userId);

    IPage<MonthSignVo> getMonthSignList(PageQueryDto pageDto);

    void exportMonthSignExcel(PageQueryDto pageDto, HttpServletResponse response);

    ResponseResult getTodayList(PageQueryDto queryDto);

    void exportSignExcel(List<Date> interval, HttpServletResponse response);
}
