package com.shangyu.utils;

import com.shangyu.VO.ResultVO;

/**
 * @author 13245625770@163.com
 * @date 2018/6/2 21:47
 */
public class ResultVOUtil {

    public static ResultVO success(Object o){
        ResultVO<Object> resultVO = new ResultVO<>();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(o);
        return resultVO;
    }
}
