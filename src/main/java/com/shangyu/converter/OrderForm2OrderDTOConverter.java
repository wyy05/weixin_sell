package com.shangyu.converter;

import com.alibaba.fastjson.JSON;
import com.shangyu.dataobject.OrderDetail;
import com.shangyu.dto.OrderDTO;
import com.shangyu.enums.ResultEnum;
import com.shangyu.exception.SellException;
import com.shangyu.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * OrderForm -> OrderDTO 转换类
 *
 * @author 13245625770@163.com
 * @date 2018/6/2 21:16
 */
@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO converter(OrderForm orderForm){
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = JSON.parseArray(orderForm.getItems(), OrderDetail.class);
        }catch (Exception e){
            log.error("[json转换失败] 失败,String={}",orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }
}
