package com.gdproj.controller;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Contract;
import com.gdproj.entity.Template;
import com.gdproj.entity.contractCategory;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.ContractService;
import com.gdproj.service.TemplateService;
import com.gdproj.service.contractCategoryService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.categoryVo;
import com.gdproj.vo.contractVo;
import com.gdproj.vo.pageVo;
import com.gdproj.vo.selectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/adminContract")
public class contractController {

    @Autowired
    ContractService contractService;

    @Autowired
    contractCategoryService categoryService;

    @Autowired
    TemplateService templateService;

    @GetMapping("/getListForSelect")
    public ResponseResult getListForSelect(){

        List<selectVo> selectList = new ArrayList<>();

        try {

            selectList =contractService.getListForSelect();

            return ResponseResult.okResult(selectList) ;

        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }


    }
    @GetMapping("/getContractList")
    public ResponseResult getContractList(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam(required = false,defaultValue = "+id")String sort,
                                        @RequestParam(required = false,defaultValue = "") String title ,
                                        @RequestParam(required = false) Integer departmentId,
                                        @RequestParam(required = false) Integer type,
                                        @RequestParam(required = false) String time){

        pageDto pageDto = new pageDto(pageNum,pageSize,departmentId,type,title,time,sort);

        IPage<contractVo> List = new Page<>();

        try {
            List = contractService.getContractList(pageDto);
            pageVo<List<contractVo>> pageList = new pageVo<>();
            pageList.setData(List.getRecords());
            pageList.setTotal((int) List.getTotal());
            return ResponseResult.okResult(pageList);
        }catch (SystemException e){
            return ResponseResult.okResult(e.getCode(),e.getMsg());
        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }

    }


    @PutMapping("updateContract")
    public ResponseResult updateContract(@RequestBody contractVo vo){


        Contract updateInfo = BeanCopyUtils.copyBean(vo, Contract.class);
//        vo中的 发布人  类型 部门

        boolean b = false;

        try {

            b = contractService.updateById(updateInfo);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }


    }

    @PostMapping("insertContract")
    public ResponseResult insertContract(@RequestBody contractVo vo){

        Contract insertInfo = BeanCopyUtils.copyBean(vo, Contract.class);

        boolean b = false;

        try {

            b = contractService.save(insertInfo);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.INSERT_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }

    }

    @DeleteMapping("deleteContract")
    public ResponseResult deleteContract(@PathParam("contractId") Integer contractId){

        boolean b = false;

        try {

            b = contractService.removeById(contractId);

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

        IPage<contractCategory> categoryList = new Page<>();

        try {

            if(ObjectUtil.isNull(pagedto.getPageNum())){
                List<contractCategory> list = categoryService.list();

                return ResponseResult.okResult(list);
            }else{
                categoryList = categoryService.getContractCategoryList(pagedto);

                pageVo<List<contractCategory>> pageList = new pageVo<>();

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

        contractCategory contractcategory = new contractCategory();

        boolean b = false;

        try {
            contractcategory = BeanCopyUtils.copyBean(category, contractCategory.class);

            b = categoryService.updateById(contractcategory);

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

        contractCategory contractcategory = new contractCategory();

        boolean b = false;

        try {
            contractcategory = BeanCopyUtils.copyBean(category, contractCategory.class);

            b = categoryService.save(contractcategory);

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

        contractCategory contractcategory = new contractCategory();


        boolean b = false;

        try {
//            notifycategory = BeanCopyUtils.copyBean(category, notifyCategory.class);

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

    @GetMapping("getTemplateList")
    public ResponseResult getTemplateList(@RequestParam(required = false) Integer pageNum,@RequestParam(required = false) Integer pageSize){
        // 是否需要转化为templateVo
        pageDto pageDto = new pageDto(pageNum, pageSize);
        IPage<Template> templateList = new Page<>();
        try {
            if(ObjectUtil.isNull(pageDto.getPageNum())){
                List<Template> list = templateService.list();
                return ResponseResult.okResult(list);
            }else {
                templateList = templateService.getTemplateList(pageDto);
                pageVo<List<Template>> pageList = new pageVo<>();
                pageList.setTotal((int) templateList.getTotal());
                pageList.setData(templateList.getRecords());
                return  ResponseResult.okResult(pageList);
            }

        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }

    }
    @PutMapping("updateTemplate")
    public ResponseResult updateTemplate(@RequestBody Template template){

        Boolean b = false;
        try {
            b = templateService.updateById(template);
            if(b){
                return ResponseResult.okResult();
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
            }
        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }

    }
    @PostMapping("insertTemplate")
    public ResponseResult insertTemplate(@RequestBody Template template){
        Boolean b = false;
        try {
            b = templateService.save(template);
            if(b){
                return ResponseResult.okResult();
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.INSERT_ERROR);
            }
        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }
    @DeleteMapping("deleteTemplate")
    public ResponseResult deleteTemplate(@RequestParam("templateId") Integer templateId){
        Boolean b = false;
        try {
            b = templateService.removeById(templateId);
            if(b){
                return ResponseResult.okResult();
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);
            }
        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }




}
