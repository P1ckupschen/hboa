package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.entity.Flow;
import generator.service.FlowService;
import generator.mapper.FlowMapper;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【sys_flow】的数据库操作Service实现
* @createDate 2023-10-18 14:57:44
*/
@Service
public class FlowServiceImpl extends ServiceImpl<FlowMapper, Flow>
    implements FlowService{

}




