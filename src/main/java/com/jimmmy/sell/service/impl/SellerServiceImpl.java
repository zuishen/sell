package com.jimmmy.sell.service.impl;

import com.jimmmy.sell.domain.SellerInfo;
import com.jimmmy.sell.repository.SellerInfoRepository;
import com.jimmmy.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl  implements SellerService{

    @Autowired
    private SellerInfoRepository repository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        SellerInfo sellerInfo = repository.findByOpenid(openid);

        return sellerInfo;
    }
}
