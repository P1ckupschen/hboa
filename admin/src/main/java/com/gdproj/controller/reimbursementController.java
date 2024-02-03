package com.gdproj.controller;


import com.gdproj.annotation.autoLog;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Reimbursement;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.ReimbursementService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.ReimbursementVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = "资金报销功能")
@RequestMapping("/adminReimbursement")
public class reimbursementController {


    @Autowired
    ReimbursementService reimbursementService;

    @GetMapping("/getReimbursementList")
    @autoLog
    @ApiOperation(value = "查询资金报销申请列表")
    public ResponseResult getReimbursementList(@Validated PageQueryDto queryVo) {

        return reimbursementService.getReimbursementList(queryVo);

    }

    @GetMapping("/getMyReimbursementList")
    @autoLog
    @ApiOperation(value = "查询我的资金报销申请列表")
    public ResponseResult getMyReimbursementList(@Validated PageQueryDto queryVo, HttpServletRequest request) {

        return reimbursementService.getMyReimbursementList(queryVo,request);

    }
    @PostMapping("/insertReimbursement")
    @autoLog
    @ApiOperation(value = "新增报销申请")
    public ResponseResult insertReimbursement(@RequestBody ReimbursementVo vo){

        Reimbursement reimbursement = BeanCopyUtils.copyBean(vo, Reimbursement.class);
        return ResponseResult.okResult(reimbursementService.insertReimbursement(reimbursement));

    }

    @PutMapping("/updateReimbursement")
    @autoLog
    @ApiOperation(value = "更新报销申请")
    public ResponseResult updateReimbursement(@RequestBody ReimbursementVo vo){

        return reimbursementService.updateReimbursement(vo);

    }

    @DeleteMapping("/deleteReimbursement")
    @autoLog
    @ApiOperation(value = "删除")
    public ResponseResult deleteReimbursement(@RequestParam("reimbursementId") Integer id){

        return reimbursementService.deleteReimbursement(id);

    }

}
