package com.shangyu.service.impl;

import com.shangyu.VO.ResultVO;
import com.shangyu.dto.OrderDTO;
import com.shangyu.enums.ResultEnum;
import com.shangyu.exception.SellException;
import com.shangyu.service.BuyerService;
import com.shangyu.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 13245625770@163.com
 * @date 2018/6/2 22:36
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    private OrderService orderService;

    @Autowired
    public BuyerServiceImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public OrderDTO detail(String openid, String orderId) {
        return checkOpenid(openid, orderId);
    }

    @Override
    @Transactional
    public OrderDTO cancel(String openid, String orderId) {
        OrderDTO orderDTO = checkOpenid(openid, orderId);
        if (orderDTO == null) {
            log.error("【取消订单】查不到改订单, orderId={}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderDTO);
    }

    private OrderDTO checkOpenid(String openid, String orderId) {
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null) {
            return null;
        }
        if (!orderDTO.getBuyerOpenid().equals(openid)) {
            log.error("[查询订单详情] 该订单不属于当前用户,openid={},orderDTO={}",
                    openid, orderDTO);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}
