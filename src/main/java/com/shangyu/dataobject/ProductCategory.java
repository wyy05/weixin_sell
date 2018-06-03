package com.shangyu.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * 商品类目
 * @author 13245625770@163.com
 * @date 2018/5/31 10:17
 */

@Data
@Entity
@DynamicUpdate
public class ProductCategory {

    @Id
    @GeneratedValue
    private Integer categoryId;  // 类目id

    private String categoryName;    //类目名字

    private Integer categoryType;   //类目编号

    private Date createTime;

    private Date updateTime;

    public ProductCategory() {
    }

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

}

