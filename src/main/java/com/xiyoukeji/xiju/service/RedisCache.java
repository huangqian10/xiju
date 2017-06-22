package com.xiyoukeji.xiju.service;


import com.xiyoukeji.xiju.core.utils.SerializeUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisCache {

	private JedisPool jedisPool;

	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	public void set(String key, Object value, int seconds) {
		Jedis jedis=null;
		try{
			jedis = jedisPool.getResource();
			jedis.set(key, String.valueOf(value));
			jedis.expire(key, seconds);
		}catch(Exception e){
			e.printStackTrace();
			if(jedis!=null){
				jedisPool.returnBrokenResource(jedis);
			}
		}finally{
			if(jedis!=null){
				jedisPool.returnResource(jedis);
			}
		}
	}

	public void set(String key, Object value) {
		set(key, value, 3600);
	}

	public String get(String key) {
		Jedis jedis =null;
		try{
			jedis = jedisPool.getResource();
			String value = jedis.get(key);
			return value;
		}catch(Exception e){
			e.printStackTrace();
			if(jedis!=null){
				jedisPool.returnBrokenResource(jedis);
			}
			return null;
		}finally{
			if(jedis!=null){
				jedisPool.returnResource(jedis);
			}
		}

	}

	public void del(String key) {
		Jedis jedis=null;
		try{
			jedis = jedisPool.getResource();
			jedis.del(key);
		}catch(Exception e){
			e.printStackTrace();
			if(jedis!=null){
				jedisPool.returnBrokenResource(jedis);
			}
		}finally{
			if(jedis!=null){
				jedisPool.returnResource(jedis);
			}
		}

	}

	public Object getObject(String key) {
		Jedis jedis = null;
		try{
			jedis = jedisPool.getResource();
			byte[] value = jedis.get(key.getBytes());
			return SerializeUtil.unserialize(value);
		}catch(Exception e){
			e.printStackTrace();
			if(jedis!=null){
				jedisPool.returnBrokenResource(jedis);
			}
			return null;
		}finally{
			if(jedis!=null){
				jedisPool.returnResource(jedis);
			}
		}
	
	}

	public void setObject(String key, Object value, Integer seconds) {
		Jedis jedis =null;
		try{
			jedis = jedisPool.getResource();
			jedis.set(key.getBytes(), SerializeUtil.serialize(value));
			jedis.expire(key.getBytes(), seconds);
		}catch(Exception e){
			e.printStackTrace();
			if(jedis!=null){
				jedisPool.returnBrokenResource(jedis);
			}
		}finally{
			if(jedis!=null){
				jedisPool.returnResource(jedis);
			}
		}

	}

	public void delObject(String key) {
		Jedis jedis =null;
		try{
			jedis = jedisPool.getResource();
			jedis.del(key.getBytes());
		}catch(Exception e){
			e.printStackTrace();
			if(jedis!=null){
				jedisPool.returnBrokenResource(jedis);
			}
		}finally{
			if(jedis!=null){
				jedisPool.returnResource(jedis);
			}
		}
	}
}
