package com.gdproj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.ErrorLog;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.ErrorLogMapper;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.DeployeeService;
import com.gdproj.service.ErrorLogService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.ErrorLogVo;
import com.gdproj.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【sys_error_log】的数据库操作Service实现
* @createDate 2023-11-13 15:44:28
*/
@Service
public class ErrorLogServiceImpl extends ServiceImpl<ErrorLogMapper, ErrorLog>
    implements ErrorLogService {

    @Autowired
    DeployeeService deployeeService;

    @Override
    public ResponseResult getErrorLogList(PageQueryDto queryDto) {

//类型
        Integer type = queryDto.getType();
        //部门
        Integer departmentId = queryDto.getDepartmentId();
        //时间
        String time = queryDto.getTime();
        //排序
        String sort = queryDto.getSort();
        //搜索框如果是产品搜索产品名称或者选择产品id
        String title = queryDto.getTitle();
        Integer pageNum = queryDto.getPageNum();
        Integer pageSize = queryDto.getPageSize();

        Page<ErrorLog> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<ErrorLog> queryWrapper = new LambdaQueryWrapper<>();

        //排序
        if (sort.equals("+id")) {
            queryWrapper.orderByAsc(ErrorLog::getErrorId);
        } else {
            queryWrapper.orderByDesc(ErrorLog::getErrorId);
        }

        //查询名称？ 应该是用户
        if (!title.isEmpty()) {
            queryWrapper.eq(ErrorLog::getUserId,title);
        }

        IPage<ErrorLog> recordPage = page(page, queryWrapper);

        PageVo<List<ErrorLogVo>> result = new PageVo<>();

        List<ErrorLogVo> resultList = new ArrayList<>();
        try {
            resultList = recordPage.getRecords().stream().map((item) -> {
                ErrorLogVo vo = BeanCopyUtils.copyBean(item, ErrorLogVo.class);
//                if(ObjectUtil.isEmpty(item.getUserId())){
//                    vo.setUsername("未登录状态未知用户");
//                }else{
//                    vo.setUsername(deployeeService.getNameByUserId(item.getUserId()));
//                }
                return vo;
            }).collect(Collectors.toList());
            result.setData(resultList);
            result.setTotal((int) recordPage.getTotal());
            return  ResponseResult.okResult(result);
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }

    }
}




