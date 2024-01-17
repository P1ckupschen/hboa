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
import com.gdproj.entity.UserRole;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.DeployeeMapper;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.AccountService;
import com.gdproj.service.DepartmentService;
import com.gdproj.service.DeployeeService;
import com.gdproj.service.UserRoleService;
import com.gdproj.utils.AesUtil;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.utils.RSAUtil;
import com.gdproj.vo.DeployeeVo;
import com.gdproj.vo.RoleSetVo;
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

    @Autowired
    UserRoleService userRoleService;

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
        //已离职的筛选掉
//        queryWrapper.eq(Deployee::getDeployeeStatus,1);

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
            queryWrapper.eq(Deployee::getDeployeeStatus,type);
        }

        IPage<Deployee> recordPage = page(page, queryWrapper);

        Page<DeployeeVo> resultPage = new Page<>();

        List<DeployeeVo> resultList = new ArrayList<>();
        try {

            resultList = recordPage.getRecords().stream().map((item) -> {
                //手机号解密
                if(!ObjectUtil.isEmpty(item.getDeployeePhone())){
                    item.setDeployeePhone(AesUtil.decrypt(item.getDeployeePhone(),AesUtil.key128));
                }
                if(item.getDeployeeStatus() == 0){
                    item.setDeployeeName(item.getDeployeeName() + " 已离职") ;
                }
                //role
                List<Integer> ids = userRoleService.getRoleIdsById(item.getDeployeeId());
                item.setDeployeeRole(ids);
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
        if(!ObjectUtil.isEmpty(deployee.getDeployeePhone())){
            deployee.setDeployeePhone(AesUtil.encrypt(deployee.getDeployeePhone(),AesUtil.key128));
        }
        boolean isSave = save(deployee);

        //insert默认员工权限为普通员工 6
        UserRole userRole = new UserRole();
        userRole.setDeployeeId(deployee.getDeployeeId());
        userRole.setRoleId(6);
        userRoleService.save(userRole);
        if(isSave){
            //TODO 用户名不能重复如果有账号密码就新建account
            Account account = new Account();
            account.setDeployeeId(deployee.getDeployeeId());
            account.setName(deployee.getDeployeeName());
            account.setUsername(deployee.getDeployeeName());
            account.setPassword(RSAUtil.DEFAULT_PASSWORD);
            return ResponseResult.okResult(accountService.save(account));
        }else{
            //如果为空 默认账号名
            Account account = new Account();
            account.setDeployeeId(deployee.getDeployeeId());
            account.setName(deployee.getDeployeeName());
            account.setUsername(deployee.getDeployeeName());
            account.setPassword(RSAUtil.DEFAULT_PASSWORD);
            return ResponseResult.okResult(accountService.save(account));
        }
//        if(isSave && !ObjectUtil.isEmpty(deployee.getUsername())){
//            //TODO 用户名不能重复如果有账号密码就新建account
//                Account account = new Account();
//                account.setDeployeeId(deployee.getDeployeeId());
//                account.setName(deployee.getDeployeeName());
//                account.setUsername(deployee.getUsername());
//                account.setPassword(RSAUtil.DEFAULT_PASSWORD);
//                return ResponseResult.okResult(accountService.save(account));
//        }else{
//            //如果为空 默认账号名
//            Account account = new Account();
//            account.setDeployeeId(deployee.getDeployeeId());
//            account.setName(deployee.getDeployeeName());
//            account.setUsername(deployee.getDeployeeName());
//            account.setPassword(RSAUtil.DEFAULT_PASSWORD);
//            return ResponseResult.okResult(accountService.save(account));
//        }

    }

    @Override
    public ResponseResult updateDeployee(DeployeeVo vo) {

        Deployee deployee = BeanCopyUtils.copyBean(vo,Deployee.class);
        if(!ObjectUtil.isEmpty(deployee.getDeployeePhone())){
            deployee.setDeployeePhone(AesUtil.encrypt(deployee.getDeployeePhone(),AesUtil.key128));
        }
        boolean isUpdate = updateById(deployee);
        //如果账号密码不为空
        if(isUpdate){
            //如果密码修改了就更新account
            LambdaUpdateWrapper<Account> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(Account::getDeployeeId,deployee.getDeployeeId())
                    .set(Account::getName,deployee.getDeployeeName())
                    .set(Account::getUsername,deployee.getDeployeeName());
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

    @Override
    public ResponseResult setDeployeeRole(RoleSetVo vo) {
        Integer currentId = vo.getDeployeeId();
        List<Integer> roleList = vo.getList();
        List<UserRole> batch = new ArrayList<>();
        //找到user-role表所有对应的deployeeid 先删除
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getDeployeeId,currentId);
        List<UserRole> list = userRoleService.list(queryWrapper);
        boolean remove = false;
        if(!ObjectUtil.isEmpty(list)){
            remove = userRoleService.remove(queryWrapper);
        }else{
            remove = true;
        }
        //如果删除成功 就把所有role信息保存
        if(remove){
            if(!ObjectUtil.isEmpty(roleList)){
                roleList.stream().forEach((item)->{
                    UserRole ur = new UserRole();
                    ur.setRoleId(item);
                    ur.setDeployeeId(currentId);
                    batch.add(ur);
                });
            }else{
                throw new SystemException(AppHttpCodeEnum.ROLE_NULL);
            }
        }else{
            throw new SystemException(AppHttpCodeEnum.DELETE_ERROR);
        }
        //再保存
        boolean b = userRoleService.saveBatch(batch);
        if(b){
            return ResponseResult.okResult(b);
        }else{
            return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
        }
    }
}




