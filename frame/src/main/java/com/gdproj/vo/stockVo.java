package com.gdproj.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gdproj.entity.Product;
import com.gdproj.handler.jsonAndListTypeHandler;
import com.sun.org.apache.xml.internal.security.keys.keyresolver.implementations.PrivateKeyResolver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Locale;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class stockVo {

    private Integer materialId;

    private Integer count;

    private List<String> recordIn;

    private List<String> recordOut;

    private String materialDescription;

    private List<fileVo> materialPic;

    private Integer productId;

    private String productName;

    private String productBrand;

    private String productUnit;


    private Product product;

    private String Category;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

}
