package com.gdproj.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseContentVo {

    private Integer stockId;
    private String stockName;
    //库存
    private Integer stockNum;
    //数量
    private Integer count;
    //单位
    private String unit;

    private BigDecimal money;
}
