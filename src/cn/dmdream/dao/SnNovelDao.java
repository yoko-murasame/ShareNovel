package cn.dmdream.dao;

import java.util.List;

import cn.dmdream.entity.SnChapter;
import cn.dmdream.entity.SnNovel;
import cn.dmdream.servlet.ReadOnlineServlet;

/**
 * 小说Dao层接口
 * @author KuluS
 *
 */
public interface SnNovelDao {

	/**
	 * 小说新增
	 * @param snNovel
	 * @return
	 */
	public int save(SnNovel snNovel);
	
	/**
	 * 删除
	 * @param snNovel
	 * @return
	 */
	public int delete(SnNovel snNovel);
	
	/**
	 * 修改
	 * @param snNovel
	 * @return
	 */
	public int update(SnNovel snNovel);
	
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

	
	/**
	 * 
	 * @param cid 类别id
	 * @return 该类别的所有小说
	 */
	public List<SnNovel> queryByCategory(int cid);
	
	
	/**
	 * 根据小说上传者查询
	 * @param userId
	 * @return
	 */
	public List<SnNovel> findByShareUserId(Integer userId);
	
	/**
	 * 根据小说上传者分页查询
	 * @param userId, pageSize, page
	 * @return
	 */
	public List<SnNovel> findByShareUserIdByPage(Integer userId, int pageSize , int page);
	
	/**
	 * 根据小说上传者计数
	 * @param userId
	 * @return
	 */
	public Integer findCountByShareUserId(Integer userId);	
	
	public Integer findCount();

	public Integer findCountByStatus(Integer status);

	/**
	 * 根据小说名查重
	 * @param novelTitle
	 * @return
	 */
	public List<SnNovel> findByTitleStrict(String novelTitle);
	public List<SnNovel> findNewestNovel(Integer size);
}
