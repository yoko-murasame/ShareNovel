package cn.dmdream.dao;

import java.util.List;

import  org.junit.Assert;

import org.junit.Before;
import org.junit.Test;

import cn.dmdream.dao.impl.SnUserDaoImpl;
import cn.dmdream.entity.SnUser;

public class TestSnUserDao {

	private SnUserDao snUserDao;
	
	@Before
	public void init() {
		snUserDao=new SnUserDaoImpl();
	}
	
	@Test
	public void testSave(){

		SnUser snUser=new SnUser(null,"pbb","666","dsg","","","",111,111,"2019-1-1 23:23:50");
		int i = snUserDao.save(snUser);
		Assert.assertEquals(1, i);
	}
	
	@Test
	public void testDeleteById(){
		int i = snUserDao.deleteById(3);
		Assert.assertEquals(1, i);
	}
	
	@Test
	public void testUpdate(){
		SnUser snUser=new SnUser(1,"pbb","666","xsg","","","",111,111,"2019-1-1 13:23:55");
		int i = snUserDao.update(snUser);
		Assert.assertEquals(1, i);
	}
	
	@Test
	public void testFindById(){
		SnUser snUser = snUserDao.findById(1);
		System.out.println(snUser);
	}
	@Test
	public void testFindAll(){
		List<SnUser> list = snUserDao.findAll();
		System.out.println(list);
	}
	@Test
	public void testFindByPage(){
		List<SnUser> list = snUserDao.findByPage(1, 1);
		System.out.println(list);
	}
	@Test
	public void testFIndByUserName(){
		SnUser snUser = snUserDao.findByUsername("pbb");
		System.out.println(snUser);
	}
}
