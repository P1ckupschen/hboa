package com.gdproj.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class stockSelectVo {

    private Integer stockId;
    private String stockName;
    private Integer count;
    //单位
    private String unit;

}
