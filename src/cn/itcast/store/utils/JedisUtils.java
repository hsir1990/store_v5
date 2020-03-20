package cn.itcast.store.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtils {
	//创建连接池
	private static JedisPoolConfig config;
	private static JedisPool pool;
	
	static{
		config=new JedisPoolConfig();
		config.setMaxTotal(30);
		config.setMaxIdle(2);
//		config.setPassword("123456");
		
		pool=new JedisPool(config, "47.92.48.144", 6379,1000,"123456");
	}
	
	
	//获取连接的方法
	public static Jedis getJedis(){
		return pool.getResource();
	}
	
	
	//释放连接
	public static void closeJedis(Jedis j){
		j.close();
	}
}
