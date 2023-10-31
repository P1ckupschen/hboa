package com.gdproj.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.dto.pageDto;
import com.gdproj.entity.DailyUse;
import com.gdproj.mapper.DailyUseMapper;
import com.gdproj.service.DailyUseService;
import com.gdproj.vo.DailyUseVo;
import org.springframework.stereotype.Service;

/**
* @author Administrator
* @description 针对表【sys_dailyuse】的数据库操作Service实现
* @createDate 2023-10-31 09:27:07
*/
@Service
public class DailyUseServiceImpl extends ServiceImpl<DailyUseMapper, DailyUse>
    implements DailyUseService {

    @Override
    public IPage<DailyUseVo> getDailyUseList(pageDto pageDto) {
        return null;
    }

    @Override
    public boolean insertDailyUse(DailyUse dailyUse) {
        return false;
    }
}




