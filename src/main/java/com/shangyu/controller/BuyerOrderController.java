package com.shangyu.controller;

import com.shangyu.VO.ResultVO;
import com.shangyu.converter.OrderForm2OrderDTOConverter;
import com.shangyu.dto.OrderDTO;
import com.shangyu.enums.ResultEnum;
import com.shangyu.exception.SellException;
import com.shangyu.form.OrderForm;
import com.shangyu.service.BuyerService;
import com.shangyu.service.OrderService;
import com.shangyu.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 13245625770@163.com
 * @date 2018/6/2 19:37
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    private BuyerService buyerService;

    private OrderService orderService;

    @Autowired
    public BuyerOrderController(BuyerService buyerService, OrderService orderService) {
        this.buyerService = buyerService;
        this.orderService = orderService;
    }

    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("[创建订单] 参数不正确,orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTOConverter.converter(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("[创建订单] 购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }

        OrderDTO result = orderService.create(orderDTO);
        Map<String, String> map = new HashMap<>();
        map.put("orderId", result.getOrderId());

        return ResultVOUtil.success(map);
    }

    //订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam String openid,
                                         @RequestParam Integer page,
                                         @RequestParam Integer size) {
        if (StringUtils.isEmpty(openid)) {
            log.error("[查询订单列表] openid不能为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        Pageable pageable = new PageRequest(page, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid, pageable);

        return ResultVOUtil.success(orderDTOPage.getContent());
    }

    //查询订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam String openid, @RequestParam String orderId) {
        OrderDTO orderDTO = buyerService.detail(openid, orderId);
        return ResultVOUtil.success(orderDTO);
    }

    //取消订单
    @GetMapping("/cancel")
    public ResultVO<OrderDTO> cancel(@RequestParam String openid, @RequestParam String orderId) {
        OrderDTO orderDTO = buyerService.cancel(openid, orderId);
        return ResultVOUtil.success(orderDTO);
    }
}
