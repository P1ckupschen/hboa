package com.gdproj.controller;


import com.gdproj.annotation.autoLog;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.RulesService;
import com.gdproj.vo.RulesVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Rules")
@Api(tags = "考勤规则")
public class rulesController {

    @Autowired
    RulesService rulesService;


    @GetMapping("getRulesList")
    @autoLog
    @ApiOperation(value = "查询考勤规则")
    public ResponseResult getRulesList(@Validated PageQueryDto queryDto){
        return rulesService.getRulesList(queryDto);
    }

    @PostMapping("insertRules")
    @autoLog
    @ApiOperation(value = "新增考勤规则")
    public ResponseResult insertRules(@RequestBody RulesVo vo){
        return rulesService.insertRules(vo);
    }

    @PutMapping("updateRules")
    @autoLog
    @ApiOperation(value = "更新考勤规则")
    public ResponseResult updateRules(@RequestBody RulesVo vo){
        return rulesService.updateRules(vo);
    }

    @DeleteMapping("deleteRules")
    @autoLog
    @ApiOperation(value = "删除考勤规则")
    public ResponseResult deleteRules(@RequestParam("id")Integer id){
        return rulesService.deleteRules(id);
    }
}
