package cn.dmdream.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.RowSet;

import cn.dmdream.dao.SnCategoryDao;
import cn.dmdream.entity.SnCategory;
import cn.dmdream.entity.SnNovel;
import cn.dmdream.utils.DbUtil;

/**
 * CategoryDao层接口实现类
 * 
 * @author KuluS
 *
 */
public class SnCategoryDaoImpl implements SnCategoryDao {

	private DbUtil dbUtil = new DbUtil();

	/**
	 * 新增分类
	 * 
	 * @param snCategory
	 * @return
	 */
	public int save(SnCategory snCategory) {
		String sql = "insert into sn_category values(null,?,?,?)";
		int i = dbUtil.update(sql, snCategory.getCatName(), snCategory.getCatParentid(), snCategory.getCatGender());
		return i;
	}

	/**
	 * 删除分类
	 */
	public int delete(SnCategory snCategory) {
		String sql = "delete from sn_category where cat_id=?";
		int i = dbUtil.update(sql, snCategory.getCatId());
		return i;
	}

	/**
	 * 更新分类
	 */
	public int update(SnCategory snCategory) {
		String sql = "update sn_category set cat_name = ? ,cat_parentid=?,cat_gender = ? where cat_id=?";
		int i = dbUtil.update(sql, snCategory.getCatName(), snCategory.getCatParentid(), snCategory.getCatGender(),
				snCategory.getCatId());
		return i;
	}

	/**
	 * 查询所有父分类
	 */
	public List<SnCategory> findAllParent() {
		String sql = "select * from sn_category where cat_parentid = 0";
		RowSet rs = dbUtil.query(sql);
		List<SnCategory> list = new ArrayList<SnCategory>();
		handleData(rs, list);
		return list.size() > 0 ? list : null;
	}

	/**
	 * 根据主键查询
	 */
	public SnCategory findById(Integer id) {
		String sql = "select * from sn_category where cat_id = ?";
		RowSet rs = dbUtil.query(sql, id);
		List<SnCategory> list = new ArrayList<SnCategory>();
		handleData(rs, list);
		return list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * 查询改父分类id下的所有子分类
	 */
	public List<SnCategory> findByParentId(Integer id) {
		String sql = "select * from sn_category where cat_parentid = ?";
		RowSet rs = dbUtil.query(sql, id);
		List<SnCategory> list = new ArrayList<SnCategory>();
		handleData(rs, list);
		return list.size() > 0 ? list : null;
	}
	
	/**
	 * 查询改父分类id下的所有子分类分页版
	 * @return
	 */
	public List<SnCategory> findByParentIdByPage(int id , int pageSize ,int page){
		String sql = "select * from sn_category where cat_parentid = ? limit ?,?";
		RowSet rs = dbUtil.query(sql, id ,(page-1)*pageSize , pageSize );
		List<SnCategory> list = new ArrayList<SnCategory>();
		handleData(rs, list);
		return list.size() > 0 ? list : null;
	};

	/**
	 * 根据分类名模糊查询
	 */
	public List<SnCategory> findByCatName(String catName) {
		String sql = "select * from sn_category where cat_name = ?";
		RowSet rs = dbUtil.query(sql, catName);
		List<SnCategory> list = new ArrayList<SnCategory>();
		handleData(rs, list);
		return list.size() > 0 ? list : null;
	}

	/**
	 * 根据男频/女频分类查询
	 */
	public List<SnCategory> findByCatGender(Integer gender) {
		String sql = "select * from sn_category where cat_gender = ?";
		RowSet rs = dbUtil.query(sql, gender);
		List<SnCategory> list = new ArrayList<SnCategory>();
		handleData(rs, list);
		return list.size() > 0 ? list : null;
	}

	/**
	 * 数据封装
	 * 
	 * @param rs
	 * @param list
	 */
	private void handleData(RowSet rs, List<SnCategory> list) {
		try {
			while (rs.next()) {
				SnCategory snCategory = new SnCategory();
				int catId = rs.getInt("cat_id");
				String catName = rs.getString("cat_name");
				int catParentid = rs.getInt("cat_parentid");
				int catGender = rs.getInt("cat_gender");
				snCategory.setCatId(catId);
				snCategory.setCatName(catName);
				snCategory.setCatParentid(catParentid);
				snCategory.setCatGender(catGender);
				list.add(snCategory);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Integer findCount(Integer id) {
		String sql = "select count(*) from sn_category where cat_parentid = ?";
		RowSet rs = dbUtil.query(sql , id);
		Integer count = 0;
		try {
			while(rs.next()){
				count = Integer.parseInt(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

}
