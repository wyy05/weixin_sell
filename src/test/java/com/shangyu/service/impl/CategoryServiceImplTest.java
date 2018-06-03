package com.shangyu.service.impl;

import com.shangyu.dataobject.ProductCategory;
import com.shangyu.service.CategoryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author 13245625770@163.com
 * @date 2018/5/31 11:39
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryService categoryService;

    @Test
    public void findOne() {
        ProductCategory one = categoryService.findOne(1);
        Assert.assertEquals(new Integer(1),one.getCategoryId());
    }

    @Test
    public void findAll() {
        List<ProductCategory> all = categoryService.findAll();
        Assert.assertNotEquals(0,all.size());
    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> byCategoryTypeIn = categoryService.findByCategoryTypeIn(Arrays.asList(1, 2, 3));
        Assert.assertNotEquals(0,byCategoryTypeIn.size());
    }

    @Test
    @Transactional
    public void save() {
        ProductCategory lalala = categoryService.save(new ProductCategory("lalala", 4));
        Assert.assertNotEquals(null,lalala);
    }
}