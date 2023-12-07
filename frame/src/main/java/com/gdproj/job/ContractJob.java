package com.gdproj.job;

import com.gdproj.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContractJob {

    @Autowired
    ContractService contractService;

//    @Scheduled(cron = "* * 9 * * 2 ")
////    @Scheduled(cron = "* * 9 * * 2 ")
//    public void checkExpireContract(){
//        //要执行的代码
//
//        List<Contract> list = contractService.list();
//        list.stream().map((item)->{
//            if(!ObjectUtil.isEmpty(item.getWarrantyEndTime())){
////                在这个时间段的前一个月 前一个礼拜 前三天 发送公众号提醒
////                if(com)
//            }
//        }).collect(Collectors.toList())
//        System.out.println("每日检查过期合同");
//    }
}
