package cn.dmdream.service.impl;

import java.util.List;

import cn.dmdream.dao.SnNovelDao;
import cn.dmdream.dao.impl.SnNovelDaoImpl;
import cn.dmdream.entity.SnNovel;
import cn.dmdream.service.SnNovelService;
import cn.dmdream.utils.JedisUtils;
import redis.clients.jedis.Jedis;

/**
 * 小说Service层实现类
 * @author KuluS
 *
 */
public class SnNovelServiceImpl implements SnNovelService {

	private SnNovelDao snNovelDao = new SnNovelDaoImpl();
	
	/**
	 * 小说新增
	 * @param snNovel
	 * @return
	 */
	public boolean save(SnNovel snNovel){
		try {
			Jedis jedis = JedisUtils.getJedis();
			jedis.del("indexNovelCache");
			jedis.close();
			System.out.println("Redis小说相关缓存已清空");
		} catch (Exception e) {
			e.printStackTrace();
		}
		int i = snNovelDao.save(snNovel);
		return i==1 ? true:false;
	};
	
	/**
	 * 删除
	 * @param snNovel
	 * @return
	 */
	public boolean delete(SnNovel snNovel){
		try {
			Jedis jedis = JedisUtils.getJedis();
			jedis.del("indexNovelCache");
			jedis.close();
			System.out.println("Redis小说相关缓存已清空");
		} catch (Exception e) {
			e.printStackTrace();
		}
		int i = snNovelDao.delete(snNovel);
		return i==1 ? true:false;
	};
	
	/**
	 * 批量删除
	 * @param list
	 * @return
	 */
	public boolean deleteAll(List<SnNovel> list){
		try{
			for (SnNovel snNovel : list) {
				snNovelDao.delete(snNovel);
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	};
	
	/**
	 * 修改
	 * @param snNovel
	 * @return
	 */
	public boolean update(SnNovel snNovel){
		try {
			Jedis jedis = JedisUtils.getJedis();
			jedis.del("indexNovelCache");
			jedis.close();
			System.out.println("Redis小说相关缓存已清空");
		} catch (Exception e) {
			e.printStackTrace();
		}
		int i = snNovelDao.update(snNovel);
		return i==1 ? true:false;
	};
	
	/**
	 * 批量修改
	 * @param list
	 * @return
	 */
	public boolean updateAll(List<SnNovel> list){
		try{
			for (SnNovel snNovel : list) {
				snNovelDao.update(snNovel);
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	};
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public SnNovel findById(Integer id){
		return snNovelDao.findById(id);
	};
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<SnNovel> findAll(){
		return snNovelDao.findAll();
	};
	
	/**
	 * 查询所有分页版
	 * @return
	 */
	public List<SnNovel> findAllByPage(int pageSize , int page){
		return snNovelDao.findAllByPage(pageSize, page);
	};
	
	/**
	 * 根据标题模糊查询（数据库版，后期使用solr）
	 * @param title
	 * @return
	 */
	public List<SnNovel> findByTitle(String title){
		return snNovelDao.findByTitle(title);
	};
	
	/**
	 * 根据作者模糊查询（数据库版，后期使用solr）
	 * @param author
	 * @return
	 */
	public List<SnNovel> findByAuthor(String author){
		return snNovelDao.findByAuthor(author);
	};
	
	/**
	 * 根据标题模糊分页查询（数据库版，后期使用solr）
	 * @param title
	 * @return
	 */
	public List<SnNovel> findByTitleByPage(String title,int pageSize , int page){
		return snNovelDao.findByTitleByPage(title, pageSize, page);
	};
	
	/**
	 * 根据作者模糊分页查询（数据库版，后期使用solr）
	 * @param author
	 * @return
	 */
	public List<SnNovel> findByAuthorByPage(String author,int pageSize , int page){
		return snNovelDao.findByAuthorByPage(author, pageSize, page);
	};
	
	/**
	 * 根据状态查询所有
	 * @param check  0:为审核 1:通过 2:未通过
	 * @return
	 */
	public List<SnNovel> findByCheck(Integer check){
		return snNovelDao.findByCheck(check);
	};
	
	/**
	 * 根据状态查询所有分页版
	 * @param check  0:为审核 1:通过 2:未通过
	 * @return
	 */
	public List<SnNovel> findByCheckByPage(Integer check,int pageSize , int page){
		return snNovelDao.findByCheckByPage(check, pageSize, page);
	}

	@Override
	public List<SnNovel> getWeekRank() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SnNovel> queryByCategory(int cid) {
		// TODO Auto-generated method stub
		return snNovelDao.queryByCategory(cid);
	};
	
	@Override
	public Integer findCount() {
		return snNovelDao.findCount();
	}

	@Override
	public Integer findCountByStatus(Integer status) {
		return snNovelDao.findCountByStatus(status);
	}

	/**
	 * 根据小说名查重
	 * @param novelTitle
	 * @return
	 */
	public List<SnNovel> findByTitleStrict(String novelTitle) {
		return snNovelDao.findByTitleStrict(novelTitle);
	}

	@Override
	public List<SnNovel> findNewestNovel(Integer size) {
		// TODO Auto-generated method stub
		return snNovelDao.findNewestNovel(size);
	}

	/**
	 * 根据小说上传者查询
	 * @param userId
	 * @return
	 */
	@Override
	public List<SnNovel> findByShareUserId(Integer userId){
		return snNovelDao.findByShareUserId(userId);
	}
	
	/**
	 * 根据小说上传者分页查询
	 * @param userId, pageSize, page
	 * @return
	 */
	@Override
	public List<SnNovel> findByShareUserIdByPage(Integer userId, int pageSize , int page){
		return snNovelDao.findByShareUserIdByPage(userId, pageSize, page);
	}
	
	@Override
	public Integer findCountByShareUserId(Integer userId) {
		return snNovelDao.findCountByShareUserId(userId);
	}
	
}
