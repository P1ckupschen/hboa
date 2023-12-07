package com.gdproj.response;

import lombok.Data;

@Data
public class AuthTokenResult {
    private String accessToken;
    private Integer expiresIn;
    private String refreshToken;
    private String openId;
    private String scope;
}
