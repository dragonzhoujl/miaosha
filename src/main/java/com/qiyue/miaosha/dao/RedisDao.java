package com.qiyue.miaosha.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
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
			try{
				String key="seckill:"+seckillId;
				byte[] bytes=jedis.get(key.getBytes());
				if(bytes!=null){
					SecKill secKill=schema.newMessage();
					ProtostuffIOUtil.mergeFrom(bytes, secKill, schema);
					return secKill;
				}
			}finally{
				jedisPool.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
				
	}
	
	public String putSeckill(SecKill secKill){
		jedisPool=new JedisPool(ip,port);
		try{
			Jedis jedis=jedisPool.getResource();
			try{
				String key="seckill:"+secKill.getSeckillId();
				byte[] bytes=ProtostuffIOUtil.toByteArray(secKill, schema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
				int timeout=60*60;
				String result=jedis.setex(key.getBytes(), timeout, bytes);
				return result;
			}finally{
				jedisPool.close();
			}
			
		}catch(Exception e){
			
		}
		return null;
		
	}

}
