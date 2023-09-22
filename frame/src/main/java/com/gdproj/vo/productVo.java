package com.gdproj.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gdproj.handler.jsonAndListTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class productVo {

    private Integer productId;

    private String productName;

    private Integer productOrderId;

    private String productFactoryCode;

    private Integer categoryId;

    private String Category;
    private String productBrand;

    private String productUnit;

    private String productModelname;

    private BigDecimal productCost;

    private List<fileVo> productPic;

    private Integer productTotal;

    private String recordIn;

    private String recordOut;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    private String productDescription;

    private BigDecimal productPrice;
}
