package com.gdproj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Task;
import com.gdproj.vo.TaskVo;

/**
* @author Administrator
* @description 针对表【sys_task】的数据库操作Service
* @createDate 2023-09-19 14:13:15
*/
public interface TaskService extends IService<Task> {

    IPage<TaskVo> getTaskList(pageDto pageDto);
}
