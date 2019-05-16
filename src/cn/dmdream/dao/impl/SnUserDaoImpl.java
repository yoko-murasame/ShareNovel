package cn.dmdream.dao.impl;

import static org.hamcrest.CoreMatchers.nullValue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.CachedRowSet;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import cn.dmdream.dao.SnUserDao;
import cn.dmdream.entity.SnUser;
import cn.dmdream.utils.DbUtil;

public class SnUserDaoImpl implements SnUserDao {

	private DbUtil dbUtil = new DbUtil();

	@Override
	public int save(SnUser snUser) {

		String sql = "insert into sn_user values(null,?,?,?,?,?,?,?,?,?,null)";
		int i = dbUtil.update(sql, snUser.getUserUsername(), snUser.getUserPassword(), snUser.getUserNickname(),
				snUser.getUserNickpic(), snUser.getUserEmail(), snUser.getUserPhone(), snUser.getUserEmailActive(),
				snUser.getUserPhoneActive(), snUser.getUserRegisttime());
		return i;
	}

	@Override
	public int deleteById(Integer id) {

		String sql = "delete from sn_user where user_id=?";
		int i = dbUtil.update(sql, id);
		return i;
	}

	@Override
	public int update(SnUser snUser) {

		String sql = "update sn_user set user_username=?,user_password=?,user_nickname=?,user_nickpic=?,user_email=?,user_phone=?,user_email_active=?,"
				+ "user_phone_active=?,user_registtime=? where user_id=?";
		int i = dbUtil.update(sql, snUser.getUserUsername(), snUser.getUserPassword(), snUser.getUserNickname(),
				snUser.getUserNickpic(), snUser.getUserEmail(), snUser.getUserPhone(), snUser.getUserEmailActive(),
				snUser.getUserPhoneActive(), snUser.getUserRegisttime(), snUser.getUserId());
		return i;
	}

	@Override
	public SnUser findById(Integer id) {

		String sql = "select * from sn_user where user_id=?";
		ResultSet rs = dbUtil.query(sql, id);
		List<SnUser> list = new ArrayList<SnUser>();
		handleData(rs, list);
		return list.size()>0?list.get(0):null;
	}



	@Override
	public List<SnUser> findAll() {

		String sql = "select * from sn_user";
		ResultSet rs = dbUtil.query(sql);
		List<SnUser> list = new ArrayList<SnUser>();// 创建一个存储的ArrayList对象
		handleData(rs, list);
		return list.size()>0?list:null;
	}

	@Override
	public List<SnUser> findByPage(int pageSize, int page) {

		String sql = "select * from sn_user";
		ResultSet rs = dbUtil.queryByPage(sql, pageSize, page);
		List<SnUser> list = new ArrayList<SnUser>();
		handleData(rs, list);
		return list.size()>0?list:null;
	}

	@Override
	public SnUser findByUsername(String username) {

		String sql = "select * from sn_user where user_username like ?";
		ResultSet rs = dbUtil.query(sql, "%"+username+"%");
		List<SnUser> list = new ArrayList<SnUser>();
		handleData(rs, list);
		return list.size()>0?list.get(0):null;
	}

	/**
	 * 封装函数
	 * @param rs
	 * @param list
	 */
	private void handleData(ResultSet rs, List<SnUser> list) {
		try {
			while (rs.next()) {
				SnUser snUser = new SnUser();
				Integer user_id = rs.getInt("user_id");
				String user_username = rs.getString("user_username");
				String user_password = rs.getString("user_password");
				String user_nickname = rs.getString("user_nickname");
				String user_nickpic = rs.getString("user_nickpic");
				String user_email = rs.getString("user_email");
				String user_phone = rs.getString("user_phone");
				Integer user_email_active = rs.getInt("user_email_active");
				Integer user_phone_active = rs.getInt("user_phone_active");
				String user_registtime = rs.getString("user_registtime");

				snUser.setUserId(user_id);
				snUser.setUserUsername(user_username);
				snUser.setUserPassword(user_password);
				snUser.setUserNickname(user_nickname);
				snUser.setUserNickpic(user_nickpic);
				snUser.setUserEmail(user_email);
				snUser.setUserPhone(user_phone);
				snUser.setUserEmailActive(user_email_active);
				snUser.setUserPhoneActive(user_phone_active);
				snUser.setUserRegisttime(user_registtime);
				
				list.add(snUser);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbUtil.closeAll();
		}
	}

	@Override
	public int countByUsername(String username) {

        String sql="select count(*) from sn_user where user_username=?"	;
        dbUtil=new DbUtil();
        CachedRowSet rs = dbUtil.query(sql, username);
        int count=1;
        try {
			while(rs.next()){
				count=rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if (rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return count;
	}

	public List<SnUser> login(String name, String pwd) {
		String sql="SELECT * from sn_user where	( user_username = ? or (user_email =? and user_email_active =1) ) and user_password =?";
		ResultSet rs = dbUtil.query(sql,name,name,pwd);
		List<SnUser> list = new ArrayList<SnUser>();
		handleData(rs, list);
		return list.size()>0?list:null;
	}
	/**
	 * 根据用户总数量
	 * @return
	 */
	@Override
	public Integer count() {
		int allCount = 0;

		String sql = "select count(*) from sn_user";

		ResultSet resultSet = dbUtil.query(sql);
		try {
			while (resultSet.next()) {
				allCount = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return allCount;
	}
}
