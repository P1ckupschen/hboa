package com.gdproj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Tool;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.ToolMapper;
import com.gdproj.service.DailyUseRecordService;
import com.gdproj.service.ToolCategoryService;
import com.gdproj.service.ToolService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.ToolVo;
import com.gdproj.vo.stockSelectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【sys_tool】的数据库操作Service实现
 * @createDate 2023-10-31 10:13:12
 */
@Service
public class ToolServiceImpl extends ServiceImpl<ToolMapper, Tool>
        implements ToolService {

    @Autowired
    ToolCategoryService categoryService;

    @Autowired
    DailyUseRecordService recordService;
    @Override
    public List<stockSelectVo> getListForSelect() {
        List<Tool> list = list();

        List<stockSelectVo> collect = list.stream().map((item) -> {
            stockSelectVo vo = new stockSelectVo();
            vo.setStockId(item.getToolId());
            vo.setStockName(item.getToolName());
            vo.setUnit(item.getToolUnit());
//           TODO 计算库存
            vo.setCount(100);
            return vo;
        }).collect(Collectors.toList());
        return collect;
    }

    @Override
    public IPage<ToolVo> getToolList(pageDto pageDto) {
        Integer type = pageDto.getType();
        Integer departmentId = pageDto.getDepartmentId();
        String time = pageDto.getTime();
        String sort = pageDto.getSort();
        String title = pageDto.getTitle();
        Integer pageNum = pageDto.getPageNum();
        Integer pageSize = pageDto.getPageSize();

        Page<Tool> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Tool> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Tool::getIsDeleted,0);
        //排序
        if(sort.equals("+id")){
            queryWrapper.orderByAsc(Tool::getToolId);
        }else{
            queryWrapper.orderByDesc(Tool::getToolId);
        }


        //模糊查询产品名
        if(!title.isEmpty()){
            //如果有模糊查询的时间 先通过查title 的用户ids
            List<Integer> ids = getIdsByTitle(title);
            if(!ObjectUtil.isEmpty(ids)){
                queryWrapper.in(Tool::getToolId, ids);
            }else{
                queryWrapper.in(Tool::getToolId,0);
            }
        }

        //产品类型
        if(!ObjectUtil.isEmpty(type)){
            queryWrapper.eq(Tool::getCategoryId,type);
        }

        IPage<Tool> productPage =page(page, queryWrapper);

        Page<ToolVo> resultPage = new Page<>();

        List<ToolVo> resultList = new ArrayList<>();
        //结果里的部门 和用户都返回成string；
        try {
            resultList = productPage.getRecords().stream().map((item) -> {

                ToolVo vo = BeanCopyUtils.copyBean(item, ToolVo.class);

                //类型名称
                vo.setCategory(categoryService.getById(item.getCategoryId()).getCategoryName());
//
//                //total 计算
                Integer count = recordService.getCountByToolId(vo.getToolId());
                vo.setToolTotal(count);
                //record 记录产品不需要  但是库存状态需要
                return vo;

            }).collect(Collectors.toList());
            resultPage.setRecords(resultList);

            resultPage.setTotal(productPage.getTotal());

            return resultPage;
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }


    }

    private List<Integer> getIdsByTitle(String title) {

        LambdaQueryWrapper<Tool> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(Tool::getToolName,title);

        List<Tool> list = list(queryWrapper);

        return list.stream().map(Tool::getToolId).collect(Collectors.toList());
    }
}




