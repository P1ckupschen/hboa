package com.gdproj.controller;


import com.gdproj.annotation.autoLog;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Sign;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.SignService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.utils.Iputil;
import com.gdproj.vo.IsSignVo;
import com.gdproj.vo.PageVo;
import com.gdproj.vo.SignVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/adminSign")
@Api(tags = "考勤功能")
public class signController {

    @Autowired
    SignService signService;

    @GetMapping("/getSignList")
    @autoLog
    @ApiOperation(value = "查询考勤列表")
    public ResponseResult getSignList(@Validated PageQueryDto queryDto){
        PageVo<List<SignVo>> pageList = new PageVo<>();
        try {
            pageList = signService.getSignList(queryDto);
            return ResponseResult.okResult(pageList);
        }catch (SystemException e){
            return ResponseResult.errorResult(e.getCode(),e.getMsg());
        } catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.LIST_ERROR);
        }
    }

    @GetMapping("/getMySignList")
    @autoLog
    @ApiOperation(value = "查询我的考勤")
    public ResponseResult getMySignList(@Validated PageQueryDto dto , HttpServletRequest request){
        PageVo<List<SignVo>> pageList = new PageVo<>();
        try {
            pageList = signService.getMySignList(dto,request);
            return ResponseResult.okResult(pageList);
        }catch (SystemException e){
            return ResponseResult.errorResult(e.getCode(),e.getMsg());
        } catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.LIST_ERROR);
        }
    }



    //TODO
    @GetMapping("/getTodayList")
    @autoLog
    @ApiOperation(value = "查询今日考勤列表")
    public ResponseResult getTodayList(@Validated PageQueryDto queryDto){

        return signService.getTodayList(queryDto);
    }

//    @GetMapping("/getMonthSignList")
//    @autoLog
//    @ApiOperation(value = "查询月度考勤统计列表")
//    public ResponseResult getMonthSignList(@RequestParam Integer pageNum,
//                                      @RequestParam Integer pageSize,
//                                      @RequestParam(required = false,defaultValue = "+id")String sort,
//                                      @RequestParam(required = false,defaultValue = "") String title ,
//                                      @RequestParam(required = false) Integer departmentId,
//                                      @RequestParam(required = false) Integer type,
//                                      @RequestParam(required = false) String time){
//
//        PageQueryDto pageDto = new PageQueryDto(pageNum,pageSize,departmentId,type,title,time,sort);
//
//        IPage<MonthSignVo> signList = new Page<MonthSignVo>();
//
//        try {
//
//            signList  =  signService.getMonthSignList(pageDto);
//
//            PageVo<List<MonthSignVo>> pageList = new PageVo<>();
//            pageList.setData(signList.getRecords());
//            pageList.setTotal((int) signList.getTotal());
//            return ResponseResult.okResult(pageList);
//        }catch (SystemException e){
//            return ResponseResult.errorResult(e.getCode(),e.getMsg());
//        } catch (Exception e){
//            return ResponseResult.errorResult(AppHttpCodeEnum.LIST_ERROR);
//        }
//    }

    @PostMapping("insertSign")
    @autoLog
    @ApiOperation(value = "签到")
    public ResponseResult insertSign(@RequestBody SignVo vo, HttpServletRequest request){

        System.out.println(vo);
        Sign sign = BeanCopyUtils.copyBean(vo, Sign.class);
        String ip = Iputil.getIp(request);
        sign.setSignIp(ip);

        boolean b = false;
        //怎么添加签到信息
        try {
            b = signService.insertSign(sign);
            return ResponseResult.okResult(b);
        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SIGN_IN_ERROR);
        }
    }

    @DeleteMapping("deleteSign")
    @autoLog
    @ApiOperation(value="删除签到信息")
    public ResponseResult deleteSign(@RequestParam("id") Integer Id){
        return signService.deleteSign(Id);
    }

    @GetMapping("/getSignInfoByUserIdAndDate")
    @autoLog
    @ApiOperation(value = "通过用户Id和日期查询用户当日的考勤信息")
    public ResponseResult getSignInfoByUserIdAndDate(@RequestParam Integer userId){

        //怎么添加签到信息
        try {
             IsSignVo vo = signService.getSignInfoByUserIdAndDate(userId);

             return ResponseResult.okResult(vo);
        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SIGN_IN_ERROR);
        }
    }

    @PostMapping("exportSignExcel")
    @autoLog
    @ApiOperation(value = "考勤数据导出")
    public void exportSignExcel(@RequestBody PageQueryDto dto, HttpServletResponse response){
        System.out.println(dto);
            signService.exportSignExcel(dto,response);
    }


    @GetMapping("getSignRules")
    @autoLog
    @ApiOperation(value = "获取考勤规则")
    public ResponseResult getSignRules(){
        return signService.getSignRules();
    }
}
