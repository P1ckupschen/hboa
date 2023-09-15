package com.gdproj.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Leave;
import com.gdproj.vo.leaveVo;

/**
* @author Administrator
* @description 针对表【sys_leave】的数据库操作Service
* @createDate 2023-09-14 15:04:32
*/
public interface LeaveService extends IService<Leave> {

    IPage<leaveVo> getLeaveList(pageDto pageDto);
}
