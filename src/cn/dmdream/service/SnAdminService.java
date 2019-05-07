package cn.dmdream.service;

import cn.dmdream.entity.SnAdmin;

/**
 * 管理员Service层接口
 * @author KuluS
 *
 */
public interface SnAdminService {
	
	/**
	 * 根据主键查询管理员角色
	 * @param id
	 * @return
	 */
	public SnAdmin findById(Integer id);
	
	/**
	 * 根据管理员用户名查询 用于登录
	 * @param adminUsername
	 * @return
	 */
	public SnAdmin findByUsername(String adminUsername);

	/**
	 * 管理员的新增操作 返回boolean
	 * @param snAdmin
	 * @return
	 */
	public boolean save(SnAdmin snAdmin);
	
	/**
	 * 管理员的修改操作 返回boolean
	 * @param snAdmin
	 * @return
	 */
	public boolean update(SnAdmin snAdmin);
}
