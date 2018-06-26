package com.qiyue.miaosha.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.qiyue.miaosha.pojo.SecKill;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisDao {
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	private JedisPool jedisPool;
	private int port;
	private String ip;
	
	public RedisDao(String ip,int port) {
		this.port=port;
		this.ip=ip;
	}
	
	private RuntimeSchema<SecKill> schema=RuntimeSchema.createFrom(SecKill.class);
	
	public SecKill getSeckill(long seckillId){
		jedisPool=new JedisPool(ip,port);
		try {
			Jedis jedis=jedisPool.getResource();
		} catch (Exception e) {
			
		}
		return null;
				
	}

}
