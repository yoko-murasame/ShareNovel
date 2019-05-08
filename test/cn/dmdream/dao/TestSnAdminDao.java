package cn.dmdream.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cn.dmdream.dao.impl.SnAdminDaoImpl;
import cn.dmdream.entity.SnAdmin;

public class TestSnAdminDao {

	private SnAdminDao snAdminDao;
	
	@Before
	public void init() {
		snAdminDao=new SnAdminDaoImpl();
	}
	
	@Test
	public void testFindById(){
		SnAdmin snAdmin = snAdminDao.findById(1);
		System.out.println(snAdmin);
	}
	
	@Test
	public void testFindByUserName(){
		SnAdmin snAdmin = snAdminDao.findByUsername("333");
		System.out.println(snAdmin);
	}
	@Test
	public void testSave(){
		SnAdmin snAdmin=new SnAdmin(2,"331","123123","");
		int i = snAdminDao.save(snAdmin);
		Assert.assertEquals(1, i);
	}
	
	@Test
	public void testUpdate(){
		SnAdmin snAdmin=new SnAdmin(2,"333","123123","");
		int i = snAdminDao.update(snAdmin);
		Assert.assertEquals(1, i);
	}
}
