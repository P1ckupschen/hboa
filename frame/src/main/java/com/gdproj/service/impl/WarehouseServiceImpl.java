package com.gdproj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Flow;
import com.gdproj.entity.Record;
import com.gdproj.entity.Warehouse;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.WarehouseMapper;
import com.gdproj.service.*;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.utils.JwtUtils;
import com.gdproj.utils.commonUtils;
import com.gdproj.vo.WarehouseContentVo;
import com.gdproj.vo.WarehouseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【sys_warehouse】的数据库操作Service实现
 * @createDate 2023-10-20 11:10:47
 */
@Service
public class WarehouseServiceImpl extends ServiceImpl<WarehouseMapper, Warehouse>
        implements WarehouseService {

    @Autowired
    FlowService flowService;

    @Autowired
    DeployeeService deployeeService;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    RecordService recordService;

    @Override
    public boolean insertWarehouse(Warehouse warehouse) {
        Integer categoryId = warehouse.getCategoryId();

        boolean f = false;

        //如果为入库申请则无需审批
        if(warehouse.getCategoryId() == 1){
            warehouse.setWarehouseStatus(1);
            boolean o = save(warehouse);
            //并且遍历warehouse 的一个json属性 将所有入库产品都加入record
            List<Record> recordList = recordService.transferWarehouseContentToRecord(warehouse);
            return recordService.saveBatch(recordList);
        }else{
            warehouse.setWarehouseStatus(0);
            boolean o = save(warehouse);
            if (o) {
                f = flowService.insertFlow(warehouse);
            }else{
                throw new SystemException(AppHttpCodeEnum.INSERT_ERROR);
            }
            return o && f;
        }

    }

    @Override
    public IPage<WarehouseVo> getWarehouseList(PageQueryDto pageDto) {
        //类型
        Integer type = pageDto.getType();
        //部门
        Integer departmentId = pageDto.getDepartmentId();
        //时间
        String time = pageDto.getTime();
        //排序
        String sort = pageDto.getSort();
        //搜索框如果是产品搜索产品名称或者选择产品id
        //如果是人 搜素人名或者人id
        //如果是物 搜索id
        String title = pageDto.getTitle();
        Integer pageNum = pageDto.getPageNum();
        Integer pageSize = pageDto.getPageSize();

        Page<Warehouse> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Warehouse> queryWrapper = new LambdaQueryWrapper<>();
        //排序
        if (sort.equals("+id")) {
            queryWrapper.orderByAsc(Warehouse::getWarehouseId);
        } else {
            queryWrapper.orderByDesc(Warehouse::getWarehouseId);
        }
        //查询名称？
        if (!title.isEmpty()) {
            List<Integer> ids = deployeeService.getIdsByTitle(title);
            if(!ObjectUtil.isEmpty(ids)){
                queryWrapper.in(Warehouse::getUserId,ids);
            }else{
                queryWrapper.eq(Warehouse::getUserId,0);
            }
        }

        if(!ObjectUtil.isEmpty(time)){
            queryWrapper.like(Warehouse::getCreatedTime,time);
        }
        //如果有类型的话 类型
        if (!ObjectUtil.isEmpty(type)) {
            //传过来的是productCategoryId,需要去产品表下找属于这个产品类型的产品 id数组
            queryWrapper.eq(Warehouse::getCategoryId,type);
        }
        IPage<Warehouse> recordPage = page(page, queryWrapper);

        Page<WarehouseVo> resultPage = new Page<>();

        List<WarehouseVo> resultList = new ArrayList<>();
        try {

            resultList = recordPage.getRecords().stream().map((item) -> {

                WarehouseVo vo = BeanCopyUtils.copyBean(item, WarehouseVo.class);

                String content = JSONUtil.toJsonStr(item.getWarehouseContent());
                List<WarehouseContentVo> warehouseContentVos = JSONUtil.toList(content, WarehouseContentVo.class);
                vo.setWarehouseContent(warehouseContentVos);
                if(item.getCategoryId() == 1){
                    vo.setCategory("入库");
                }else if(item.getCategoryId() == 2){
                    vo.setCategory("出库");
                }

                vo.setUsername(deployeeService.getNameByUserId(item.getUserId()));

                return vo;
            }).collect(Collectors.toList());

            List<WarehouseVo> warehouseVos = addOrderId(resultList, pageNum, pageSize);
            resultPage.setRecords(warehouseVos);

            resultPage.setTotal(recordPage.getTotal());

            return resultPage;
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }

    }

    @Override
    public IPage<WarehouseVo> getMyWarehouseList(PageQueryDto pageDto, HttpServletRequest request) {
        //类型
        Integer type = pageDto.getType();
        //部门
        Integer departmentId = pageDto.getDepartmentId();
        //时间
        String time = pageDto.getTime();
        //排序
        String sort = pageDto.getSort();
        //搜索框如果是产品搜索产品名称或者选择产品id
        //如果是人 搜素人名或者人id
        //如果是物 搜索id
        String title = pageDto.getTitle();
        Integer pageNum = pageDto.getPageNum();
        Integer pageSize = pageDto.getPageSize();

        Page<Warehouse> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Warehouse> queryWrapper = new LambdaQueryWrapper<>();
        String id = JwtUtils.getMemberIdByJwtToken(request);
        List<String> role = userRoleService.getRoleKeysByUserId(id);
        if(!ObjectUtil.isEmpty(role)){
            boolean is = commonUtils.checkRole(role);
            if(!is){
                queryWrapper.eq(Warehouse::getUserId, JwtUtils.getMemberIdByJwtToken(request));
            }
        }
        //排序
        if (sort.equals("+id")) {
            queryWrapper.orderByAsc(Warehouse::getWarehouseId);
        } else {
            queryWrapper.orderByDesc(Warehouse::getWarehouseId);
        }
        //查询名称？
        if (!title.isEmpty()) {
            List<Integer> ids = deployeeService.getIdsByTitle(title);
            if(!ObjectUtil.isEmpty(ids)){
                queryWrapper.in(Warehouse::getUserId,ids);
            }else{
                queryWrapper.eq(Warehouse::getUserId,0);
            }
        }

        if(!ObjectUtil.isEmpty(time)){
            queryWrapper.like(Warehouse::getCreatedTime,time);
        }
        //如果有类型的话 类型
        if (!ObjectUtil.isEmpty(type)) {
            //传过来的是productCategoryId,需要去产品表下找属于这个产品类型的产品 id数组
            queryWrapper.eq(Warehouse::getCategoryId,type);
        }
        IPage<Warehouse> recordPage = page(page, queryWrapper);

        Page<WarehouseVo> resultPage = new Page<>();

        List<WarehouseVo> resultList = new ArrayList<>();
        try {

            resultList = recordPage.getRecords().stream().map((item) -> {

                WarehouseVo vo = BeanCopyUtils.copyBean(item, WarehouseVo.class);

                String content = JSONUtil.toJsonStr(item.getWarehouseContent());
                List<WarehouseContentVo> warehouseContentVos = JSONUtil.toList(content, WarehouseContentVo.class);
                vo.setWarehouseContent(warehouseContentVos);
                if(item.getCategoryId() == 1){
                    vo.setCategory("入库");
                }else if(item.getCategoryId() == 2){
                    vo.setCategory("出库");
                }

                vo.setUsername(deployeeService.getNameByUserId(item.getUserId()));

                return vo;
            }).collect(Collectors.toList());

            List<WarehouseVo> warehouseVos = addOrderId(resultList, pageNum, pageSize);
            resultPage.setRecords(warehouseVos);

            resultPage.setTotal(recordPage.getTotal());

            return resultPage;
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }
    }

    @Override
    public boolean updateWarehouse(Warehouse warehouse) {
        warehouse.setWarehouseStatus(0);
        boolean update = updateById(warehouse);
        if(update){
            Integer typeId = warehouse.getTypeId();
            Integer id = warehouse.getWarehouseId();
            LambdaQueryWrapper<Flow> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Flow::getTypeId,typeId);
            queryWrapper.eq(Flow::getRunId,id);
            Flow one = flowService.getOne(queryWrapper);
            one.setFlowTitle(warehouse.getWarehouseTitle());
            Flow flow = flowService.resetProperty(one);
            return flowService.updateById(flow);
        }else{
            return false;
        }
    }

    private List<WarehouseVo> addOrderId(List<WarehouseVo> list, Integer pageNum, Integer pageSize){
        if (!ObjectUtil.isEmpty(pageNum) && !ObjectUtil.isEmpty(pageSize)) {
            for (int i = 0 ; i < list.size() ; i++){
                list.get(i).setOrderId((pageNum - 1) * pageSize + i + 1);
            }
        }
        return list;
    }
}




