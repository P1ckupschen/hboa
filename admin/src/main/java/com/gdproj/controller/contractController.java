package com.gdproj.controller;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.annotation.autoLog;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Contract;
import com.gdproj.entity.Template;
import com.gdproj.entity.ContractCategory;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.ContractService;
import com.gdproj.service.TemplateService;
import com.gdproj.service.contractCategoryService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.CategoryVo;
import com.gdproj.vo.ContractVo;
import com.gdproj.vo.PageVo;
import com.gdproj.vo.SelectVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/adminContract")
@Api(tags = "合同功能")
public class contractController {

    @Autowired
    ContractService contractService;

    @Autowired
    contractCategoryService categoryService;

    @Autowired
    TemplateService templateService;

    @GetMapping("/getListForSelect")
    @autoLog
    @ApiOperation(value = "查询用于选择的合同列表")
    public ResponseResult getListForSelect(){

        List<SelectVo> selectList = new ArrayList<>();

        try {

            selectList =contractService.getListForSelect();

            return ResponseResult.okResult(selectList) ;

        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }


    }
    @GetMapping("/getContractList")
    @autoLog
    @ApiOperation(value = "查询合同列表")
    public ResponseResult getContractList(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam(required = false,defaultValue = "+id")String sort,
                                        @RequestParam(required = false,defaultValue = "") String title ,
                                        @RequestParam(required = false) Integer departmentId,
                                        @RequestParam(required = false) Integer type,
                                        @RequestParam(required = false) String time){

        pageDto pageDto = new pageDto(pageNum,pageSize,departmentId,type,title,time,sort);

        IPage<ContractVo> List = new Page<>();

        try {
            List = contractService.getContractList(pageDto);
            PageVo<List<ContractVo>> pageList = new PageVo<>();
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
    @autoLog
    @ApiOperation(value = "更新合同")
    public ResponseResult updateContract(@RequestBody ContractVo vo){


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
    @autoLog
    @ApiOperation(value = "新增合同")
    public ResponseResult insertContract(@RequestBody ContractVo vo){

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
    @autoLog
    @ApiOperation(value = "删除合同")
    public ResponseResult deleteContract(@RequestParam("contractId") Integer contractId){

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
    @autoLog
    @ApiOperation(value = "查询类型数据")
    public ResponseResult getCategoryList(@RequestParam(required = false) Integer pageNum,@RequestParam(required = false) Integer pageSize){

        pageDto pagedto = new pageDto(pageNum, pageSize);

        IPage<ContractCategory> categoryList = new Page<>();

        try {

            if(ObjectUtil.isNull(pagedto.getPageNum())){
                List<ContractCategory> list = categoryService.list();

                return ResponseResult.okResult(list);
            }else{
                categoryList = categoryService.getContractCategoryList(pagedto);

                PageVo<List<ContractCategory>> pageList = new PageVo<>();

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
    @ApiOperation(value = "更新类型", notes = "更新")
    public ResponseResult updateCategory(@RequestBody CategoryVo category){

        ContractCategory contractcategory = new ContractCategory();

        boolean b = false;

        try {
            contractcategory = BeanCopyUtils.copyBean(category, ContractCategory.class);

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
    @autoLog
    @ApiOperation(value = "新增类型", notes = "新增")
    public ResponseResult insertCategory(@RequestBody CategoryVo category){

        ContractCategory contractcategory = new ContractCategory();

        boolean b = false;

        try {
            contractcategory = BeanCopyUtils.copyBean(category, ContractCategory.class);

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
    @autoLog
    @ApiOperation(value = "删除类型", notes = "删除")
    public ResponseResult deleteCategory(@PathParam("categoryId") Integer categoryId){

        ContractCategory contractcategory = new ContractCategory();


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
    @autoLog
    @ApiOperation(value = "查询模板列表", notes = "可传分页")
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
                PageVo<List<Template>> pageList = new PageVo<>();
                pageList.setTotal((int) templateList.getTotal());
                pageList.setData(templateList.getRecords());
                return  ResponseResult.okResult(pageList);
            }

        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }

    }
    @PutMapping("updateTemplate")
    @autoLog
    @ApiOperation(value = "更新模板", notes = "更新")
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
    @autoLog
    @ApiOperation(value = "新增模板", notes = "新增")
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
    @autoLog
    @ApiOperation(value = "删除模板", notes = "删除")
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
