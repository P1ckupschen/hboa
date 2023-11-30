package com.gdproj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gdproj.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Administrator
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Mapper
* @createDate 2023-11-24 14:04:07
* @Entity generator.entity.Menu
*/
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

//    @Select("select DISTINCT m.perms from `sys_user_role` ur " +
//            "left join `sys_role_menu` rm ON ur.`role_id` = rm.`role_id`" +
//            "left join `sys_menu` m on m.`menu_id` = rm.`menu_id`" +
//            "where ur.`deployee_id` = #{deployeeId} AND " +
//            "m.`menu_type` IN ('M','C') AND" +
//            "m.`status` = 0 ")
//    List<String> getMenuPermissionsByDeployeeId(Integer deployeeId);
}




