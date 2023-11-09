package com.gdproj.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseContentVo {
    // TODO 修改
    private Integer productId;
    private String productName;
    private Integer count;
    private BigDecimal money;
    private String description;
}
