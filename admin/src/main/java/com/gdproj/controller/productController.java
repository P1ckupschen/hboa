package com.gdproj.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Product;
import com.gdproj.entity.Task;
import com.gdproj.entity.overtimeCategory;
import com.gdproj.entity.productCategory;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.ProductService;
import com.gdproj.service.productCategoryService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/adminProduct")
public class productController {

    @Autowired
    ProductService productService;

    @Autowired
    productCategoryService categoryService;

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



    //获取List时 通过去搜record表 实时计算count值
    @GetMapping("/getProductList")
    public ResponseResult getProductList(@RequestParam Integer pageNum,
                                         @RequestParam Integer pageSize,
                                         @RequestParam(required = false,defaultValue = "+id")String sort,
                                         @RequestParam(required = false,defaultValue = "") String title ,
                                         @RequestParam(required = false) Integer departmentId,
                                         @RequestParam(required = false) Integer type,
                                         @RequestParam(required = false) String time){

        pageDto pageDto = new pageDto(pageNum,pageSize,departmentId,type,title,time,sort);

        IPage<productVo> productList = new Page<>();

        try {
            productList = productService.getProductList(pageDto);

            pageVo<List<productVo>> pageList = new pageVo<>();
            pageList.setData(productList.getRecords());
            pageList.setTotal((int) productList.getTotal());
            return ResponseResult.okResult(pageList);

        }catch (SystemException e){
            return ResponseResult.okResult(e.getCode(),e.getMsg());
        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }



    @PutMapping("updateProduct")
    public ResponseResult updateProduct(@RequestBody productVo vo){

        Product updateInfo = BeanCopyUtils.copyBean(vo, Product.class);

        boolean b = false;

        try {

            b = productService.updateById(updateInfo);

            if(b){

                return ResponseResult.okResult(b);

            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }


    }

    @PostMapping("insertProduct")
    public ResponseResult insertProduct(@RequestBody productVo vo){

        Product insertInfo = BeanCopyUtils.copyBean(vo, Product.class);

        boolean b = false;

        try {

            b = productService.save(insertInfo);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.INSERT_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }

    }

    @DeleteMapping("deleteProduct")
    public ResponseResult deleteProduct(@PathParam("productId") Integer productId){

        boolean b = false;

        try {

            b = productService.removeById(productId);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }

    }

    @DeleteMapping("deleteProductList")
    //批量删除
    public ResponseResult deleteProductList(@RequestBody Integer categoryId){

        boolean b = false;

        try {

            b = productService.removeById(categoryId);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }

    }




    //类型的增删改查

    @GetMapping("/getCategoryList")
    public ResponseResult getCategoryList(@RequestParam(required = false) Integer pageNum,@RequestParam(required = false) Integer pageSize){

        pageDto pagedto = new pageDto(pageNum, pageSize);

        IPage<categoryVo> categoryList = new Page<>();
        try {
            if(ObjectUtil.isNull(pagedto.getPageNum())){

                List<categoryVo> list = categoryService.getProductCategoryList();
                return ResponseResult.okResult(list);

            }else{
                categoryList = categoryService.getProductCategoryListByPage(pagedto);
                pageVo<List<categoryVo>> pageList = new pageVo<>();
                pageList.setData(categoryList.getRecords());
                pageList.setTotal((int) categoryList.getTotal());
                return ResponseResult.okResult(pageList);
            }


        }catch (Exception e){
            return  ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }

    }

    @PutMapping("updateCategory")
    public ResponseResult updateCategory(@RequestBody categoryVo category){

        productCategory Category = new productCategory();


        boolean b = false;

        try {
            Category = BeanCopyUtils.copyBean(category, productCategory.class);

            b = categoryService.updateById(Category);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }


    }

    @PostMapping("insertCategory")
    public ResponseResult insertCategory(@RequestBody categoryVo category){

        productCategory Category = new productCategory();

        boolean b = false;

        try {
            Category = BeanCopyUtils.copyBean(category, productCategory.class);

            b = categoryService.save(Category);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.INSERT_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }

    }

    @DeleteMapping("deleteCategory")
    public ResponseResult deleteCategory(@PathParam("categoryId") Integer categoryId){


        System.out.println(categoryId);

        boolean b = false;

        try {

            b = categoryService.removeById(categoryId);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }

    }


}
