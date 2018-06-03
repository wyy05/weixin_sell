package com.shangyu.service;

import com.shangyu.VO.ResultVO;
import com.shangyu.dto.OrderDTO;

/**
 * @author 13245625770@163.com
 * @date 2018/6/2 22:36
 */
public interface BuyerService {

    //查询订单详情
    public OrderDTO detail(String openid,String orderId);

    //取消订单
    public OrderDTO cancel(String openid,String orderId);
}
