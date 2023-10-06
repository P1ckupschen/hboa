package com.gdproj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.Client;
import com.gdproj.enums.AppHttpCodeEnum;
import com.gdproj.exception.SystemException;
import com.gdproj.mapper.ClientMapper;
import com.gdproj.service.ClientService;
import com.gdproj.service.DeployeeService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.clientVo;
import com.gdproj.vo.selectVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【sys_client】的数据库操作Service实现
* @createDate 2023-09-26 09:28:44
*/
@Service
public class ClientServiceImpl extends ServiceImpl<ClientMapper, Client>
    implements ClientService {

    @Autowired
    ClientMapper clientMapper;

    @Autowired
    DeployeeService deployeeService;


    @Override
    public IPage<clientVo> getClientList(pageDto pageDto) {

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

        Page<Client> page = new Page<>(pageNum, pageSize);

        LambdaQueryWrapper<Client> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(Client::getIsDeleted, 0);
        //排序
        if (sort.equals("+id")) {
            queryWrapper.orderByAsc(Client::getClientId);
        } else {
            queryWrapper.orderByDesc(Client::getClientId);
        }

        //查询产品名称？
        if (!title.isEmpty()) {
            queryWrapper.eq(Client::getClientId,title);
        }

        //如果有类型的话 客户类型
//        if (!ObjectUtil.isEmpty(type)) {
//            //传过来的是productCategoryId,需要去产品表下找属于这个产品类型的产品 id数组
//            List<Integer> ids = productService.getIdsByCategoryId(type);
//            //querywrapper
//            queryWrapper.in(Product::getProductId, ids);
//        }

        IPage<Client> recordPage = clientMapper.selectPage(page, queryWrapper);

        Page<clientVo> resultPage = new Page<>();

        List<clientVo> resultList = new ArrayList<>();
        try {

            resultList = recordPage.getRecords().stream().map((item) -> {

                clientVo vo = BeanCopyUtils.copyBean(item, clientVo.class);
                //类型名称?

                return vo;
            }).collect(Collectors.toList());
        }catch (Exception e){
            throw new SystemException(AppHttpCodeEnum.MYSQL_FIELD_ERROR);
        }

        resultPage.setRecords(resultList);

        resultPage.setTotal(recordPage.getTotal());

        return resultPage;

    }

    @Override
    public List<selectVo> getListForSelect() {

        List<Client> list = list();

        List<selectVo> collect = list.stream().map((item) -> {
            selectVo vo = new selectVo();
            vo.setId(item.getClientId());
            vo.setName(item.getClientName());
            return vo;
        }).collect(Collectors.toList());
        return collect;

    }
}




