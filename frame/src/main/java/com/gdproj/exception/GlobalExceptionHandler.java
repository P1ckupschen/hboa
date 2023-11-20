package com.gdproj.exception;

import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.result.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e){
        //打印异常信息
        log.error("出现了SystemException！ {}",e);
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(e.getCode(),e.getMsg());
    }

    @ExceptionHandler(BindException.class)
    public ResponseResult bindExceptionHandler(BindException e){
        //打印异常信息
        log.error("出现了BindException！ {}",e);
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(e.getErrorCount(),e.getMessage());
    }


    @ExceptionHandler(TokenExpiredException.class)
    public ResponseResult tokenExpiredExceptionHandler(TokenExpiredException e){
        //打印异常信息
        log.error("出现了token过期！ {}",e);
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(e.getCode(),e.getMsg());
    }


    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e){
        //打印异常信息
        log.error("出现了一般exception！ {}",e);
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),e.getMessage());
    }
}