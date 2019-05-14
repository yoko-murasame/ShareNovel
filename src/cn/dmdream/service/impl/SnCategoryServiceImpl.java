package cn.dmdream.service.impl;

import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.dmdream.dao.SnCategoryDao;
import cn.dmdream.dao.impl.SnCategoryDaoImpl;
import cn.dmdream.entity.SnCategory;
import cn.dmdream.service.SnCategoryService;
import cn.dmdream.utils.JedisUtils;
import redis.clients.jedis.Jedis;

public class SnCategoryServiceImpl implements SnCategoryService {

	private SnCategoryDao snCategoryDao = new SnCategoryDaoImpl();
	private Jedis jedis = JedisUtils.getJedis();
	private ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 新增分类
	 * 
	 * @param snCategory
	 * @return
	 */
	public boolean save(SnCategory snCategory) {
		int i = snCategoryDao.save(snCategory);
		if (i == 1) {
			// Redis
			// 进入新增/修改/删除成功后清空Redis中的缓存
			jedis.hdel("categoryListMap", "" + snCategory.getCatParentid());
			System.out.println("父id为:" + snCategory.getCatParentid() + "的hash表记录已删除!");
		}
		return i == 1 ? true : false;
	};

	/**
	 * 删除分类
	 * 
	 * @param snCategory
	 * @return
	 */
	public boolean delete(SnCategory snCategory) {
		int i = snCategoryDao.delete(snCategory);
		if (i == 1) {
			// Redis
			// 进入新增/修改/删除成功后清空Redis中的缓存
			jedis.hdel("categoryListMap", "" + snCategory.getCatParentid());
			System.out.println("父id为:" + snCategory.getCatParentid() + "的hash表记录已删除!");
		}
		return i == 1 ? true : false;
	};

	/**
	 * 修改分类
	 * 
	 * @param snCategory
	 * @return
	 */
	public boolean update(SnCategory snCategory) {
		int i = snCategoryDao.update(snCategory);
		if (i == 1) {
			// Redis
			// 进入新增/修改/删除成功后清空Redis中的缓存
			jedis.hdel("categoryListMap", "" + snCategory.getCatParentid());
			System.out.println("父id为:" + snCategory.getCatParentid() + "的hash表记录已删除!");
		}
		return i == 1 ? true : false;
	};

	/**
	 * 查询所有父分类
	 * 
	 * @return
	 */
	public List<SnCategory> findAllParent() {
		return snCategoryDao.findAllParent();
	};

	/**
	 * 根据主键查询
	 * 
	 * @param id
	 * @return
	 */
	public SnCategory findById(Integer id) {
		return snCategoryDao.findById(id);
	};

	/**
	 * 查询改父分类id下的所有子分类
	 * 
	 * @return
	 */
	public List<SnCategory> findByParentId(Integer id) {

		try {
			// 先查询redis 若有,返回
			String listStr = jedis.hget("categoryListMap", id + "");
			if (listStr != null) {
				System.out.println("数据是从Redis中查询的");
				List<SnCategory> list = objectMapper.readValue(listStr, new TypeReference<List<SnCategory>>() {
				});
				return list;
			} else {
				// 若没有,从数据库中查询并赋值Redis再返回
				System.out.println("数据是从数据库查询的");
				List<SnCategory> list = snCategoryDao.findByParentId(id);
				String jsonStr = objectMapper.writeValueAsString(list);
				// 存入redis
				jedis.hset("categoryListMap", id + "", jsonStr);
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	};

	/**
	 * 根据分类名模糊查询
	 * 
	 * @param catName
	 * @return
	 */
	public List<SnCategory> findByCatName(String catName) {
		return snCategoryDao.findByCatName(catName);
	};

	/**
	 * 根据男频0/1女频分类查询
	 * 
	 * @param gender
	 * @return
	 */
	public List<SnCategory> findByCatGender(Integer gender) {
		return snCategoryDao.findByCatGender(gender);
	};

	/**
	 * 查询改父分类id下的所有子分类分页版
	 * 
	 * @return
	 */
	public List<SnCategory> findByParentIdByPage(int id, int pageSize, int page) {
		List<SnCategory> list = null;
		try {
			// 先查询redis 若有,返回redis数据,若无,返回数据库数据
			String listStr = jedis.hget("categoryListMap", id + "");
			if (listStr != null) {
				System.out.println("数据是从Redis中查询的");
				list = objectMapper.readValue(listStr, new TypeReference<List<SnCategory>>() {
				});
			} else {
				// 若没有,从数据库中查询并赋值Redis再返回
				System.out.println("数据是从数据库查询的");
				// bug 这里需要查询所有对象,存入数据库的也需要是所有对象
				list = snCategoryDao.findByParentId(id);
				String jsonStr = objectMapper.writeValueAsString(list);
				// 存入redis
				jedis.hset("categoryListMap", id + "", jsonStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return JedisUtils.getRedisListByPage(pageSize, page, list);
	}

	public Integer findCount(Integer id) {
		return snCategoryDao.findCount(id);
	}

	@Override
	public List<SnCategory> queryAll() {
		// TODO Auto-generated method stub
		return snCategoryDao.queryAll();
	};
}
