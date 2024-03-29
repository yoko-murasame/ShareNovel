package cn.dmdream.dao;

import java.util.List;

import cn.dmdream.entity.SnChapter;
import cn.dmdream.entity.SnNovel;

public interface SnChapterDao {

	public String ASC = "asc";
	public String DESC = "desc";

	/**
	 * 新增
	 * 
	 * @param snChapter
	 * @return
	 */
	public int save(SnChapter snChapter);

	/**
	 * 删除
	 * 
	 * @param snChapter
	 * @return
	 */
	public int delete(SnChapter snChapter);

	/**
	 * 修改
	 * 
	 * @param snChapter
	 * @return
	 */
	public int update(SnChapter snChapter);

	/**
	 * 主键查询
	 * 
	 * @param id
	 * @return
	 */
	public SnChapter findById(Integer id);

	/**
	 * 根据小说查询章节
	 * 
	 * @param snNovel
	 * @return
	 */
	public List<SnChapter> findByNovel(SnNovel snNovel);

	/**
	 * 根据小说查询章节分页版
	 * 
	 * @param snNovel
	 * @return
	 */
	public List<SnChapter> queryByNovelByPage(SnNovel snNovel, int pageSize, int page);

	/**
	 * 给定小说时,根据章节标题模糊查询
	 * 
	 * @param title
	 * @return
	 */
	public List<SnChapter> findByNovelByChapterTitle(SnNovel snNovel, String title);

	/**
	 * 给定小说时,根据章节标题模糊查询分页版
	 * 
	 * @param title
	 * @return
	 */
	public List<SnChapter> findByNovelByChapterTitleByPage(SnNovel snNovel, String title, int pageSize, int page);

	/**
	 * 给定小说时,根据优先级如下
	 * @param snNovel
	 * @param idOrder 1.主键的升序asc/降序desc 可以为null,代表不设置
	 * @param titleOrder 2.章节标题的升序asc/降序desc 可以为null,代表不设置
	 * @param updatetimeOrder 3.日期的升序asc/降序desc 可以为null,代表不设置
	 * @param pageSize
	 * @param page
	 * @return 分页查询
	 */
	public List<SnChapter> findByNovelByOrdersByPage(SnNovel snNovel, String idOrder,String titleOrder,String updatetimeOrder, int pageSize, int page);
	/**
	 * 获取n毫秒更新的 所有小说章节记录
	 * @param  time 单位毫秒 到现在的时间间隔 必须大于0
	 * @param  limit 前n条记录 n可以为空 ,为空则获取前n天更新的所有记录
	 * @return 最近更新的小说章节
	 */
	public List<SnChapter> findRecentUpdate(Long time,Integer limit);
	/**
	 * 获取n毫秒前 到现在 更新的某本小说的新章节
	 * @param  time 单位毫秒 到现在的时间间隔 必须大于0
	 * @param  limit 前n条记录 n可以为空 ,为空则获取前n天更新的所有记录
	 * @return 最近更新的小说章节
	 */
	public List<SnChapter> findRecentUpdate(SnNovel novel,Long time,Integer limit);
	/**
	 * 获取某本小说 的章节总数
	 * @param  novel 指定小说
	 * @return 章节数
	 */
	public int findNovelChapterTotalCount(SnNovel novel);

}
