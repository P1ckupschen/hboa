package com.gdproj.controller;

import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.ProductService;
import com.gdproj.vo.productVo;
import com.gdproj.vo.userVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
public class productController {

    @Autowired
    ProductService productService;

    @GetMapping("/getProductForSelect")
    public ResponseResult getProductForSelect(){

        List<productVo> selectList = new ArrayList<>();

        try {

            selectList =productService.getProductForSelect();

            return ResponseResult.okResult(selectList) ;

        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }
}
