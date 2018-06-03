package com.shangyu.converter;

import com.shangyu.dataobject.OrderMaster;
import com.shangyu.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 13245625770@163.com
 * @date 2018/6/2 16:27
 */
public class OrderMaster2OrderDTOConverter {

    public static OrderDTO converter(OrderMaster orderMaster){
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> converter(List<OrderMaster> orderMasterList){
        return orderMasterList.stream().map(e->converter(e)).collect(Collectors.toList());
    }

}
