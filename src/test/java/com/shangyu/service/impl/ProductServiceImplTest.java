package com.shangyu.service.impl;

import com.shangyu.dataobject.ProductInfo;
import com.shangyu.enums.ProductStatusEnum;
import com.shangyu.service.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author 13245625770@163.com
 * @date 2018/5/31 16:55
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    private ProductService productService;

    @Test
    public void findOne() {
        ProductInfo one = productService.findOne("1001");
        Assert.assertNotEquals(null,one);
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> upAll = productService.findUpAll();
        Assert.assertNotEquals(0,upAll.size());
    }

    @Test
    public void findAll() {
        Pageable pageable = new PageRequest(1,10);
        Page<ProductInfo> all = productService.findAll(pageable);
        Assert.assertNotEquals(0,all.getTotalElements());
    }

    @Test
    @Transactional
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("1003");
        productInfo.setProductName("慕斯蛋糕");
        productInfo.setProductPrice(new BigDecimal(19.9));
        productInfo.setProductStock(200);
        productInfo.setProductDescription("就是慕斯蛋糕...");
        productInfo.setProductIcon("https://xxxxx.com");
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        productInfo.setCategoryType(2);
        System.out.println(productInfo);
        ProductInfo save = productService.save(productInfo);
        Assert.assertNotEquals(null,save);
    }
}