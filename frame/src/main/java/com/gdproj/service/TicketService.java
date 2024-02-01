package com.gdproj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Ticket;
import com.gdproj.result.ResponseResult;
import com.gdproj.vo.TicketVo;

/**
* @author Administrator
* @description 针对表【sys_ticket】的数据库操作Service
* @createDate 2024-01-18 14:00:31
*/
public interface TicketService extends IService<Ticket> {

    ResponseResult getTicketList(PageQueryDto queryDto);

    ResponseResult insertTicket(TicketVo vo);

    ResponseResult updateTicket(TicketVo vo);

    ResponseResult deleteTicket(Integer id);
}
