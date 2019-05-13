package cn.dmdream.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;

import cn.dmdream.dao.SnCategoryDao;
import cn.dmdream.dao.SnNovelDao;
import cn.dmdream.dao.SnUserDao;
import cn.dmdream.entity.SnCategory;
import cn.dmdream.entity.SnNovel;
import cn.dmdream.entity.SnUser;
import cn.dmdream.utils.DateTimeUtils;
import cn.dmdream.utils.DbUtil;
import cn.dmdream.utils.StringUtils;

/**
 * 小说Dao层接口实现类
 * 
 * @author KuluS
 *
 */
public class SnNovelDaoImpl implements SnNovelDao {

	DbUtil dbUtil = new DbUtil();
	SnCategoryDao snCategoryDao = new SnCategoryDaoImpl();
	SnUserDao snUserDao = new SnUserDaoImpl();

	/**
	 * 小说新增
	 * 
	 * @param snNovel
	 * @return
	 */
	public int save(SnNovel snNovel) {

		String sql = "insert into sn_novel values(null,?,?,?,?,?,?,?,?,?,?)";
		String novelTitle = snNovel.getNovelTitle();
		String novelAuthor = snNovel.getNovelAuthor();
		// 获取category的id
		Integer catId = snNovel.getSnCategory().getCatId();
		String updateTime = DateTimeUtils.getCurrentFormatDateTime();// 在dao层每一次插入时设置最新时间
		Integer novelIsEnd = snNovel.getNovelIsEnd();
		String novelSummary = snNovel.getNovelSummary();
		// 获取外键-分享者id
		Integer userId = snNovel.getNovelShareUser().getUserId();
		String novelDownloadurl = snNovel.getNovelDownloadurl().toString();
		Integer novelCheck = snNovel.getNovelCheck();
		String novelCover = snNovel.getNovelCover();

		int i = dbUtil.update(sql, novelTitle, novelAuthor, catId, updateTime, novelIsEnd, novelSummary, userId,
				novelDownloadurl, novelCheck, novelCover);

		return i;
	}

	/**
	 * 删除
	 */
	public int delete(SnNovel snNovel) {
		String sql = "delete from sn_novel where novel_id = ?";
		int i = dbUtil.update(sql, snNovel.getNovelId());
		return i;
	}

	/**
	 * 修改
	 * 
	 * @param snNovel
	 * @return
	 */
	public int update(SnNovel snNovel) {
		String sql = "update sn_novel set novel_title=?,novel_author=?,novel_categoryid=?,novel_updatetime=?,novel_isEnd=?,novel_summary=?,novel_share_userid=?,novel_downloadurl=?,novel_check=?,novel_cover=? where novel_id=?";

		String novelTitle = snNovel.getNovelTitle();
		String novelAuthor = snNovel.getNovelAuthor();
		// 获取category的id
		Integer catId = snNovel.getSnCategory().getCatId();
		// 在dao层每一次修改时设置最新时间
		String updateTime = DateTimeUtils.getCurrentFormatDateTime();
		Integer novelIsEnd = snNovel.getNovelIsEnd();
		String novelSummary = snNovel.getNovelSummary();
		// 获取外键-分享者id
		Integer userId = snNovel.getNovelShareUser().getUserId();
		String novelDownloadurl = snNovel.getNovelDownloadurl().toString();
		Integer novelCheck = snNovel.getNovelCheck();
		String novelCover = snNovel.getNovelCover();

		int i = dbUtil.update(sql, novelTitle, novelAuthor, catId, updateTime, novelIsEnd, novelSummary, userId,
				novelDownloadurl, novelCheck, novelCover, snNovel.getNovelId());
		return i;
	}

	/**
	 * 根据主键查询
	 * 
	 * @param id
	 * @return
	 */
	public SnNovel findById(Integer id) {
		String sql = "SELECT sn_novel.novel_id,sn_novel.novel_title,sn_novel.novel_author,sn_novel.novel_categoryid,sn_novel.novel_updatetime,sn_novel.novel_isEnd,sn_novel.novel_summary,sn_novel.novel_share_userid,sn_novel.novel_downloadurl,sn_novel.novel_check,sn_novel.novel_cover,sn_category.cat_name,sn_category.cat_parentid,sn_user.user_nickname,sn_user.user_nickpic FROM sn_novel INNER JOIN sn_category ON sn_novel.novel_categoryid = sn_category.cat_id INNER JOIN sn_user ON sn_novel.novel_share_userid = sn_user.user_id"+" where novel_id = ?";
		RowSet rs = dbUtil.query(sql, id);
		List<SnNovel> list = new ArrayList<SnNovel>();
		handleData(rs, list);
		return list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 查询所有,包括未审核的
	 */
	public List<SnNovel> findAll() {
		String sql = "SELECT sn_novel.novel_id,sn_novel.novel_title,sn_novel.novel_author,sn_novel.novel_categoryid,sn_novel.novel_updatetime,sn_novel.novel_isEnd,sn_novel.novel_summary,sn_novel.novel_share_userid,sn_novel.novel_downloadurl,sn_novel.novel_check,sn_novel.novel_cover,sn_category.cat_name,sn_category.cat_parentid,sn_user.user_nickname,sn_user.user_nickpic FROM sn_novel INNER JOIN sn_category ON sn_novel.novel_categoryid = sn_category.cat_id INNER JOIN sn_user ON sn_novel.novel_share_userid = sn_user.user_id";
		RowSet rs = dbUtil.query(sql);
		List<SnNovel> list = new ArrayList<SnNovel>();
		handleData(rs, list);
		return list.size() > 0 ? list : null;
	}

	/**
	 * 查询所有分页版
	 * 
	 * @return
	 */
	public List<SnNovel> findAllByPage(int pageSize, int page) {
		if (page < 0)
			page = 1;
		String sql = "SELECT sn_novel.novel_id,sn_novel.novel_title,sn_novel.novel_author,sn_novel.novel_categoryid,sn_novel.novel_updatetime,sn_novel.novel_isEnd,sn_novel.novel_summary,sn_novel.novel_share_userid,sn_novel.novel_downloadurl,sn_novel.novel_check,sn_novel.novel_cover,sn_category.cat_name,sn_category.cat_parentid,sn_user.user_nickname,sn_user.user_nickpic FROM sn_novel INNER JOIN sn_category ON sn_novel.novel_categoryid = sn_category.cat_id INNER JOIN sn_user ON sn_novel.novel_share_userid = sn_user.user_id"+" limit ?,?";
		RowSet rs = dbUtil.query(sql, (page - 1) * pageSize, pageSize);
		List<SnNovel> list = new ArrayList<SnNovel>();
		handleData(rs, list);
		return list.size() > 0 ? list : null;
	}

	/**
	 * 根据标题模糊查询（数据库版，后期使用solr）
	 * 
	 * @param title
	 * @return
	 */
	public List<SnNovel> findByTitle(String title) {
		String sql = "SELECT sn_novel.novel_id,sn_novel.novel_title,sn_novel.novel_author,sn_novel.novel_categoryid,sn_novel.novel_updatetime,sn_novel.novel_isEnd,sn_novel.novel_summary,sn_novel.novel_share_userid,sn_novel.novel_downloadurl,sn_novel.novel_check,sn_novel.novel_cover,sn_category.cat_name,sn_category.cat_parentid,sn_user.user_nickname,sn_user.user_nickpic FROM sn_novel INNER JOIN sn_category ON sn_novel.novel_categoryid = sn_category.cat_id INNER JOIN sn_user ON sn_novel.novel_share_userid = sn_user.user_id"+" where novel_title like ?";
		RowSet rs = dbUtil.query(sql, "%" + title + "%");
		List<SnNovel> list = new ArrayList<SnNovel>();
		handleData(rs, list);
		return list.size() > 0 ? list : null;
	}

	/**
	 * 根据作者模糊查询（数据库版，后期使用solr）
	 * 
	 * @param author
	 * @return
	 */
	public List<SnNovel> findByAuthor(String author) {
		String sql = "SELECT sn_novel.novel_id,sn_novel.novel_title,sn_novel.novel_author,sn_novel.novel_categoryid,sn_novel.novel_updatetime,sn_novel.novel_isEnd,sn_novel.novel_summary,sn_novel.novel_share_userid,sn_novel.novel_downloadurl,sn_novel.novel_check,sn_novel.novel_cover,sn_category.cat_name,sn_category.cat_parentid,sn_user.user_nickname,sn_user.user_nickpic FROM sn_novel INNER JOIN sn_category ON sn_novel.novel_categoryid = sn_category.cat_id INNER JOIN sn_user ON sn_novel.novel_share_userid = sn_user.user_id"+" where novel_author like ?";
		RowSet rs = dbUtil.query(sql, "%" + author + "%");
		List<SnNovel> list = new ArrayList<SnNovel>();
		handleData(rs, list);
		return list.size() > 0 ? list : null;
	}

	/**
	 * 根据标题模糊分页查询（数据库版，后期使用solr）
	 * 
	 * @param title
	 * @return
	 */
	public List<SnNovel> findByTitleByPage(String title, int pageSize, int page) {
		String sql = "SELECT sn_novel.novel_id,sn_novel.novel_title,sn_novel.novel_author,sn_novel.novel_categoryid,sn_novel.novel_updatetime,sn_novel.novel_isEnd,sn_novel.novel_summary,sn_novel.novel_share_userid,sn_novel.novel_downloadurl,sn_novel.novel_check,sn_novel.novel_cover,sn_category.cat_name,sn_category.cat_parentid,sn_user.user_nickname,sn_user.user_nickpic FROM sn_novel INNER JOIN sn_category ON sn_novel.novel_categoryid = sn_category.cat_id INNER JOIN sn_user ON sn_novel.novel_share_userid = sn_user.user_id"+" where novel_title like ? limit ?,?";
		RowSet rs = dbUtil.query(sql, "%" + title + "%", (page - 1) * pageSize, pageSize);
		List<SnNovel> list = new ArrayList<SnNovel>();
		handleData(rs, list);
		return list.size() > 0 ? list : null;
	}

	/**
	 * 根据作者模糊分页查询（数据库版，后期使用solr）
	 * 
	 * @param author
	 * @return
	 */
	public List<SnNovel> findByAuthorByPage(String author, int pageSize, int page) {
		String sql = "SELECT sn_novel.novel_id,sn_novel.novel_title,sn_novel.novel_author,sn_novel.novel_categoryid,sn_novel.novel_updatetime,sn_novel.novel_isEnd,sn_novel.novel_summary,sn_novel.novel_share_userid,sn_novel.novel_downloadurl,sn_novel.novel_check,sn_novel.novel_cover,sn_category.cat_name,sn_category.cat_parentid,sn_user.user_nickname,sn_user.user_nickpic FROM sn_novel INNER JOIN sn_category ON sn_novel.novel_categoryid = sn_category.cat_id INNER JOIN sn_user ON sn_novel.novel_share_userid = sn_user.user_id"+" where novel_author like ? limit ?,?";
		RowSet rs = dbUtil.query(sql, "%" + author + "%", (page - 1) * pageSize, pageSize);
		List<SnNovel> list = new ArrayList<SnNovel>();
		handleData(rs, list);
		return list.size() > 0 ? list : null;
	}

	/**
	 * 根据状态查询所有
	 * 
	 * @param check
	 *            0:为审核 1:通过 2:未通过
	 * @return
	 */
	public List<SnNovel> findByCheck(Integer check) {
		String sql = "SELECT sn_novel.novel_id,sn_novel.novel_title,sn_novel.novel_author,sn_novel.novel_categoryid,sn_novel.novel_updatetime,sn_novel.novel_isEnd,sn_novel.novel_summary,sn_novel.novel_share_userid,sn_novel.novel_downloadurl,sn_novel.novel_check,sn_novel.novel_cover,sn_category.cat_name,sn_category.cat_parentid,sn_user.user_nickname,sn_user.user_nickpic FROM sn_novel INNER JOIN sn_category ON sn_novel.novel_categoryid = sn_category.cat_id INNER JOIN sn_user ON sn_novel.novel_share_userid = sn_user.user_id"+" where novel_check = ?";
		RowSet rs = dbUtil.query(sql, check);
		List<SnNovel> list = new ArrayList<SnNovel>();
		handleData(rs, list);
		return list.size() > 0 ? list : null;
	}

	/**
	 * 根据状态查询所有分页版
	 * 
	 * @param check
	 *            0:为审核 1:通过 2:未通过
	 * @return
	 */
	public List<SnNovel> findByCheckByPage(Integer check, int pageSize, int page) {
		String sql = "SELECT sn_novel.novel_id,sn_novel.novel_title,sn_novel.novel_author,sn_novel.novel_categoryid,sn_novel.novel_updatetime,sn_novel.novel_isEnd,sn_novel.novel_summary,sn_novel.novel_share_userid,sn_novel.novel_downloadurl,sn_novel.novel_check,sn_novel.novel_cover,sn_category.cat_name,sn_category.cat_parentid,sn_user.user_nickname,sn_user.user_nickpic FROM sn_novel INNER JOIN sn_category ON sn_novel.novel_categoryid = sn_category.cat_id INNER JOIN sn_user ON sn_novel.novel_share_userid = sn_user.user_id"
				+ " where novel_check = ? limit ?,?";
		RowSet rs = dbUtil.query(sql, check, (page - 1) * pageSize, pageSize);
		List<SnNovel> list = new ArrayList<SnNovel>();
		handleData(rs, list);
		return list.size() > 0 ? list : null;
	}

	/**
	 * 封装数据
	 * 
	 * @param rs
	 * @param list
	 */
	private void handleData(RowSet rs, List<SnNovel> list) {
		try {
			while (rs.next()) {
				SnNovel snNovel = new SnNovel();

				Integer novelId = rs.getInt("novel_id");
				String novelTitle = rs.getString("novel_title");
				String novelAuthor = rs.getString("novel_author");

				// 从 ->查询cat对象 -> 创建cat对象
				SnCategory snCategory = new SnCategory();
				// 修改->查询catId和catName
				Integer snCategoryId = rs.getInt("novel_categoryid");
				Integer catParentid = rs.getInt("cat_parentid");
				String catName = rs.getString("cat_name");
				snCategory.setCatId(snCategoryId);
				snCategory.setCatName(catName);
				snCategory.setCatParentid(catParentid);

				// 时间需要查询
				String novelUpdatetime = rs.getString("novel_updatetime");
				Integer novelIsEnd = rs.getInt("novel_isEnd");
				String novelSummary = rs.getString("novel_summary");
				// 从 查询用户对象 -> 改变成创建用户对象
				SnUser snUser = new SnUser();
				// 修改->查询 user_nickname user_nickpic
				Integer userId = rs.getInt("novel_share_userid");
				String userNickname = rs.getString("user_nickname");
				String userNickpic = rs.getString("user_nickpic");
				snUser.setUserId(userId);
				snUser.setUserNickname(userNickname);
				snUser.setUserNickpic(userNickpic);

				// 将map字符串转回map
				Map<String, String> novelDownloadurl = null;
				try {
					novelDownloadurl = StringUtils.mapStringToMap(rs.getString("novel_downloadurl"));
				} catch (Exception e) {
					e.printStackTrace();
				}
				Integer novelCheck = rs.getInt("novel_check");
				String novelCover = rs.getString("novel_cover");

				// 封装
				snNovel.setNovelId(novelId);
				snNovel.setNovelTitle(novelTitle);
				snNovel.setNovelAuthor(novelAuthor);
				snNovel.setSnCategory(snCategory);
				snNovel.setNovelUpdatetime(novelUpdatetime);
				snNovel.setNovelIsEnd(novelIsEnd);
				snNovel.setNovelSummary(novelSummary);
				snNovel.setNovelShareUser(snUser);
				snNovel.setNovelDownloadurl(novelDownloadurl);
				snNovel.setNovelCheck(novelCheck);
				snNovel.setNovelCover(novelCover);
				list.add(snNovel);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Integer findCount() {
		String sql = "select count(*) from sn_novel";
		RowSet rs = dbUtil.query(sql);
		Integer count = 0;
		try {
			while (rs.next()) {
				count = Integer.parseInt(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public Integer findCountByStatus(Integer status) {
		String sql = "select count(*) from sn_novel where novel_check = ?";
		RowSet rs = dbUtil.query(sql, status);
		Integer count = 0;
		try {
			while (rs.next()) {
				count = Integer.parseInt(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * 根据小说名查重
	 * @param novelTitle
	 * @return
	 */
	public List<SnNovel> findByTitleStrict(String novelTitle) {
		String sql = "SELECT sn_novel.novel_id,sn_novel.novel_title,sn_novel.novel_author,sn_novel.novel_categoryid,sn_novel.novel_updatetime,sn_novel.novel_isEnd,sn_novel.novel_summary,sn_novel.novel_share_userid,sn_novel.novel_downloadurl,sn_novel.novel_check,sn_novel.novel_cover,sn_category.cat_name,sn_category.cat_parentid,sn_user.user_nickname,sn_user.user_nickpic FROM sn_novel INNER JOIN sn_category ON sn_novel.novel_categoryid = sn_category.cat_id INNER JOIN sn_user ON sn_novel.novel_share_userid = sn_user.user_id"+" where novel_title = ?";
		RowSet rs = dbUtil.query(sql, novelTitle);
		List<SnNovel> list = new ArrayList<SnNovel>();
		handleData(rs, list);
		return list.size() > 0 ? list : null;
	}
	
	@Override
	public List<SnNovel> queryByCategory(int cid) {
		String sql="select * from sn_novel where novel_categoryid=?";
		List<SnNovel> list=new ArrayList<SnNovel>();
		CachedRowSet rs = dbUtil.query(sql, cid);
		try {
			while (rs.next()) {
				SnNovel snNovel = new SnNovel();

				Integer novelId = rs.getInt("novel_id");
				String novelTitle = rs.getString("novel_title");
				String novelAuthor = rs.getString("novel_author");
				// 从 ->查询cat对象 -> 创建cat对象
				SnCategory snCategory = new SnCategory();
				// 修改->查询catId和catName
				Integer snCategoryId = rs.getInt("novel_categoryid");
				// 时间需要查询
				String novelUpdatetime = rs.getString("novel_updatetime");
				Integer novelIsEnd = rs.getInt("novel_isEnd");
				String novelSummary = rs.getString("novel_summary");
				// 从 查询用户对象 -> 改变成创建用户对象
				SnUser snUser = new SnUser();
				// 修改->查询 user_nickname user_nickpic
				Integer userId = rs.getInt("novel_share_userid");
				// 将map字符串转回map
				Map<String, String> novelDownloadurl = null;
				try {
					novelDownloadurl = StringUtils.mapStringToMap(rs.getString("novel_downloadurl"));
				} catch (Exception e) {
					e.printStackTrace();
				}
				Integer novelCheck = rs.getInt("novel_check");
				String novelCover = rs.getString("novel_cover");

				// 封装
				snNovel.setNovelId(novelId);
				snNovel.setNovelTitle(novelTitle);
				snNovel.setNovelAuthor(novelAuthor);
				snNovel.setSnCategory(snCategory);
				snNovel.setNovelUpdatetime(novelUpdatetime);
				snNovel.setNovelIsEnd(novelIsEnd);
				snNovel.setNovelSummary(novelSummary);
				snNovel.setNovelShareUser(snUser);
				snNovel.setNovelDownloadurl(novelDownloadurl);
				snNovel.setNovelCheck(novelCheck);
				snNovel.setNovelCover(novelCover);
				list.add(snNovel);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list.size()>0?list:null;
	}

	@Override
	public List<SnNovel> findNewestNovel(Integer size) {
		String sql;
		RowSet rs;
		if(size==null) {
			sql="select * from sn_novel order by novel_updatetime desc";
			rs = dbUtil.query(sql);
		}else {
			sql="select * from sn_novel order by novel_updatetime desc limit ?";
			rs = dbUtil.query(sql,size);
		}
		List<SnNovel> list = new ArrayList<SnNovel>();
		try {
			while (rs.next()) {
				SnNovel snNovel = new SnNovel();

				Integer novelId = rs.getInt("novel_id");
				String novelTitle = rs.getString("novel_title");
				String novelAuthor = rs.getString("novel_author");
				// 从 ->查询cat对象 -> 创建cat对象
				SnCategory snCategory = new SnCategory();
				// 修改->查询catId和catName
				Integer snCategoryId = rs.getInt("novel_categoryid");
				// 时间需要查询
				String novelUpdatetime = rs.getString("novel_updatetime");
				Integer novelIsEnd = rs.getInt("novel_isEnd");
				String novelSummary = rs.getString("novel_summary");
				// 从 查询用户对象 -> 改变成创建用户对象
				SnUser snUser = new SnUser();
				// 修改->查询 user_nickname user_nickpic
				Integer userId = rs.getInt("novel_share_userid");
				// 将map字符串转回map
				Map<String, String> novelDownloadurl = null;
				try {
					novelDownloadurl = StringUtils.mapStringToMap(rs.getString("novel_downloadurl"));
				} catch (Exception e) {
					e.printStackTrace();
				}
				Integer novelCheck = rs.getInt("novel_check");
				String novelCover = rs.getString("novel_cover");

				// 封装
				snNovel.setNovelId(novelId);
				snNovel.setNovelTitle(novelTitle);
				snNovel.setNovelAuthor(novelAuthor);
				snNovel.setSnCategory(snCategory);
				snNovel.setNovelUpdatetime(novelUpdatetime);
				snNovel.setNovelIsEnd(novelIsEnd);
				snNovel.setNovelSummary(novelSummary);
				snNovel.setNovelShareUser(snUser);
				snNovel.setNovelDownloadurl(novelDownloadurl);
				snNovel.setNovelCheck(novelCheck);
				snNovel.setNovelCover(novelCover);
				list.add(snNovel);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list.size() > 0 ? list : null;
	}


}
