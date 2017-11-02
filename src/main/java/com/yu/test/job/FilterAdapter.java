package com.yu.test.job;

import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * Created by koreyoshi on 2017/10/31.
 */
@Component
public class FilterAdapter {

    private static final int BIT_SIZE = 2 << 28;//二进制向量的位数，相当于能存储1000万条url左右，误报率为千万分之一
    private static final int[] seeds = new int[]{3, 5, 7, 11, 13, 31, 37, 61};//用于生成信息指纹的8个随机数，最好选取质数

    private static final String REDIS_CACHE_KEY = "key1";
    private static Hash[] func = new Hash[seeds.length];//用于存储8个随机哈希值对象

    public FilterAdapter() {
        for (int i = 0; i < seeds.length; i++) {
            func[i] = new Hash(BIT_SIZE, seeds[i]);
        }
    }


    public static class Hash {
        private int size;//二进制向量数组大小
        private int seed;//随机数种子

        public Hash(int cap, int seed) {
            this.size = cap;
            this.seed = seed;
        }

        /**
         * 计算哈希值(也可以选用别的恰当的哈希函数)
         */
        public int hash(String value) {
            int result = 0;
            int len = value.length();
            for (int i = 0; i < len; i++) {
                result = seed * result + value.charAt(i);
            }
            return (size - 1) & result;
        }
    }


    /**
     * 判断字符串是否包含在布隆过滤器中
     */
    public boolean contains(String value, Jedis jedis) {
        boolean ret = true;
        //将要比较的字符串重新以上述方法计算hash值，再与布隆过滤器比对
        for (FilterAdapter.Hash f : func) {
            boolean redisIssetRes = jedis.getbit(REDIS_CACHE_KEY, f.hash(value));
            if (!redisIssetRes) {
                return false;
            }
            ret = ret && redisIssetRes;
        }
        return ret;
    }

    /**
     * 像过滤器中添加字符串
     */
    public void addValue(String value, Jedis jedis) {
        //将字符串value哈希为8个或多个整数，然后在这些整数的bit上变为1
        for (Hash f : func) {
            jedis.setbit(REDIS_CACHE_KEY, f.hash(value), true);
        }
    }
}
