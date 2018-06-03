package com.shangyu.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * 前端form表单校验用类
 * @author 13245625770@163.com
 * @date 2018/6/2 21:07
 */
@Data
public class OrderForm {

    // 买家姓名
    @NotEmpty(message = "姓名必填")
    private String name;

    // 买家手机号
    @NotEmpty(message = "手机号必填")
    private String phone;

    // 买家地址
    @NotEmpty(message = "地址必填")
    private String address;

    // 买家微信openid
    @NotEmpty(message = "openid必填")
    private String openid;

    // 购物车
    @NotEmpty(message = "购物车不能为空")
    private String items;

}
