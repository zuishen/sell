package com.jimmmy.sell.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
public class OrderDetail {

    @Id
    private String detailId;

    /* 订单ID */
    private String orderId;

    /* 商品ID */
    private String productId;

    /* 商品 */
    private String productName;

    /* 商品单价 */
    private BigDecimal productPrice;

    /* 商品数量 */
    private Integer productQuantity;

    /* 商品小图 */
    private String productIcon;
}
