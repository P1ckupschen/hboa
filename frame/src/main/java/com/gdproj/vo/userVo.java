package com.gdproj.vo;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class userVo {

    private Integer userId;

    private String username;

    private Integer departmentId;

    private String department;
}
