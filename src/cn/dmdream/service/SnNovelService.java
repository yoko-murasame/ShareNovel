package cn.dmdream.service;

import java.util.List;

import cn.dmdream.entity.SnNovel;

/**
 * 小说Service层接口
 * @author KuluS
 *
 */
public interface SnNovelService {

	/**
	 * 小说新增
	 * @param snNovel
	 * @return
	 */
	public boolean save(SnNovel snNovel);
	
	/**
	 * 删除
	 * @param snNovel
	 * @return
	 */
	public boolean delete(SnNovel snNovel);
	
	/**
	 * 批量删除
	 * @param list
	 * @return
	 */
	public boolean deleteAll(List<SnNovel> list);
	
	/**
	 * 修改
	 * @param snNovel
	 * @return
	 */
	public boolean update(SnNovel snNovel);
	
	/**
	 * 批量修改
	 * @param list
	 * @return
	 */
	public boolean updateAll(List<SnNovel> list);
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public SnNovel findById(Integer id);
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<SnNovel> findAll();
	
	/**
	 * 查询所有分页版
	 * @return
	 */
	public List<SnNovel> findAllByPage(int pageSize , int page);
	
	/**
	 * 根据标题模糊查询（数据库版，后期使用solr）
	 * @param title
	 * @return
	 */
	public List<SnNovel> findByTitle(String title);
	
	/**
	 * 根据作者模糊查询（数据库版，后期使用solr）
	 * @param author
	 * @return
	 */
	public List<SnNovel> findByAuthor(String author);
	
	/**
	 * 根据标题模糊分页查询（数据库版，后期使用solr）
	 * @param title
	 * @return
	 */
	public List<SnNovel> findByTitleByPage(String title,int pageSize , int page);
	
	/**
	 * 根据作者模糊分页查询（数据库版，后期使用solr）
	 * @param author
	 * @return
	 */
	public List<SnNovel> findByAuthorByPage(String author,int pageSize , int page);
	
	/**
	 * 根据状态查询所有
	 * @param check  0:为审核 1:通过 2:未通过
	 * @return
	 */
	public List<SnNovel> findByCheck(Integer check);
	
	/**
	 * 根据状态查询所有分页版
	 * @param check  0:为审核 1:通过 2:未通过
	 * @return
	 */
	public List<SnNovel> findByCheckByPage(Integer check,int pageSize , int page);
	public List<SnNovel> getWeekRank();
}
