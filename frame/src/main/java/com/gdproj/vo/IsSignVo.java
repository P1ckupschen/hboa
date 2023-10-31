package com.gdproj.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IsSignVo {

    private Integer isSignIn;

    private Integer isSignOut;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date signInTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date signOutTime;
    @JsonFormat(pattern = "HH:mm",timezone = "GMT+8")
    private Date shouldInTime;
    @JsonFormat(pattern = "HH:mm",timezone = "GMT+8")
    private Date shouldOutTime;
}
