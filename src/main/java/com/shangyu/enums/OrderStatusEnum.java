package com.shangyu.enums;

import lombok.Data;
import lombok.Getter;

/**
 * 订单状态的枚举
 * @author 13245625770@163.com
 * @date 2018/5/31 21:14
 */
@Getter
public enum OrderStatusEnum implements CodeEnum{
    NEW(0,"新订单"),
    FINISHED(1,"完成"),
    CANCEL(2, "已取消")
    ;
    private Integer code;
    private String msg;

    OrderStatusEnum(Integer code,String msg){
        this.code = code;
        this.msg=msg;
    }

}
