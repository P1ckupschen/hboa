package com.gdproj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Client;
import com.gdproj.vo.clientVo;
import com.gdproj.vo.selectVo;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_client】的数据库操作Service
* @createDate 2023-09-26 09:28:44
*/
public interface ClientService extends IService<Client> {

    IPage<clientVo> getClientList(pageDto pageDto);

    List<selectVo> getListForSelect();
}
