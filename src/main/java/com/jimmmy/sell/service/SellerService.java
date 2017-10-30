package com.jimmmy.sell.service;

import com.jimmmy.sell.domain.SellerInfo;

// 卖家端
public interface SellerService {

    SellerInfo findSellerInfoByOpenid(String openid);
}
