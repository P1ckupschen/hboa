package com.gdproj.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.annotation.autoLog;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Record;
import com.gdproj.entity.Stock;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.RecordService;
import com.gdproj.service.StockService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/adminRecord")
@Api(tags = "库存记录功能")
public class recordController {

    @Autowired
    RecordService recordService;

    @Autowired
    StockService stockService;


    //记录的增删改查
    @GetMapping("/getRecordList")
    @autoLog
    @ApiOperation(value = "查询记录列表")
    public ResponseResult getRecordList(@RequestParam Integer pageNum,
                                      @RequestParam Integer pageSize,
                                      @RequestParam(required = false,defaultValue = "+id")String sort,
                                      @RequestParam(required = false,defaultValue = "") String title ,
                                      @RequestParam(required = false) Integer departmentId,
                                      @RequestParam(required = false) Integer type,
                                      @RequestParam(required = false) String time){
        PageQueryDto pageDto = new PageQueryDto(pageNum,pageSize,departmentId,type,title,time,sort);

        IPage<RecordVo> recordList = new Page<>();

        try {

            recordList =  recordService.getRecordList(pageDto);

            PageVo<List<RecordVo>> pageList = new PageVo<>();

            pageList.setData(recordList.getRecords());

            pageList.setTotal((int) recordList.getTotal());

            return ResponseResult.okResult(pageList);

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }

    }

    @PutMapping("updateRecord")
    @autoLog
    @ApiOperation(value = "更新记录")
    public ResponseResult updateRecord(@RequestBody RecordVo recordVo){

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
    @autoLog
    @ApiOperation(value = "新增记录")
    public ResponseResult insertRecord(@RequestBody RecordVo recordVo){

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
    @autoLog
    @ApiOperation(value = "删除记录")
    public ResponseResult deleteRecord(@RequestParam("recordId") Integer recordId){

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




    //库存的增删改查
    //不需要单独的库存表了，由产品表形成库存表，由productVo来接收
    @GetMapping("/getStockList")
    @autoLog
    @ApiOperation(value = "查询库存列表")
    public ResponseResult getStockList(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam(required = false,defaultValue = "+id")String sort,
                                        @RequestParam(required = false,defaultValue = "") String title ,
                                        @RequestParam(required = false) Integer departmentId,
                                        @RequestParam(required = false) Integer type,
                                        @RequestParam(required = false) String time){
        PageQueryDto pageDto = new PageQueryDto(pageNum,pageSize,departmentId,type,title,time,sort);

        //是通过统计出来的 不是通过增加记录的 所以不用新表stock
//        IPage<stockVo> stockList = new Page<>();

        IPage<ProductVo> stockList = new Page<>();

        try {

            stockList =  recordService.getStockList(pageDto);

            PageVo<List<ProductVo>> pageList = new PageVo<>();

            pageList.setData(stockList.getRecords());

            pageList.setTotal((int) stockList.getTotal());

            return ResponseResult.okResult(pageList);

        }catch (SystemException e){

            return ResponseResult.errorResult(e.getCode(),e.getMsg());
        }

        catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }

    }

    @GetMapping("/getStockListForSelect")
    @autoLog
    @ApiOperation(value = "查询用于选择的库存列表")
    public ResponseResult getStockListForSelect(){

        List<Stock> list = new ArrayList<>();
        try {
            List<stockSelectVo> resultList = stockService.getStockListForSelect();

            return ResponseResult.okResult(resultList);
        }catch (SystemException e){

            return ResponseResult.errorResult(e.getCode(),e.getMsg());
        }

        catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }

    }

}
