package com.gdproj.utils;


import com.gdproj.entity.Menu;

import java.util.List;
import java.util.stream.Collectors;

public class analysisTree {

    public static <T> T array_last(List<T> list){
        return list.get(list.size() - 1);
    }

    public static List<Menu> analysis(List<Menu> childrenlist , List objects){
        childrenlist.stream().map(item->{
            if(item.getChildren()!=null){
                item.setChildren(analysis(item.getChildren(),objects));
            }
            objects.add(item);
            return item;
        }).collect(Collectors.toList());
        return objects;
    }

}
