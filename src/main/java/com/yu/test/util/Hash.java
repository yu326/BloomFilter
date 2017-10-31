package com.yu.test.util;

/**
 * Created by koreyoshi on 2017/10/31.
 */
public  class Hash {
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