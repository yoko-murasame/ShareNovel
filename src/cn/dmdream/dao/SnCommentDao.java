package cn.dmdream.dao;

import java.util.List;

import cn.dmdream.entity.SnComment;
import cn.dmdream.entity.SnNovel;
import cn.dmdream.entity.SnUser;

/**
 * SnComment用户评论Dao层接口
 * @author KuluS
 *
 */
public interface SnCommentDao {

	/**
	 * 新增
	 * @param comment
	 * @return
	 */
	public int save(SnComment comment);
	
	/**
	 * 根据对象删除,删除父评论会删除子评论
	 * @param comment
	 * @return
	 */
	public int delete(SnComment comment);
	
	/**
	 * 根据主键删除
	 * @param id
	 * @return
	 */
	public int deleteById(Integer id);
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public SnComment findById(Integer id);
	
	/**
	 * 更新
	 * @param comment
	 * @return
	 */
	public int update(SnComment comment);
	
	/**
	 * 根据用户查询所有评论
	 * @param user
	 * @return
	 */
	public List<SnComment> findByUser(SnUser user);
	
	/**
	 * 根据用户查询所有评论,分页版
	 * @param user
	 * @return
	 */
	public List<SnComment> findByUserByPage(SnUser user , int pageSize , int page);
	
	/**
	 * 根据小说查询所有评论
	 * @param novel
	 * @return
	 */
	public List<SnComment> findByNovel(SnNovel novel);
	
	/**
	 * 根据小说查询所有评论,分页版
	 * @param novel
	 * @return
	 */
	public List<SnComment> findByNovelByPage(SnNovel novel , int pageSize , int page);
	
	/**
	 * 查询父评论下的所有子评论
	 * @param comment
	 * @return
	 */
	
	public List<SnComment> findAllChildComment(SnComment comment);
	
	/**
	 * 查询父评论下的所有子评论,分页版
	 * @param comment
	 * @param pageSize
	 * @param page
	 * @return
	 */
	public List<SnComment> findAllChildCommentByPage(SnComment comment , int pageSize , int page);
	public List<SnComment> queryByUser(SnUser user);

}
