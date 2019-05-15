package cn.dmdream.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.dmdream.dao.SnCommentDao;
import cn.dmdream.entity.SnComment;
import cn.dmdream.entity.SnNovel;
import cn.dmdream.entity.SnUser;
import cn.dmdream.utils.DateTimeUtils;
import cn.dmdream.utils.DbUtil;

/**
 * SnComment用户评论Dao层实现类
 * 
 * @author KuluS
 *
 */
public class SnCommentDaoImpl implements SnCommentDao {

	private DbUtil dbUtil = new DbUtil();

	/**
	 * 新增
	 * 
	 * @param comment
	 * @return
	 */
	public int save(SnComment comment) {
		String sql = "insert into sn_comment values(null,?,?,?,?,?,?)";
		SnUser user = comment.getCommUser();
		SnNovel novel = comment.getCommNovel();
		String commContent = comment.getCommContent();
		String commTime = DateTimeUtils.getCurrentFormatDateTime();
		Integer commHitnum = comment.getCommHitnum();
		Integer parentId = comment.getCommParent().getCommId();
		if (parentId == null || parentId.equals(0)) {
			parentId = null;
		}
		int i = dbUtil.update(sql, user.getUserId(), novel.getNovelId(), commContent, commTime, commHitnum, parentId);
		return i;
	};

	/**
	 * 根据对象删除,删除父评论会删除子评论
	 * 
	 * @param comment
	 * @return
	 */
	public int delete(SnComment comment) {
		String sql = "delete from sn_comment where comm_id = ? or comm_parentId = ?";
		int i = dbUtil.update(sql, comment.getCommId(), comment.getCommId());
		return i;
	};

	/**
	 * 根据主键删除
	 * 
	 * @param id
	 * @return
	 */
	public int deleteById(Integer id) {
		String sql = "delete from sn_comment where comm_id = ? or comm_parentId = ?";
		int i = dbUtil.update(sql, id, id);
		return i;
	};

	/**
	 * 根据主键查询
	 * 
	 * @param id
	 * @return
	 */
	public SnComment findById(Integer id) {
		String sql = "SELECT "
				+ "sn_comment.comm_id,sn_comment.comm_userid,sn_comment.comm_content,sn_comment.comm_time,sn_comment.comm_hitnum,sn_comment.comm_parentId"
				+ ",sn_user.user_id,sn_user.user_nickname,sn_user.user_nickpic" + ",sn_novel.novel_id"
				+ " FROM sn_comment,sn_user,sn_novel" + " WHERE sn_comment.comm_id = ?"
				+ " and sn_comment.comm_userid = sn_user.user_id" + " and sn_comment.comm_novelid = sn_novel.novel_id";
		ResultSet rs = dbUtil.query(sql, id);
		List<SnComment> list = new ArrayList<SnComment>();
		handleData(rs, list);

		return list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 更新
	 * 
	 * @param comment
	 * @return
	 */
	public int update(SnComment comment) {
		String sql = "update sn_comment set comm_content = ? , comm_time = ? , comm_hitnum = ? where comm_id = ?";
		Integer commId = comment.getCommId();
		String commContent = comment.getCommContent();
		String commTime = DateTimeUtils.getCurrentFormatDateTime();
		Integer commHitnum = comment.getCommHitnum();
		int i = dbUtil.update(sql, commContent, commTime, commHitnum, commId);
		return i;
	};

	/**
	 * 根据用户查询所有评论
	 * 
	 * @param user
	 * @return
	 */
	public List<SnComment> findByUser(SnUser user) {
		String sql = "SELECT "
				+ "sn_comment.comm_id,sn_comment.comm_userid,sn_comment.comm_content,sn_comment.comm_time,sn_comment.comm_hitnum,sn_comment.comm_parentId"
				+ ",sn_user.user_id,sn_user.user_nickname,sn_user.user_nickpic" + ",sn_novel.novel_id"
				+ " FROM sn_comment,sn_user,sn_novel" + " WHERE sn_comment.comm_userid = ?"
				+ " and sn_comment.comm_userid = sn_user.user_id" + " and sn_comment.comm_novelid = sn_novel.novel_id"
				+ " order by comm_time desc";
		ResultSet rs = dbUtil.query(sql, user.getUserId());
		List<SnComment> list = new ArrayList<SnComment>();
		handleData(rs, list);

		return list.size() > 0 ? list : null;
	};

	/**
	 * 根据用户查询所有评论,分页版
	 * 
	 * @param user
	 * @return
	 */
	public List<SnComment> findByUserByPage(SnUser user, int pageSize, int page) {
		String sql = "SELECT "
				+ "sn_comment.comm_id,sn_comment.comm_userid,sn_comment.comm_content,sn_comment.comm_time,sn_comment.comm_hitnum,sn_comment.comm_parentId"
				+ ",sn_user.user_id,sn_user.user_nickname,sn_user.user_nickpic" + ",sn_novel.novel_id"
				+ " FROM sn_comment,sn_user,sn_novel" + " WHERE sn_comment.comm_userid = ?"
				+ " and sn_comment.comm_userid = sn_user.user_id" + " and sn_comment.comm_novelid = sn_novel.novel_id"
				+ " order by comm_time desc"
				+ " limit ?,?";
		ResultSet rs = dbUtil.query(sql, user.getUserId(), (page - 1) * pageSize, pageSize);
		List<SnComment> list = new ArrayList<SnComment>();
		handleData(rs, list);

		return list.size() > 0 ? list : null;
	};

	/**
	 * 根据小说查询所有评论
	 * 
	 * @param novel
	 * @return
	 */
	public List<SnComment> findByNovel(SnNovel novel) {
		String sql = "SELECT "
				+ "sn_comment.comm_id,sn_comment.comm_userid,sn_comment.comm_content,sn_comment.comm_time,sn_comment.comm_hitnum,sn_comment.comm_parentId"
				+ ",sn_user.user_id,sn_user.user_nickname,sn_user.user_nickpic" + ",sn_novel.novel_id"
				+ " FROM sn_comment,sn_user,sn_novel" + " WHERE sn_comment.comm_novelid = ?"
				+ " and sn_comment.comm_userid = sn_user.user_id" + " and sn_comment.comm_novelid = sn_novel.novel_id"
				+ " order by comm_time desc";
		ResultSet rs = dbUtil.query(sql, novel.getNovelId());
		List<SnComment> list = new ArrayList<SnComment>();
		handleData(rs, list);

		return list.size() > 0 ? list : null;
	};

	/**
	 * 根据小说查询所有评论,分页版
	 * 
	 * @param novel
	 * @return
	 */
	public List<SnComment> findByNovelByPage(SnNovel novel, int pageSize, int page) {
		String sql = "SELECT "
				+ "sn_comment.comm_id,sn_comment.comm_userid,sn_comment.comm_content,sn_comment.comm_time,sn_comment.comm_hitnum,sn_comment.comm_parentId"
				+ ",sn_user.user_id,sn_user.user_nickname,sn_user.user_nickpic" + ",sn_novel.novel_id"
				+ " FROM sn_comment,sn_user,sn_novel" + " WHERE sn_comment.comm_novelid = ?"
				+ " and sn_comment.comm_userid = sn_user.user_id" + " and sn_comment.comm_novelid = sn_novel.novel_id"
				+ " order by comm_time desc" 
				+ " limit ?,?";
		ResultSet rs = dbUtil.query(sql, novel.getNovelId(), (page - 1) * pageSize, pageSize);
		List<SnComment> list = new ArrayList<SnComment>();
		handleData(rs, list);

		return list.size() > 0 ? list : null;
	};

	/**
	 * 查询父评论下的所有子评论
	 * 
	 * @param comment
	 * @return
	 */

	public List<SnComment> findAllChildComment(SnComment comment) {
		String sql = "SELECT "
				+ "sn_comment.comm_id,sn_comment.comm_userid,sn_comment.comm_content,sn_comment.comm_time,sn_comment.comm_hitnum,sn_comment.comm_parentId"
				+ ",sn_user.user_id,sn_user.user_nickname,sn_user.user_nickpic" + ",sn_novel.novel_id"
				+ " FROM sn_comment,sn_user,sn_novel" + " WHERE sn_comment.comm_parentId = ?"
				+ " and sn_comment.comm_userid = sn_user.user_id" + " and sn_comment.comm_novelid = sn_novel.novel_id"
				+ " order by comm_time desc";
		ResultSet rs = dbUtil.query(sql, comment.getCommId());
		List<SnComment> list = new ArrayList<SnComment>();
		handleData(rs, list);

		return list.size() > 0 ? list : null;
	};

	/**
	 * 查询父评论下的所有子评论,分页版
	 * 
	 * @param comment
	 * @param pageSize
	 * @param page
	 * @return
	 */
	public List<SnComment> findAllChildCommentByPage(SnComment comment, int pageSize, int page) {
		String sql = "SELECT "
				+ "sn_comment.comm_id,sn_comment.comm_userid,sn_comment.comm_content,sn_comment.comm_time,sn_comment.comm_hitnum,sn_comment.comm_parentId"
				+ ",sn_user.user_id,sn_user.user_nickname,sn_user.user_nickpic" + ",sn_novel.novel_id"
				+ " FROM sn_comment,sn_user,sn_novel" + " WHERE sn_comment.comm_parentId = ?"
				+ " and sn_comment.comm_userid = sn_user.user_id" + " and sn_comment.comm_novelid = sn_novel.novel_id"
				+ " order by comm_time desc" 
				+ " limit ?,?";
		ResultSet rs = dbUtil.query(sql, comment.getCommId(), (page - 1) * pageSize, pageSize);
		List<SnComment> list = new ArrayList<SnComment>();
		handleData(rs, list);

		return list.size() > 0 ? list : null;
	};

	/**
	 * 封装数据
	 * 
	 * @param rs
	 * @param list
	 */
	private void handleData(ResultSet rs, List<SnComment> list) {
		try {
			while (rs.next()) {
				// 封装用户对象
				Integer userId = rs.getInt("user_id");
				String userNickname = rs.getString("user_nickname");
				String userNickpic = rs.getString("user_nickpic");
				SnUser user = new SnUser();
				user.setUserId(userId);
				user.setUserNickname(userNickname);
				user.setUserNickpic(userNickpic);
				// 封装小说对象
				Integer novelId = rs.getInt("novel_id");
				SnNovel novel = new SnNovel();
				novel.setNovelId(novelId);
				// 封装其它属性
				Integer commId = rs.getInt("comm_id");
				String commContent = rs.getString("comm_content");
				String commTime = rs.getString("comm_time");
				Integer commHitnum = rs.getInt("comm_hitnum");
				// 父评论
				SnComment parent = new SnComment();
				parent.setCommId(rs.getInt("comm_parentId"));

				SnComment comment = new SnComment();
				comment.setCommId(commId);
				comment.setCommUser(user);
				comment.setCommNovel(novel);
				comment.setCommContent(commContent);
				comment.setCommTime(commTime);
				comment.setCommHitnum(commHitnum);
				comment.setCommParent(parent);
				list.add(comment);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<SnComment> queryByUser(SnUser user) {
		// TODO Auto-generated method stub
		return null;
	}


}
