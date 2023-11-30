package com.gdproj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.entity.Menu;
import com.gdproj.mapper.MenuMapper;
import com.gdproj.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【sys_menu】的数据库操作Service实现
* @createDate 2023-09-11 14:04:31
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService {

    @Autowired
    MenuMapper menuMapper;

    @Override
    public Menu getPermissionsByDeployeeId(Integer deployeeId) {

        //传过来 员工id 先去 user-role表 找对应的role
        //再根据role_id 去 role—menu 表 找到对应的menuIds
        //根据 menuIds 去 menu 表 找到对应的树状结构 permissions
//        List<String> result = menuMapper.getMenuPermissionsByDeployeeId(deployeeId);
        System.out.println("result");
        return null;
    }

}




