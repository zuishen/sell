package com.jimmmy.sell.service;

import com.jimmmy.sell.domain.OrderMaster;
import com.jimmmy.sell.dto.CartDTO;
import com.jimmmy.sell.dto.OrderDTO;
import com.sun.webkit.PageCache;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface OrderService {
    /** 创建订单 */
    OrderDTO create(OrderDTO orderDTO);

    /** 查询单个订单 */
    OrderDTO findOne(String orderId);

    /** 查询订单列表 */
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

    /** 取消订单 */
    OrderDTO cancel(OrderDTO orderDTO);

    /** 完结订单 */
    OrderDTO finish(OrderDTO orderDTO);

    /** 支付订单 */
    OrderDTO paid(OrderDTO orderDTO);

    /** 查询所有订单 */
    Page<OrderDTO> findList(Pageable pageable);

}
