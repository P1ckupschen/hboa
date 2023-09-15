package com.gdproj.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Sign;
import com.gdproj.vo.signVo;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_sign(考勤
)】的数据库操作Service
* @createDate 2023-09-13 11:09:17
*/
public interface SignService extends IService<Sign> {

    IPage<signVo> getSignList(pageDto pagedto);
}
