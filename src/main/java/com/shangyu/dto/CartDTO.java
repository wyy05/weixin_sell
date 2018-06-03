package com.shangyu.dto;

import lombok.Data;

/**
 * @author 13245625770@163.com
 * @date 2018/6/2 11:22
 */
@Data
public class CartDTO {

    private String productId;

    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
        Integer s = 1 + 1;
    }

    public CartDTO() {
    }
}
