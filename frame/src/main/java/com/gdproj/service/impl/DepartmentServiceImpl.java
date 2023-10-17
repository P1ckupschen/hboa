package com.gdproj.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Department;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.DepartmentMapper;
import com.gdproj.service.DepartmentService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.departmentVo;
import com.gdproj.vo.selectVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【sys_department】的数据库操作Service实现
* @createDate 2023-09-14 11:01:16
*/
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department>
    implements DepartmentService {

    @Override
    public String getDepartmentNameByDepartmentId(Integer departmentId) {

        LambdaQueryWrapper<Department> lambdaQueryWrapper =new LambdaQueryWrapper<>();

        lambdaQueryWrapper.eq(Department::getDepartmentId,departmentId);

        Department one = getOne(lambdaQueryWrapper);

        if (ObjectUtil.isEmpty(one)){
            throw new SystemException(AppHttpCodeEnum.DEPARTMENT_NULL);
        }else{
            return one.getDepartmentName();
        }

    }

    /**
     * 树形结构
     *
     * */
    @Override
    public List<selectVo> getListForSelect() {
        List<Department> list = list();

        List<selectVo> collect = list.stream().map((item) -> {
            selectVo vo = new selectVo();
            vo.setId(item.getDepartmentId());
            vo.setName(item.getDepartmentName());
            vo.setParentId(item.getParentId());
            return vo;
        }).collect(Collectors.toList());
        List<selectVo> selectVos = builderTreeForSelect(collect, 0);
        return selectVos;
    }

    @Override
    public IPage<departmentVo> getDepartmentList(pageDto pageDto) {
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

        Page<Department> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Department> queryWrapper = new LambdaQueryWrapper<>();
        //排序
        if (sort.equals("+id")) {
            queryWrapper.orderByAsc(Department::getDepartmentId);
        } else {
            queryWrapper.orderByDesc(Department::getDepartmentId);
        }

        //查询名称？
//        if (!title.isEmpty()) {
//            queryWrapper.eq(Department::getDepartmentId,title);
//        }

        //如果有类型的话 类型
//        if (!ObjectUtil.isEmpty(type)) {
//            //传过来的是productCategoryId,需要去产品表下找属于这个产品类型的产品 id数组
//            queryWrapper.eq(Epiboly::getCategoryId,type);
//        }
        IPage<Department> recordPage = page(page, queryWrapper);

        Page<departmentVo> resultPage = new Page<>();

        List<departmentVo> resultList = new ArrayList<>();
        try {

            resultList = recordPage.getRecords().stream().map((item) -> {

                departmentVo vo = BeanCopyUtils.copyBean(item, departmentVo.class);

                return vo;
            }).collect(Collectors.toList());

            List<departmentVo> result = builderTree(resultList, 0);

            resultPage.setRecords(result);

            resultPage.setTotal(recordPage.getTotal());

            return resultPage;
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }
    }





    /**
     * 树形结构
     *    //方法重写有问题？
     * */
    public static List<departmentVo> builderTree(List<departmentVo> list, Integer parentId) {
        List<departmentVo> Tree = list.stream()
                .filter(item -> item.getParentId().equals(parentId))
                .map(item -> item.setChildren(getChildren(item, list)))
                .collect(Collectors.toList());
        return Tree;
    }

    public static List<departmentVo> getChildren(departmentVo vo, List<departmentVo> list) {
        List<departmentVo> childrenList = list.stream()
                .filter(m -> m.getParentId().equals(vo.getDepartmentId()))
                .map(m->m.setChildren(getChildren(m,list)))
                .collect(Collectors.toList());
        return childrenList;
    }


    public static List<selectVo> builderTreeForSelect(List<selectVo> list, Integer parentId) {
        List<selectVo> Tree = list.stream()
                .filter(item -> item.getParentId().equals(parentId))
                .map(item -> item.setChildren(getChildren(item, list)))
                .collect(Collectors.toList());
        return Tree;
    }

    public static List<selectVo> getChildren(selectVo vo, List<selectVo> list) {
        List<selectVo> childrenList = list.stream()
                .filter(m -> m.getParentId().equals(vo.getId()))
                .map(m->m.setChildren(getChildren(m,list)))
                .collect(Collectors.toList());
        return childrenList;
    }


}




