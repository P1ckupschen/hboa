package com.gdproj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gdproj.entity.Task;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Administrator
* @description 针对表【sys_task】的数据库操作Mapper
* @createDate 2023-09-19 14:13:15
* @Entity generator.entity.Task
*/

@Mapper
public interface TaskMapper extends BaseMapper<Task> {

}




