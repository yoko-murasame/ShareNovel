package cn.dmdream.dao;

import java.util.List;

import cn.dmdream.entity.SnUser;

/**
 * 用户Dao层接口
 * @author KuluS
 *
 */
public interface SnUserDao {

	/**
	 * 用户新增，传入对象，主键置为空
	 * @param snAdmin
	 * @return int类型受影响的记录数
	 */
	public int save(SnUser snUser);
	
	/**
	 * 根据主键删除
	 * @param id
	 * @return int类型受影响的记录数
	 */
	public int deleteById(Integer id);
	
	/**
	 * 用户修改 传入对象 根据对象的主键修改
	 * @param snAdmin
	 * @return int类型受影响的记录数
	 */
	public int update(SnUser snUser);
	
	/**
	 * 根据主键查询用户
	 * @param id
	 * @return
	 */
	public SnUser findById(Integer id);
	
	/**
	 * 查询所有
	 * @return
	 */
	public List<SnUser> findAll();
	
	/**
	 * 分页查询 
	 * @param pageSize 页面大小
	 * @param page 当前第几页
	 * @return
	 */
	public List<SnUser> findByPage(int pageSize , int page);
	
	/**
	 * 根据用户名查询 用于登录操作 返回对象 用户名是唯一的
	 * @param username
	 * @return
	 */
	public SnUser findByUsername(String username);
	
	public int countByUsername(String username);
	/**
	 * 
	 * @param name  用户名 或者邮箱 手机号暂时不考虑
	 * @param pwd  密码
	 * @return
	 */
	public List<SnUser> login(String name,String pwd);
	
}
