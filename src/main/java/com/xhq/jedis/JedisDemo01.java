package com.xhq.jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author：XuHaiqing
 * @Package：com.xhq.jedis
 * @Project：jedis_redisdemo
 * @name：JedisDemo01
 * @Date：2024/8/17 14:05
 * @Filename：JedisDemo01
 */
public class JedisDemo01 {

    public static void main(String[] args) {
        //test01();
        //test02();
        //test03();
//        test04();
        test05();
    }


    // 字符串
    @Test
    public static void test01() {
        Jedis jedis = new Jedis("192.168.18.146", 6379);
        Set<String> keys = jedis.keys("*");
        jedis.set("k1", "v1");
        jedis.set("k2", "v2");
        jedis.set("k3", "v3");
        for (String key : keys) {
            System.out.println(key);
        }

        System.out.println(jedis.get("k1"));
        System.out.println(jedis.exists("k2"));
        System.out.println(jedis.ttl("k3"));

        jedis.mset("st1", "v1", "st2", "v2", "st3", "v3");
        List<String> vals = jedis.mget("st1", "st2", "st3");
        for (String val : vals) {
            System.out.println(val);
        }
        jedis.close();
    }

    // list
    @Test
    public static void test02() {
        Jedis jedis = new Jedis("192.168.18.146", 6379);
        jedis.flushDB();

        Long count = jedis.lpush("list1", "v1", "v2", "v3");
        System.out.println(count);
        List<String> list = jedis.lrange("list1", 0, 0 - 1);
        for (String string : list) {
            System.out.println(string);
        }

        jedis.close();
    }

    // set
    @Test
    public static void test03() {
        Jedis jedis = new Jedis("192.168.18.146", 6379);
        jedis.flushDB();

        jedis.sadd("set1", "v1", "v2", "v3");
        Set<String> set1 = jedis.smembers("set1");
        System.out.println(set1);
        jedis.srem("set1","v2");

        jedis.close();
    }

    // hash
    @Test
    public static void test04() {
        Jedis jedis = new Jedis("192.168.18.146", 6379);
        jedis.flushDB();

        jedis.hset("user1", "name", "zhangsan");
        jedis.hset("user1", "age", "18");
        jedis.hset("user1", "gender", "male");
        Map<String, String> map = jedis.hgetAll("user1");
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

        jedis.hmset("user2", map);
        String s = jedis.hget("user2", "age");
        System.out.println(s);

        for (String s1 : jedis.hmget("user2", "name", "gender", "age")) {
            System.out.println(s1);
        }

        jedis.close();
    }

    // zset
    @Test
    public static void test05() {
        Jedis jedis = new Jedis("192.168.18.146", 6379);
        jedis.flushDB();
        jedis.zadd("students",80, "zhangsan");
        jedis.zadd("students",90, "lisi");
        jedis.zadd("students",70, "wangwu");
        System.out.println(jedis.zcard("students"));
        Set<String> zrange = jedis.zrange("students", 0, -1);
        System.out.println(zrange);
        jedis.close();
    }

}
