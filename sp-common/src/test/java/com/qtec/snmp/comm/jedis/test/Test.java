package com.qtec.snmp.comm.jedis.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class Test {
    @org.junit.Test
    public void testJedis1(){
        Jedis jedis = new Jedis("192.168.100.216",6379);
        jedis.set("111","222");
        System.out.println(jedis.get("111"));
        jedis.close();
    }
    @org.junit.Test
    public void testJedis2(){
        JedisPool jedisPool = new JedisPool("192.168.100.216",6379);
        Jedis jedis = jedisPool.getResource();
        jedis.set("123","1234");
        System.out.println(jedis.get("123"));
        jedis.close();
        jedisPool.close();
    }
}
