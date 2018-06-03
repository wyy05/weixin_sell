package com.shangyu.VO;

import lombok.Data;

import java.util.List;

/**
 * 商品列表 返回给前台的根节点
 * @author 13245625770@163.com
 * @date 2018/5/31 19:10
 */
@Data
public class ResultVO<T> {

    private Integer code;

    private String msg;

    private T data;
}
