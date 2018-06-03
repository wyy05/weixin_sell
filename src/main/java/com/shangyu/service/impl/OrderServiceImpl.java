package com.shangyu.service.impl;

import com.shangyu.converter.OrderMaster2OrderDTOConverter;
import com.shangyu.dataobject.OrderDetail;
import com.shangyu.dataobject.OrderMaster;
import com.shangyu.dataobject.ProductInfo;
import com.shangyu.dto.CartDTO;
import com.shangyu.dto.OrderDTO;
import com.shangyu.enums.OrderStatusEnum;
import com.shangyu.enums.PayStatusEnum;
import com.shangyu.enums.ResultEnum;
import com.shangyu.exception.SellException;
import com.shangyu.repository.OrderDetailRepository;
import com.shangyu.repository.OrderMasterRepository;
import com.shangyu.service.OrderService;
import com.shangyu.service.ProductService;
import com.shangyu.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 客户订单service实现
 *
 * @author 13245625770@163.com
 * @date 2018/6/2 8:54
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private OrderMasterRepository orderMasterRepository;

    private OrderDetailRepository orderDetailRepository;

    private ProductService productService;

    @Autowired
    OrderServiceImpl(OrderMasterRepository orderMasterRepository,
                     OrderDetailRepository orderDetailRepository,ProductService productService ){
        this.orderMasterRepository=orderMasterRepository;
        this.orderDetailRepository=orderDetailRepository;
        this.productService=productService;
    }

    /**
     * 创建订单
     *
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        String orderId = KeyUtil.getUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        //1. 查询商品（数量, 价格）
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo producterror = productService.findOne(orderDetail.getProductId());
            if (producterror == null) {
                log.error("[创建订单] 商品不存在,orderDTO={}", orderDTO);
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //2. 计算订单总价
            orderAmount = producterror.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);
            //订单详情入库
            BeanUtils.copyProperties(producterror, orderDetail);
            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            orderDetail.setOrderId(orderId);
            orderDetailRepository.save(orderDetail);
        }

        //3. 写入订单数据库（orderMaster和orderDetail）
        orderDTO.setOrderId(orderId);
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());

        orderMasterRepository.save(orderMaster);

        //4. 扣库存
        List<CartDTO> cartDTOS = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.decreaseStock(cartDTOS);

        //发送websocket消息

        return orderDTO;
    }

    /**
     * 查找单个订单
     *
     * @param orderId
     * @return
     */
    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if (orderMaster == null) {
            log.error("[查询单个订单] 订单不存在,orderId={}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            log.error("[查询单个订单] 订单详情不存在,orderId={}", orderId);
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    /**
     * 根据openid 查找对应的订单 并且 使用分页
     *
     * @param buyerOpenid
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> OrderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.converter(OrderMasterPage.getContent());
        return new PageImpl<>(orderDTOList, pageable, OrderMasterPage.getTotalElements());
    }


    /**
     * 取消订单
     *
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        //判断订单的状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("[取消订单] 订单状态不正确,orderId = {}, orderStatus = {}",
                    orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if (result == null) {
            log.error("[取消订单] 更新失败, orderMaster = {}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        //返回库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("[取消订单] 订单中无商品, orderDTO = {}", orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }

        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStock(cartDTOList);

        //如果已经付款,需要退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            //TODO
        }
        return orderDTO;
    }

    /**
     * 完结订单
     *
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单的状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("[完结订单] 订单状态不正确,orderId = {}, orderStatus = {}",
                    orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if (result == null) {
            log.error("[完结订单] 更新失败, orderMaster = {}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    /**
     * 订单支付完成
     *
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单的状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("[订单支付完成] 订单状态不正确,orderId = {}, orderStatus = {}",
                    orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("[订单支付完成] 订单支付状态不正确,orderDTO = {}", orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }

        //修改支付状态
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        OrderMaster result = orderMasterRepository.save(orderMaster);
        if (result == null) {
            log.error("[订单支付完成] 更新失败, orderMaster = {}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }

        return orderDTO;
    }

    /**
     * 搜索全部订单
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> OrderMasterPage = orderMasterRepository.findAll(pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.converter(OrderMasterPage.getContent());
        return new PageImpl<>(orderDTOList, pageable, OrderMasterPage.getTotalElements());
    }
}
