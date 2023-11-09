package com.gdproj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Log;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.LogMapper;
import com.gdproj.service.DeployeeService;
import com.gdproj.service.LogService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.LogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【sys_log】的数据库操作Service实现
* @createDate 2023-10-16 13:24:39
*/
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log>
    implements LogService {

    @Autowired
    DeployeeService deployeeService;

    @Override
    public IPage<LogVo> getLogList(PageQueryDto pageDto) {

        //类型
        Integer type = pageDto.getType();
        //部门
        Integer departmentId = pageDto.getDepartmentId();
        //时间
        String time = pageDto.getTime();
        //排序
        String sort = pageDto.getSort();
        //搜索框如果是产品搜索产品名称或者选择产品id
        String title = pageDto.getTitle();
        Integer pageNum = pageDto.getPageNum();
        Integer pageSize = pageDto.getPageSize();

        Page<Log> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Log> queryWrapper = new LambdaQueryWrapper<>();

        //排序
        if (sort.equals("+id")) {
            queryWrapper.orderByAsc(Log::getLogId);
        } else {
            queryWrapper.orderByDesc(Log::getLogId);
        }

        //查询名称？ 应该是用户
        if (!title.isEmpty()) {
            queryWrapper.eq(Log::getUserId,title);
        }

        IPage<Log> recordPage = page(page, queryWrapper);

        Page<LogVo> resultPage = new Page<>();

        List<LogVo> resultList = new ArrayList<>();
        try {
            resultList = recordPage.getRecords().stream().map((item) -> {
                LogVo vo = BeanCopyUtils.copyBean(item, LogVo.class);
                if(ObjectUtil.isEmpty(item.getUserId())){
                    vo.setUsername("未登录状态未知用户");
                }else{
                    vo.setUsername(deployeeService.getNameByUserId(item.getUserId()));
                }
                return vo;
            }).collect(Collectors.toList());
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }
        resultPage.setRecords(resultList);
        resultPage.setTotal(recordPage.getTotal());
        return resultPage;
    }

    @Override
    public void insertLogWhenOperating(Log log) {

        try {
            save(log);
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.INSERT_ERROR);
        }
    }


}




