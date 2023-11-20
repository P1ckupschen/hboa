package com.gdproj.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.annotation.autoLog;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.DeployeeService;
import com.gdproj.vo.DeployeeVo;
import com.gdproj.vo.PageVo;
import com.gdproj.vo.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/deployee")
@Api(tags = "员工功能")
public class deployeeController {

    @Autowired
    DeployeeService deployeeService;

    @GetMapping("/getListForSelect")
    @autoLog
    @ApiOperation(value = "查询用于选择的员工列表")
    public ResponseResult getListForSelect(){

        List<UserVo> selectList = new ArrayList<>();

        try {

            selectList =deployeeService.getListForSelect();

            return ResponseResult.okResult(selectList) ;

        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @GetMapping("/getDeployeeList")
    @autoLog
    @ApiOperation(value = "查询员工列表")
    public ResponseResult getDeployeeList(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam(required = false,defaultValue = "+id")String sort,
                                        @RequestParam(required = false,defaultValue = "") String title ,
                                        @RequestParam(required = false) Integer departmentId,
                                        @RequestParam(required = false) Integer type,
                                        @RequestParam(required = false) String time){

        PageQueryDto pageDto = new PageQueryDto(pageNum,pageSize,departmentId,type,title,time,sort);

        IPage<DeployeeVo> deployeeList = new Page<DeployeeVo>();

        try {
            deployeeList = deployeeService.getDeployeeList(pageDto);

            PageVo<List<DeployeeVo>> pageList = new PageVo<>();
            pageList.setData(deployeeList.getRecords());
            pageList.setTotal((int) deployeeList.getTotal());
            return ResponseResult.okResult(pageList);
        }catch (SystemException e){
            return ResponseResult.errorResult(e.getCode(),e.getMsg());
        } catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }


    }


    @PutMapping("updateDeployee")
    @autoLog
    @ApiOperation(value = "更新员工" , notes = "如果涉及密码修改 也要同时修改front-account表")
    public ResponseResult updateDeployee(@RequestBody DeployeeVo vo){

        return deployeeService.updateDeployee(vo);

    }

    @PostMapping("insertDeployee")
    @autoLog
    @ApiOperation(value = "新增员工", notes = "新增的同时front-account表中 新增一条用户名密码账号数据")
    public ResponseResult insertDeployee(@RequestBody DeployeeVo vo){

        return deployeeService.inserDeployee(vo);

    }

    @DeleteMapping("deleteDeployee")
    @autoLog
    @ApiOperation(value = "删除员工",notes = "不彻底删除 只修改用户状态 deployee_status")
    public ResponseResult deleteDeployee(@RequestParam("deployeeId") Integer id){

        return deployeeService.deleteDeployee(id);

    }
}
