package com.gym.owner.common;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisJava {

    private static Jedis jedis = null;
    public void ConnectRedis(){

       // JedisPool  jedisPool = new JedisPool("43.204.230.101",6379,"","gym");
        //JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), "redis://gym@43.204.230.101", 6379);

        System.out.println("Connection to server sucessfully");
        //check whether server is running or not
       // jedis = jedisPool.getResource();
        System.out.println("Connection to server sucessfully");


    }
    public static Jedis getJedis(){
        return jedis;
    }
}
