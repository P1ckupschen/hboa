package com.gdproj.controller;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.annotation.autoLog;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Notify;
import com.gdproj.entity.Task;
import com.gdproj.entity.notifyCategory;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.TaskService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.categoryVo;
import com.gdproj.vo.notifyVo;
import com.gdproj.vo.pageVo;
import com.gdproj.vo.taskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/adminTask")
public class taskController {

    @Autowired
    TaskService taskService;


    //公告的增删改查
    @GetMapping("/getTaskList")
    @autoLog
    public ResponseResult getTaskList(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam(required = false,defaultValue = "+id")String sort,
                                        @RequestParam(required = false,defaultValue = "") String title ,
                                        @RequestParam(required = false) Integer departmentId,
                                        @RequestParam(required = false) Integer type,
                                        @RequestParam(required = false) String time){
        pageDto pageDto = new pageDto(pageNum,pageSize,departmentId,type,title,time,sort);

        IPage<taskVo> taskList = new Page<>();

        try {

            taskList =  taskService.getTaskList(pageDto);

            pageVo<List<taskVo>> pageList = new pageVo<>();

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
    public ResponseResult updateTask(@RequestBody taskVo taskVo){

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
    public ResponseResult insertTask(@RequestBody taskVo taskVo){

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
    public ResponseResult deleteTask(@PathParam("taskId") Integer taskId){

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
