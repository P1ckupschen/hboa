package com.gdproj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Rules;
import com.gdproj.result.ResponseResult;
import com.gdproj.vo.RulesVo;

/**
* @author Administrator
* @description 针对表【sys_rules】的数据库操作Service
* @createDate 2024-01-19 08:23:46
*/
public interface RulesService extends IService<Rules> {

    ResponseResult getRulesList(PageQueryDto queryDto);

    ResponseResult insertRules(RulesVo vo);

    ResponseResult updateRules(RulesVo vo);

    ResponseResult deleteRules(Integer id);
}
