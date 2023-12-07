package com.gdproj.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JsApiTicket {
    private String jsApiTicket;
    private LocalDateTime expiresIn;
}
