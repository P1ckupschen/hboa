package com.gdproj.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdproj.entity.DailyUse;
import com.gdproj.entity.DailyUseRecord;
import com.gdproj.mapper.DailyUseRecordMapper;
import com.gdproj.service.DailyUseRecordService;
import com.gdproj.service.DailyUseService;
import com.gdproj.utils.BeanCopyUtils;
import com.gdproj.vo.DailyUseContentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author Administrator
* @description 针对表【sys_dailyuse_record】的数据库操作Service实现
* @createDate 2023-10-31 13:35:04
*/
@Service
public class DailyUseRecordServiceImpl extends ServiceImpl<DailyUseRecordMapper, DailyUseRecord>
    implements DailyUseRecordService {

    @Autowired
    DailyUseService dailyUseService;

    @Override
    public Integer getCountByToolId(Integer toolId) {

        LambdaQueryWrapper<DailyUseRecord> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(DailyUseRecord::getToolId, toolId);

        List<DailyUseRecord> list = list(queryWrapper);
        int count = 0;
        for ( DailyUseRecord item : list) {
            if(item.getCategoryId() == 1){
                count += item.getCount();
            } else if (item.getCategoryId() == 2) {
                count -= item.getCount();
            } else if (count < 0) {
                count = 0;
                break;
            }
        }
        return count;
    }

    @Override
    public boolean insertRecordByDailyUseId(Integer runId) {
        DailyUse dailyUse = dailyUseService.getById(runId);
        List<DailyUseRecord> records = transferDailyUseContentToRecord(dailyUse);
        return saveBatch(records);
    }
    @Override
    public List<DailyUseRecord> transferDailyUseContentToRecord(DailyUse dailyUse) {

        // map 转json  再转object
        String Content = JSONUtil.toJsonStr(dailyUse.getDailyuseContent());
        List<DailyUseContentVo> contentVoList = JSONUtil.toList(Content, DailyUseContentVo.class);
        List<DailyUseRecord> collect = contentVoList.stream().map((item) -> {
            // 所以List 需要一个实体类声明 不能用泛型  TODO contentVo里有个id属性
            DailyUseRecord record = BeanCopyUtils.copyBean(item, DailyUseRecord.class);
            record.setDailyuseId(dailyUse.getDailyuseId());
            record.setUserId(dailyUse.getUserId());
            record.setCategoryId(dailyUse.getCategoryId());
            return record;
        }).collect(Collectors.toList());
        return collect;
    }
}



