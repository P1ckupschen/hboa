package com.gdproj.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Record;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.RecordService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.pageVo;
import com.gdproj.vo.recordVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/adminRecord")
public class recordController {

    @Autowired
    RecordService recordService;


    //公告的增删改查
    @GetMapping("/getRecordList")
    public ResponseResult getRecordList(@RequestParam Integer pageNum,
                                      @RequestParam Integer pageSize,
                                      @RequestParam(required = false,defaultValue = "+id")String sort,
                                      @RequestParam(required = false,defaultValue = "") String title ,
                                      @RequestParam(required = false) Integer departmentId,
                                      @RequestParam(required = false) Integer type,
                                      @RequestParam(required = false) String time){
        pageDto pageDto = new pageDto(pageNum,pageSize,departmentId,type,title,time,sort);

        IPage<recordVo> recordList = new Page<>();

        try {

            recordList =  recordService.getRecordList(pageDto);

            pageVo<List<recordVo>> pageList = new pageVo<>();

            pageList.setData(recordList.getRecords());

            pageList.setTotal((int) recordList.getTotal());

            return ResponseResult.okResult(pageList);

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }

    }

    @PutMapping("updateRecord")
    public ResponseResult updateRecord(@RequestBody recordVo recordVo){

        Record updateRecord = BeanCopyUtils.copyBean(recordVo, Record.class);

        boolean b = false;

        try {

            b = recordService.updateById(updateRecord);

            if(b){

                return ResponseResult.okResult(b);

            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }


    }

    @PostMapping("insertRecord")
    public ResponseResult insertRecord(@RequestBody recordVo recordVo){

        Record updateRecord = BeanCopyUtils.copyBean(recordVo, Record.class);

        boolean b = false;

        try {

            b = recordService.save(updateRecord);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.INSERT_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }

    }

    @DeleteMapping("deleteRecord")
    public ResponseResult deleteRecord(@PathParam("recordId") Integer recordId){

        boolean b = false;

        try {

            b = recordService.removeById(recordId);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }

    }

}
