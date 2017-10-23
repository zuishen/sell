package com.jimmmy.sell.service;

import com.jimmmy.sell.domain.ProductInfo;
import com.jimmmy.sell.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductInfo findOne(String productId);

    /* 查询在售的所有商品 */
    List<ProductInfo> findUpAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    /** 加库存 */
    void increaseStock(List<CartDTO> cartDTOList);

    /** 减库存 */
    void decreaseStock(List<CartDTO> cartDTOList);
}
