package com.gdproj.job;

import cn.hutool.core.util.ObjectUtil;
import com.gdproj.controller.wxSubscribeController;
import com.gdproj.entity.Contract;
import com.gdproj.service.AccountService;
import com.gdproj.service.ContractService;
import com.gdproj.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class ContractJob {

    @Autowired
    ContractService contractService;
    @Autowired
    RoleService roleService;
    @Autowired
    AccountService accountService;
    @Autowired
    wxSubscribeController subscribeController;
//    @Scheduled(cron = "*/10 * * * * ?")
    @Scheduled(cron = "0 0 8 * * ?")
    public void checkExpireContract(){
        //要执行的代码
        List<Contract> list = contractService.list();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<String> general = roleService.getListByRoleKey("general-manager");
        List<String> deputy = roleService.getListByRoleKey("deputy-manager");
        list.stream().filter((item)-> !ObjectUtil.isEmpty(item.getWarrantyEndTime())).map((item)->{
            long gapMills = item.getWarrantyEndTime().getTime() - System.currentTimeMillis();
            double gapDays = gapMills / 1000 / 60 / 60 / 24.0;
            String openId = accountService.getAccountById(item.getCreatedUser()).getOpenId();
            if(Math.round(gapDays) == 30 ){
                //刚好30天发送
                subscribeController.sendExpireMessage(item.getContractTitle(),sdf.format(item.getWarrantyEndTime()),"合同即将在30天内过期",openId);
                for(String openid : general){
                    subscribeController.sendExpireMessage(item.getContractTitle(),sdf.format(item.getWarrantyEndTime()),"合同即将在30天内过期",openid);
                }
                for(String openid : deputy){
                    subscribeController.sendExpireMessage(item.getContractTitle(),sdf.format(item.getWarrantyEndTime()),"合同即将在30天内过期",openid);
                }
            } else if (Math.round(gapDays) == 7 ) {
                //刚好7天发送
                subscribeController.sendExpireMessage(item.getContractTitle(),sdf.format(item.getWarrantyEndTime()),"合同即将在7天内过期",openId);
                for(String openid : general){
                    subscribeController.sendExpireMessage(item.getContractTitle(),sdf.format(item.getWarrantyEndTime()),"合同即将在7天内过期",openid);
                }
                for(String openid : deputy){
                    subscribeController.sendExpireMessage(item.getContractTitle(),sdf.format(item.getWarrantyEndTime()),"合同即将在7天内过期",openid);
                }
            } else if (Math.round(gapDays) <= 3 ) {
                //小于3天每天发送
                subscribeController.sendExpireMessage(item.getContractTitle(),sdf.format(item.getWarrantyEndTime()),"合同即将在3天内过期",openId);
                for(String openid : general){
                    subscribeController.sendExpireMessage(item.getContractTitle(),sdf.format(item.getWarrantyEndTime()),"合同即将在3天内过期",openid);
                }
                for(String openid : deputy){
                    subscribeController.sendExpireMessage(item.getContractTitle(),sdf.format(item.getWarrantyEndTime()),"合同即将在3天内过期",openid);
                }
            }
            return item;
//                在这个时间段的前一个月 前一个礼拜 前三天 发送公众号提醒
        });
        System.out.println("每日检查过期合同");
    }

}
