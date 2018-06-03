package com.shangyu.utils;

import com.shangyu.enums.CodeEnum;

/**
 * 枚举工具类
 * @author 13245625770@163.com
 * @date 2018/5/31 16:38
 */
public class EnumUtil {

    /**
     * 通过 code值 查询继承了CodeEnum借口的枚举 返回code对应的枚举
     * @param code
     * @param enumClass
     * @param <T>
     * @return
     */
    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T each: enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}
