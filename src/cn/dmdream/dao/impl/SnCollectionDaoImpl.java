package cn.dmdream.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.dmdream.dao.SnCollectionDao;
import cn.dmdream.entity.SnChapter;
import cn.dmdream.entity.SnCollection;
import cn.dmdream.entity.SnNovel;
import cn.dmdream.entity.SnUser;
import cn.dmdream.utils.DbUtil;

public class SnCollectionDaoImpl implements SnCollectionDao {

	DbUtil dbUtil = new DbUtil();

	/**
	 * 新增
	 * 
	 * @return
	 */
	public int save(SnCollection collection) {
		String sql = "insert into sn_collection values(null,?,?,?)";
		Integer userId = collection.getCollectUser().getUserId();
		Integer novelId = collection.getCollectNovel().getNovelId();
		Integer chapterId = collection.getCollectChapterHistory().getChapterId();
		int i = dbUtil.update(sql, userId, novelId, chapterId);
		return i;
	};

	/**
	 * 删除对象 类似主键删除
	 * 
	 * @param collection
	 * @return
	 */
	public int delete(SnCollection collection) {
		String sql = "delete from sn_collection where collect_id = ?";
		int i = dbUtil.update(sql, collection.getCollectId());
		return i;
	};

	/**
	 * 主键删除
	 * 
	 * @param id
	 * @return
	 */
	public int delete(Integer id) {
		String sql = "delete from sn_collection where collect_id = ?";
		int i = dbUtil.update(sql, id);
		return i;
	};

	/**
	 * 指定用户,取消指定书本的收藏
	 * 
	 * @param user
	 * @param novel
	 * @return
	 */
	public int deleteByUserByNovel(SnUser user, SnNovel novel) {
		String sql = "delete from sn_collection where collect_userid = ? and collect_novelid = ?";
		int i = dbUtil.update(sql, user.getUserId(), novel.getNovelId());
		return i;
	};

	/**
	 * 根据小说更新用户的阅读章节记录
	 * 
	 * @param user
	 * @param novel
	 * @param chapter
	 * @return
	 */
	public int updateChapterHistoryByNovel(SnUser user, SnNovel novel, SnChapter chapter) {
		String sql = "update sn_collection set collect_chapter_history = ? where collect_userid = ? and collect_novelid = ?";
		int i = dbUtil.update(sql, chapter.getChapterId(), user.getUserId(), novel.getNovelId());
		return i;
	};

	/**
	 * 根据用户和小说 查询用户是否收藏过小说
	 * 
	 * @param user
	 * @param novel
	 * @return 收藏记录 or null
	 */
	public SnCollection findDistinctCollection(SnUser user, SnNovel novel) {
		String sql = "select distinct sn_collection.collect_id,sn_collection.collect_userid,sn_collection.collect_novelid,sn_collection.collect_chapter_history from sn_collection where sn_collection.collect_userid = ? AND sn_collection.collect_novelid = ?";
		ResultSet rs = dbUtil.query(sql, user.getUserId(), novel.getNovelId());
		List<SnCollection> list = new ArrayList<SnCollection>();
		handleData(rs, list);
		return list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 查询用户的所有收藏书籍 可得到最新阅读记录
	 * 
	 * @param user
	 * @return
	 */
	public List<SnCollection> findByUserByPage(SnUser user, int pageSize, int page) {
		String sql = "select sn_collection.collect_id,sn_collection.collect_userid,sn_collection.collect_novelid,sn_collection.collect_chapter_history from sn_collection where sn_collection.collect_userid = ? group by collect_novelid limit ?,?";
		ResultSet rs = dbUtil.query(sql, user.getUserId(), (page - 1) * pageSize, pageSize);
		List<SnCollection> list = new ArrayList<SnCollection>();
		handleData(rs, list);
		return list.size() > 0 ? list : null;
	};
	
	/**
	 * wxw专用查询用户的所有收藏书籍 可得到最新阅读记录
	 * 
	 * @param user
	 * @return
	 */
	public List<SnCollection> wxwfindByUserByPage(SnUser user, int pageSize, int page) {
		String sql = "select sn_collection.*,sn_novel.novel_title,sn_novel.novel_cover,sn_chapter.chapter_title from sn_collection,sn_novel,sn_chapter where sn_collection.collect_novelid = sn_novel.novel_id and sn_collection.collect_chapter_history = sn_chapter.chapter_id and sn_collection.collect_userid = ? group by collect_novelid limit ?,?";
		ResultSet rs = dbUtil.query(sql, user.getUserId(), (page - 1) * pageSize, pageSize);
		List<SnCollection> list = new ArrayList<SnCollection>();
		wxwhandleData(rs, list);
		return list.size() > 0 ? list : null;
	};

	/**
	 * 查询书籍的所有收藏用户
	 * 
	 * @param novel
	 * @return
	 */
	public List<SnCollection> findByNovelByPage(SnNovel novel, int pageSize, int page) {
		String sql = "select sn_collection.collect_id,sn_collection.collect_userid,sn_collection.collect_novelid,sn_collection.collect_chapter_history from sn_collection where sn_collection.collect_novelid = ? group by collect_userid limit ?,?";
		ResultSet rs = dbUtil.query(sql, novel.getNovelId(), (page - 1) * pageSize, pageSize);
		List<SnCollection> list = new ArrayList<SnCollection>();
		handleData(rs, list);
		return list.size() > 0 ? list : null;
	};

	/**
	 * 统计书籍的收藏人数量
	 * 
	 * @param novel
	 * @return
	 */
	public int countByNovel(SnNovel novel) {
		String sql = "SELECT COUNT(*) FROM sn_collection where collect_novelid = ?";
		ResultSet rs = dbUtil.query(sql, novel.getNovelId());
		int i = 0;
		try {
			while(rs.next()){
				i = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	};

	/**
	 * 数据封装
	 * 
	 * @param rs
	 * @param list
	 */
	private void handleData(ResultSet rs, List<SnCollection> list) {
		try {
			while (rs.next()) {
				// 封装user对象id
				SnUser collectUser = new SnUser();
				collectUser.setUserId(rs.getInt("collect_userid"));
				// 封装novel对象id
				SnNovel snNovel = new SnNovel();
				snNovel.setNovelId(rs.getInt("collect_novelid"));
				// 封装chapterHistory对象id
				SnChapter snChapter = new SnChapter();
				snChapter.setChapterId(rs.getInt("collect_chapter_history"));
				// 封装
				SnCollection collection = new SnCollection();
				collection.setCollectId(rs.getInt("collect_id"));
				collection.setCollectUser(collectUser);
				collection.setCollectNovel(snNovel);
				collection.setCollectChapterHistory(snChapter);
				list.add(collection);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	};
	
	/**
	 * wxw专属数据封装
	 * 
	 * @param rs
	 * @param list
	 */
	private void wxwhandleData(ResultSet rs, List<SnCollection> list) {
		try {
			while (rs.next()) {
				// 封装user对象id
				SnUser collectUser = new SnUser();
				collectUser.setUserId(rs.getInt("collect_userid"));
				// 封装novel对象id和title
				SnNovel snNovel = new SnNovel();
				snNovel.setNovelId(rs.getInt("collect_novelid"));
				snNovel.setNovelTitle(rs.getString("novel_title"));
				snNovel.setNovelCover(rs.getString("novel_cover"));
				// 封装chapterHistory对象id
				SnChapter snChapter = new SnChapter();
				snChapter.setChapterId(rs.getInt("collect_chapter_history"));
				snChapter.setChapterTitle(rs.getString("chapter_title"));
				// 封装
				SnCollection collection = new SnCollection();
				collection.setCollectId(rs.getInt("collect_id"));
				collection.setCollectUser(collectUser);
				collection.setCollectNovel(snNovel);
				collection.setCollectChapterHistory(snChapter);
				list.add(collection);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	};
}
