package com.shangyu.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shangyu.dataobject.OrderDetail;
import com.shangyu.enums.OrderStatusEnum;
import com.shangyu.enums.PayStatusEnum;
import com.shangyu.utils.EnumUtil;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * OrderMaster的数据传输类
 *
 * @author 13245625770@163.com
 * @date 2018/6/2 8:40
 */
@Data
public class OrderDTO {
    /**
     * 订单id.
     */
    private String orderId;

    /**
     * 买家名字.
     */
    private String buyerName;

    /**
     * 买家手机号.
     */
    private String buyerPhone;

    /**
     * 买家地址.
     */
    private String buyerAddress;

    /**
     * 买家微信Openid.
     */
    private String buyerOpenid;

    /**
     * 订单总金额.
     */
    private BigDecimal orderAmount;

    /**
     * 订单状态, 默认为0新下单.
     */
    private Integer orderStatus;

    /**
     * 支付状态, 默认为0未支付.
     */
    private Integer payStatus;

    /**
     * 创建时间.
     */
    private Date createTime;

    /**
     * 更新时间.
     */
    private Date updateTime;

    private List<OrderDetail> orderDetailList;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum() {
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum() {
        return EnumUtil.getByCode(payStatus, PayStatusEnum.class);
    }

}
