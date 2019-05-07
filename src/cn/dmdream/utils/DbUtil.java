package cn.dmdream.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

public class DbUtil {

	private static String driverName;
	private static String url;
	private static String username;
	private static String password;

	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;

	static {
		// src目录
		ResourceBundle resourceBundle = ResourceBundle.getBundle("db");
		driverName = resourceBundle.getString("drivername");
		url = resourceBundle.getString("url");
		username = resourceBundle.getString("username");
		password = resourceBundle.getString("password");

		// Properties properties = new Properties();
		// try {
		// properties.load(
		// new FileInputStream(new File("db.properties")));
		// // properties.load(new FileInputStream(new
		// // File("db.properties")));//必须在同级目录
		// driverName = properties.getProperty("drivername");
		// url = properties.getProperty("url");
		// username = properties.getProperty("username");
		// password = properties.getProperty("password");
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

	}

	/**
	 * 获取连接
	 * 
	 * @return
	 */
	public Connection getConnection() {

		Connection connection = null;
		try {
			Class.forName(driverName);
			connection = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * 释放所有连接
	 */
	public void closeAll() {

		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新数据库的方法->insert/update/delete
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public int update(String sql, Object... params) {

		int r = 0;

		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			// 设置参数
			if (params != null) {
				// 不定参数是被当成数组使用
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, params[i]);
				}
			}

			// r为受该sql语句影响的总记录数
			r = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}

		return r;

	}

	/**
	 * 查询数据库的方法
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public CachedRowSet query(String sql, Object... params) {

		CachedRowSet cachedRowSet = null;

		try {
			conn = getConnection();

			// 使用cachedRowSet优化
			RowSetFactory factory = RowSetProvider.newFactory();
			cachedRowSet = factory.createCachedRowSet();

			// 通过sql语句生成预编译的操作语句
			ps = conn.prepareStatement(sql);
			// 设置参数(参数位置,参数值)
			if (params != null) {
				// 不定参数是被当成数组使用
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, params[i]);
				}
			}

			// 查询得到结果集
			rs = ps.executeQuery();
			cachedRowSet.populate(rs);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll();
		}

		return cachedRowSet;

	}

	/**
	 * 分页查询
	 * 
	 * @param sql
	 * @param pageSize
	 *            页面记录数
	 * @param page
	 *            第几页
	 * @param params
	 *            参数
	 * @return
	 */
	public CachedRowSet queryByPage(String sql, int pageSize, int page, Object... params) {

		CachedRowSet cachedRowSet = null;

		try {
			conn = getConnection();
			ps = conn.prepareStatement(sql);
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					ps.setObject(i + 1, params[i]);
				}
			}
			rs = ps.executeQuery();
			// RowSet进行分页
			RowSetFactory factory = RowSetProvider.newFactory();
			cachedRowSet = factory.createCachedRowSet();
			// 参数判断
			if (pageSize < 1)
				pageSize = 1;
			if (page < 1)
				page = 1;
			// 设置一页数量
			cachedRowSet.setPageSize(pageSize);
			// 生成cachedRowSet,然后指定的条数开始
			cachedRowSet.populate(rs, (page - 1) * pageSize + 1);

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			closeAll();
		}

		return cachedRowSet;
	}

}
