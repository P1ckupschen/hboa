package com.gdproj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.entity.Project;
import com.gdproj.vo.selectVo;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_project】的数据库操作Service
* @createDate 2023-10-05 08:41:52
*/
public interface ProjectService extends IService<Project> {

    List<selectVo> getListForSelect();
}
