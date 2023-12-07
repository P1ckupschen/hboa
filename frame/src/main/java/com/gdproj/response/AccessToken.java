package com.gdproj.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccessToken {
    private String accessToken;
    private LocalDateTime expiresIn;
}
