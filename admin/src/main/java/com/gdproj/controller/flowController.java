package com.gdproj.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.annotation.autoLog;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.FlowService;
import com.gdproj.service.flowConfigService;
import com.gdproj.vo.FlowConfigVo;
import com.gdproj.vo.FlowVo;
import com.gdproj.vo.PageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Flow")
@Api(tags = "流程功能")
public class flowController {

    @Autowired
    FlowService flowService;

    @Autowired
    flowConfigService configService;

    @PutMapping("/approveFlow")
    @autoLog
    @ApiOperation(value = "审批是否通过")
    public ResponseResult approveFlow(@RequestBody FlowVo vo){
        boolean b = false;

        b = flowService.approveFlow(vo);

        return ResponseResult.okResult(b);
    }


    @GetMapping("/getFlowList")
    @autoLog
    @ApiOperation(value = "获取审批流程列表")
    public ResponseResult getFlowList(@RequestParam Integer pageNum,
                                          @RequestParam Integer pageSize,
                                          @RequestParam(required = false,defaultValue = "+id")String sort,
                                          @RequestParam(required = false,defaultValue = "") String title ,
                                          @RequestParam(required = false) Integer departmentId,
                                          @RequestParam(required = false) Integer type,
                                          @RequestParam(required = false) String time){
        PageQueryDto pageDto = new PageQueryDto(pageNum,pageSize,departmentId,type,title,time,sort);

        IPage<FlowVo> flowList = new Page<FlowVo>();

        try {
            flowList = flowService.getFlowList(pageDto);

            PageVo<List<FlowVo>> pageList = new PageVo<>();
            pageList.setData(flowList.getRecords());
            pageList.setTotal((int) flowList.getTotal());
            return ResponseResult.okResult(pageList);

        }catch (SystemException e) {
            return ResponseResult.okResult(e.getCode(), e.getMsg());
        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }



































    @GetMapping("/getFlowConfigList")
    @ApiOperation(value = "获取流程配置列表")
    public ResponseResult getFlowConfigList(@RequestParam Integer pageNum,
                                      @RequestParam Integer pageSize,
                                      @RequestParam(required = false,defaultValue = "+id")String sort,
                                      @RequestParam(required = false,defaultValue = "") String title ,
                                      @RequestParam(required = false) Integer departmentId,
                                      @RequestParam(required = false) Integer type,
                                      @RequestParam(required = false) String time){
        PageQueryDto pageDto = new PageQueryDto(pageNum,pageSize,departmentId,type,title,time,sort);

        IPage<FlowConfigVo> flowList = new Page<FlowConfigVo>();

        try {
            flowList = configService.getFlowConfigList(pageDto);

            PageVo<List<FlowConfigVo>> pageList = new PageVo<>();
            pageList.setData(flowList.getRecords());
            pageList.setTotal((int) flowList.getTotal());
            return ResponseResult.okResult(pageList);

        }catch (SystemException e) {
            return ResponseResult.okResult(e.getCode(), e.getMsg());
        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }
}
