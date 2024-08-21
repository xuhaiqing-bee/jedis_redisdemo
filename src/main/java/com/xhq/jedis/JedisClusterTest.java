package com.xhq.jedis;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

/**
 * @Author：XuHaiqing
 * @Package：com.xhq.jedis
 * @Project：jedis_redisdemo
 * @name：JedisClusterTest
 * @Date：2024/8/21 10:12
 * @Filename：JedisClusterTest
 * @Description：集群的Jedis开发
 */
public class JedisClusterTest {
    public static void main(String[] args) {
        HostAndPort hostAndPort = new HostAndPort("192.168.18.146", 6379);
        JedisCluster jedisCluster = new JedisCluster(hostAndPort);
        jedisCluster.set("b1","value1");
        String value = jedisCluster.get("b1");
        System.out.println("value = " + value);
        jedisCluster.close();
    }
}
