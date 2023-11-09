package com.gdproj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Client;
import com.gdproj.vo.ClientVo;
import com.gdproj.vo.SelectVo;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_client】的数据库操作Service
* @createDate 2023-09-26 09:28:44
*/
public interface ClientService extends IService<Client> {

    IPage<ClientVo> getClientList(PageQueryDto pageDto);

    List<SelectVo> getListForSelect();
}
