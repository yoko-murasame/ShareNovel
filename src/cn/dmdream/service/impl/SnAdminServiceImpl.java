package cn.dmdream.service.impl;

import cn.dmdream.dao.SnAdminDao;
import cn.dmdream.dao.impl.SnAdminDaoImpl;
import cn.dmdream.entity.SnAdmin;
import cn.dmdream.service.SnAdminService;

public class SnAdminServiceImpl implements SnAdminService{
	
	private SnAdminDao snAdminDao=new SnAdminDaoImpl();

	@Override
	public SnAdmin findById(Integer id) {

		SnAdmin snAdmin = snAdminDao.findById(id);
		return snAdmin;
	}

	@Override
	public SnAdmin findByUsername(String adminUsername) {

		SnAdmin snAdmin = snAdminDao.findByUsername(adminUsername);
		return snAdmin;
	}

	@Override
	public boolean save(SnAdmin snAdmin) {

		int i = snAdminDao.save(snAdmin);
		if(i==1){
			return true;
		}else {
			return false;
		}
		
	}

	@Override
	public boolean update(SnAdmin snAdmin) {

		int i = snAdminDao.update(snAdmin);
		if (i==1) {
			return true;
		}else {
			return false;
		}
		
	}

}
