package com.gdproj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gdproj.dto.PageQueryDto;
import com.gdproj.entity.Purchase;
import com.gdproj.result.ResponseResult;
import com.gdproj.vo.PurchaseVo;

import javax.servlet.http.HttpServletRequest;

/**
* @author Administrator
* @description 针对表【sys_purchase】的数据库操作Service
* @createDate 2023-11-07 09:36:09
*/
public interface PurchaseService extends IService<Purchase> {

    ResponseResult getPurchaseList(PageQueryDto queryVo);

    boolean insertPurchase(Purchase purchase);

    ResponseResult deletePurchase(Integer id);

    ResponseResult updatePurchase(PurchaseVo vo);

    ResponseResult getMyPurchaseList(PageQueryDto queryVo, HttpServletRequest request);
}
