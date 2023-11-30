package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.entity.Menu;
import generator.service.MenuService;
import generator.mapper.MenuMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【sys_menu(菜单权限表)】的数据库操作Service实现
* @createDate 2023-11-24 14:04:07
*/
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu>
    implements MenuService{

}




