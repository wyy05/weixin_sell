package com.shangyu.service.impl;

import com.shangyu.dataobject.ProductCategory;
import com.shangyu.repository.ProductCategoryRepository;
import com.shangyu.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品类目service实现
 * @author 13245625770@163.com
 * @date 2018/5/31 11:38
 */
@Service
public class CategoryServiceImpl implements CategoryService{

    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    public CategoryServiceImpl(ProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return productCategoryRepository.findOne(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return productCategoryRepository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return productCategoryRepository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    @Transactional
    public ProductCategory save(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }
}
