package com.gdproj.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.annotation.autoLog;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Product;
import com.gdproj.entity.productCategory;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.ProductService;
import com.gdproj.service.productCategoryService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.categoryVo;
import com.gdproj.vo.pageVo;
import com.gdproj.vo.productVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/adminProduct")
@Api(tags = "产品功能")
public class productController {

    @Autowired
    ProductService productService;

    @Autowired
    productCategoryService categoryService;

    @GetMapping("/getProductForSelect")
    @autoLog
    @ApiOperation(value = "查询用于选择的产品列表")
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
    @autoLog
    @ApiOperation(value = "查询产品列表")
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
    @autoLog
    @ApiOperation(value = "更新产品")
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
    @autoLog
    @ApiOperation(value = "新增产品")
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
    @autoLog
    @ApiOperation(value = "删除产品")
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
    @autoLog

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
    @autoLog
    @ApiOperation(value = "查询类型列表")
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
    @autoLog
    @ApiOperation(value = "更新类型")
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
    @autoLog
    @ApiOperation(value = "新增类型")
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
    @autoLog
    @ApiOperation(value = "删除类型")
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
