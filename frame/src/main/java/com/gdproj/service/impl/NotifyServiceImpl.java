package com.gdproj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Notify;
import com.gdproj.entity.NotifyCategory;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.NotifyMapper;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.DepartmentService;
import com.gdproj.service.DeployeeService;
import com.gdproj.service.NotifyService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.NotifyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【sys_notify】的数据库操作Service实现
 * @createDate 2023-09-15 15:27:55
 */
@Service
public class NotifyServiceImpl extends ServiceImpl<NotifyMapper, Notify>
        implements NotifyService {

    @Autowired
    DeployeeService deployeeService;

    @Autowired
    NotifyMapper notifyMapper;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    notifyCategoryServiceImpl notifyCategoryService;


    @Override
    public IPage<NotifyVo> getNotifyList(PageQueryDto pageDto) {

        Integer type = pageDto.getType();
        Integer departmentId = pageDto.getDepartmentId();
        String time = pageDto.getTime();
        String sort = pageDto.getSort();
        String title = pageDto.getTitle();
        Integer pageNum = pageDto.getPageNum();
        Integer pageSize = pageDto.getPageSize();
        Integer status = pageDto.getStatus();

        Page<Notify> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Notify> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Notify::getIsDeleted, 0);
        //排序
        if (sort.equals("+id")) {
            queryWrapper.orderByAsc(Notify::getNotifyId);
        } else {
            queryWrapper.orderByDesc(Notify::getNotifyId);
        }
        //如果根据部门分类，有一定几率会与模糊人民冲突
        if (!Objects.isNull(departmentId) && title.isEmpty()) {
            //如果没有对象没有部门id属性就找到对应id的部门所以的员工的userid
            List<Integer> userIds = deployeeService.getIdsByDepartmentId(departmentId);
            if (ObjectUtil.isEmpty(userIds)) {
                queryWrapper.in(Notify::getUserId, 0);
            } else {
                queryWrapper.in(Notify::getUserId, userIds);
            }
        }
        if(!ObjectUtil.isEmpty(status)){
            queryWrapper.eq(Notify::getNotifyStatus,status);
        }
        //设置时间 年 月 日
        //模糊查询时间
        if (time != null) {
            queryWrapper.like(Notify::getCreatedTime, time);
        }

        //模糊查询人名
        if (!title.isEmpty()) {
            //如果有模糊查询的时间 先通过查title 的用户ids
            queryWrapper.like(Notify::getNotifyTitle, title);
            //通过ids去找所有符合ids的对象 sign;
        }

        if (!ObjectUtil.isEmpty(type)) {
            queryWrapper.eq(Notify::getCategoryId, type);
        }

        IPage<Notify> notifyPage = notifyMapper.selectPage(page, queryWrapper);

        Page<NotifyVo> resultPage = new Page<>();
        //结果里的部门 和用户都返回成string；
        List<NotifyVo> resultList = new ArrayList<>();
        try {
            resultList = notifyPage.getRecords().stream().map((item) -> {

                NotifyVo notifyVo = BeanCopyUtils.copyBean(item, NotifyVo.class);
                //人员
                if (!ObjectUtil.isEmpty(item.getUserId())) {
                    notifyVo.setUsername(deployeeService.getNameByUserId(item.getUserId()));
                    notifyVo.setDepartment(deployeeService.getDepartmentNameByUserId(item.getUserId()));
                    notifyVo.setDepartmentId(deployeeService.getDepartmentIdByUserId(item.getUserId()));
                } else {
                    notifyVo.setUsername("");
                    notifyVo.setDepartment("");
                }
                if (!ObjectUtil.isEmpty(item.getCategoryId())) {
                    NotifyCategory one = notifyCategoryService.getById(item.getCategoryId());
                    if(!ObjectUtil.isEmpty(one)){
                        notifyVo.setCategory(one.getCategoryName());
                    }
                } else {
                    notifyVo.setCategory("");
                }

//                //公告审核人
//                if (!ObjectUtil.isEmpty(item.getExaminerId())) {
//                    notifyVo.setExaminerName(deployeeService.getNameByUserId(item.getExaminerId()));
//                } else {
//                    notifyVo.setExaminerName("");
//                }
                return notifyVo;

            }).collect(Collectors.toList());
        } catch (Exception e) {
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }
        List<NotifyVo> notifyVos = addOrderId(resultList, pageNum, pageSize);
        resultPage.setRecords(notifyVos);

        resultPage.setTotal(notifyPage.getTotal());

        return resultPage;
    }

    @Override
    public ResponseResult updateReadListById(Integer notifyid, Integer userId) {

        // 接收当前用户id 和 点击查看的公告id
        // 找出对应的用户姓名
        String nameByUserId = deployeeService.getNameByUserId(userId);
        LambdaQueryWrapper<Notify> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Notify::getNotifyId, notifyid);
        Notify one = getOne(queryWrapper);
        List<String> readList = one.getReadList();

        if (!ObjectUtil.isEmpty(readList)) {
            //如果不为空 并且当前用户未阅读过当前公告 则添加当前用户进入已读列表
            if (!readList.contains(nameByUserId)) {
                readList.add(nameByUserId);
                one.setReadList(readList);
            }
        }else{
            readList = new ArrayList<>();
            readList.add(nameByUserId);
            one.setReadList(readList);
        }
        //然后更新回去
        boolean update = updateById(one);
        if (update) {
            return ResponseResult.okResult();
        } else {
            return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
        }
    }

    @Override
    public IPage<NotifyVo> getEffectiveNotifyList(PageQueryDto pageDto) {
        Integer type = pageDto.getType();
        Integer departmentId = pageDto.getDepartmentId();
        String time = pageDto.getTime();
        String sort = pageDto.getSort();
        String title = pageDto.getTitle();
        Integer pageNum = pageDto.getPageNum();
        Integer pageSize = pageDto.getPageSize();
        Integer status = pageDto.getStatus();

        Page<Notify> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Notify> queryWrapper = new LambdaQueryWrapper<>();

        //排序
        if (sort.equals("+id")) {
            queryWrapper.orderByAsc(Notify::getNotifyId);
        } else {
            queryWrapper.orderByDesc(Notify::getNotifyId);
        }
        //如果根据部门分类，有一定几率会与模糊人民冲突
        if (!Objects.isNull(departmentId) && title.isEmpty()) {
            //如果没有对象没有部门id属性就找到对应id的部门所以的员工的userid
            List<Integer> userIds = deployeeService.getIdsByDepartmentId(departmentId);
            if (ObjectUtil.isEmpty(userIds)) {
                queryWrapper.in(Notify::getUserId, 0);
            } else {
                queryWrapper.in(Notify::getUserId, userIds);
            }
        }
        if(!ObjectUtil.isEmpty(status)){
            queryWrapper.eq(Notify::getNotifyStatus,status);
        }
        //设置时间 年 月 日
        //模糊查询时间
        if (time != null) {
            queryWrapper.like(Notify::getCreatedTime, time);
        }
        queryWrapper.eq(Notify::getNotifyStatus,1);
        //模糊查询人名
        if (!title.isEmpty()) {
            //如果有模糊查询的时间 先通过查title 的用户ids
            List<Integer> ids = deployeeService.getIdsByTitle(title);
            if (!ObjectUtil.isEmpty(ids)) {
                queryWrapper.in(Notify::getUserId, ids);
            } else {
                queryWrapper.in(Notify::getUserId, 0);
            }
            //通过ids去找所有符合ids的对象 sign;
        }

        if (!ObjectUtil.isEmpty(type)) {
            queryWrapper.eq(Notify::getCategoryId, type);
        }

        IPage<Notify> notifyPage = notifyMapper.selectPage(page, queryWrapper);

        Page<NotifyVo> resultPage = new Page<>();
        //结果里的部门 和用户都返回成string；
        List<NotifyVo> resultList = new ArrayList<>();
        try {
            resultList = notifyPage.getRecords().stream().map((item) -> {

                NotifyVo notifyVo = BeanCopyUtils.copyBean(item, NotifyVo.class);
                //人员
                if (!ObjectUtil.isEmpty(item.getUserId())) {
                    notifyVo.setUsername(deployeeService.getNameByUserId(item.getUserId()));
                    notifyVo.setDepartment(deployeeService.getDepartmentNameByUserId(item.getUserId()));
                    notifyVo.setDepartmentId(deployeeService.getDepartmentIdByUserId(item.getUserId()));
                } else {
                    notifyVo.setUsername("");
                    notifyVo.setDepartment("");
                }
                if (!ObjectUtil.isEmpty(item.getCategoryId())) {
                    NotifyCategory one = notifyCategoryService.getById(item.getCategoryId());
                    if(!ObjectUtil.isEmpty(one)){
                        notifyVo.setCategory(one.getCategoryName());
                    }
                } else {
                    notifyVo.setCategory("");
                }

//                //公告审核人
//                if (!ObjectUtil.isEmpty(item.getExaminerId())) {
//                    notifyVo.setExaminerName(deployeeService.getNameByUserId(item.getExaminerId()));
//                } else {
//                    notifyVo.setExaminerName("");
//                }
                return notifyVo;

            }).collect(Collectors.toList());
        } catch (Exception e) {
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }
        List<NotifyVo> notifyVos = addOrderId(resultList, pageNum, pageSize);
        resultPage.setRecords(notifyVos);

        resultPage.setTotal(notifyPage.getTotal());

        return resultPage;
    }

    private List<NotifyVo> addOrderId(List<NotifyVo> list, Integer pageNum, Integer pageSize){
        if (!ObjectUtil.isEmpty(pageNum) && !ObjectUtil.isEmpty(pageSize)) {
            for (int i = 0 ; i < list.size() ; i++){
                list.get(i).setOrderId((pageNum - 1) * pageSize + i + 1);
            }
        }
        return list;
    }

}




