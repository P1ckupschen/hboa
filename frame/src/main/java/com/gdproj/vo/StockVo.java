package com.gdproj.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gdproj.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockVo {

    private Integer materialId;

    private Integer count;

    private List<String> recordIn;

    private List<String> recordOut;

    private String materialDescription;

    private List<FileVo> materialPic;

    private Integer productId;

    private String productName;

    private String productBrand;

    private String productUnit;


    private Product product;

    private String Category;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

}
