package cn.dmdream.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
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
 * @author KuluS
 *
 */
public class SnNovelDaoImpl implements SnNovelDao {

	DbUtil dbUtil = new DbUtil();
	SnCategoryDao snCategoryDao = new SnCategoryDaoImpl();
	SnUserDao snUserDao = new SnUserDaoImpl();
	/**
	 * 小说新增
	 * @param snNovel
	 * @return
	 */
	public int save(SnNovel snNovel) {

		String sql = "insert into sn_novel values(null,?,?,?,?,?,?,?,?,?,?)";
		String novelTitle = snNovel.getNovelTitle();
		String novelAuthor = snNovel.getNovelAuthor();
		//获取category的id
		Integer catId = snNovel.getSnCategory().getCatId();
		String updateTime = DateTimeUtils.getCurrentFormatDateTime();//在dao层每一次插入时设置最新时间
		Integer novelIsEnd = snNovel.getNovelIsEnd();
		String novelSummary = snNovel.getNovelSummary();
		//获取外键-分享者id
		Integer userId = snNovel.getNovelShareUser().getUserId();
		String novelDownloadurl = snNovel.getNovelDownloadurl().toString();
		Integer novelCheck = snNovel.getNovelCheck();
		String novelCover = snNovel.getNovelCover();
		
		int i = dbUtil.update(sql, novelTitle,novelAuthor,catId,updateTime,novelIsEnd,novelSummary,userId,novelDownloadurl,novelCheck,novelCover);
		
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
	 * @param snNovel
	 * @return
	 */
	public int update(SnNovel snNovel) {
		String sql = "update sn_novel set novel_title=?,novel_author=?,novel_categoryid=?,novel_updatetime=?,novel_isEnd=?,novel_summary=?,novel_share_userid=?,novel_downloadurl=?,novel_check=?,novel_cover=? where novel_id=?";
		
		String novelTitle = snNovel.getNovelTitle();
		String novelAuthor = snNovel.getNovelAuthor();
		//获取category的id
		Integer catId = snNovel.getSnCategory().getCatId();
		//在dao层每一次修改时设置最新时间
		String updateTime = DateTimeUtils.getCurrentFormatDateTime();
		Integer novelIsEnd = snNovel.getNovelIsEnd();
		String novelSummary = snNovel.getNovelSummary();
		//获取外键-分享者id
		Integer userId = snNovel.getNovelShareUser().getUserId();
		String novelDownloadurl = snNovel.getNovelDownloadurl().toString();
		Integer novelCheck = snNovel.getNovelCheck();
		String novelCover = snNovel.getNovelCover();
		
		int i = dbUtil.update(sql, novelTitle,novelAuthor,catId,updateTime,novelIsEnd,novelSummary,userId,novelDownloadurl,novelCheck,novelCover,snNovel.getNovelId());
		return i;
	}

	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public SnNovel findById(Integer id) {
		String sql = "select * from sn_novel where novel_id = ?";
		RowSet rs = dbUtil.query(sql , id);
		List<SnNovel> list = new ArrayList<SnNovel>();
		handleData(rs, list);
		return list.size()>0?list.get(0):null;
	}

	/**
	 * 查询所有,包括未审核的
	 */
	public List<SnNovel> findAll() {
		String sql = "select * from sn_novel";
		RowSet rs = dbUtil.query(sql);
		List<SnNovel> list = new ArrayList<SnNovel>();
		handleData(rs, list);
		return list.size()>0?list:null;
	}

	/**
	 * 查询所有分页版
	 * @return
	 */
	public List<SnNovel> findAllByPage(int pageSize, int page) {
		if(page < 0) page = 1;
		String sql = "select * from sn_novel limit ?,?";
		RowSet rs = dbUtil.query(sql, (page - 1) * pageSize , pageSize);
		List<SnNovel> list = new ArrayList<SnNovel>();
		handleData(rs, list);
		return list.size()>0?list:null;
	}

	/**
	 * 根据标题模糊查询（数据库版，后期使用solr）
	 * @param title
	 * @return
	 */
	public List<SnNovel> findByTitle(String title) {
		String sql = "select * from sn_novel where novel_title like ?";
		RowSet rs = dbUtil.query(sql,"%" + title + "%");
		List<SnNovel> list = new ArrayList<SnNovel>();
		handleData(rs, list);
		return list.size()>0?list:null;
	}

	/**
	 * 根据作者模糊查询（数据库版，后期使用solr）
	 * @param author
	 * @return
	 */
	public List<SnNovel> findByAuthor(String author) {
		String sql = "select * from sn_novel where novel_author like ?";
		RowSet rs = dbUtil.query(sql,"%" + author + "%");
		List<SnNovel> list = new ArrayList<SnNovel>();
		handleData(rs, list);
		return list.size()>0?list:null;
	}

	/**
	 * 根据标题模糊分页查询（数据库版，后期使用solr）
	 * @param title
	 * @return
	 */
	public List<SnNovel> findByTitleByPage(String title,int pageSize , int page) {
		String sql = "select * from sn_novel where novel_title like ? limit ?,?";
		RowSet rs = dbUtil.query(sql,"%" + title + "%",(page - 1) * pageSize , pageSize);
		List<SnNovel> list = new ArrayList<SnNovel>();
		handleData(rs, list);
		return list.size()>0?list:null;
	}

	/**
	 * 根据作者模糊分页查询（数据库版，后期使用solr）
	 * @param author
	 * @return
	 */
	public List<SnNovel> findByAuthorByPage(String author,int pageSize , int page) {
		String sql = "select * from sn_novel where novel_author like ? limit ?,?";
		RowSet rs = dbUtil.query(sql,"%" + author + "%",(page - 1) * pageSize , pageSize);
		List<SnNovel> list = new ArrayList<SnNovel>();
		handleData(rs, list);
		return list.size()>0?list:null;
	}

	/**
	 * 根据状态查询所有
	 * @param check  0:为审核 1:通过 2:未通过
	 * @return
	 */
	public List<SnNovel> findByCheck(Integer check) {
		String sql = "select * from sn_novel where novel_check = ?";
		RowSet rs = dbUtil.query(sql,check);
		List<SnNovel> list = new ArrayList<SnNovel>();
		handleData(rs, list);
		return list.size()>0?list:null;
	}

	/**
	 * 根据状态查询所有分页版
	 * @param check  0:为审核 1:通过 2:未通过
	 * @return
	 */
	public List<SnNovel> findByCheckByPage(Integer check, int pageSize, int page) {
		String sql = "select * from sn_novel where novel_check = ? limit ?,?";
		RowSet rs = dbUtil.query(sql,check,(page - 1) * pageSize , pageSize);
		List<SnNovel> list = new ArrayList<SnNovel>();
		handleData(rs, list);
		return list.size()>0?list:null;
	}

	
	/**
	 * 封装数据
	 * @param rs
	 * @param list
	 */
	private void handleData(RowSet rs, List<SnNovel> list) {
		try {
			while(rs.next()){
				SnNovel snNovel = new SnNovel();
				
				Integer novelId = rs.getInt("novel_id");
				String novelTitle = rs.getString("novel_title");
				String novelAuthor = rs.getString("novel_author");
				//查询cat对象
				SnCategory snCategory = snCategoryDao.findById(rs.getInt("novel_categoryid"));
				//时间需要查询
				String novelUpdatetime = rs.getString("novel_updatetime");
				Integer novelIsEnd = rs.getInt("novel_isEnd");
				String novelSummary = rs.getString("novel_summary");
				//查询用户对象
				SnUser snUser = snUserDao.findById(rs.getInt("novel_share_userid"));
				//将map字符串转回map
				Map<String, String> novelDownloadurl = null;
				try{
					novelDownloadurl = StringUtils.mapStringToMap(rs.getString("novel_downloadurl"));
				}catch (Exception e) {
					e.printStackTrace();
				}
				Integer novelCheck = rs.getInt("novel_check");
				String novelCover = rs.getString("novel_cover");
				
				//封装
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
	public List<SnNovel> queryByCategory(int cid) {
		String sql="select * from sn_novel where novel_categoryid=?";
		List<SnNovel> list=new ArrayList<SnNovel>();
		CachedRowSet rs = dbUtil.query(sql, cid);
		handleData(rs, list);
		return list.size()>0?list:null;
	}
}
