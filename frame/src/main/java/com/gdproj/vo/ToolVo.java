package com.gdproj.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ToolVo {

    private Integer toolId;
    private String toolName;

    private Integer toolOrderId;

    private String toolFactoryCode;

    private Integer categoryId;

    private String toolBrand;

    private String toolUnit;

    private String toolModelname;

    private BigDecimal toolCost;

    private List<FileVo> toolPic;

    private Integer toolTotal;

    private List recordIn;

    private List recordOut;

    private String toolDescription;

    private BigDecimal toolPrice;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    private String Category;
}

