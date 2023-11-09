package com.gdproj.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdproj.annotation.autoLog;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Tool;
import com.gdproj.entity.ToolCategory;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.ToolCategoryService;
import com.gdproj.service.ToolService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.CategoryVo;
import com.gdproj.vo.PageVo;
import com.gdproj.vo.ToolVo;
import com.gdproj.vo.stockSelectVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/adminTool")
@Api(tags = "日常领用工具功能")
public class toolController {

    @Autowired
    ToolService toolService;

    @Autowired
    ToolCategoryService categoryService;

    @GetMapping("getListForSelect")
    @ApiOperation(value = "查询用于选择的工具列表")
    @autoLog
    public ResponseResult getListForSelect() {
        List<stockSelectVo> selectList = new ArrayList<>();
        try {
            selectList = toolService.getListForSelect();

            return ResponseResult.okResult(selectList);

        } catch (Exception e) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }

    @GetMapping("/getToolList")
    @autoLog
    @ApiOperation(value = "查询工具列表")
    public ResponseResult getToolList(@RequestParam Integer pageNum,
                                      @RequestParam Integer pageSize,
                                      @RequestParam(required = false, defaultValue = "+id") String sort,
                                      @RequestParam(required = false, defaultValue = "") String title,
                                      @RequestParam(required = false) Integer departmentId,
                                      @RequestParam(required = false) Integer type,
                                      @RequestParam(required = false) String time) {

        PageQueryDto pageDto = new PageQueryDto(pageNum, pageSize, departmentId, type, title, time, sort);

        IPage<ToolVo> toolList = new Page<>();

        try {
            toolList = toolService.getToolList(pageDto);
            PageVo<List<ToolVo>> pageList = new PageVo<>();
            pageList.setData(toolList.getRecords());
            pageList.setTotal((int) toolList.getTotal());
            return ResponseResult.okResult(pageList);
        } catch (SystemException e) {
            return ResponseResult.okResult(e.getCode(), e.getMsg());
        } catch (Exception e) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }

    }

    @PutMapping("updateTool")
    @autoLog
    @ApiOperation(value = "更新工具")
    public ResponseResult updateTool(@RequestBody ToolVo vo) {

        Tool tool = BeanCopyUtils.copyBean(vo, Tool.class);

        boolean b = false;

        try {

            b = toolService.updateById(tool);

            if (b) {

                return ResponseResult.okResult(b);

            } else {
                return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
            }

        } catch (Exception e) {

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }

    }

    @PostMapping("insertTool")
    @autoLog
    @ApiOperation(value = "新增工具")
    public ResponseResult insertTool(@RequestBody ToolVo vo) {
        Tool tool = BeanCopyUtils.copyBean(vo, Tool.class);
        boolean b = toolService.save(tool);
        if (b) {
            return ResponseResult.okResult(b);
        } else {
            throw new SystemException(AppHttpCodeEnum.INSERT_ERROR);
        }

    }

    @DeleteMapping("deleteTool")
    @autoLog
    @ApiOperation(value = "删除工具")
    public ResponseResult deleteTool(@RequestParam("toolId") Integer toolId) {
        boolean b = toolService.removeById(toolId);
            if (b) {
                return ResponseResult.okResult(b);
            } else {
                throw new SystemException(AppHttpCodeEnum.DELETE_ERROR);
            }
    }

    @GetMapping("/getCategoryList")
    @autoLog
    @ApiOperation(value = "查询类型列表")
    public ResponseResult getCategoryList(@RequestParam(required = false) Integer pageNum, @RequestParam(required = false) Integer pageSize) {

        PageQueryDto pagedto = new PageQueryDto(pageNum, pageSize);

        IPage<ToolCategory> categoryList = new Page<>();

        try {

            if (ObjectUtil.isNull(pagedto.getPageNum())) {
                List<ToolCategory> list = categoryService.list();
                return ResponseResult.okResult(list);
            } else {
                categoryList = categoryService.getDailyUseCategoryList(pagedto);
                PageVo<List<ToolCategory>> pageList = new PageVo<>();
                pageList.setData(categoryList.getRecords());
                pageList.setTotal((int) categoryList.getTotal());
                return ResponseResult.okResult(pageList);
            }

        } catch (Exception e) {
            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);
        }

    }

    @PutMapping("updateCategory")
    @autoLog
    @ApiOperation(value = "更新类型")
    public ResponseResult updateCategory(@RequestBody CategoryVo category) {

        ToolCategory category1 = new ToolCategory();


        boolean b = false;

        try {
            category1 = BeanCopyUtils.copyBean(category, ToolCategory.class);

            b = categoryService.updateById(category1);

            if (b) {
                return ResponseResult.okResult(b);
            } else {
                return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
            }

        } catch (Exception e) {

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }


    }

    @PostMapping("insertCategory")
    @autoLog
    @ApiOperation(value = "新增类型")
    public ResponseResult insertCategory(@RequestBody CategoryVo category) {

        ToolCategory category1 = new ToolCategory();

        boolean b = false;

        try {
            category1 = BeanCopyUtils.copyBean(category, ToolCategory.class);

            b = categoryService.save(category1);

            if (b) {
                return ResponseResult.okResult(b);
            } else {
                return ResponseResult.errorResult(AppHttpCodeEnum.INSERT_ERROR);
            }

        } catch (Exception e) {

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }

    }

    @DeleteMapping("deleteCategory")
    @autoLog
    @ApiOperation(value = "删除类型")
    public ResponseResult deleteCategory(@RequestParam("categoryId") Integer categoryId) {


        System.out.println(categoryId);

        boolean b = false;

        try {

            b = categoryService.removeById(categoryId);

            if (b) {
                return ResponseResult.okResult(b);
            } else {
                return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);
            }

        } catch (Exception e) {

            return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR);

        }

    }
}
