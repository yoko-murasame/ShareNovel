package cn.dmdream.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import cn.dmdream.dao.SnChapterDao;
import cn.dmdream.dao.SnNovelDao;
import cn.dmdream.entity.SnChapter;
import cn.dmdream.entity.SnNovel;
import cn.dmdream.utils.DateTimeUtils;
import cn.dmdream.utils.DbUtil;

public class SnChapterDaoImpl implements SnChapterDao {

	private DbUtil dbUtil = new DbUtil();

	/**
	 * 新增
	 * 
	 * @param snChapter
	 * @return
	 */
	public int save(SnChapter snChapter) {
		String sql = "insert into sn_chapter values(null,?,?,?,?)";
		// 取得外键指向的小说id
		Integer novelId = snChapter.getSnNovel().getNovelId();
		String chapterTitle = snChapter.getChapterTitle();
		String chapterContent = snChapter.getChapterContent();
		String chapterUpdatetime = DateTimeUtils.getCurrentFormatDateTime();
		int i = dbUtil.update(sql, novelId,chapterTitle, chapterContent, chapterUpdatetime);
		return i;
	};

	/**
	 * 删除
	 * 
	 * @param snChapter
	 * @return
	 */
	public int delete(SnChapter snChapter) {
		String sql = "delete from sn_chapter where chapter_id = ?";
		int i = dbUtil.update(sql, snChapter.getChapterId());
		return i;
	};

	/**
	 * 修改
	 * 
	 * @param snChapter
	 * @return
	 */
	public int update(SnChapter snChapter) {
		String sql = "update sn_chapter set chapter_novelid = ?,chapter_title=?,chapter_content=?,chapter_updatetime=? where chapter_id=?";
		// 取得外键指向的小说id
		Integer novelId = snChapter.getSnNovel().getNovelId();
		String chapterTitle = snChapter.getChapterTitle();
		String chapterContent = snChapter.getChapterContent();
		String chapterUpdatetime = DateTimeUtils.getCurrentFormatDateTime();
		Integer chapterId = snChapter.getChapterId();
		int i = dbUtil.update(sql, novelId,chapterTitle, chapterContent, chapterUpdatetime, chapterId);
		return i;
	};

	/**
	 * 主键查询
	 * 
	 * @param id
	 * @return
	 */
	public SnChapter findById(Integer id) {
		String sql = "select * from sn_chapter where chapter_id = ?";
		ResultSet rs = dbUtil.query(sql, id);
		List<SnChapter> list = new ArrayList<SnChapter>();
		handleData(rs, list);
		return list.size() > 0 ? list.get(0) : null;
	}
	/**
	 * 根据小说查询章节
	 * 
	 * @param snNovel
	 * @return
	 */
	public List<SnChapter> findByNovel(SnNovel snNovel) {
		String sql = "select * from sn_chapter where chapter_novelid = ?";
		ResultSet rs = dbUtil.query(sql, snNovel.getNovelId());
		List<SnChapter> list = new ArrayList<SnChapter>();
		handleData(rs, list);
		return list.size() > 0 ? list : null;
	};
	/**
	 * 根据小说查询章节分页版
	 * 
	 * @param snNovel
	 * @return 只返回 小说章节标题 和id
	 */
	public List<SnChapter> queryByNovelByPage(SnNovel snNovel, int pageSize, int page) {
		if (page <= 0)
			page = 1;
		String sql = "select chapter_id,chapter_title from sn_chapter where chapter_novelid = ? order by chapter_id limit ?,?";
		ResultSet rs = dbUtil.query(sql, snNovel.getNovelId(), pageSize * (page - 1), pageSize);
		List<SnChapter> list = new ArrayList<SnChapter>();
		try {
			while (rs.next()) {
				SnChapter chapter = new SnChapter();
				Integer chapterId = rs.getInt("chapter_id");
				String chapterTitle = rs.getString("chapter_title");
				chapter.setChapterId(chapterId);
				chapter.setChapterTitle(chapterTitle);
				list.add(chapter);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list.size() > 0 ? list : null;
	};

	/**
	 * 给定小说时,根据章节标题模糊查询
	 * 
	 * @param title
	 * @return
	 */
	public List<SnChapter> findByNovelByChapterTitle(SnNovel snNovel, String title) {
		String sql = "select * from sn_chapter where chapter_novelid = ? and chapter_title like ?";
		ResultSet rs = dbUtil.query(sql, snNovel.getNovelId(), "%" + title + "%");
		List<SnChapter> list = new ArrayList<SnChapter>();
		handleData(rs, list);
		return list.size() > 0 ? list : null;
	};

	/**
	 * 给定小说时,根据章节标题模糊查询分页版
	 * 
	 * @param title
	 * @return
	 */
	public List<SnChapter> findByNovelByChapterTitleByPage(SnNovel snNovel, String title, int pageSize, int page) {
		String sql = "select * from sn_chapter where chapter_novelid = ? and chapter_title like ? limit ?,?";
		ResultSet rs = dbUtil.query(sql, snNovel.getNovelId(), "%" + title + "%", (page - 1) * pageSize, pageSize);
		List<SnChapter> list = new ArrayList<SnChapter>();
		handleData(rs, list);
		return list.size() > 0 ? list : null;
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
	public List<SnChapter> findByNovelByOrdersByPage(SnNovel snNovel, String idOrder,String titleOrder,String updatetimeOrder, int pageSize, int page) {
		//语句优化
		String sql = "SELECT " +
		"sn_chapter.chapter_id,"+
		"sn_chapter.chapter_novelid,"+
		"sn_chapter.chapter_title,"+
		"sn_chapter.chapter_content,"+
		"sn_chapter.chapter_updatetime "+
		"FROM "+
		"sn_chapter "+
		"WHERE "+
		"sn_chapter.chapter_novelid = ? "+
		(idOrder!=null || titleOrder !=null || updatetimeOrder != null ? "ORDER BY " : "")
		+
		(idOrder!=null ? "sn_chapter.chapter_id "+idOrder : "")
		+	
		(idOrder!=null && (titleOrder !=null||updatetimeOrder!=null) ? "," : "")
		+
		(titleOrder!=null ? "sn_chapter.chapter_title "+titleOrder : "")
		+
		(idOrder!=null && titleOrder !=null && updatetimeOrder!=null ? "," : "")
		+
		(updatetimeOrder!=null ? "sn_chapter.chapter_updatetime "+updatetimeOrder+" " : " ")
		+
		"LIMIT ?, ?";
		System.out.println(sql);
		ResultSet rs = dbUtil.query(sql, snNovel.getNovelId(), (page - 1) * pageSize, pageSize);
		List<SnChapter> list = new ArrayList<SnChapter>();
		handleData(rs, list);
		return list.size() > 0 ? list : null;
	};


	/**
	 * 封装数据
	 * 
	 * @param rs
	 * @param list
	 */
	private void handleData(ResultSet rs, List<SnChapter> list) {
		try {
			while (rs.next()) {
				SnChapter chapter = new SnChapter();
				Integer chapterId = rs.getInt("chapter_id");
				// 获取外键id查询小说主体并封装,由于效率较低,因此不查询实体封装了
				Integer novelId = rs.getInt("chapter_novelid");
				// SnNovel chapterNovel = snNovelDao.findById(novelId);
				SnNovel chapterNovel = new SnNovel();
				chapterNovel.setNovelId(novelId);
				String chapterTitle = rs.getString("chapter_title");
				String chapterContent = rs.getString("chapter_content");
				// 获取章节最近的更近时间
				String chapterUpdatetime = rs.getString("chapter_updatetime");
				// 封装数据
				chapter.setChapterId(chapterId);
				chapter.setSnNovel(chapterNovel);
				chapter.setChapterTitle(chapterTitle);
				chapter.setChapterContent(chapterContent);
				chapter.setChapterUpdatetime(chapterUpdatetime);
				list.add(chapter);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<SnChapter> findRecentUpdate(Long time,Integer limit) {
		Date since=new java.util.Date(new java.util.Date().getTime()-time);
		CachedRowSet rs;

		//多表查询
		String sql1="select sn_chapter.chapter_id,sn_chapter.chapter_novelid,sn_chapter.chapter_title,sn_chapter.chapter_updatetime,sn_novel.novel_title,sn_novel.novel_author from sn_chapter join sn_novel where sn_chapter.chapter_novelid=sn_novel.novel_id and chapter_updatetime>=? order by chapter_updatetime DESC";
		if(limit==null) {
			rs = dbUtil.query(sql1,since);			
		}else {
			sql1+=" limit 0,?";
			rs = dbUtil.query(sql1, since,limit);	
		}
		List<SnChapter> list = new ArrayList<SnChapter>();
		try {
			while (rs.next()) {
				SnChapter chapter = new SnChapter();
				Integer chapterId = rs.getInt("chapter_id");
				// 获取外键id查询小说主体并封装,由于效率较低,因此不查询实体封装了
				Integer novelId = rs.getInt("chapter_novelid");
				// SnNovel chapterNovel = snNovelDao.findById(novelId);
				SnNovel chapterNovel = new SnNovel();
				chapterNovel.setNovelId(novelId);
				String chapterTitle = rs.getString("chapter_title");
				// 获取章节最近的更近时间
				String chapterUpdatetime = rs.getString("chapter_updatetime");
				String noveltitle=rs.getString("novel_title");
				String novelauthor=rs.getString("novel_author");
				// 封装数据
				chapter.setChapterId(chapterId);
				chapter.setSnNovel(chapterNovel);
				chapter.setChapterTitle(chapterTitle);
				chapter.setChapterUpdatetime(chapterUpdatetime);
				chapter.getSnNovel().setNovelAuthor(novelauthor);
				chapter.getSnNovel().setNovelTitle(noveltitle);
				list.add(chapter);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list.size() > 0 ? list : null;
	}

	@Override
	public List<SnChapter> findRecentUpdate(SnNovel novel, Long time, Integer limit) {
		Date since=new java.util.Date(new java.util.Date().getTime()-time);
		String sql="select * from sn_chapter where chapter_novelid=? and chapter_updatetime>=? order by chapter_updatetime desc";
		List<SnChapter> newshapter=new ArrayList<SnChapter>();
		CachedRowSet query;
		if(limit==null) {	
			query=dbUtil.query(sql,novel.getNovelId(),since);
			
		}else {
			sql+=" limit 0,?";
			query = dbUtil.query(sql,novel.getNovelId(),since,limit);
		}
		handleData(query,newshapter);
		return newshapter.size()>0?newshapter:null;
	}

	@Override
	public int findNovelChapterTotalCount(SnNovel novel) {
		String sql="select count(chapter_novelid) from sn_chapter where chapter_novelid = ?";
		CachedRowSet query = dbUtil.query(sql,novel.getNovelId());
		int ret=0;
		try {
			if(query.next()) 
			ret=query.getInt(1);
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

}
