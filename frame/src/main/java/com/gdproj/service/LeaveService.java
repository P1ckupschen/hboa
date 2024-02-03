package com.gdproj.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Leave;
import com.gdproj.vo.LeaveVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @author Administrator
* @description 针对表【sys_leave】的数据库操作Service
* @createDate 2023-09-14 15:04:32
*/
public interface LeaveService extends IService<Leave> {

    IPage<LeaveVo> getLeaveList(PageQueryDto pageDto);

    boolean insertLeave(Leave insertLeave);

    void exportLeaveExcel(PageQueryDto queryDto , HttpServletResponse response);

    IPage<LeaveVo> getMyLeaveList(PageQueryDto pageDto, HttpServletRequest request);

    boolean updateLeave(Leave updateLeave);
}
