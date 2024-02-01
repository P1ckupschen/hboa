package com.gdproj.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Rules;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.RulesMapper;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.RulesService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.PageVo;
import com.gdproj.vo.RulesVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Administrator
* @description 针对表【sys_rules】的数据库操作Service实现
* @createDate 2024-01-19 08:23:45
*/
@Service
public class RulesServiceImpl extends ServiceImpl<RulesMapper, Rules>
    implements RulesService {

    @Override
    public ResponseResult getRulesList(PageQueryDto queryDto) {

        Integer pageSize = queryDto.getPageSize();
        Integer pageNum = queryDto.getPageNum();
        Page<Rules> page = new Page<>(pageNum, pageSize);
        try {
            Page<Rules> rulesPage = page(page, null);
            PageVo<List<Rules>> result = new PageVo<>(rulesPage.getRecords(), (int) rulesPage.getTotal());
            return ResponseResult.okResult(result);
        }catch (Exception e ){
            throw new SystemException(AppHttpCodeEnum.SELECT_ERROR);
        }


    }

    @Override
    public ResponseResult insertRules(RulesVo vo) {
        Rules rules = BeanCopyUtils.copyBean(vo, Rules.class);

        try {
            boolean success = save(rules);
            if(success){
                return ResponseResult.okResult(true);
            }else{
                throw new SystemException(AppHttpCodeEnum.INSERT_ERROR);
            }
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.INSERT_ERROR);
        }
    }

    @Override
    public ResponseResult updateRules(RulesVo vo) {
        Rules rules = BeanCopyUtils.copyBean(vo, Rules.class);

        try {
            boolean success = updateById(rules);
            if(success){
                return ResponseResult.okResult(true);
            }else{
                throw new SystemException(AppHttpCodeEnum.UPDATE_ERROR);
            }
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.UPDATE_ERROR);
        }
    }

    @Override
    public ResponseResult deleteRules(Integer id) {

        try {
            boolean success = removeById(id);
            if(success){
                return ResponseResult.okResult(true);
            }else{
                throw new SystemException(AppHttpCodeEnum.DELETE_ERROR);
            }
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.DELETE_ERROR);
        }
    }
}




