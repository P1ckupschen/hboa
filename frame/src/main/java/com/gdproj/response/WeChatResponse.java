package com.gdproj.response;

import lombok.Data;

@Data
public class WeChatResponse<T> {
    private Integer errorCode = 0;
    private String errMsg = "ok";

    public Boolean isSuccess() {
        return errorCode==0;
    }

    private T data;

}
