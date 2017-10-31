package com.yu.test.service.impl;

/**
 * Created by koreyoshi on 2017/10/31.
 */
//implements BloomFilterService
public class BloomFilterServiceImpl  {
//    /**
//     * 判断字符串是否包含在布隆过滤器中
//     */
//    public boolean contains(String value) {
//        if (value == null)
//            return false;
//
//        boolean ret = true;
//
//        //将要比较的字符串重新以上述方法计算hash值，再与布隆过滤器比对
//        for (FilterAdapter.Hash f : func) {
//            boolean redisIssetRes = jedis.getbit(REDIS_CACHE_KEY, f.hash(value));
//            if (!redisIssetRes) {
//                return false;
//            }
//            ret = ret && redisIssetRes;
//        }
//
//
//        return ret;
//    }
//
//    /**
//     * 像过滤器中添加字符串
//     */
//    public void addValue(String value) {
//        //将字符串value哈希为8个或多个整数，然后在这些整数的bit上变为1
//        if (value != null) {
//            for (Hash f : func) {
//                jedis.setbit(REDIS_CACHE_KEY, f.hash(value), true);
////                bits.set(f.hash(value), true);
//            }
//
//        }
//    }


}
