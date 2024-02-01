package com.gdproj.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.annotation.autoLog;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Contract;
import com.gdproj.entity.Project;
import com.gdproj.entity.ProjectCategory;
import com.gdproj.entity.ReportCategory;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.ContractService;
import com.gdproj.service.ProjectService;
import com.gdproj.service.projectCategoryService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.CategoryVo;
import com.gdproj.vo.PageVo;
import com.gdproj.vo.ProjectVo;
import com.gdproj.vo.SelectVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/adminProject")
@Api(tags = "项目功能")
public class projectController {

    @Autowired
    ContractService contractService;

    @Autowired
    ProjectService projectService;

    @Autowired
    projectCategoryService categoryService;

    @GetMapping("/getListForSelect")
    @autoLog
    @ApiOperation(value = "查询用于选择的项目列表")
    public ResponseResult getListForSelect(){

        List<SelectVo> selectList = new ArrayList<>();

        try {

            selectList =projectService.getListForSelect();

            return ResponseResult.okResult(selectList) ;

        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }


    }

    @GetMapping("/getProjectListForBackend")
    @autoLog
    @ApiOperation(value = "后台查询项目列表")
    public ResponseResult getProjectListForBackend(@Validated PageQueryDto queryDto){
        return projectService.getProjectListForBackend(queryDto);
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

        PageQueryDto pageDto = new PageQueryDto(pageNum,pageSize,departmentId,type,title,time,sort);

        IPage<ProjectVo> projectList = new Page<>();

        try {
            projectList = projectService.getProjectList(pageDto);
            PageVo<List<ProjectVo>> pageList = new PageVo<>();
            pageList.setData(projectList.getRecords());
            pageList.setTotal((int) projectList.getTotal());
            return ResponseResult.okResult(pageList);
        }catch (SystemException e){
            return ResponseResult.okResult(e.getCode(),e.getMsg());
        }catch (Exception e){
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }

    }

    @GetMapping("/getProjectListByCurrentId")
    @autoLog
    @ApiOperation(value = "查询当前用户有关的项目列表")
    public ResponseResult getProjectListByCurrentId(@Validated PageQueryDto queryDto , HttpServletRequest request){

        return projectService.getProjectListByCurrentId(queryDto,request);

    }

    @GetMapping("/getPrivateProjectList")
    @autoLog
    @ApiOperation(value = "查询私人的项目列表")
    public ResponseResult getPrivateProjectList(@Validated PageQueryDto queryDto , HttpServletRequest request){

        return projectService.getPrivateProjectList(queryDto,request);

    }

    @PutMapping("updateProject")
    @autoLog
    @ApiOperation(value = "更新项目")
    public ResponseResult updateProject(@RequestBody ProjectVo vo){


        Project updateInfo = BeanCopyUtils.copyBean(vo, Project.class);
//        vo中的 发布人  类型 部门
        // 如果有了质保时间和质保年限
        if(!ObjectUtil.isEmpty(updateInfo.getCompletedTime()) && !ObjectUtil.isEmpty(updateInfo.getWarrantyYear())){
            Contract one = contractService.getById(updateInfo.getContractId());
//            one.setWarrantyAmount(updateInfo.getWarrantyAmount());
            Calendar instance = Calendar.getInstance();
            instance.setTime(updateInfo.getCompletedTime());
            instance.add(Calendar.YEAR, one.getWarrantyYear());
            Date time = instance.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            one.setWarrantyEndTime(time);
            one.setWarrantyStartTime(updateInfo.getCompletedTime());
//            one.setWarrantyYear(Integer.valueOf(updateInfo.getWarrantyYear()));
            contractService.updateById(one);
        }
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
    public ResponseResult insertProject(@RequestBody ProjectVo vo){

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
    public ResponseResult deleteProject(@RequestParam("projectId") Integer Id){

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

        PageQueryDto pagedto = new PageQueryDto(pageNum, pageSize);

        IPage<ProjectCategory> categoryList = new Page<>();

        try {

            if(ObjectUtil.isNull(pagedto.getPageNum())){
                List<ProjectCategory> list = categoryService.list();
                List<SelectVo> collect = list.stream().map((item) -> {
                    SelectVo vo = new SelectVo();
                    vo.setId(item.getCategoryId());
                    vo.setName(item.getCategoryName());
                    return vo;
                }).collect(Collectors.toList());
                return ResponseResult.okResult(collect);
            }else{

                categoryList = categoryService.getProjectCategoryList(pagedto);
                PageVo<List<ProjectCategory>> pageList = new PageVo<>();
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
    public ResponseResult updateCategory(@RequestBody CategoryVo category){

        ProjectCategory projectcategory = new ProjectCategory();

        boolean b = false;

        try {
            projectcategory = BeanCopyUtils.copyBean(category, ProjectCategory.class);

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
    public ResponseResult insertCategory(@RequestBody CategoryVo category){

        ProjectCategory projectcategory = new ProjectCategory();

        boolean b = false;

        try {
            projectcategory = BeanCopyUtils.copyBean(category, ProjectCategory.class);

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
    public ResponseResult deleteCategory(@RequestParam("categoryId") Integer categoryId){

        ReportCategory reportcategory = new ReportCategory();

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
