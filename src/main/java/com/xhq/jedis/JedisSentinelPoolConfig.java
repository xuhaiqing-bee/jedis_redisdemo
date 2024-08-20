package com.xhq.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author：XuHaiqing
 * @Package：com.xhq.jedis
 * @Project：jedis_redisdemo
 * @name：JedisSentinelPoolConfig
 * @Date：2024/8/20 15:50
 * @Filename：JedisSentinelPoolConfig
 * @Description：主从复制配置类
 */
public class JedisSentinelPoolConfig {
    private static JedisSentinelPool jedisSentinelPool = null;

    public static Jedis getJedisFromSentinel() {
        if (jedisSentinelPool == null) {
            Set<String> sentinelSet = new HashSet<>();
            sentinelSet.add("192.168.11.103:26379");

            JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
            jedisPoolConfig.setMaxTotal(10); //最大可用连接数
            jedisPoolConfig.setMaxIdle(5); //最大闲置连接数
            jedisPoolConfig.setMinIdle(5); //最小闲置连接数
            jedisPoolConfig.setBlockWhenExhausted(true); //连接耗尽是否等待
            jedisPoolConfig.setMaxWaitMillis(2000); //等待时间
            jedisPoolConfig.setTestOnBorrow(true); //取连接的时候进行一下测试 ping pong

            jedisSentinelPool = new JedisSentinelPool("mymaster", sentinelSet, jedisPoolConfig);
            return jedisSentinelPool.getResource();
        } else {
            return jedisSentinelPool.getResource();
        }
    }
}
