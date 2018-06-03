package com.shangyu.service.impl;

import com.shangyu.dataobject.ProductInfo;
import com.shangyu.dto.CartDTO;
import com.shangyu.enums.ProductStatusEnum;
import com.shangyu.enums.ResultEnum;
import com.shangyu.exception.SellException;
import com.shangyu.repository.ProductInfoRepository;
import com.shangyu.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品信息service实现
 * @author 13245625770@163.com
 * @date 2018/5/31 16:50
 */
@Service
public class ProductServiceImpl implements ProductService{

    private ProductInfoRepository productInfoRepository;

    @Autowired
    public ProductServiceImpl(ProductInfoRepository productInfoRepository) {
        this.productInfoRepository = productInfoRepository;
    }

    @Override
    public ProductInfo findOne(String productId) {
        return productInfoRepository.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoRepository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO :cartDTOList) {
            ProductInfo productInfo = productInfoRepository.findOne(cartDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer productStock = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(productStock);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO :cartDTOList) {
            ProductInfo productInfo = productInfoRepository.findOne(cartDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            Integer productStock = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (productStock < 0) {
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(productStock);
        }
    }

    @Override
    public ProductInfo onSale(String productId) {
        return null;
    }

    @Override
    public ProductInfo offSale(String productId) {
        return null;
    }
}
