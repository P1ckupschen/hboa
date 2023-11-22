package com.gdproj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Purchase;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.PurchaseMapper;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.DeployeeService;
import com.gdproj.service.FlowService;
import com.gdproj.service.PurchaseService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.PageVo;
import com.gdproj.vo.PurchaseContentVo;
import com.gdproj.vo.PurchaseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【sys_purchase】的数据库操作Service实现
* @createDate 2023-11-07 09:36:09
*/
@Service
public class PurchaseServiceImpl extends ServiceImpl<PurchaseMapper, Purchase>
    implements PurchaseService {

    @Autowired
    DeployeeService deployeeService;

    @Autowired
    FlowService flowService;

    @Override
    public ResponseResult getPurchaseList(PageQueryDto queryVo) {
        //类型
        Integer type = queryVo.getType();
        //部门
        Integer departmentId = queryVo.getDepartmentId();
        //时间
        String time = queryVo.getTime();
        //排序
        String sort = queryVo.getSort();
        //搜索框如果是产品搜索产品名称或者选择产品id
        //如果是人 搜素人名或者人id
        //如果是物 搜索id
        String title = queryVo.getTitle();
        Integer pageNum = queryVo.getPageNum();
        Integer pageSize = queryVo.getPageSize();

        Page<Purchase> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Purchase> queryWrapper = new LambdaQueryWrapper<>();
        //排序
        if (sort.equals("+id")) {
            queryWrapper.orderByAsc(Purchase::getPurchaseId);
        } else {
            queryWrapper.orderByDesc(Purchase::getPurchaseId);
        }

        if (!title.isEmpty()) {
            List<Integer> ids = deployeeService.getIdsByTitle(title);
            if(!ObjectUtil.isEmpty(ids)){
                queryWrapper.in(Purchase::getUserId,ids);
            }else{
                queryWrapper.eq(Purchase::getUserId,0);
            }
        }

        if(!ObjectUtil.isEmpty(time)){
            queryWrapper.like(Purchase::getCreatedTime,time);
        }
        //如果有类型的话 类型
        if (!ObjectUtil.isEmpty(type)) {
            //传过来的是productCategoryId,需要去产品表下找属于这个产品类型的产品 id数组
            queryWrapper.eq(Purchase::getCategoryId,type);
        }
        IPage<Purchase> recordPage = page(page, queryWrapper);

        List<PurchaseVo> collect = recordPage.getRecords().stream().map((item) -> {
            PurchaseVo vo = BeanCopyUtils.copyBean(item, PurchaseVo.class);

            String content = JSONUtil.toJsonStr(item.getPurchaseContent());
            List<PurchaseContentVo> contentVoList = JSONUtil.toList(content, PurchaseContentVo.class);
            vo.setPurchaseContent(contentVoList);
            if(ObjectUtil.isEmpty(item.getUserId())){
                throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
            }
            if(!ObjectUtil.isEmpty(item.getUserId())){
                vo.setUsername(deployeeService.getNameByUserId(item.getUserId()));
            }else{
                vo.setUsername("");
            }
            return vo;
        }).collect(Collectors.toList());

        PageVo<List<PurchaseVo>> pageList = new PageVo<>();

        pageList.setData(collect);

        pageList.setTotal((int) recordPage.getTotal());

        return ResponseResult.okResult(pageList);
    }

    @Override
    public boolean insertPurchase(Purchase purchase) {
        //插入overtime数据
        boolean f =false;
        boolean o =save(purchase);
        if(o){
            f = flowService.insertFlow(purchase);
        }else{
            throw new SystemException(AppHttpCodeEnum.INSERT_ERROR);
        }
        return o && f;

    }

    @Override
    public ResponseResult deletePurchase(Integer id) {

        boolean b = removeById(id);
        if(b){
            return ResponseResult.okResult(b);
        }else{
            throw new SystemException(AppHttpCodeEnum.DELETE_ERROR);
        }
    }

    @Override
    public ResponseResult updatePurchase(PurchaseVo vo) {
        Purchase purchase = BeanCopyUtils.copyBean(vo, Purchase.class);
        boolean b = updateById(purchase);
        if(b){
            return ResponseResult.okResult(b);
        }else{
            throw  new SystemException(AppHttpCodeEnum.UPDATE_ERROR);
        }
    }
}




