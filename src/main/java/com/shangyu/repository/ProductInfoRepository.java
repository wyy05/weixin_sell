package com.shangyu.repository;

import com.shangyu.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 商品信息DAO
 * @author 13245625770@163.com
 * @date 2018/5/31 16:40
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo,String>{

    List<ProductInfo> findByProductStatus(Integer productStatus);

}
