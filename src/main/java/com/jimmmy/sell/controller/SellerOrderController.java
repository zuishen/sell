package com.jimmmy.sell.controller;

import com.jimmmy.sell.dto.OrderDTO;
import com.jimmmy.sell.enums.ResultEnum;
import com.jimmmy.sell.exception.SellException;
import com.jimmmy.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Map;

@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        PageRequest request = new PageRequest(page - 1, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(request);
        map.put("orderDTOPage", orderDTOPage);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("order/list", map);
    }

    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId,
                               Map<String, Object> map) {
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.cancel(orderDTO);
        } catch (SellException e) {
            log.error("[卖家端取消订单] 发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/seller/order/list");
            return  new ModelAndView("common/error", map);
        }
        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMessage());
        map.put("url", "/seller/order/list");
        //
        return new ModelAndView("common/success", map);
    }

    // 订单详情
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId,
                               Map<String, Object> map) {
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            map.put("orderDTO", orderDTO);
        } catch (SellException e) {
            log.error("[卖家端取消订单] 发生异常{}", e);
            map.put("msg", e.getMessage());
            map.put("url", "/seller/order/list");
            return  new ModelAndView("common/error", map);
        }



        return new ModelAndView("order/detail", map);
    }
}
