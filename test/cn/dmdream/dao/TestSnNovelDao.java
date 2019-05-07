package cn.dmdream.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import cn.dmdream.dao.impl.SnCategoryDaoImpl;
import cn.dmdream.dao.impl.SnNovelDaoImpl;
import cn.dmdream.entity.SnCategory;
import cn.dmdream.entity.SnNovel;
import cn.dmdream.entity.SnUser;

public class TestSnNovelDao {

	private SnNovelDao snNovelDao = new SnNovelDaoImpl();
	private SnCategoryDao snCategoryDao = new SnCategoryDaoImpl();

	@Test
	public void testSave() {
		SnNovel snNovel = new SnNovel();
		SnCategory snCategory = snCategoryDao.findById(1);
		SnUser snUser = new SnUser();
		snUser.setUserId(1);
		HashMap<String, String> novelDownloadurl = new HashMap<String, String>();
		novelDownloadurl.put("txt文本下载", "http://193.112.41.124/group1/M00/00/00/rBAABVzRGrKAbhKPAH1lTXI8cK8029.txt");

		snNovel.setNovelTitle("网游之邪龙逆天 ");
		snNovel.setNovelAuthor("火星引力");
		snNovel.setSnCategory(snCategory);
		snNovel.setNovelIsEnd(0);
		snNovel.setNovelSummary("一个龙魂少年的逆天之路。龙魂爆，苍穹断，花开花谢已千年。前生缘，今生恋，化身邪龙誓逆天。");
		snNovel.setNovelShareUser(snUser);
		snNovel.setNovelDownloadurl(novelDownloadurl);
		snNovel.setNovelCheck(0);
		snNovel.setNovelCover("http://193.112.41.124/group1/M00/00/00/rBAABVzRG5qADThpAABX1u4KyUQ675.jpg");

		snNovelDao.save(snNovel);

	}

	@Test
	public void testSave2() {
		SnNovel snNovel = new SnNovel();
		SnCategory snCategory = snCategoryDao.findById(1);
		SnUser snUser = new SnUser();
		snUser.setUserId(1);
		HashMap<String, String> novelDownloadurl = new HashMap<String, String>();
		novelDownloadurl.put("暂无下载", "#");

		snNovel.setNovelTitle("元尊");
		snNovel.setNovelAuthor("天蚕土豆");
		snNovel.setSnCategory(snCategory);
		snNovel.setNovelIsEnd(0);
		snNovel.setNovelSummary("土豆新书,不知东西");
		snNovel.setNovelShareUser(snUser);
		snNovel.setNovelDownloadurl(novelDownloadurl);
		snNovel.setNovelCheck(0);
		snNovel.setNovelCover("http://193.112.41.124/group1/M00/00/00/rBAABVzRHTSAWjEEAAKvF_lV0sc827.jpg");
		// snNovel.setNovelTitle("逆天邪神");
		// snNovel.setNovelAuthor("火星引力");
		// snNovel.setSnCategory(snCategory);
		// snNovel.setNovelIsEnd(0);
		// snNovel.setNovelSummary("云澈小白脸的故事");
		// snNovel.setNovelShareUser(snUser);
		// snNovel.setNovelDownloadurl(novelDownloadurl);
		// snNovel.setNovelCheck(0);
		// snNovel.setNovelCover("http://193.112.41.124/group1/M00/00/00/rBAABVzRHPyAVKeEAAGCMX8MHUQ669.jpg");

		snNovelDao.save(snNovel);
	}

	@Test
	public void testSave3(){
		SnNovel snNovel = new SnNovel();
		SnUser snUser = new SnUser();
		snUser.setUserId(1);
		HashMap<String, String> novelDownloadurl = new HashMap<String, String>();
		novelDownloadurl.put("暂无下载", "#");
		for(int i = 1 ;i <= 10 ; i++){
			SnCategory snCategory = snCategoryDao.findById(new Random().nextInt(15) + 1);//随机分类
			snNovel.setNovelTitle("测试标题" + i);
			snNovel.setNovelAuthor("测试作者" + i);
			snNovel.setSnCategory(snCategory);
			snNovel.setNovelIsEnd(i%2);
			snNovel.setNovelSummary("测试简介~~~~~~" + i);
			snNovel.setNovelShareUser(snUser);
			snNovel.setNovelDownloadurl(novelDownloadurl);
			snNovel.setNovelCheck(0);
			snNovel.setNovelCover("http://193.112.41.124/group1/M00/00/00/rBAABVzRHTSAWjEEAAKvF_lV0sc827.jpg");
			snNovelDao.save(snNovel);
		}
	}
	
	
	@Test
	public void testdelete(){
		SnNovel snNovel = new SnNovel();
		snNovel.setNovelId(13);
		int i = snNovelDao.delete(snNovel);
		Assert.assertEquals(i, 1);
	}
	
	@Test
	public void testUpdate(){
		SnNovel novel = snNovelDao.findById(4);
		novel.setNovelTitle("执魔");
		novel.setNovelAuthor("我是墨水");
		novel.setSnCategory(snCategoryDao.findById(1));
		novel.setNovelSummary("宁凡的故事,一月一更..");
		int i = snNovelDao.update(novel);
		Assert.assertEquals(i, 1);
	}
	
	@Test
	public void testfindById(){
		SnNovel novel = snNovelDao.findById(1);
		System.out.println(novel);
	}
	
	@Test
	public void testfindAll(){
		List<SnNovel> list = snNovelDao.findAll();
		list.forEach(System.out::println);
	}
	
	@Test
	public void testfindAllByPage(){
		List<SnNovel> list = snNovelDao.findAllByPage(3, 2);
		list.forEach(System.out::println);
	}
	
	@Test
	public void testfindByTitle(){
		List<SnNovel> list = snNovelDao.findByTitle("逆天");
		list.forEach(System.out::println);
	}
	
	@Test
	public void testfindByAuthor(){
		List<SnNovel> list = snNovelDao.findByAuthor("火星");
		list.forEach(System.out::println);
	}
	
	@Test
	public void testfindByTitleByPage(){
		List<SnNovel> list = snNovelDao.findByTitleByPage("测试", 3, 2);
		list.forEach(System.out::println);
	}
	
	@Test
	public void testfindByAuthorByPage(){
		List<SnNovel> list = snNovelDao.findByAuthorByPage("火星", 1, 2);
		list.forEach(System.out::println);
	}
	
	@Test
	public void testfindByCheck(){
		List<SnNovel> list = snNovelDao.findByCheck(2);
		list.forEach(System.out::println);
	}
	
	@Test
	public void testfindByCheckByPage(){
		List<SnNovel> list = snNovelDao.findByCheckByPage(0, 4, 2);
		list.forEach(System.out::println);
	}
}
