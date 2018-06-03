package com.shangyu.repository;

import com.shangyu.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 订单商品DAO
 * @author 13245625770@163.com
 * @date 2018/5/31 21:24
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail,String>{
    List<OrderDetail> findByOrderId(String orderId);
}
