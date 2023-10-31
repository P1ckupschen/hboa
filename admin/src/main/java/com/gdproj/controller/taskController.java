package com.gdproj.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.annotation.autoLog;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Task;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.TaskService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.PageVo;
import com.gdproj.vo.TaskVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adminTask")
@Api(tags = "任务功能")
public class taskController {

    @Autowired
    TaskService taskService;


    //公告的增删改查
    @GetMapping("/getTaskList")
    @autoLog
    @ApiOperation(value = "查询任务列表")
    public ResponseResult getTaskList(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam(required = false,defaultValue = "+id")String sort,
                                        @RequestParam(required = false,defaultValue = "") String title ,
                                        @RequestParam(required = false) Integer departmentId,
                                        @RequestParam(required = false) Integer type,
                                        @RequestParam(required = false) String time){
        pageDto pageDto = new pageDto(pageNum,pageSize,departmentId,type,title,time,sort);

        IPage<TaskVo> taskList = new Page<>();

        try {

            taskList =  taskService.getTaskList(pageDto);

            PageVo<List<TaskVo>> pageList = new PageVo<>();

            pageList.setData(taskList.getRecords());

            pageList.setTotal((int) taskList.getTotal());

            return ResponseResult.okResult(pageList);

        }catch (SystemException e){
            return ResponseResult.errorResult(e.getCode(),e.getMsg());
        }
        catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }

    }

    @PutMapping("updateTask")
    @autoLog
    @ApiOperation(value = "更新任务")
    public ResponseResult updateTask(@RequestBody TaskVo taskVo){

        Task updateTask = BeanCopyUtils.copyBean(taskVo, Task.class);

        boolean b = false;

        try {

            b = taskService.updateById(updateTask);

            if(b == true){

                return ResponseResult.okResult(b);

            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }


    }

    @PostMapping("insertTask")
    @autoLog
    @ApiOperation(value = "新增任务")
    public ResponseResult insertTask(@RequestBody TaskVo taskVo){

        Task updateTask = BeanCopyUtils.copyBean(taskVo, Task.class);

        boolean b = false;

        try {

            b = taskService.save(updateTask);

            if(b == true){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.INSERT_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }

    }

    @DeleteMapping("deleteTask")
    @autoLog
    @ApiOperation(value = "删除任务")
    public ResponseResult deleteTask(@RequestParam("taskId") Integer taskId){

        boolean b = false;

        try {

            b = taskService.removeById(taskId);

            if(b == true){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }

    }

    @DeleteMapping("deleteTaskList")
    @autoLog
    //批量删除
    public ResponseResult deleteTaskList(@RequestBody Integer categoryId){

        boolean b = false;

        try {

            b = taskService.removeById(categoryId);

            if(b == true ){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }


    }



}
