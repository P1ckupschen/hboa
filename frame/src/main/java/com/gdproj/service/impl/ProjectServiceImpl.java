package com.gdproj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.entity.Project;
import com.gdproj.mapper.ProjectMapper;
import com.gdproj.service.ProjectService;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【sys_project】的数据库操作Service实现
* @createDate 2023-10-05 08:41:52
*/
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project>
    implements ProjectService {

}




