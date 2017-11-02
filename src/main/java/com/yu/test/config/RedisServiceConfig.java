package com.yu.test.config;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Created by koreyoshi on 2017/11/2.
 */
public class RedisServiceConfig {
    private static Resource resource = new ClassPathResource("/redisService.properties");

    private static RedisServiceConfig config;

    private static String REDIS_SERVICE_IP = "redisServiceIp";
    private static String REDIS_SERVICE_PORT = "redisServicePort";
    private static String REDIS_DBAUTH = "dbAuth";
    private static String REDIS_DBAUTH_PASSWORD = "dbAuthPassword";

    private static Map<String, String> redisServiceConfigMap = new HashMap<String, String>(4);

    public static void main(String[] args) {
        RedisServiceConfig.getConfig();
    }

    public static RedisServiceConfig getConfig() {
        synchronized (RedisServiceConfig.class) {
            if (config != null) {
                return config;
            }
            try {
                config = new RedisServiceConfig();
                Properties props = PropertiesLoaderUtils.loadProperties(resource);
                Iterator it = props.keySet().iterator();
                String key;
                while (it.hasNext()) {
                    key = (String) it.next();
                    if (key.contains(REDIS_SERVICE_IP)) {
                        config.redisServiceConfigMap.put(REDIS_SERVICE_IP, (String) props.get(key));
                    } else if (key.contains(REDIS_SERVICE_PORT)) {
                        config.redisServiceConfigMap.put(REDIS_SERVICE_PORT, (String) props.get(key));
                    } else if (key.contains(REDIS_DBAUTH_PASSWORD)) {
                        config.redisServiceConfigMap.put(REDIS_DBAUTH_PASSWORD, (String) props.get(key));
                    } else if (key.contains(REDIS_DBAUTH)) {
                        config.redisServiceConfigMap.put(REDIS_DBAUTH, (String) props.get(key));
                    } else {
                        //暂不处理
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return config;
        }
    }

    //获取配置文件中的“.”之后的符号，作为redisServiceConfigMap的key值
    private static String getKeyIn(String key) {
        int start = key.indexOf(".") + 1;
        int end = key.length();
        String rs = key.substring(start, end);
        return rs;
    }

    public static String getServiceHost() {
        return redisServiceConfigMap.get(REDIS_SERVICE_IP);
    }

    public static String getServicePort() {
        return redisServiceConfigMap.get(REDIS_SERVICE_PORT);
    }

    public static String getAuth() {
        return redisServiceConfigMap.get(REDIS_DBAUTH);
    }

    public static String getAuthPassword() {
        return redisServiceConfigMap.get(REDIS_DBAUTH_PASSWORD);
    }

}
