package com.gdproj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Ticket;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.TicketMapper;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.TicketService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.PageVo;
import com.gdproj.vo.TicketVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【sys_ticket】的数据库操作Service实现
* @createDate 2024-01-18 14:00:31
*/
@Service
public class TicketServiceImpl extends ServiceImpl<TicketMapper, Ticket>
    implements TicketService {

    @Override
    public ResponseResult getTicketList(PageQueryDto queryDto) {

        String time = queryDto.getTime();
        String sort = queryDto.getSort();
        Integer type = queryDto.getType();
        Integer departmentId = queryDto.getDepartmentId();
        Integer pageNum = queryDto.getPageNum();
        Integer pageSize = queryDto.getPageSize();
        Integer status = queryDto.getStatus();
        String title = queryDto.getTitle();

        LambdaQueryWrapper<Ticket>  queryWrapper = new LambdaQueryWrapper<Ticket>();

        if(!ObjectUtil.isEmpty(time)){
            queryWrapper.like(Ticket::getTicketTime, time);
        }
        if(!ObjectUtil.isEmpty(sort)){
            if(Objects.equals(sort, "+id")){
                queryWrapper.orderByAsc(Ticket::getTicketId);
            }else if(Objects.equals(sort, "-id")){
                queryWrapper.orderByDesc(Ticket::getTicketId);
            }
        }
        if (!ObjectUtil.isEmpty(type)) {
            queryWrapper.eq(Ticket::getCategoryId, type);
        }
        if (!ObjectUtil.isEmpty(title)) {
            queryWrapper.like(Ticket::getTicketName, title);
        }

        Page<Ticket> page = new Page<>(pageNum, pageSize);
        Page<Ticket> queryPage = page(page, queryWrapper);
        PageVo<List<TicketVo>> pageVo = new PageVo<>();
        try {
            List<TicketVo> collect = queryPage.getRecords().stream().map((item) -> {
                return BeanCopyUtils.copyBean(item, TicketVo.class);
            }).collect(Collectors.toList());
            List<TicketVo> ticketVos = addOrderId(collect, pageNum, pageSize);
            pageVo.setData(ticketVos);
            pageVo.setTotal((int) queryPage.getTotal());
            return ResponseResult.okResult(pageVo);
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }

    }

    @Override
    public ResponseResult insertTicket(TicketVo vo) {

        Ticket ticket = BeanCopyUtils.copyBean(vo, Ticket.class);
        try {
            boolean success = save(ticket);
            if(success){
                return ResponseResult.okResult(true);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.INSERT_ERROR);
            }
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.INSERT_ERROR);
        }
    }

    @Override
    public ResponseResult updateTicket(TicketVo vo) {
        Ticket ticket = BeanCopyUtils.copyBean(vo, Ticket.class);
        try {
            boolean success = updateById(ticket);
            if(success){
                return ResponseResult.okResult(true);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
            }
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.UPDATE_ERROR);
        }
    }

    @Override
    public ResponseResult deleteTicket(Integer id) {

        try {
            boolean success = removeById(id);
            if(success){
                return ResponseResult.okResult(true);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);
            }
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.DELETE_ERROR);
        }
    }
    private List<TicketVo> addOrderId(List<TicketVo> list, Integer pageNum, Integer pageSize){
        if (!ObjectUtil.isEmpty(pageNum) && !ObjectUtil.isEmpty(pageSize)) {
            for (int i = 0 ; i < list.size() ; i++){
                list.get(i).setOrderId((pageNum - 1) * pageSize + i + 1);
            }
        }
        return list;
    }
}




