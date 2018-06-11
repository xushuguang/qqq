package com.qtec.snmp.comm.jedis.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Test {
    @org.junit.Test
    public void testJedis1(){
        Jedis jedis = new Jedis("192.168.100.21",6379);
        jedis.set("11","123123");
        System.out.println(jedis.get("11"));
        jedis.close();
    }
    @org.junit.Test
    public void testJedis2() {
        JedisPool jedisPool = new JedisPool("192.168.100.21", 6379);
        Jedis jedis = jedisPool.getResource();
        jedis.set("123", "1234");
        System.out.println(jedis.get("123"));
        jedis.close();
        jedisPool.close();
    }
}
