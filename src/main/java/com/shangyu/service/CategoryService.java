package com.shangyu.service;

import com.shangyu.dataobject.ProductCategory;

import java.util.List;

/**
 * 商品类目service接口
 * @author 13245625770@163.com
 * @date 2018/5/31 11:33
 */
public interface CategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);

}
