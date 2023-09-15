package com.gdproj.exception;


import com.gdproj.enums.AppHttpCodeEnum;

public class TokenExpiredException extends RuntimeException{

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {

        return msg;
    }

    public TokenExpiredException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }
    public TokenExpiredException(int code , String msg ) {
        this.code = code;
        this.msg = msg;
    }
    
}