package com.jimmmy.sell.utils;

import com.jimmmy.sell.VO.ProductVO;
import com.jimmmy.sell.VO.ResultVO;

import java.util.List;
import java.util.Map;

public class ResultVOUtil {

    public static ResultVO success(Object productVOList) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");

        resultVO.setData(productVOList);
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);

        //resultVO.setData(null);
        return resultVO;
    }
}
