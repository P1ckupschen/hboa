package com.gdproj.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactVo {
    private Integer id;
    private String name;
    private String phone;
}
