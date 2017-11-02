package com.yu.test.service;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by koreyoshi on 2017/11/2.
 */
public class RepositoryFactory {
    //redisClientCacheMap
    private static Map<String, Jedis> JedisClientsMap = new HashMap<String, Jedis>(2);

    /**
     * 获取redisclient，以host+port为key缓存在JedisClientsMap中。
     *
     * @param redisUrl          host
     * @param redisPort         端口
     * @param redisAuth         是否需要密码
     * @param redisAuthPassword 密码
     * @return
     */
    public static Jedis getRedisClient(String redisUrl, String redisPort, String redisAuth, String redisAuthPassword) {
        Jedis jedis = null;
        String repositoryName = redisUrl + redisPort;
        if (JedisClientsMap.containsKey(repositoryName)) {
            return JedisClientsMap.get(repositoryName);
        }
        synchronized (JedisClientsMap) {
            if (!JedisClientsMap.containsKey(repositoryName)) {
                Jedis collection = createRedisCollection(redisUrl, redisPort, redisAuth, redisAuthPassword);
                JedisClientsMap.put(repositoryName, collection);
                return collection;
            } else {
                return JedisClientsMap.get(repositoryName);
            }
        }
    }

    /**
     * 创建redisclient
     *
     * @param redisUrl          host
     * @param redisPort         端口
     * @param redisAuth         是否需要密码
     * @param redisAuthPassword 密码
     * @return
     */
    public static Jedis createRedisCollection(String redisUrl, String redisPort, String redisAuth, String redisAuthPassword) {
        Jedis redisCollection = new Jedis(redisUrl, Integer.parseInt(redisPort));
        if (redisAuth.equals("true")) {
            redisCollection.auth(redisAuthPassword);
        }
        return redisCollection;
    }
}
