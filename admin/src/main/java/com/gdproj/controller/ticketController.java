package com.gdproj.controller;


import com.gdproj.annotation.autoLog;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.TicketService;
import com.gdproj.vo.TicketVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "票据模块")
@RequestMapping("/Ticket")
public class ticketController {

    @Autowired
    TicketService ticketService;

    @GetMapping("/getTicketList")
    @ApiOperation(value = "查询票据列表")
    @autoLog
    public ResponseResult getTicketList(@Validated PageQueryDto queryDto) {
        return ticketService.getTicketList(queryDto);
    }

    @PostMapping("/insertTicket")
    @ApiOperation(value="新增票据")
    @autoLog
    public ResponseResult insertTicket(@RequestBody TicketVo vo) {
        return ticketService.insertTicket(vo);
    }

    @PutMapping("/updateTicket")
    @ApiOperation(value="更新票据")
    @autoLog
    public ResponseResult updateTicket(@RequestBody TicketVo vo) {
        return ticketService.updateTicket(vo);
    }

    @DeleteMapping("/deleteTicket")
    @ApiOperation(value="删除票据")
    @autoLog
    public ResponseResult deleteTicket(@RequestParam("id") Integer id) {
        return ticketService.deleteTicket(id);
    }

}
