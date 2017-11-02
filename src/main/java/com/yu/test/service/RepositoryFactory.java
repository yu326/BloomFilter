package com.yu.test.service;

import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by koreyoshi on 2017/11/2.
 */
public class RepositoryFactory {
    private static Map<String, Jedis> JedisClientsMap = new HashMap<String, Jedis>(2);

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

    public static Jedis createRedisCollection(String redisUrl, String redisPort, String redisAuth, String redisAuthPassword) {
        Jedis redisCollection = new Jedis(redisUrl, Integer.parseInt(redisPort));
        if (redisAuth.equals("true")) {
            redisCollection.auth(redisAuthPassword);
        }
        return redisCollection;
    }
}
