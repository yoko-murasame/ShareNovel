package cn.dmdream.utils;


import java.util.List;

import cn.dmdream.entity.SnCategory;
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
		
		
		pool=new JedisPool(config, "cloud.dmdream.cn", 6379);
	}
	
	
	//获取连接的方法
	public static Jedis getJedis(){
		Jedis jedis = pool.getResource();
		jedis.auth("s18334435420");
		return jedis;
	}
	
	
	//释放连接
	public static void closeJedis(Jedis j){
		j.close();
	}
	
	//获取Redis分页数据
	public static <T> List<T> getRedisListByPage(int pageSize,int page , List<T> dataList){
		
		if(dataList==null) return null;
		
		int start = (page - 1) * pageSize;
		int end = page * pageSize;
		if (end > dataList.size()) end = dataList.size();
		if (start <= dataList.size()) {
			return dataList.subList(start, end);
		}
		
		return null;
	}
}
