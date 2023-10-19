package com.gdproj.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class accountVo {
    private Integer id;

    private String username;

    private String password;

    private Date loginTime;

    private List<fileVo> avatar;

    private Integer userStatus;

    private Integer deployeeId;

    private String name;

    private deployeeVo deployee;
}
