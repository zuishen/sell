package com.jimmmy.sell.enums;

import lombok.Getter;

@Getter
public enum PayStatusEnum implements CodeEnum{

    UNPAY(0, "未支付"),
    PAID(1, "已支付");

    private Integer code;

    private String msg;


    PayStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
