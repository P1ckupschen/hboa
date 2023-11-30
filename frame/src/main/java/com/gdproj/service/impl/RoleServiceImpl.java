package com.gdproj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Role;
import com.gdproj.entity.UserRole;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.mapper.RoleMapper;
import com.gdproj.mapper.UserRoleMapper;
import com.gdproj.result.ResponseResult;
import com.gdproj.service.RoleService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.PageVo;
import com.gdproj.vo.RoleVo;
import com.gdproj.vo.SelectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【sys_role(角色信息表)】的数据库操作Service实现
* @createDate 2023-11-24 14:19:44
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService {

    @Autowired
    UserRoleMapper UserRoleMapper;

    @Autowired
    RoleMapper roleMapper;

    @Override
    public ResponseResult getRoleList(PageQueryDto queryDto) {
        //类型
        Integer type = queryDto.getType();
        //部门
        Integer departmentId = queryDto.getDepartmentId();
        //时间
        String time = queryDto.getTime();
        //排序
        String sort = queryDto.getSort();
        //搜索框如果是产品搜索产品名称或者选择产品id
        //如果是人 搜素人名或者人id
        //如果是物 搜索id
        String title = queryDto.getTitle();
        Integer pageNum = queryDto.getPageNum();
        Integer pageSize = queryDto.getPageSize();

        Page<Role> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<>();
        //排序
        if (sort.equals("+id")) {
            queryWrapper.orderByAsc(Role::getRoleId);
        } else {
            queryWrapper.orderByDesc(Role::getRoleId);
        }

        IPage<Role> recordPage = page(page, queryWrapper);

        List<RoleVo> collect = recordPage.getRecords().stream().map((item) -> {
            RoleVo vo = BeanCopyUtils.copyBean(item, RoleVo.class);

            return vo;
        }).collect(Collectors.toList());

        PageVo<List<RoleVo>> pageList = new PageVo<>();

        pageList.setData(collect);

        pageList.setTotal((int) recordPage.getTotal());

        return ResponseResult.okResult(pageList);
    }

    @Override
    public ResponseResult insertRole(RoleVo vo) {
        Role role = BeanCopyUtils.copyBean(vo, Role.class);
        boolean s = save(role);
        if(s){
            return ResponseResult.okResult(s);
        }else{
            return ResponseResult.errorResult(AppHttpCodeEnum.INSERT_ERROR);
        }
    }

    @Override
    public ResponseResult updateRole(RoleVo vo) {
        Role role = BeanCopyUtils.copyBean(vo, Role.class);
        boolean u = updateById(role);
        if(u){
            return ResponseResult.okResult(u);
        }else{
            return ResponseResult.errorResult(AppHttpCodeEnum.UPDATE_ERROR);
        }
    }

    @Override
    public ResponseResult deleteRole(Integer id) {
        boolean d = removeById(id);
        if(d){
            return ResponseResult.okResult(d);
        }else{
            return ResponseResult.errorResult(AppHttpCodeEnum.DELETE_ERROR);
        }
    }

    @Override
    public List<String> getRoleById(Integer deployeeId) {
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getDeployeeId,deployeeId);
        List<UserRole> userRoles = UserRoleMapper.selectList(queryWrapper);
        System.out.println(userRoles);
        List<Integer> collect = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        LambdaQueryWrapper<Role> queryWrapper1 =new LambdaQueryWrapper<>();
        queryWrapper1.in(Role::getRoleId,collect);
        List<Role> roles = roleMapper.selectList(queryWrapper1);
        return roles.stream().map(Role::getRoleKey).collect(Collectors.toList());
    }

    @Override
    public ResponseResult getRoleListForSelect() {

        List<Role> list = list();
        return ResponseResult.okResult(list.stream().map((item) -> {
            SelectVo vo = new SelectVo();
            vo.setId(item.getRoleId());
            vo.setName(item.getRoleName());
            return vo;
        }).collect(Collectors.toList()));
    }
}




