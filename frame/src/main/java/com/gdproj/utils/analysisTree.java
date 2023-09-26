package com.gdproj.utils;


import cn.hutool.core.lang.tree.TreeUtil;
import cn.hutool.core.util.ObjectUtil;
import com.gdproj.entity.Menu;
import com.gdproj.entity.productCategory;
import com.gdproj.vo.categoryVo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class analysisTree {

    public static List<categoryVo> builderTree(List<categoryVo> list, Integer parentId) {
        List<categoryVo> Tree = list.stream()
                .filter(item -> item.getParentId().equals(parentId))
                //set方法报错是因为set没有返回值而map需要返回值
                //1.类上添加链式编程注解@Accessors(chain = true)
                //2.手动返回
                .map(item -> item.setChildren(getChildren(item, list)))
                .collect(Collectors.toList());
        return Tree;
    }

    /**
     * 获取存入参数的
     * @param
     * @param
     * @return
     */
    public static List<categoryVo> getChildren(categoryVo vo, List<categoryVo> list) {
        List<categoryVo> childrenList = list.stream()
                .filter(m -> m.getParentId().equals(vo.getCategoryId()))
                .map(m->m.setChildren(getChildren(m,list)))
                .collect(Collectors.toList());
        return childrenList;
    }

}
