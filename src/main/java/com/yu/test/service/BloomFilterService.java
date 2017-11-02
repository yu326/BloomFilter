package com.yu.test.service;

import com.alibaba.fastjson.JSONObject;
import com.yu.test.config.RedisServiceConfig;
import com.yu.test.job.FilterAdapter;

/**
 * Created by koreyoshi on 2017/10/31.
 */
public interface BloomFilterService {
    /**
     * 拿到需要过滤的数据
     *
     * @param doc 原始数据
     * @return
     */
    public String getFilterParam(JSONObject doc);

    /**
     * 进行判断字符串是否在过滤器中
     *
     * @param redisServiceConfig redis配置文件
     * @param filterData         待判断的数据
     * @return
     */
    public boolean filterByData(RedisServiceConfig redisServiceConfig, FilterAdapter filterAdapter, String filterData);


}
