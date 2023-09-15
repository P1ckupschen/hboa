package com.gdproj.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.dto.pageDto;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.result.ResponseResult;
import com.gdproj.vo.leaveVo;
import com.gdproj.vo.signVo;
import org.omg.PortableInterceptor.INACTIVE;
import com.gdproj.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/leave")
public class leaveController {

    @Autowired
    LeaveService leaveService;

    @GetMapping("/getLeaveList")
    public ResponseResult getLeaveList(@RequestParam Integer pageNum,
                                       @RequestParam Integer pageSize,
                                       @RequestParam(required = false,defaultValue = "+id")String sort,
                                       @RequestParam(required = false,defaultValue = "") String title ,
                                       @RequestParam(required = false) Integer type,
                                       @RequestParam(required = false) String time){
        pageDto pageDto = new pageDto(pageNum,pageSize,type,title,time,sort);

        IPage<leaveVo> leaveList = new Page<leaveVo>();

        try {
            leaveList = leaveService.getLeaveList(pageDto);
        }catch (SystemException e){
            return ResponseResult.okResult(e.getCode(),e.getMsg());
        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }

        return ResponseResult.okResult(leaveList);
    }

}
