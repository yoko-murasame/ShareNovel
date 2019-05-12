package cn.dmdream.service.impl;

import java.util.List;

import cn.dmdream.dao.SnUserDao;
import cn.dmdream.dao.impl.SnUserDaoImpl;
import cn.dmdream.entity.SnUser;
import cn.dmdream.service.SnUserService;

public class SnUserServiceImpl implements SnUserService{
	
	private SnUserDao snUserDao=new SnUserDaoImpl();

	@Override
	public boolean save(SnUser snUser) {

		int i = snUserDao.save(snUser);
		if (i==1) {
			return true;
		}else {
			return false;
		}
		
	}

	@Override
	public boolean deleteById(Integer id) {

		int i = snUserDao.deleteById(id);
		if (i==1) {
			return true;
		}else {
			return false;
		}
	
	}

	@Override
	public boolean update(SnUser snUser) {

		int i = snUserDao.update(snUser);
		if (i==0) {
			return true;
		}else {
			return false;
		}
		
	}

	@Override
	public SnUser findById(Integer id) {

		SnUser snUser = snUserDao.findById(id);
		return snUser;
	}

	@Override
	public List<SnUser> findAll() {

		List<SnUser> list = snUserDao.findAll();
		return list;
	}

	@Override
	public List<SnUser> findByPage(int pageSize, int page) {

		List<SnUser> list = snUserDao.findByPage(pageSize, page);
		return list;
	}

	@Override
	public SnUser findByUsername(String username) {

		SnUser snUser = snUserDao.findByUsername(username);
		return snUser;
	}

	@Override
	public Boolean login(String name, String pwd) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 *  修改邮箱已验证
	 */
	@Override
	public Boolean emailActive(SnUser u) {
		u.setUserEmailActive(1);
		int ret = snUserDao.update(u);
		if(ret==1) {
			return true;
		}else
		return false;
	}

}
