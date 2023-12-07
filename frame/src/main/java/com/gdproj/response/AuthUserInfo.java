package com.gdproj.response;
import lombok.Data;

@Data
public class AuthUserInfo {
    private String openId;
    private String nickname;
    private Integer sex;
    private String province;
    private String city;
    private String country;
    private String headImgUrl;
}
