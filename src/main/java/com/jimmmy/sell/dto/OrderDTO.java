package com.jimmmy.sell.dto;

import com.jimmmy.sell.domain.OrderDetail;
import com.jimmmy.sell.enums.OrderStatusEnum;
import com.jimmmy.sell.enums.PayStatusEnum;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {
    /* 订单ID */
    private  String orderId;

    /* 买家名字 */
    private String buyerName;

    /* 买家手机号 */
    private String buyerPhone;

    /* 买家地址 */
    private String buyerAddress;

    /* 买家OPENID */
    private String buyerOpenid;

    /* 订单金额 */
    private BigDecimal orderAmount;

    /* 订单状态 */
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /* 支付状态 */
    private Integer payStatus = PayStatusEnum.UNPAY.getCode();

    /* 创建时间 */
    private Date createTime;

    /* 更新时间 */
    private Date updateTime;

    List<OrderDetail> orderDetailList;
}
