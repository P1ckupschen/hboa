package com.gdproj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Tool;
import com.gdproj.vo.ToolVo;
import com.gdproj.vo.stockSelectVo;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_tool】的数据库操作Service
* @createDate 2023-10-31 10:13:12
*/
public interface ToolService extends IService<Tool> {

    List<stockSelectVo> getListForSelect();

    IPage<ToolVo> getToolList(pageDto pageDto);
}
