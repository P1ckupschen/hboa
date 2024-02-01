package com.gdproj.exception;

import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.result.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
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

    /**
     * 处理数据校验异常
     * 处理配合@requestBody产生的数据校验异常
     *
     * @author 王子龙
     * @date 2020-8-30 21:34
     * @param e 数据校验异常
     * @return com.centerm.util.ResponseTemplate
     **/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseResult bindExceptionHandler(Exception e) {
//        BindingResult result;
//        if (e instanceof BindException) {
//            result = ((BindException) e).getBindingResult();
//        } else {
//            result = ((MethodArgumentNotValidException) e).getBindingResult();
//        }
//        Map<String, String> messages = new HashMap<>(16);
//        // 遍历所有字段的异常信息
//        if (result.hasErrors()) {
//            List<ObjectError> errors = result.getAllErrors();
//            for (ObjectError error : errors) {
//                FieldError fieldError = (FieldError) error;
//                // 判断是否是某个字段转换失败
//                if (fieldError.isBindingFailure()) {
//                    messages.put(fieldError.getField(), "数据格式非法！");
//                }
//                else {
//                    messages.put(fieldError.getField(), fieldError.getDefaultMessage());
//                }
//            }
//        }
        return ResponseResult.errorResult(AppHttpCodeEnum.PRARM_VALIDATE_FAILED);
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