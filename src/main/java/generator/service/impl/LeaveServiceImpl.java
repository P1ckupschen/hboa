package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.entity.Leave;
import generator.service.LeaveService;
import generator.mapper.LeaveMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【sys_leave】的数据库操作Service实现
* @createDate 2023-09-14 16:33:32
*/
@Service
public class LeaveServiceImpl extends ServiceImpl<LeaveMapper, Leave>
    implements LeaveService{

}




