package com.gdproj.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Epiboly;
import com.gdproj.result.ResponseResult;
import com.gdproj.vo.EpibolyVo;
import com.gdproj.vo.SelectVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_epiboly】的数据库操作Service
* @createDate 2023-10-05 08:41:52
*/
public interface EpibolyService extends IService<Epiboly> {

    IPage<EpibolyVo> getEpibolyList(PageQueryDto pageDto);

    List<SelectVo> getListForSelect();

    ResponseResult getEpibolyListByCurrentId(PageQueryDto queryDto, HttpServletRequest request);

    List<Integer> getidsByName(String title);
}
