package com.shangyu.controller;

import com.shangyu.VO.ProductInfoVO;
import com.shangyu.VO.ProductVO;
import com.shangyu.VO.ResultVO;
import com.shangyu.dataobject.ProductCategory;
import com.shangyu.dataobject.ProductInfo;
import com.shangyu.service.CategoryService;
import com.shangyu.service.ProductService;
import com.shangyu.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 13245625770@163.com
 * @date 2018/5/31 17:27
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    private ProductService productService;

    private CategoryService categoryService;

    @Autowired
    public BuyerProductController(ProductService productService,CategoryService categoryService){
        this.productService=productService;
        this.categoryService=categoryService;
    }

    @GetMapping("list")
    public ResultVO<List<ProductVO>> list() {
        // 1.查询上架的商品
        List<ProductInfo> productInfos = productService.findUpAll();
        // 2.通过 上架商品 的类目类型来查询 类目信息
        // 精简方法(java8新特性, lambda表达式)
        List<Integer> categoryTypeList = productInfos.stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());
        List<ProductCategory> productCategories = categoryService.findByCategoryTypeIn(categoryTypeList);

        // 3.数据拼接
        List<ProductVO> productVOS = new ArrayList<>();

        productCategories.forEach((productCategory)->{
            ProductVO productVO = new ProductVO();
            BeanUtils.copyProperties(productCategory, productVO);

            List<ProductInfoVO> productInfoVOS = new ArrayList<>();
            productInfos.forEach((productInfo)->{
                if (productVO.getCategoryType().equals(productInfo.getCategoryType())) {
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOS.add(productInfoVO);
                }
            });
            productVO.setProductInfoVOList(productInfoVOS);
            productVOS.add(productVO);
        });

        return ResultVOUtil.success(productVOS);
    }
}
