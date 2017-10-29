package com.jimmmy.sell.controller;

import com.jimmmy.sell.dto.OrderDTO;
import com.jimmmy.sell.enums.ResultEnum;
import com.jimmmy.sell.exception.SellException;
import com.jimmmy.sell.service.OrderService;
import com.jimmmy.sell.service.PayService;
import com.lly835.bestpay.model.PayResponse;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@Slf4j
public class PayController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @GetMapping("/pay")
    public ModelAndView index(@RequestParam("openid") String openid,
                        @RequestParam("orderId") String ordeId,
                        @RequestParam("returnUrl") String returnUrl,
                        Map<String, Object> map) {
        log.info("openid={}", openid);
        // 1 查询订单"1508780524377710310"
        OrderDTO orderDTO = orderService.findOne(ordeId);
        if (orderDTO == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        // 发起支付
        PayResponse payResponse = payService.create(orderDTO);
        map.put("payResponse", payResponse);
        map.put("returnUrl", returnUrl);


        return new ModelAndView("pay/create", map);
    }

//    @GetMapping("/create")
//    public ModelAndView create(@RequestParam("orderId") String orderId,
//                               @RequestParam("returnUrl") String returnUrl,
//                               Map<String, Object> map) {
//
//        // 1 查询订单
//        OrderDTO orderDTO = orderService.findOne(orderId);
//        if (orderDTO == null) {
//            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
//        }
//        // 发起支付
//        PayResponse payResponse = payService.create(orderDTO);
//        map.put("payResponse", payResponse);
//
//
//        return new ModelAndView("pay/create", map);
//
//    }
    @PostMapping("/notify")
    public void notify(@RequestBody String notifyData) {

        payService.notify();
    }
}
