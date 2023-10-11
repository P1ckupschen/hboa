package com.gdproj.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.annotation.autoLog;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Project;
import com.gdproj.entity.projectCategory;
import com.gdproj.entity.reportCategory;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.ProjectService;
import com.gdproj.service.projectCategoryService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.categoryVo;
import com.gdproj.vo.pageVo;
import com.gdproj.vo.projectVo;
import com.gdproj.vo.selectVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/adminProject")
@Api(tags = "项目功能")
public class projectController {

    @Autowired
    ProjectService projectService;

    @Autowired
    projectCategoryService categoryService;

    @GetMapping("/getListForSelect")
    @autoLog
    @ApiOperation(value = "查询用于选择的项目列表")
    public ResponseResult getListForSelect(){

        List<selectVo> selectList = new ArrayList<>();

        try {

            selectList =projectService.getListForSelect();

            return ResponseResult.okResult(selectList) ;

        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }


    }

    @GetMapping("/getProjectList")
    @autoLog
    @ApiOperation(value = "查询项目列表")
    public ResponseResult getProjectList(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam(required = false,defaultValue = "+id")String sort,
                                        @RequestParam(required = false,defaultValue = "") String title ,
                                        @RequestParam(required = false) Integer departmentId,
                                        @RequestParam(required = false) Integer type,
                                        @RequestParam(required = false) String time){

        pageDto pageDto = new pageDto(pageNum,pageSize,departmentId,type,title,time,sort);

        IPage<projectVo> projectList = new Page<>();

        try {
            projectList = projectService.getProjectList(pageDto);
            pageVo<List<projectVo>> pageList = new pageVo<>();
            pageList.setData(projectList.getRecords());
            pageList.setTotal((int) projectList.getTotal());
            return ResponseResult.okResult(pageList);
        }catch (SystemException e){
            return ResponseResult.okResult(e.getCode(),e.getMsg());
        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }

    }

    @PutMapping("updateProject")
    @autoLog
    @ApiOperation(value = "更新项目")
    public ResponseResult updateProject(@RequestBody projectVo vo){


        Project updateInfo = BeanCopyUtils.copyBean(vo, Project.class);
//        vo中的 发布人  类型 部门

        boolean b = false;

        try {

            b = projectService.updateById(updateInfo);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }


    }

    @PostMapping("insertProject")
    @autoLog
    @ApiOperation(value = "新增项目")
    public ResponseResult insertProject(@RequestBody projectVo vo){

        Project insertInfo = BeanCopyUtils.copyBean(vo, Project.class);

        boolean b = false;

        try {

            b = projectService.save(insertInfo);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.INSERT_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }

    }

    @DeleteMapping("deleteProject")
    @autoLog
    @ApiOperation(value = "删除项目")
    public ResponseResult deleteProject(@PathParam("projectId") Integer Id){

        boolean b = false;

        try {

            b = projectService.removeById(Id);

            if(b){
                return ResponseResult.okResult(b);
            }else{
                return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);
            }

        }catch (Exception e){

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }
    }

    @DeleteMapping("deleteProjectList")
    @autoLog
    public ResponseResult deleteProjectList(@RequestBody List<Integer> Ids){
        boolean b = false;

        try {

            b = projectService.removeBatchByIds(Ids);

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

        IPage<projectCategory> categoryList = new Page<>();

        try {

            if(ObjectUtil.isNull(pagedto.getPageNum())){
                List<projectCategory> list = categoryService.list();

                return ResponseResult.okResult(list);
            }else{

                categoryList = categoryService.getProjectCategoryList(pagedto);
                pageVo<List<projectCategory>> pageList = new pageVo<>();
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

        projectCategory projectcategory = new projectCategory();

        boolean b = false;

        try {
            projectcategory = BeanCopyUtils.copyBean(category, projectCategory.class);

            b = categoryService.updateById(projectcategory);

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

        projectCategory projectcategory = new projectCategory();

        boolean b = false;

        try {
            projectcategory = BeanCopyUtils.copyBean(category, projectCategory.class);

            b = categoryService.save(projectcategory);

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

        reportCategory reportcategory = new reportCategory();

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
