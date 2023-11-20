package com.gdproj.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.gdproj.property.WxProperties;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @describe:
 * @author: jiazl /
 * @version: v1.0
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(WxProperties.class)
public class WxConfig {
    private final WxProperties properties;

    @Autowired
    public WxConfig(WxProperties properties) {
        this.properties = properties;
    }

    @Bean
    public WxMaService getService() {
        if (properties == null || properties.getAppid() == null || properties.getSecret() == null) {
            throw new WxRuntimeException("required wechat param not found");
        }
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(properties.getAppid());
        config.setSecret(properties.getSecret());
        config.setToken(properties.getToken());
        config.setAesKey(properties.getAesKey());
        config.setMsgDataFormat(properties.getMsgDataFormat());
        WxMaService service = new WxMaServiceImpl();
        service.setWxMaConfig(config);
        return service;
    }
}
