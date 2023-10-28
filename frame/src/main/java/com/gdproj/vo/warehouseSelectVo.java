package com.gdproj.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class warehouseSelectVo {

    //TODO 名字没有下划线  并且需要遍历的 stream 报错
    private Integer id;
    private Integer count;
    private String unit;
}
