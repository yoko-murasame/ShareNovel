package cn.dmdream.dao;

import org.junit.Test;

import cn.dmdream.dao.impl.SnCategoryDaoImpl;
import cn.dmdream.dao.impl.SnNovelDaoImpl;
import cn.dmdream.entity.SnCategory;
import cn.dmdream.entity.SnNovel;

public class TestSnNovelDao {

	private SnNovelDao snNovelDao = new SnNovelDaoImpl();
	private SnCategoryDao snCategoryDao = new SnCategoryDaoImpl();
	
	@Test
	public void testSave(){
		SnNovel snNovel = new SnNovel();
		SnCategory snCategory = snCategoryDao.findById(1);
		
		snNovel.setNovelTitle("元尊");
		snNovel.setNovelAuthor("天蚕土豆");
		snNovel.setSnCategory(snCategory);
		snNovel.setNovelIsEnd(0);
		
	}
}
