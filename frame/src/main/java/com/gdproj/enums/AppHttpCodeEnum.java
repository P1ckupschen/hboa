package com.gdproj.enums;

public enum AppHttpCodeEnum {
    // 成功
    SUCCESS(200,"操作成功"),
    LOGIN_FAIL(205,"登录出现错误"),
    // 登录
    USERNAME_EXIST(206,"用户名已存在"),
    LOGIN_ERROR(207,"用户名或密码错误"),
    NEED_LOGIN(208,"需要登录后操作"),

    GET_ACCOUNTINFO_ERROR(209,"获取用户信息失败"),

    DATE_FORMAT_ERROR(210,"时间转换失败"),


    TOKEN_PARSE_ERRPE(211,"token解析失败"),
    ACCOUNT_NULL(212,"用户名或密码为空"),
    REVISE_PASSWORD_ERROR(213, "修改密码失败"),
    ROLE_NULL(214 , "权限为空"),

    ENCODE_ERROR(215,"编码失败"),


    LIST_ERROR(300,"列表数据获取失败"),
    DEPARTMENT_NULL(301,"无此部门"),

    DEPARTMENT_LIST_ERROR(302,"部门类型获取失败"),



    WX_AUTH_ERROR(410,"微信授权错误"),
    OPEN_ID_NULL(411,"openId为空"),
    CANT_FIND_ERROR(412,"openid未绑定"),
    BINDING_FAILED(413,"绑定失败"),
    JSAPITICKET_EXPIRE(414,"ticket过期"),

    CUSTOM_MENU_ERROR(415,"自定义菜单失败"),

    GET_TOKEN_ERROR(416,"获取token失败"),

    GET_SIGNATURE_ERROR(417,"获取签名失败"),

    SEND_MESSAGE_ERROR(418,"发送消息失败"),
    SELECT_ERROR(10000,"数据查询失败"),
    UPDATE_ERROR(10001,"数据修改失败"),
    INSERT_ERROR(10002,"数据新增失败" ),

    DELETE_ERROR(10003,"数据删除失败"),

    MYSQL_FIELD_ERROR(10004,"数据字段为空或错误"),
    ARRAY_NULL_ERROR(10005,"数组为空"),

    PRARM_VALIDATE_FAILED(10006,"参数校验失败"),
    FILE_STREAM_NULL(101,"文件流为空"),
    NO_OPERATOR_AUTH(403,"无权限操作"),
    SYSTEM_ERROR(500,"出现错误"),
    PHONENUMBER_EXIST(502,"手机号已存在"),
    EMAIL_EXIST(503, "邮箱已存在"),
    REQUIRE_USERNAME(504, "必需填写用户名"),

    PRARM_NULL(505,"参数为空"),

    NO_DATE_INPUT(506,"日期为空"),
    NO_DEPLOYEE(507,"没有找到员工"),


    DEVISION_BYZERO(301,"除零"),

    FILE_SIZE_MAX(10010,"文件过大"),
    FILE_TYPE_ERROR(10011,"文件类型错误"),
    FILE_CONTENT_NULL(10012,"文件为空" ),
    FILE_STORAGE_ERROR(10013,"文件保存异常"),

    EXCEL_EXPORT_ERROR(10014,"excel导出失败"),
    DOWNLOAD_EXCEL_ERROR(10015,"excel下载失败"),
    SIGN_IN_ERROR(20001,"签到打卡失败"),
    FLOW_CONFIG_CONTENT_NULL(40001,"流程类配置审核级数为空"),
    FLOW_TYPE_ERROR(40002,"流程申请类型id不明确"),


    SET_PASS_ERROR(40002, "设置通过失败"),

    SET_NO_PASS_ERROR(40003,"设置不通过失败");



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