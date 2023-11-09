package com.gdproj.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyUseContentVo {

    private Integer stockId;
    private String stockName;
    //库存
    private Integer stockNum;
    //数量
    private Integer count;
    //单位
    private String unit;
}
