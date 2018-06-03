package com.shangyu.enums;

import lombok.Getter;

/**
 * 支付状态的枚举
 * @author 13245625770@163.com
 * @date 2018/5/31 21:17
 */
@Getter
public enum PayStatusEnum implements CodeEnum{
    WAIT(0, "等待支付"),
    SUCCESS(1, "支付成功"),
    ;

    private Integer code;

    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
