package com.gdproj.request;

import lombok.Data;

@Data
public class WechatLoginRequest {
    private String code;
    private String state;
}
