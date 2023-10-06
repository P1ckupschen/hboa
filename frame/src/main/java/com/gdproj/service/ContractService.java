package com.gdproj.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Contract;
import com.gdproj.vo.contractVo;
import com.gdproj.vo.selectVo;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_contract】的数据库操作Service
* @createDate 2023-10-05 08:41:52
*/
public interface ContractService extends IService<Contract> {

    IPage<contractVo> getContractList(pageDto pageDto);

    List<selectVo> getListForSelect();
}
