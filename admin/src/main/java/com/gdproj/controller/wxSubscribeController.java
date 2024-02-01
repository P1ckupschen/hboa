package com.gdproj.controller;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SecureUtil;
import com.gdproj.annotation.autoLog;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.AccountService;
import com.gdproj.service.SystemLoginService;
import com.gdproj.utils.JwtUtils;
import com.gdproj.vo.AccountVo;
import com.gdproj.vo.SignatureVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/wx")
@Api(tags = "微信功能")
@Slf4j
public class wxSubscribeController {
    @Value("${ProjectUrl}")
    String projectUrl;

    @Value("${wx.mp.templateId1}")
    String templateId1;

    @Value("${wx.mp.templateId2}")
    String templateId2;

    @Autowired
    AccountService accountService;

    @Autowired
    SystemLoginService loginService;

    @Autowired
    WxMpService wxService;

    private static final String TOKEN = "hboatoken"; // 替换成自己的Token

//    private final WxMpService wxService;

    private Jedis resource = new JedisPool().getResource();

//    public wxSubscribeController(WxMpService wxService) {
//        this.wxService = wxService;
//    }

    @GetMapping("/verification")
    @autoLog
    @ApiOperation(value = "测试接口")
    public String verify(@RequestParam("signature") String signature,
                     @RequestParam("timestamp") String timestamp,
                     @RequestParam("nonce") String nonce,
                     @RequestParam("echostr") String echostr){
        String[] arr = {TOKEN, timestamp, nonce};
        Arrays.sort(arr);
        StringBuilder sb = new StringBuilder();
        for (String s : arr) {
            sb.append(s);
        }
        String encryptedStr = SecureUtil.sha1(sb.toString());
        if (encryptedStr.equals(signature)) {
            return  echostr; // 校验通过，返回echostr给微信服务器
        }
        return "解析失败";
    }


    @GetMapping("getAccessToken")
    @autoLog
    @ApiOperation(value = "获取全局AccessToken")
    public ResponseResult getAccessToken(HttpServletRequest request){
        refreshTokenTime();
        //过期就刷新 不过期就用旧的
        return ResponseResult.okResult(wxService.getWxMpConfigStorage().getAccessToken());
    }

    @GetMapping("getSignature")
    @autoLog
    @ApiOperation(value = "获取签名")
    public ResponseResult getSignature(@RequestParam("url") String url){
        refreshTokenTime();
        //accesstoken肯定有值;
            try {
                wxService.getJsapiTicket();
                WxJsapiSignature jsapiSignature = wxService.createJsapiSignature(url);
                SignatureVo signatureVo = new SignatureVo();
                log.info("【微信网页授权】url={}", url);
                log.info("【微信网页授权】签名={}", jsapiSignature.getSignature());
                log.info("【微信网页授权】timestamp={}", jsapiSignature.getTimestamp());
                log.info("【微信网页授权】noncestr={}", jsapiSignature.getNonceStr());
                signatureVo.setSignature(jsapiSignature.getSignature());
                signatureVo.setTimestamps(jsapiSignature.getTimestamp());
                signatureVo.setNoncestr(jsapiSignature.getNonceStr());
                return ResponseResult.okResult(signatureVo);
            }catch (Exception e){
                throw new SystemException(AppHttpCodeEnum.GET_SIGNATURE_ERROR);
            }

    }

    @GetMapping("redirectToAuthorize")
    @autoLog
    @ApiOperation(value = "跳转授权地址，返回openid")
    public String redirectToAuthorize(@RequestParam(value = "returnUrl", defaultValue = "https://qkongtao.cn/") String returnUrl){
        log.info("【微信网页授权】进来了，参数={}", returnUrl);
        System.out.println("进来了:" + returnUrl);
        //1. 配置
        //2. 调用方法
        String url = projectUrl + "wechat/userInfo";
        /*
         * 相当于这种形式
         * URLEncoder.decode(returnUrl,"UTF-8"
         * https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
         */
        try {
            String redirectUrl = wxService.getOAuth2Service().buildAuthorizationUrl(URLEncoder.encode(returnUrl, "utf-8"), WxConsts.OAuth2Scope.SNSAPI_BASE ,"");
            log.info("【微信网页授权】获取code,result={}", redirectUrl);
            return "redirect:" + redirectUrl;
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.ENCODE_ERROR);
        }

    }

    @GetMapping("getAuthToken")
    @autoLog
    @ApiOperation(value = "根据code换取特殊access_token,并返回openId")
    public ResponseResult getAuthToken(@RequestParam("code") String code){
        WxOAuth2AccessToken wxMpOAuth2AccessToken = new WxOAuth2AccessToken();
        try {
            wxMpOAuth2AccessToken = wxService.getOAuth2Service().getAccessToken(code);
            log.info("【AccessToken：】{}", wxMpOAuth2AccessToken.getAccessToken());
            //把token存入redis 定下时效
            resource.setex("OAuth2AccessToken-"+ 135,7200 ,wxMpOAuth2AccessToken.getAccessToken());

        } catch (WxErrorException e) {
            log.error("【微信网页授权】{}", e);
            throw new SystemException(AppHttpCodeEnum.WX_AUTH_ERROR);
        }
        String openId = wxMpOAuth2AccessToken.getOpenId();
        return ResponseResult.okResult(openId);
        //验证 授权token
//        boolean valid = wxService.getOAuth2Service().validateAccessToken(wxOAuth2AccessToken);
    }


    public void  refreshTokenTime(){
        String accessToken = wxService.getWxMpConfigStorage().getAccessToken();
        try {
          if(ObjectUtil.isEmpty(accessToken)){
              wxService.getAccessToken();
          }
        } catch (WxErrorException e) {
            log.error("【微信网页授权】{}", e);
            throw new SystemException(AppHttpCodeEnum.GET_TOKEN_ERROR);
        }
    }


//    @GetMapping("/sendTemplateMessage")
//    @autoLog
//    @ApiOperation(value = "发送消息推送")
    public  void sendExpireMessage(String name , String date , String content , String openId){
        //实例化模板对象
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        //设置模板ID
        wxMpTemplateMessage.setTemplateId(templateId1);
        //设置发送给哪个用户
        wxMpTemplateMessage.setToUser(openId);
        //构建消息格式
        List<WxMpTemplateData> list = Arrays.asList(
                new WxMpTemplateData("thing6", name),
                new WxMpTemplateData("time8", date),
                new WxMpTemplateData("thing3", content)
        );
        //放进模板对象。准备发送
        wxMpTemplateMessage.setData(list);
        try {
            //发送模板
            wxService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
        } catch (WxErrorException e) {
            throw new SystemException(AppHttpCodeEnum.SEND_MESSAGE_ERROR);
        }
    }

    public  void sendTaskMessage(String name , String date , String content , String openId){
        //实例化模板对象
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        //设置模板ID
        wxMpTemplateMessage.setTemplateId(templateId2);
        //设置发送给哪个用户
        wxMpTemplateMessage.setToUser(openId);
        //构建消息格式
        List<WxMpTemplateData> list = Arrays.asList(
                new WxMpTemplateData("thing1", name),
                new WxMpTemplateData("time5", date),
                new WxMpTemplateData("thing11", content)
        );
        //放进模板对象。准备发送
        wxMpTemplateMessage.setData(list);
        try {
            //发送模板
            wxService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
        } catch (WxErrorException e) {
            throw new SystemException(AppHttpCodeEnum.SEND_MESSAGE_ERROR);
        }
    }



    // auth的token刷新
    @GetMapping("updateTokenTime")
    @autoLog
    @ApiOperation(value = "每次发请求都走一遍判断是否过期")
    public ResponseResult updateTokenTime(@RequestParam("token") String token){
        WxOAuth2AccessToken refreshAccessToken = new WxOAuth2AccessToken();
        try {
            if(ObjectUtil.isEmpty(resource.get(resource.get("OAuth2AccessToken-"+ 135)))){
//            说明过期
                refreshAccessToken = wxService.getOAuth2Service().refreshAccessToken("OAuth2refreshToken-" + 135);
                //TODO refreshtoken 刷新时间
                resource.setex("OAuth2AccessToken-" + 135 , 7200 , refreshAccessToken.getAccessToken());
                resource.setnx("OAuth2refreshToken-" + 135 , refreshAccessToken.getRefreshToken());
                return ResponseResult.okResult("过期已刷新");
            }else{
                //说明没过期
                return ResponseResult.okResult("未失效");
            }
        } catch (WxErrorException e) {
            log.error("【微信网页授权】{}", e);
            throw new SystemException(AppHttpCodeEnum.WX_AUTH_ERROR);
        }
        //验证 授权token
//        boolean valid = wxService.getOAuth2Service().validateAccessToken(wxOAuth2AccessToken);
    }


    /**
     * 先通过openid判断是否已绑定用户
     *
     * */
    @PostMapping("loginByOpenId")
    @autoLog
    @ApiOperation(value = "openId判断登录")
    public ResponseResult loginByOpenId(@RequestParam("openId") String openId){

        Integer deployeeId = accountService.getDeployeeIdByOpenId(openId);
        if(deployeeId != 0){
            //说明找到了一个员工并且有openid
            return ResponseResult.okResult(JwtUtils.getJwtToken(deployeeId.toString()));
        }else{
            return ResponseResult.errorResult(AppHttpCodeEnum.CANT_FIND_ERROR);
        }
    }

    /**
     * 若openId未绑定 则把openid和用户进行绑定
     *
     * */
    @PostMapping("loginByAccount")
    @autoLog
    @ApiOperation(value = "openid和用户进行绑定")
    public ResponseResult loginByAccount(@RequestBody AccountVo vo , HttpServletRequest request){

        ResponseResult responseResult = loginService.frontLogin(vo, request);
        if(!ObjectUtil.isEmpty(responseResult) && responseResult.getCode()  == 200){
            String Token = (String) responseResult.getData();
            return ResponseResult.okResult(Token);
        }else{
            return ResponseResult.errorResult(AppHttpCodeEnum.BINDING_FAILED);
        }
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
     *  配置wxmenuButtonList
     *  TODO  自定义菜单
     *
     * */
    @GetMapping("customMenu")
    @autoLog
    @ApiOperation(value = "自定义菜单")
    public ResponseResult customMenu(){
        WxMenu wxMenu = new WxMenu();
        WxMenuButton button = new WxMenuButton();
        button.setName("oa系统");
        button.setUrl("https://phpssl.hangzhouwan.net/hboa/wx");
        button.setType("view");
        wxMenu.getButtons().add(button);
        try {
            String s = wxService.getMenuService().menuCreate(wxMenu);
            return ResponseResult.okResult(s);
        } catch (WxErrorException e) {
            throw new RuntimeException(e);
        }

//        List<WxMenuButton> buttons = new ArrayList<WxMenuButton>();
//        WxMenuButton btn1 = new WxMenuButton();
//        btn1.setType("click");
//        btn1.setName("查询城市");
//        btn1.setKey("QUERY_CITY");
//        WxMenuButton btn2 = new WxMenuButton();
//        btn2.setType("view");
//        btn2.setName("跳转网页");
//        btn2.setUrl("http://www.csdn.net");
//        buttons.add(btn1);
//        buttons.add(btn2);
//        // 创建
//        WxMenu wxMenu = new WxMenu();
//        wxMenu.setButtons(buttons);
//        String re= null;
//        try {
//            re = wxService.getMenuService().menuCreate(wxMenu);
//        } catch (WxErrorException e) {
//            throw new SystemException(AppHttpCodeEnum.CUSTOM_MENU_ERROR);
//        }
//        System.out.println(re);
//        return true;
    }

}
