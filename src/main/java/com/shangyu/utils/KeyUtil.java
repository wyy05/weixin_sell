package com.shangyu.utils;

import java.util.Random;

/**
 * 用来生成不重复的数字ID
 *
 * @author 13245625770@163.com
 * @date 2018/6/2 10:55
 */
public class KeyUtil {

    /**
     * 根据当前时间 加上 六位随机数 生成数字ID
     * @return
     */
    public static synchronized String getUniqueKey() {
        Random random = new Random();
        int num = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(num);
    }
}
