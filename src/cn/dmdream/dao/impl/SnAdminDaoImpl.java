package cn.dmdream.dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;

import cn.dmdream.dao.SnAdminDao;
import cn.dmdream.entity.SnAdmin;
import cn.dmdream.utils.DbUtil;

public class SnAdminDaoImpl implements SnAdminDao {

	private DbUtil dbUtil = new DbUtil();

	@Override
	public SnAdmin findById(Integer id) {

		String sql = "select * from sn_admin where id=?";
		ResultSet rs = dbUtil.query(sql, id);
		SnAdmin snAdmin = null;
		try {
			while (rs.next()) {
				snAdmin = new SnAdmin();
				String admin_username = rs.getString("admin_username");
				String admin_password = rs.getString("admin_password");
				String admin_nickpic = rs.getString("admin_nickpic");

				snAdmin.setAdminId(id);
				snAdmin.setAdminUsername(admin_username);
				snAdmin.setAdminPassword(admin_password);
				snAdmin.setAdminNickpic(admin_nickpic);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dbUtil.closeAll();
		}
		return snAdmin;
	}

	@Override
	public SnAdmin findByUsername(String adminUsername) {

		String sql="select * from sn_admin where admin_username = ?";
		ResultSet rs=dbUtil.query(sql, adminUsername);
		SnAdmin snAdmin=null;
		try {
			while(rs.next()){
				snAdmin=new SnAdmin();
				int admin_id=rs.getInt("id");
				String admin_password=rs.getString("admin_password");
				String admin_nickpic = rs.getString("admin_nickpic");
				
				snAdmin.setAdminId(admin_id);
				snAdmin.setAdminUsername(adminUsername);
				snAdmin.setAdminPassword(admin_password);
				snAdmin.setAdminNickpic(admin_nickpic);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			dbUtil.closeAll();
		}
		return snAdmin;
	}

	@Override
	public int save(SnAdmin snAdmin) {

		String sql="insert into sn_admin values(null,?,?,?)";
		int i=dbUtil.update(sql, snAdmin.getAdminUsername(),snAdmin.getAdminPassword(),snAdmin.getAdminNickpic());
		return i;
	}

	@Override
	public int update(SnAdmin snAdmin) {

		String sql="update sn_admin set admin_username=?, admin_password=?,admin_nickpic=? where id=?";
		int i =dbUtil.update(sql, snAdmin.getAdminUsername(),snAdmin.getAdminPassword(),snAdmin.getAdminNickpic(),snAdmin.getAdminId());
		return i;
	}

}
