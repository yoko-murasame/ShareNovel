package cn.dmdream.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import cn.dmdream.dao.impl.SnCategoryDaoImpl;
import cn.dmdream.entity.SnCategory;

public class TestSnCategoryDao {

	private SnCategoryDao snCategoryDao = new SnCategoryDaoImpl();
	
	@Test
	public void testSave(){
		SnCategory snCategory = new SnCategory();
		//snCategory.setCatName("奇幻玄幻");
		//snCategory.setCatName("武侠仙侠");
		//snCategory.setCatName("都市娱乐");
		//snCategory.setCatName("历史军事");
		//snCategory.setCatName("游戏竞技");
		//snCategory.setCatName("科幻灵异");
		//snCategory.setCatName("二次元");
		
		//snCategory.setCatName("都市言情");
		//snCategory.setCatName("古装言情");
		//snCategory.setCatName("幻想言情");
		//snCategory.setCatName("浪漫青春");
		//snCategory.setCatName("游戏竞技");
		//snCategory.setCatName("科幻空间");
		//snCategory.setCatName("悬疑灵异");
		//snCategory.setCatName("耽美同人");
		
		//snCategory.setCatParentid(0);
		//snCategory.setCatGender(1);//女频
		//int i = snCategoryDao.save(snCategory);
		//Assert.assertEquals(i, 1);
		
		//子分类的测试插入
		for(int i = 0 ; i < 5 ; i++){
			snCategory.setCatName("测试分类" + (i+1));
			snCategory.setCatParentid(1);
			
			snCategoryDao.save(snCategory);
		}
		
	}
	
	@Test
	public void testDelete(){
		SnCategory snCategory = new SnCategory();
		snCategory.setCatId(16);
		snCategoryDao.delete(snCategory);
	}
	
	@Test
	public void testfindAllParent(){
		List<SnCategory> list = snCategoryDao.findAllParent();
		list.forEach(System.out::println);
	}
	
	@Test
	public void testfindById(){
		SnCategory category = snCategoryDao.findById(1);
		System.out.println(category);
	}
	
	@Test
	public void testfindByParentId(){
		List<SnCategory> list = snCategoryDao.findByParentId(1);
		list.forEach(System.out::println);
	}
	
	@Test
	public void testfindByCatName(){
		List<SnCategory> list = snCategoryDao.findByCatName("游戏");
		list.forEach(System.out::println);
	}
	
	@Test
	public void testfindByCatGender(){
		List<SnCategory> list = snCategoryDao.findByCatGender(1);
		list.forEach(System.out::println);
	}
	
}
