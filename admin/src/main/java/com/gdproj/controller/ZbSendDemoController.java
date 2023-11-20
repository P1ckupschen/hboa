//package com.gdproj.controller;
//
//import cn.binarywang.wx.miniapp.api.WxMaService;
//import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
//import cn.hutool.core.date.LocalDateTimeUtil;
//import com.gdproj.result.ResponseResult;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import me.chanjar.weixin.common.error.WxErrorException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@Slf4j
//@Api(tags = "征兵测试接口")
//public class ZbSendDemoController {
//
//
//    @Autowired
//    private WxMaService wxService;
//
//    @Value("${wx.miniapp.template-id}")
//    private String templteId;
//    @Resource
//
//    /**
//     * 跳转的小程序页面
//     */
//    private static final String PAGES_ZP = "pages/draft-review/list/list";
//
//
//    @GetMapping("/api/zphs/sendZbhsMsg")
//    @ApiOperation("传openId发送微信服务通知")
//    public ResponseResult<Boolean> sendMsg(String openID) {
//        sendSmallMsg(openID);
//        return ResponseResult.okResult(true);
//    }
//
//
//    private void sendSmallMsg(String openId) {
//        Map<String, String> map = new HashMap<>();
//        map.put("phrase1", "测试");
//        map.put("thing3", "您收到了应征公民测试测试的任务提醒消息");
//        map.put("time12", LocalDateTimeUtil.formatNormal(LocalDateTime.now()));
//        WxMaSubscribeMessage wxMaSubscribeMessage = WxMaSubscribeMessage.builder()
//                .toUser(openId)
//                .templateId(templteId)
//                .page(PAGES_ZP)
//                .build();
//        // 设置将推送的消息
//        map.forEach((k, v) -> {
//            wxMaSubscribeMessage.addData(new WxMaSubscribeMessage.MsgData(k, v));
//        });
//        try {
//            log.info("开始发送消息！！！！");
//            wxService.getMsgService().sendSubscribeMsg(wxMaSubscribeMessage);
//            log.info("消息发送成功！！！！");
//        } catch (WxErrorException e) {
//            e.printStackTrace();
//        }
//    }
//}
