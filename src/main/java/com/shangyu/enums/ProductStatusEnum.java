package com.shangyu.enums;

import lombok.Getter;

/**
 * @author 13245625770@163.com
 * @date 2018/5/31 16:14
 */
@Getter
public enum ProductStatusEnum implements CodeEnum{
    UP(0,"在架"),
    DOWN(1,"下架");

    private Integer code;

    private String msg;

    ProductStatusEnum(Integer code,String msg) {
        this.code = code;
        this.msg=msg;
    }

}
