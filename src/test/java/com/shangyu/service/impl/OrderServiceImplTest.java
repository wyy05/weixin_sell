package com.shangyu.service.impl;

import com.shangyu.dataobject.OrderDetail;
import com.shangyu.dto.OrderDTO;
import com.shangyu.enums.OrderStatusEnum;
import com.shangyu.enums.PayStatusEnum;
import com.shangyu.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 订单service测试
 * @author 13245625770@163.com
 * @date 2018/6/2 11:46
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    private final String BUYER_OPENID ="ew3euwhd7sjw9diwkq";

    @Autowired
    private OrderService orderService;

    @Test
    public void create() {

        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("1001");
        o1.setProductQuantity(2);
        OrderDetail o2 = new OrderDetail();
        o2.setProductId("1002");
        o2.setProductQuantity(1);
        orderDetailList.add(o1);
        orderDetailList.add(o2);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("张三");
        orderDTO.setBuyerPhone("18868822111");
        orderDTO.setBuyerAddress("慕课网总部");
        orderDTO.setBuyerOpenid(BUYER_OPENID);
        orderDTO.setOrderDetailList(orderDetailList);

        OrderDTO result = orderService.create(orderDTO);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOne() {
        OrderDTO result = orderService.findOne("1527922740345275940");
        log.info("[查询单个订单]result={}",result);
        Assert.assertEquals("1527922740345275940",result.getOrderId());
    }

    @Test
    public void findList() {
        Page<OrderDTO> result = orderService.findList(BUYER_OPENID, new PageRequest(0, 10));
        Assert.assertNotNull(result);
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO = orderService.findOne("1527922740345275940");
        OrderDTO result = orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),result.getOrderStatus());
    }

    @Test
    public void finish() {
        OrderDTO orderDTO = orderService.findOne("1527922740345275940");
        OrderDTO result = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),result.getOrderStatus());
    }

    @Test
    public void paid() {
        OrderDTO orderDTO = orderService.findOne("1527922740345275940");
        OrderDTO result = orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),result.getPayStatus());
    }

    @Test
    public void findList1() {
        Page<OrderDTO> result = orderService.findList(new PageRequest(0, 10));
        Assert.assertNotNull(result);
    }
}