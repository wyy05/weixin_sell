package com.shangyu.exception;

import com.shangyu.enums.ResultEnum;

/**
 * @author 13245625770@163.com
 * @date 2018/6/2 10:25
 */
public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public SellException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

}
