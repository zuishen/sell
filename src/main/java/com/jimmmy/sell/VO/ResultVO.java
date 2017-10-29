package com.jimmmy.sell.VO;

// 返回给前端的  ViewObject
// Http请求的最外层对象

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
public class ResultVO<T> {

    /* 错误码 */
    private Integer code;

    /* 提示信息 */
    private String msg;

    /* 具体内容*/
    private T data;
}
