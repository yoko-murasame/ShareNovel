package cn.dmdream.service;

import java.util.List;

import cn.dmdream.entity.SnChapter;
import cn.dmdream.entity.SnCollection;
import cn.dmdream.entity.SnNovel;
import cn.dmdream.entity.SnUser;

public interface SnCollectionService {

	/**
	 * 新增
	 * @return
	 */
	public boolean save(SnCollection collection);
	
	/**
	 * 删除对象 类似主键删除
	 * @param collection
	 * @return
	 */
	public boolean delete(SnCollection collection);
	
	/**
	 * 主键删除
	 * @param id
	 * @return
	 */
	public boolean delete(Integer id);
	
	/**
	 * 指定用户,取消指定书本的收藏
	 * @param user
	 * @param novel
	 * @return
	 */
	public boolean deleteByUserByNovel(SnUser user,SnNovel novel);
	
	/**
	 * 根据小说更新用户的阅读章节记录
	 * @param user
	 * @param novel
	 * @param chapter
	 * @return
	 */
	public boolean updateChapterHistoryByNovel(SnUser user,SnNovel novel,SnChapter chapter);
	
	/**
	 * 根据用户和小说 查询用户是否收藏过小说
	 * @param user
	 * @param novel
	 * @return
	 */
	public SnCollection findDistinctCollection(SnUser user , SnNovel novel);
	
	/**
	 * 查询用户的所有收藏书籍 可得到最新阅读记录
	 * @param user
	 * @return
	 */
	public List<SnCollection> findByUserByPage(SnUser user ,int pageSize ,int page);
	
	/**
	 * 查询书籍的所有收藏用户
	 * @param novel
	 * @return
	 */
	public List<SnCollection> findByNovelByPage(SnNovel novel , int pageSize , int page);
	
	/**
	 * 统计书籍的收藏人数量
	 * @param novel
	 * @return
	 */
	public int countByNovel(SnNovel novel);
	
	/**
	 * wxw专用查询用户的所有收藏书籍 可得到最新阅读记录
	 * @param user
	 * @return
	 */
	public List<SnCollection> wxwfindByUserByPage(SnUser user ,int pageSize ,int page);
}
