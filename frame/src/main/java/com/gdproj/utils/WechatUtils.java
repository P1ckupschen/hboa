package com.gdproj.utils;

import cn.hutool.core.net.URLEncodeUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.Log;
import com.gdproj.response.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

/** 微信公众号接口
 *
 */
@Configuration
@ConfigurationProperties(prefix = "wechat", ignoreUnknownFields = true)
@Description("微信公众号通用方法")
public class WechatUtils {
    private static final Log log = Log.get(WechatUtils.class);
    /**
     * 接口 access_token 地址
     */
    public static String AccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token";
    /**
     * 鉴权地址
     */
    public static String SnsApiBaseUrl = "https://open.weixin.qq.com/connect/oauth2/authorize";
    /**
     * 网页授权 access_token 接口地址
     */
    public static String AuthAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";
    /**
     * 网页授权 刷新access_token 接口地址
     */
    public static String AuthRefreshTokenUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
    /**
     * 获取 用户信息 接口地址
     * scope: snsapi_userinfo
     */
    public static String UserInfoUrl = "https://api.weixin.qq.com/sns/userinfo";
    /**
     * 检验授权凭证（access_token）是否有效 接口地址
     */
    public static String VerifyAuthAccessTokenUrl = "https://api.weixin.qq.com/sns/auth";

    public static String jsapiTicketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";

    /**
     * 普通授权
     */
    public static String AuthScopeBase = "snsapi_base";
    /**
     * 高级授权，可调用用户信息接口获得用户信息
     */
    public static String AuthScopeUserInfo = "snsapi_userinfo";
    /**
     * AppId
     */
    public static String appId;
    /**
     * AppSecret
     */
    public static String appSecret;

    public void setAppId(String appId) {
        WechatUtils.appId = appId;
    }
    public void setAppSecret(String appSecret) {
        WechatUtils.appSecret = appSecret;
    }

    private static String serviceAccessToken;
    private static LocalDateTime serviceAccessTokenExpiresIn;

    private static String serviceJsApiTicket;
    private static LocalDateTime serviceJsApiTicketExpiresIn;

    public static String toGetUrl(String url, Map<String, Object> params) {
        return url + "?" +HttpUtil.toParams(params, StandardCharsets.UTF_8, true);
    }

    /**
     * 获取 ACCESS_TOKEN
     */
    public static WeChatResponse<AccessToken> getAccessToken() {
        if (StringUtils.hasText(WechatUtils.serviceAccessToken) && WechatUtils.serviceAccessTokenExpiresIn!=null && WechatUtils.serviceAccessTokenExpiresIn.isAfter(LocalDateTime.now().plusMinutes(5))) {
            return new WeChatResponse<AccessToken>() {{
                setErrorCode(0);
                setErrMsg("请求成功");
                setData(new AccessToken() {{
                    setAccessToken(WechatUtils.serviceAccessToken);
                    setExpiresIn(WechatUtils.serviceAccessTokenExpiresIn);
                }});
            }};
        }
        Map<String, Object> params = new LinkedHashMap<String, Object>(){{
            put("grant_type","client_credential");
            put("appid", appId);
            put("secret", appSecret);
        }};
        HttpResponse response = HttpUtil.createRequest(Method.GET,toGetUrl(AccessTokenUrl, params))
                .header("Content-Type", "application/json")
                .execute();
        if (response.getStatus()!=200) {
            return new WeChatResponse<AccessToken>() {{
                setErrorCode(-5);
                setErrMsg("系统繁忙，请稍后再试");
            }};
        }
        String body = response.body();
        if (JSONUtil.isTypeJSON(body)&& JSONUtil.isTypeJSONObject(body)) {
            JSONObject jsonObject = JSONUtil.parseObj(body);
            if (jsonObject.isEmpty()) {
                return new WeChatResponse<AccessToken>() {{
                   setErrorCode(-2);
                   setErrMsg("系统繁忙，请稍后再试");
                }};
            }
            if(jsonObject.containsKey("errcode")) {
                return new WeChatResponse<AccessToken>() {{
                    setErrorCode(jsonObject.getInt("errcode"));
                    setErrMsg(jsonObject.getStr("errmsg"));
                }};
            }
            if(jsonObject.containsKey("access_token")) {
                WechatUtils.serviceAccessToken = jsonObject.getStr("access_token");
                WechatUtils.serviceAccessTokenExpiresIn = LocalDateTime.now().plusSeconds(jsonObject.getInt("expires_in"));
                log.debug("access_token: {}", WechatUtils.serviceAccessToken);
                return new WeChatResponse<AccessToken>() {{
                    setErrorCode(0);
                    setErrMsg("请求成功");
                    setData(new AccessToken() {{
                        setAccessToken(WechatUtils.serviceAccessToken);
                        setExpiresIn(WechatUtils.serviceAccessTokenExpiresIn);
                    }});
                }};
            }
        }
        return new WeChatResponse<AccessToken>() {{
            setErrorCode(-3);
            setErrMsg("系统繁忙，请稍后再试");
        }};
    }


    /**
     * 生成鉴权地址
     * @param redirectUri 回调地址
     * @param scope 鉴权类型
     * @param state 其他参数 a-zA-Z0-9
     * @return url地址
     */
    public static String buildAuthUrl(String redirectUri, String scope, String state) {
        Map<String, Object> params = new LinkedHashMap<String, Object>(){{
            put("appid", appId);
            put("redirect_uri", URLEncodeUtil.encode(redirectUri));
            put("response_type", "code");
            put("scope", scope);
            put("state", state);
        }};
        return toGetUrl(SnsApiBaseUrl, params)+"#wechat_redirect";
    }

    /**
     * 通过code换取获取网页授权token
     * @param code
     * @return
     */
    public static WeChatResponse<AuthTokenResult> getAuthToken(String code) {
        Map<String, Object> params = new LinkedHashMap<String, Object>(){{
            put("appid", appId);
            put("secret", appSecret);
            put("code", code);
            put("grant_type", "authorization_code");
        }};
        HttpResponse response = HttpUtil.createRequest(Method.GET,toGetUrl(AuthAccessTokenUrl, params))
                .header("Content-Type", "application/json")
                .execute();
        if (response.getStatus()!=200) {
            return new WeChatResponse<AuthTokenResult>() {{
                setErrorCode(-5);
                setErrMsg("系统繁忙，请稍后再试");
            }};
        }
        String body = response.body();
        if (JSONUtil.isTypeJSON(body)&& JSONUtil.isTypeJSONObject(body)) {
            JSONObject jsonObject = JSONUtil.parseObj(body);
            if (jsonObject.isEmpty()) {
                return new WeChatResponse<AuthTokenResult>() {{
                    setErrorCode(-2);
                    setErrMsg("系统繁忙，请稍后再试");
                }};
            }
            if(jsonObject.containsKey("errcode")) {
                return new WeChatResponse<AuthTokenResult>() {{
                    setErrorCode(jsonObject.getInt("errcode"));
                    setErrMsg(jsonObject.getStr("errmsg"));
                }};
            }
            if(jsonObject.containsKey("access_token")) {
                return new WeChatResponse<AuthTokenResult>() {{
                    setErrorCode(0);
                    setErrMsg("请求成功");
                    setData(new AuthTokenResult() {{
                        setAccessToken(jsonObject.getStr("access_token"));
                        setExpiresIn(jsonObject.getInt("expires_in"));
                        setRefreshToken(jsonObject.getStr("refresh_token"));
                        setOpenId(jsonObject.getStr("openid"));
                        setScope(jsonObject.getStr("scope"));
                    }});
                }};
            }
        }
        return new WeChatResponse<AuthTokenResult>() {{
            setErrorCode(-3);
            setErrMsg("系统繁忙，请稍后再试");
        }};
    }

    /**
     * 刷新网页授权token
     * @param refreshToken
     * @return
     */
    public static WeChatResponse<AuthTokenResult> toRefreshAuthToken(String refreshToken) {
        Map<String, Object> params = new LinkedHashMap<String, Object>(){{
            put("appid", appId);
            put("grant_type", "refresh_token");
            put("refresh_token", refreshToken);
        }};
        HttpResponse response = HttpUtil.createRequest(Method.GET,toGetUrl(AuthRefreshTokenUrl, params))
                .header("Content-Type", "application/json")
                .execute();
        if (response.getStatus()!=200) {
            return new WeChatResponse<AuthTokenResult>() {{
                setErrorCode(-5);
                setErrMsg("系统繁忙，请稍后再试");
            }};
        }
        String body = response.body();
        if (JSONUtil.isTypeJSON(body)) {
            JSONObject jsonObject = JSONUtil.parseObj(body);
            if (jsonObject.isEmpty()) {
                return new WeChatResponse<AuthTokenResult>() {{
                    setErrorCode(-2);
                    setErrMsg("系统繁忙，请稍后再试");
                }};
            }
            if(jsonObject.containsKey("errcode")) {
                return new WeChatResponse<AuthTokenResult>() {{
                    setErrorCode(jsonObject.getInt("errcode"));
                    setErrMsg(jsonObject.getStr("errmsg"));
                }};
            }
            if(jsonObject.containsKey("access_token")) {
                return new WeChatResponse<AuthTokenResult>() {{
                    setErrorCode(0);
                    setErrMsg("请求成功");
                    setData(new AuthTokenResult() {{
                        setAccessToken(jsonObject.getStr("access_token"));
                        setExpiresIn(jsonObject.getInt("expires_in"));
                        setRefreshToken(jsonObject.getStr("refresh_token"));
                        setOpenId(jsonObject.getStr("openid"));
                        setScope(jsonObject.getStr("scope"));
                    }});
                }};
            }
        }
        return new WeChatResponse<AuthTokenResult>() {{
            setErrorCode(-3);
            setErrMsg("系统繁忙，请稍后再试");
        }};
    }

    /**
     * 通过网页鉴权access_token获取用户信息
     * scope: snsapi_userinfo
     * @param accessToken 网页授权access_token
     * @param openId
     * @return
     */
    public static WeChatResponse<AuthUserInfo> getUserInfo(String accessToken, String openId) {
        Map<String, Object> params = new LinkedHashMap<String, Object>(){{
            put("access_token", accessToken);
            put("openid", openId);
            put("lang", "zh_CN");
        }};
        HttpResponse response = HttpUtil.createRequest(Method.GET,toGetUrl(UserInfoUrl, params))
                .header("Content-Type", "application/json")
                .execute();
        if (response.getStatus()!=200) {
            return new WeChatResponse<AuthUserInfo>() {{
                setErrorCode(-5);
                setErrMsg("系统繁忙，请稍后再试");
            }};
        }
        String body = response.body();
        if (JSONUtil.isTypeJSON(body)&& JSONUtil.isTypeJSONObject(body)) {
            JSONObject jsonObject = JSONUtil.parseObj(body);
            if (jsonObject.isEmpty()) {
                return new WeChatResponse<AuthUserInfo>() {{
                    setErrorCode(-2);
                    setErrMsg("系统繁忙，请稍后再试");
                }};
            }
            if(jsonObject.containsKey("errcode")) {
                return new WeChatResponse<AuthUserInfo>() {{
                    setErrorCode(jsonObject.getInt("errcode"));
                    setErrMsg(jsonObject.getStr("errmsg"));
                }};
            }
            if(jsonObject.containsKey("openid")) {
                return new WeChatResponse<AuthUserInfo>() {{
                    setErrorCode(0);
                    setErrMsg("请求成功");
                    setData(new AuthUserInfo() {{
                        setOpenId(jsonObject.getStr("openid"));
                        setNickname(jsonObject.getStr("nickname"));
                        setSex(jsonObject.getInt("sex"));
                        setProvince(jsonObject.getStr("province"));
                        setCity(jsonObject.getStr("city"));
                        setCountry(jsonObject.getStr("country"));
                        setHeadImgUrl(jsonObject.getStr("headimgurl"));
                    }});
                }};
            }
        }
        return new WeChatResponse<AuthUserInfo>() {{
            setErrorCode(-3);
            setErrMsg("系统繁忙，请稍后再试");
        }};
    }

    /**
     * 检验授权凭证（access_token）是否有效
     * @param accessToken 网页授权access_token
     * @param openId
     * @return
     */
    public static WeChatResponse<Object> verifyAuth(String accessToken, String openId) {
        Map<String, Object> params = new LinkedHashMap<String, Object>(){{
            put("access_token", accessToken);
            put("openid", openId);
        }};
        HttpResponse response = HttpUtil.createRequest(Method.GET,toGetUrl(VerifyAuthAccessTokenUrl, params))
                .header("Content-Type", "application/json")
                .execute();
        if (response.getStatus()!=200) {
            return new WeChatResponse<Object>() {{
                setErrorCode(-5);
                setErrMsg("系统繁忙，请稍后再试");
            }};
        }
        String body = response.body();
        if (JSONUtil.isTypeJSON(body)&& JSONUtil.isTypeJSONObject(body)) {
            JSONObject jsonObject = JSONUtil.parseObj(body);
            if (jsonObject.isEmpty()) {
                return new WeChatResponse<Object>() {{
                    setErrorCode(-2);
                    setErrMsg("系统繁忙，请稍后再试");
                }};
            }
            if(jsonObject.containsKey("errcode")) {
                return new WeChatResponse<Object>() {{
                    setErrorCode(jsonObject.getInt("errcode"));
                    setErrMsg(jsonObject.getStr("errmsg"));
                }};
            }
        }
        return new WeChatResponse<Object>() {{
            setErrorCode(-3);
            setErrMsg("系统繁忙，请稍后再试");
        }};
    }

    /**
     * 获取jsapi_ticket
     * @return
     */
    public static WeChatResponse<JsApiTicket> jsapiTicket() {
        if (StringUtils.hasText(WechatUtils.serviceJsApiTicket) && WechatUtils.serviceAccessTokenExpiresIn!=null && WechatUtils.serviceAccessTokenExpiresIn.isAfter(LocalDateTime.now().plusMinutes(5))) {
            return new WeChatResponse<JsApiTicket>() {{
                setErrorCode(0);
                setErrMsg("请求成功");
                setData(new JsApiTicket() {{
                    setJsApiTicket(WechatUtils.serviceJsApiTicket);
                    setExpiresIn(WechatUtils.serviceJsApiTicketExpiresIn);
                }});
            }};
        }

        WeChatResponse<AccessToken> accessTokenWeChatResponse = getAccessToken();
        if (!accessTokenWeChatResponse.isSuccess()) {
            return new WeChatResponse<JsApiTicket>() {{
                setErrorCode(accessTokenWeChatResponse.getErrorCode());
                setErrMsg(accessTokenWeChatResponse.getErrMsg());
            }};
        }

        Map<String, Object> params = new LinkedHashMap<String, Object>(){{
            put("access_token", WechatUtils.serviceAccessToken);
            put("type", "jsapi");
        }};
        HttpResponse response = HttpUtil.createRequest(Method.GET,toGetUrl(jsapiTicketUrl, params))
                .header("Content-Type", "application/json")
                .execute();
        if (response.getStatus()!=200) {
            return new WeChatResponse<JsApiTicket>() {{
                setErrorCode(-5);
                setErrMsg("系统繁忙，请稍后再试");
            }};
        }
        String body = response.body();
        if (JSONUtil.isTypeJSON(body)&& JSONUtil.isTypeJSONObject(body)) {
            JSONObject jsonObject = JSONUtil.parseObj(body);
            if (jsonObject.isEmpty()) {
                return new WeChatResponse<JsApiTicket>() {{
                    setErrorCode(-2);
                    setErrMsg("系统繁忙，请稍后再试");
                }};
            }
            if (jsonObject.containsKey("ticket")) {
                WechatUtils.serviceJsApiTicket = jsonObject.getStr("ticket");
                WechatUtils.serviceJsApiTicketExpiresIn = LocalDateTime.now().plusSeconds(jsonObject.getInt("expires_in"));
                log.debug("jsapi_ticket: {}", WechatUtils.serviceJsApiTicket);
                return new WeChatResponse<JsApiTicket>() {{
                    setErrorCode(jsonObject.getInt("errcode"));
                    setErrMsg(jsonObject.getStr("errmsg"));
                    setData(new JsApiTicket(){{
                        setJsApiTicket(jsonObject.getStr(WechatUtils.serviceJsApiTicket));
                        setExpiresIn(WechatUtils.serviceJsApiTicketExpiresIn);
                    }});

                }};
            }
        }
        return new WeChatResponse<JsApiTicket>() {{
            setErrorCode(-3);
            setErrMsg("系统繁忙，请稍后再试");
        }};
    }


    /**
     * 获取jsapi签名
     * @param url
     * @return
     */
    public static WeChatResponse<JsApiSignature> jsApiSignature(String url) {
        WeChatResponse<JsApiTicket> jsApiTicketWeChatResponse = WechatUtils.jsapiTicket();
        if (!jsApiTicketWeChatResponse.isSuccess()) {
            return new WeChatResponse<JsApiSignature>() {{
                setErrorCode(jsApiTicketWeChatResponse.getErrorCode());
                setErrMsg(jsApiTicketWeChatResponse.getErrMsg());
            }};
        }

        String noncestr = RandomUtil.randomString(16);
        String jsapiTicket = WechatUtils.serviceJsApiTicket;
        Long timestamp = System.currentTimeMillis() / 1000;

        String str = StrUtil.format("jsapi_ticket={}&noncestr={}&timestamp={}&url={}", jsapiTicket,noncestr, timestamp, url);
        String signature = SecureUtil.sha1(str);

        return new WeChatResponse<JsApiSignature>() {{
            setErrorCode(0);
            setErrMsg("请求成功");
            setData(new JsApiSignature(){{
                setNoncestr(noncestr);
                setAppId(WechatUtils.appId);
                setTimestamp(timestamp);
                setSignature(signature);
            }});
        }};
    }
}
