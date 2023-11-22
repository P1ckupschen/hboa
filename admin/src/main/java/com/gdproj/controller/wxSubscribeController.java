package com.gdproj.controller;


import cn.binarywang.wx.miniapp.api.WxMaService;
import com.gdproj.annotation.autoLog;
import com.gdproj.result.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/wx")
@Api(tags = "微信订阅通知功能")
public class wxSubscribeController {

    @Autowired
    WxMaService wxMaService;

    @GetMapping("pushTest")
    @autoLog
    @ApiOperation(value = "push测试")
    public ResponseResult pushTest(HttpServletRequest request){
//        HttpUtil.post("")
        try {
            String accessToken = wxMaService.getAccessToken();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  TODO 1.接口类型 2.过期合同时间推送 提前一个月 一个星期 3天
     *       yml需要配置template_id(微信公众平台-》小程序-》消息模板:获取模板id) secret_id(唯一凭证密钥)
     *       access_token(小程序全局唯一后台接口调用凭据) app_id(小程序身份码) open_id(用户在小程序里的唯一标识)
     *      是否需要 wx 的依赖
     * */
    @GetMapping("pushExpireContract")
    @autoLog
    @ApiOperation(value = "推送即将过期的合同提醒")
    public ResponseResult PushExpireContract(HttpServletRequest request){

        return null;
    }

    /**
     *  TODO 1.接口类型  2.审批通过的时候即发送公司公告提醒
     *
     * */
    @GetMapping("pushApprovedNotify")
    @ApiOperation(value = "推送审批通过并生效的公司公告")
    public ResponseResult PushApprovedNotify(){

        return null;
    }

}
