package com.gdproj.enums;

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),
    LOGIN_FAIL(205,"登录出现错误"),
    // 登录
    USERNAME_EXIST(206,"用户名已存在"),
    LOGIN_ERROR(207,"用户名或密码错误"),
    NEED_LOGIN(208,"需要登录后操作"),




    LIST_ERROR(300,"列表数据获取失败"),
    DEPARTMENT_NULL(301,"无此部门"),

    DEPARTMENT_LIST_ERROR(302,"部门类型获取失败"),
    FILE_STREAM_NULL(101,"文件流为空"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    PHONENUMBER_EXIST(502,"手机号已存在"),
    EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),

    DEVISION_BYZERO(301,"除零");

    int code;
    String msg;

    AppHttpCodeEnum(int code, String errorMessage){
        this.code = code;
        this.msg = errorMessage;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}