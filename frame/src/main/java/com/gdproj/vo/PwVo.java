package com.gdproj.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PwVo {

    private Integer id;
    private String oldPw;
    private String newPw;
}
