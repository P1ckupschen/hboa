package com.gdproj.response;

import lombok.Data;

@Data
public class JsApiSignature {
    private String appId;
    private String noncestr;
    private Long timestamp;
    private String signature;
}
