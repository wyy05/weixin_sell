package com.shangyu.repository;

import com.shangyu.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author 13245625770@163.com
 * @date 2018/5/31 10:24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Test
    public void findOne(){
        ProductCategory productCategory = productCategoryRepository.findOne(1);
        System.out.println(
                productCategory
        );
    }

    @Test
    public void save(){
        ProductCategory productCategory = new ProductCategory("甜品",2);
        productCategoryRepository.save(productCategory);
    }

    @Test
    public void update(){
        ProductCategory productCategory = productCategoryRepository.findOne(2);
        productCategory.setCategoryType(10);
        ProductCategory result = productCategoryRepository.save(productCategory);
        Assert.assertNotEquals(null,result);
    }

    @Test
    public void findByCategoryTypeIn(){
        List<ProductCategory> productCategories = productCategoryRepository.findByCategoryTypeIn(Arrays.asList(1, 10));
        Assert.assertNotEquals(0,productCategories.size());
    }
}