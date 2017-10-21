package com.jimmmy.sell.utils;

import com.jimmmy.sell.VO.ProductVO;
import com.jimmmy.sell.VO.ResultVO;

import java.util.List;

public class ResultVOUtil {

    public static ResultVO success(List<ProductVO> productVOList) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMgs("成功");

        resultVO.setData(productVOList);
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(Integer code, String msg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMgs(msg);

        //resultVO.setData(null);
        return resultVO;
    }
}
