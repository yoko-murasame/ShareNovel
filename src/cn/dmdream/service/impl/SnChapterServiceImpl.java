package cn.dmdream.service.impl;

import java.util.List;

import cn.dmdream.dao.SnChapterDao;
import cn.dmdream.dao.impl.SnChapterDaoImpl;
import cn.dmdream.entity.SnChapter;
import cn.dmdream.entity.SnNovel;
import cn.dmdream.service.SnChapterService;

public class SnChapterServiceImpl implements SnChapterService {

	private SnChapterDao chapterDao = new SnChapterDaoImpl();
	/**
	 * 新增
	 * 
	 * @param snChapter
	 * @return
	 */
	public boolean save(SnChapter snChapter){
		int i = chapterDao.save(snChapter);
		return i>0?true:false;
	};

	/**
	 * 删除
	 * 
	 * @param snChapter
	 * @return
	 */
	public boolean delete(SnChapter snChapter){
		int i = chapterDao.delete(snChapter);
		return i>0?true:false;
	};

	/**
	 * 修改
	 * 
	 * @param snChapter
	 * @return
	 */
	public boolean update(SnChapter snChapter){
		int i = chapterDao.update(snChapter);
		return i>0?true:false;
	};

	/**
	 * 主键查询
	 * 
	 * @param id
	 * @return
	 */
	public SnChapter findById(Integer id){
		SnChapter chapter = chapterDao.findById(id);
		return chapter;
	};

	/**
	 * 根据小说查询章节
	 * 
	 * @param snNovel
	 * @return
	 */
	public List<SnChapter> findByNovel(SnNovel snNovel){
		return chapterDao.findByNovel(snNovel);
	};

	/**
	 * 根据小说查询章节分页版
	 * 
	 * @param snNovel
	 * @return
	 */
	public List<SnChapter> findByNovelByPage(SnNovel snNovel, int pageSize, int page){
		return chapterDao.findByNovelByPage(snNovel, pageSize, page);
	};

	/**
	 * 给定小说时,根据章节标题模糊查询
	 * 
	 * @param title
	 * @return
	 */
	public List<SnChapter> findByNovelByChapterTitle(SnNovel snNovel, String title){
		return chapterDao.findByNovelByChapterTitle(snNovel, title);
	};

	/**
	 * 给定小说时,根据章节标题模糊查询分页版
	 * 
	 * @param title
	 * @return
	 */
	public List<SnChapter> findByNovelByChapterTitleByPage(SnNovel snNovel, String title, int pageSize, int page){
		return chapterDao.findByNovelByChapterTitleByPage(snNovel, title, pageSize, page);
	};

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
	public List<SnChapter> findByNovelByOrdersByPage(SnNovel snNovel, String idOrder,String titleOrder,String updatetimeOrder, int pageSize, int page){
		return chapterDao.findByNovelByOrdersByPage(snNovel, idOrder, titleOrder, updatetimeOrder, pageSize, page);
	};

}
