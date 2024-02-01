package com.gdproj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Reimbursement;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.ReimbursementMapper;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.DeployeeService;
import com.gdproj.service.FlowService;
import com.gdproj.service.ReimbursementService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.PageVo;
import com.gdproj.vo.ReimbursementContentVo;
import com.gdproj.vo.ReimbursementVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【sys_reimbursement】的数据库操作Service实现
* @createDate 2023-10-19 14:22:09
*/
@Service
public class ReimbursementServiceImpl extends ServiceImpl<ReimbursementMapper, Reimbursement>
    implements ReimbursementService {

    @Autowired
    FlowService flowService;

    @Autowired
    DeployeeService deployeeService;

    @Override
    public boolean insertReimbursement(Reimbursement reimbursement) {
        //插入overtime数据
        boolean f =false;
        reimbursement.setUserName(deployeeService.getNameByUserId(reimbursement.getUserId()));
        boolean o =save(reimbursement);
        if(o){
            f = flowService.insertFlow(reimbursement);
        }else{
            throw new SystemException(AppHttpCodeEnum.INSERT_ERROR);
        }
        //同时更新flow 表 增加一条flow数据

        return o && f;
    }

    @Override
    public ResponseResult updateReimbursement(ReimbursementVo vo) {

        Reimbursement reimbursement = BeanCopyUtils.copyBean(vo, Reimbursement.class);
        boolean b = updateById(reimbursement);
        if(b){
            return ResponseResult.okResult(b);
        }else{
            throw  new SystemException(AppHttpCodeEnum.UPDATE_ERROR);
        }
    }

    @Override
    public ResponseResult deleteReimbursement(Integer id) {

        boolean b = removeById(id);
        if(b){
            return ResponseResult.okResult(b);
        }else{
            throw new SystemException(AppHttpCodeEnum.DELETE_ERROR);
        }
    }

    @Override
    public ResponseResult getReimbursementList(PageQueryDto queryVo) {
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

        Page<Reimbursement> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Reimbursement> queryWrapper = new LambdaQueryWrapper<>();
        //排序
        if (sort.equals("+id")) {
            queryWrapper.orderByAsc(Reimbursement::getReimbursementId);
        } else {
            queryWrapper.orderByDesc(Reimbursement::getReimbursementId);
        }

        if (!title.isEmpty()) {
            List<Integer> ids = deployeeService.getIdsByTitle(title);
            if(!ObjectUtil.isEmpty(ids)){
                queryWrapper.in(Reimbursement::getUserId,ids);
            }else{
                queryWrapper.eq(Reimbursement::getUserId,0);
            }
        }

        if(!ObjectUtil.isEmpty(time)){
            queryWrapper.like(Reimbursement::getCreatedTime,time);
        }
        //如果有类型的话 类型
//        if (!ObjectUtil.isEmpty(type)) {
//            //传过来的是productCategoryId,需要去产品表下找属于这个产品类型的产品 id数组
//            queryWrapper.eq(Reimbursement::getCategoryId,type);
//        }
        IPage<Reimbursement> recordPage = page(page, queryWrapper);

        List<ReimbursementVo> collect = recordPage.getRecords().stream().map((item) -> {
            ReimbursementVo vo = BeanCopyUtils.copyBean(item, ReimbursementVo.class);

            String content = JSONUtil.toJsonStr(item.getReimbursementContent());
            List<ReimbursementContentVo> contentVoList = JSONUtil.toList(content, ReimbursementContentVo.class);
            vo.setReimbursementContent(contentVoList);
            return vo;
        }).collect(Collectors.toList());

        PageVo<List<ReimbursementVo>> pageList = new PageVo<>();
        List<ReimbursementVo> reimbursementVos = addOrderId(collect, pageNum, pageSize);
        pageList.setData(reimbursementVos);

        pageList.setTotal((int) recordPage.getTotal());

        return ResponseResult.okResult(pageList);
    }

    private List<ReimbursementVo> addOrderId(List<ReimbursementVo> list, Integer pageNum, Integer pageSize){
        if (!ObjectUtil.isEmpty(pageNum) && !ObjectUtil.isEmpty(pageSize)) {
            for (int i = 0 ; i < list.size() ; i++){
                list.get(i).setOrderId((pageNum - 1) * pageSize + i + 1);
            }
        }
        return list;
    }
}




