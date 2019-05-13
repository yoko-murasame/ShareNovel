package cn.dmdream.utils;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.dmdream.entity.SnCategory;
import cn.dmdream.service.SnCategoryService;
import cn.dmdream.service.impl.SnCategoryServiceImpl;
import redis.clients.jedis.Jedis;

public class TestJedis {

	// 测试Jedis的使用
	public static void main(String[] args) {

		// 连接,初始化操作已经交给了工具类

		// 1.从工具类获取Jedis对象
		Jedis jedis = JedisUtils.getJedis();
		ObjectMapper objectMapper = new ObjectMapper();
		// 2.Redis分页版查询
		try {
			// 先查询redis 若有,返回
			String value = jedis.hget("key1", "0");
			if (value != null) {
				System.out.println("数据是从Redis中查询的");
				List<SnCategory> readValue = objectMapper.readValue(value, new TypeReference<List<SnCategory>>() {
				});
				int page = 1;
				int pageSize = 10;
				int start = (page - 1) * pageSize;
				int end = page * pageSize;
				if (end > readValue.size())
					end = readValue.size();
				if (start < readValue.size()) {
					List<SnCategory> subList = readValue.subList(start, end);
					subList.forEach(System.out::println);
				}
			} else {
				// 若没有,从数据库中查询并赋值Redis再返回
				System.out.println("数据是从数据库查询的");
				SnCategoryService categoryService = new SnCategoryServiceImpl();
				List<SnCategory> list = categoryService.findByParentId(1);
				String jsonStr = objectMapper.writeValueAsString(list);
				// 存入redis
				jedis.hset("key1", "1", jsonStr);
				list.forEach(System.out::println);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// 3.使用完记得关闭jedis
		jedis.close();
	}

}
