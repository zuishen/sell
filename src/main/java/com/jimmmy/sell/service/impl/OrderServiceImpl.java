package com.jimmmy.sell.service.impl;

import com.jimmmy.sell.converter.OrderMaster2OrderDTOConverter;
import com.jimmmy.sell.domain.OrderDetail;
import com.jimmmy.sell.domain.OrderMaster;
import com.jimmmy.sell.domain.ProductInfo;
import com.jimmmy.sell.dto.CartDTO;
import com.jimmmy.sell.dto.OrderDTO;
import com.jimmmy.sell.enums.OrderStatusEnum;
import com.jimmmy.sell.enums.PayStatusEnum;
import com.jimmmy.sell.enums.ResultEnum;
import com.jimmmy.sell.exception.SellException;
import com.jimmmy.sell.repository.OrderDetailRepository;
import com.jimmmy.sell.repository.OrderMasterRepositoy;
import com.jimmmy.sell.service.OrderService;
import com.jimmmy.sell.service.ProductService;
import com.jimmmy.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService{

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepositoy orderMasterRepositoy;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        BigDecimal orderAmount = new BigDecimal(0);
        // 1. 查询商品 价格
        String orderId = KeyUtil.genUniqueKey();
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo  = productService.findOne(orderDetail.getProductId());
            if (productInfo == null) {
                throw  new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            // 2. 计算订单总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);
            // 订单详情入库
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            orderDetailRepository.save(orderDetail);
        }
        // 3. 写入订单数据库 (orderMaster, orderDetail)
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMasterRepositoy.save(orderMaster);

        // 4. 扣库存
        //List<CartDTO> cartDTOList = new ArrayList<>();

        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(), e.getProductQuantity())
        ).collect(Collectors.toList());

        productService.decreaseStock(cartDTOList);
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {

        OrderMaster orderMaster = orderMasterRepositoy.findOne(orderId);
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepositoy.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        Page<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        // 0. OrderDTO -> OrderMaster
        OrderMaster orderMaster = new OrderMaster();

        // 1. 判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("[取消订单] 订单状态不正确， orderId={}, orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        // 2. 修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster updateResult = orderMasterRepositoy.save(orderMaster);
        if (updateResult == null) {
            log.error("[取消订单] 更新失败，order");
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        // 3. 返回库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("[取消订单] 订单中无商品详情，orderDTO={}", orderDTO);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                    new CartDTO(e.getProductId(), e.getProductQuantity())
                ).collect(Collectors.toList());
        productService.increaseStock(cartDTOList);

        // 4. 如果已支付，需要退款
        if (orderMaster.getPayStatus().equals(PayStatusEnum.PAID.getCode())) {
            //TODO
        }
        return orderDTO;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
//        //判断订单状态
//        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
//            log.error("[完结订单] 订单状态不正确，orderId={}, orderSTATUS={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
//            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
//        }
//        //修改订单状态
//        orderDTO.setOrderStatus(OrderStatusEnum.FINISH.getCode());
        return null;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        return null;
    }
}
