package com.gdproj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.FlowConfig;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.flowConfigMapper;
import com.gdproj.service.flowConfigService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.FlowConfigVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【sys_flow_config】的数据库操作Service实现
* @createDate 2023-10-17 15:09:38
*/
@Service
public class flowConfigServiceImpl extends ServiceImpl<flowConfigMapper, FlowConfig>
    implements flowConfigService {

    @Override
    public IPage<FlowConfigVo> getFlowConfigList(PageQueryDto pageDto) {
        //类型
        Integer type = pageDto.getType();
        //部门
        Integer departmentId = pageDto.getDepartmentId();
        //时间
        String time = pageDto.getTime();
        //排序
        String sort = pageDto.getSort();
        //搜索框如果是产品搜索产品名称或者选择产品id
        //如果是人 搜素人名或者人id
        //如果是物 搜索id
        String title = pageDto.getTitle();
        Integer pageNum = pageDto.getPageNum();
        Integer pageSize = pageDto.getPageSize();

        Page<FlowConfig> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<FlowConfig> queryWrapper = new LambdaQueryWrapper<>();
        //排序
        if (sort.equals("+id")) {
            queryWrapper.orderByAsc(FlowConfig::getTypeId);
        } else {
            queryWrapper.orderByDesc(FlowConfig::getTypeId);
        }


        IPage<FlowConfig> recordPage = page(page, queryWrapper);

        Page<FlowConfigVo> resultPage = new Page<>();

        List<FlowConfigVo> resultList = new ArrayList<>();
        try {

            resultList = recordPage.getRecords().stream().map((item) -> {

                FlowConfigVo vo = BeanCopyUtils.copyBean(item, FlowConfigVo.class);
                //类型名称?
                return vo;
            }).collect(Collectors.toList());

            resultPage.setRecords(resultList);

            resultPage.setTotal(recordPage.getTotal());

            return resultPage;
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }


    }
}




