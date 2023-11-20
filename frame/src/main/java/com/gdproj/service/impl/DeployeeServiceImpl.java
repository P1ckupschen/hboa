package com.gdproj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Account;
import com.gdproj.entity.Department;
import com.gdproj.entity.Deployee;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.DeployeeMapper;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.AccountService;
import com.gdproj.service.DepartmentService;
import com.gdproj.service.DeployeeService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.utils.RSAUtil;
import com.gdproj.vo.DeployeeVo;
import com.gdproj.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【sys_deployee】的数据库操作Service实现
* @createDate 2023-09-11 14:12:54
*/
@Service
public class DeployeeServiceImpl extends ServiceImpl<DeployeeMapper, Deployee>
    implements DeployeeService {


    @Autowired
    DepartmentService departmentService;

    @Autowired
    AccountService accountService;

    @Override
    public List<Integer> getIdsByTime(String time) {

        return null;

    }

    @Override
    public List<Integer> getIdsByTitle(String title) {

        LambdaQueryWrapper<Deployee> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(Deployee::getDeployeeName,title);

        List<Deployee> list = list(queryWrapper);

        return list.stream().map(item -> item.getDeployeeId()).collect(Collectors.toList());
    }

    @Override
    public List<Integer> getIdsByDepartmentId(Integer type) {

        LambdaQueryWrapper<Deployee> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Deployee::getDepartmentId,type);

        List<Deployee> list = list(queryWrapper);

        return list.stream().map(Deployee::getDeployeeId).collect(Collectors.toList());


    }

    @Override
    public String getNameByUserId(Integer userId) {

        LambdaQueryWrapper<Deployee> lambdaQueryWrapper =new LambdaQueryWrapper<>();

        lambdaQueryWrapper.eq(Deployee::getDeployeeId,userId);

        Deployee one = getOne(lambdaQueryWrapper);

        return one.getDeployeeName();
    }

    public String getDepartmentNameByUserId(Integer userId){

        LambdaQueryWrapper<Deployee> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Deployee::getDeployeeId,userId);

        Deployee one = getOne(queryWrapper);

        Department department = departmentService.getById(one.getDepartmentId());

        return  department.getDepartmentName();
    }

    @Override
    public Integer getDepartmentIdByUserId(Integer userId) {


        LambdaQueryWrapper<Deployee> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Deployee::getDeployeeId,userId);

        Deployee one = getOne(queryWrapper);

        Department department = departmentService.getById(one.getDepartmentId());

        return  department.getDepartmentId();

    }

    @Override
    public List<UserVo> getListForSelect() {

        List<Deployee> list = list();

        return list.stream().map((item) -> {
            // 判断 deployee状态 为1 正常； 为2 则为离职
            UserVo userVo = new UserVo();
            userVo.setUserId(item.getDeployeeId());
            if(item.getDeployeeStatus() == 0){
                userVo.setUsername(item.getDeployeeName() + " [已离职]");
            }else{
                userVo.setUsername(item.getDeployeeName());
            }
            userVo.setDepartmentId(item.getDepartmentId());
            //
            userVo.setDepartment(departmentService.getDepartmentNameByDepartmentId(item.getDepartmentId()));
            //
            return userVo;
        }).collect(Collectors.toList());
    }

    @Override
    public IPage<DeployeeVo> getDeployeeList(PageQueryDto pageDto) {

        //类型
        Integer type = pageDto.getType();
        //部门
        Integer departmentId = pageDto.getDepartmentId();
        //时间
        String time = pageDto.getTime();
        //排序
        String sort = pageDto.getSort();
        //搜索框如果是产品搜索产品名称或者选择产品id
        String title = pageDto.getTitle();
        Integer pageNum = pageDto.getPageNum();
        Integer pageSize = pageDto.getPageSize();

        Page<Deployee> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Deployee> queryWrapper = new LambdaQueryWrapper<>();
        //排序
        if (sort.equals("+id")) {
            queryWrapper.orderByAsc(Deployee::getDeployeeId);
        } else {
            queryWrapper.orderByDesc(Deployee::getDeployeeId);
        }

        //查询名称？
        if (!title.isEmpty()) {
            queryWrapper.eq(Deployee::getDeployeeId,title);
        }
        //查询部门 是否需要找到子部门的所有员工
        if(!ObjectUtil.isNull(departmentId)){
            queryWrapper.eq(Deployee::getDepartmentId,departmentId);
        }

        //如果有类型的话 类型
        if (!ObjectUtil.isEmpty(type)) {
            queryWrapper.eq(Deployee::getDeployeeRole,type);
        }

        IPage<Deployee> recordPage = page(page, queryWrapper);

        Page<DeployeeVo> resultPage = new Page<>();

        List<DeployeeVo> resultList = new ArrayList<>();
        try {

            resultList = recordPage.getRecords().stream().map((item) -> {

                //类型名称?
                return BeanCopyUtils.copyBean(item, DeployeeVo.class);
            }).collect(Collectors.toList());
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }

        resultPage.setRecords(resultList);

        resultPage.setTotal(recordPage.getTotal());

        return resultPage;
    }

    @Override
    public ResponseResult inserDeployee(DeployeeVo vo) {

        Deployee deployee = BeanCopyUtils.copyBean(vo,Deployee.class);
        boolean isSave = save(deployee);
        if(isSave){
            Account account = new Account();
            account.setDeployeeId(deployee.getDeployeeId());
            account.setName(deployee.getDeployeeName());
            account.setPassword(RSAUtil.decrypt(deployee.getPassword()));
            account.setUsername(deployee.getUsername());
            return ResponseResult.okResult(accountService.save(account));
        }else{
            throw new SystemException(AppHttpCodeEnum.INSERT_ERROR);
        }

    }

    @Override
    public ResponseResult updateDeployee(DeployeeVo vo) {

        Deployee deployee = BeanCopyUtils.copyBean(vo,Deployee.class);
        boolean isUpdate = updateById(deployee);
        //如果账号密码不为空
        if(isUpdate && !ObjectUtil.isEmpty(deployee.getPassword()) && !ObjectUtil.isEmpty(deployee.getUsername())){
            LambdaUpdateWrapper<Account> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Account::getDeployeeId,deployee.getDeployeeId())
                    .set(Account::getName,deployee.getDeployeeName())
                    .set(Account::getUsername,deployee.getUsername())
                    .set(Account::getPassword,RSAUtil.decrypt(deployee.getPassword()));
            return ResponseResult.okResult(accountService.update(updateWrapper));
        }else{
            throw new SystemException(AppHttpCodeEnum.UPDATE_ERROR);
        }
    }

    @Override
    public ResponseResult deleteDeployee(Integer id) {

        LambdaUpdateWrapper<Deployee> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Deployee::getDeployeeId,id).set(Deployee::getDeployeeStatus,0);
        return ResponseResult.okResult(update(updateWrapper));

    }

}




