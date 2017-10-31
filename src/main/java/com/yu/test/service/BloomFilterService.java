package com.yu.test.service;

/**
 * Created by koreyoshi on 2017/10/31.
 */
public interface BloomFilterService {
    /**
     * 判断字符串是否包含在布隆过滤器中
     */
    public boolean contains(String value);

    /**
     * 像过滤器中添加字符串
     */
    public void addValue(String value);


}
