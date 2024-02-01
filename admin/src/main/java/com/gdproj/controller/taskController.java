package com.gdproj.controller;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.annotation.autoLog;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Account;
import com.gdproj.entity.Task;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.AccountService;
import com.gdproj.service.TaskService;
import com.gdproj.utils.AesUtil;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.PageVo;
import com.gdproj.vo.TaskVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/adminTask")
@Api(tags = "任务功能")
@Slf4j
public class taskController {

    @Autowired
    TaskService taskService;

    @Autowired
    wxSubscribeController wxSubscribeController;

    @Autowired
    AccountService accountService;

    @GetMapping("/getTaskListByCurrentId")
    @autoLog
    @ApiOperation(value = "查询当前用户的任务")
    public ResponseResult getTaskListByCurrentId(@Validated PageQueryDto queryDto , HttpServletRequest request){
        return taskService.getTaskListByCurrentId(queryDto,request);
    }


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
        PageQueryDto pageDto = new PageQueryDto(pageNum,pageSize,departmentId,type,title,time,sort);

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
        if(!ObjectUtil.isEmpty(updateTask.getTaskContacts())){
            updateTask.setTaskContacts(AesUtil.encrypt(updateTask.getTaskContacts(),AesUtil.key128));
        }
        boolean b = false;
        try {
            b = taskService.updateById(updateTask);
            if(b){
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
        Task insertTask = BeanCopyUtils.copyBean(taskVo, Task.class);
        if(!ObjectUtil.isEmpty(insertTask.getTaskContacts())){
            insertTask.setTaskContacts(AesUtil.encrypt(insertTask.getTaskContacts(),AesUtil.key128));
        }
        boolean b = false;
        try {
            b = taskService.save(insertTask);
            //批量增加任务
            if(b){
                if(ObjectUtil.isEmpty(insertTask.getExecutorIds()) || ObjectUtil.isEmpty(insertTask.getTaskName())){
                    //如果执行人为空 或者 任务名称为空 就不推送消息
                }else{
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    if(ObjectUtil.isEmpty(insertTask.getTaskName())){
                        insertTask.setTaskName("新任务");
                    }
                    for( Integer id : taskVo.getExecutorIds()){
                        Account account = accountService.getById(id);
                        if(!ObjectUtil.isEmpty(account.getOpenId())){
                            wxSubscribeController.sendTaskMessage(insertTask.getTaskName(),sdf.format(insertTask.getTaskTime()),"新任务提醒",account.getOpenId());
                            System.out.println("发送了推送"+id);
                        }

                    }
                }
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
