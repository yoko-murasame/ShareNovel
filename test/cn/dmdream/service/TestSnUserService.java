package cn.dmdream.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cn.dmdream.dao.SnUserDao;
import cn.dmdream.dao.impl.SnUserDaoImpl;
import cn.dmdream.entity.SnUser;
import cn.dmdream.service.impl.SnUserServiceImpl;

public class TestSnUserService {
	
	SnUserService snUserService=new SnUserServiceImpl();
	
	
	@Before
	public void init() {
		snUserService=new SnUserServiceImpl();
	}
	
	@Test
	public void testSave(){

		SnUser snUser=new SnUser(1,"pbbpp","666","dsg","","","",111,111,"2019-1-1 13:23:55");
		boolean isok = snUserService.save(snUser);
		Assert.assertEquals(true, isok);
	}
}
