package cn.dmdream.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import cn.dmdream.dao.impl.SnChapterDaoImpl;
import cn.dmdream.dao.impl.SnCollectionDaoImpl;
import cn.dmdream.entity.SnChapter;
import cn.dmdream.entity.SnCollection;
import cn.dmdream.entity.SnNovel;
import cn.dmdream.entity.SnUser;

public class TestSnCollectionDao {

	private SnCollectionDao collectionDao = new SnCollectionDaoImpl();
	private SnChapterDao chapterDao = new SnChapterDaoImpl();
	
	@Test
	public void testSave(){
		SnCollection collection = new SnCollection();
		
		SnUser user = new SnUser();
		user.setUserId(1);
		SnNovel novel = new SnNovel();
		novel.setNovelId(2);
		//获取当前小说最新更新章节
		List<SnChapter> list = chapterDao.findByNovelByOrdersByPage(novel, null, null , "desc" , 1, 1);
		list.forEach(System.out::println);
		
		collection.setCollectUser(user);
		collection.setCollectNovel(novel);
		collection.setCollectChapterHistory(list.get(0));
		
		int i = collectionDao.save(collection);
		Assert.assertEquals(1, i);
	}
	
	@Test
	public void testdeleteByUserByNovel(){
		SnUser user = new SnUser();
		user.setUserId(1);
		SnNovel novel = new SnNovel();
		novel.setNovelId(2);
		int i = collectionDao.deleteByUserByNovel(user, novel);
		Assert.assertEquals(1, i);
	}
	
	@Test
	public void testupdateChapterHistoryByNovel(){
		SnUser user = new SnUser();
		user.setUserId(1);
		SnNovel novel = new SnNovel();
		novel.setNovelId(1);
		SnChapter chapter = chapterDao.findByNovelByChapterTitle(novel, "第1章").get(0);
		collectionDao.updateChapterHistoryByNovel(user, novel, chapter);
	}
	
	@Test
	public void testfindDistinctCollection(){
		SnUser user = new SnUser();
		user.setUserId(1);
		SnNovel novel = new SnNovel();
		novel.setNovelId(1);
		SnCollection collection = collectionDao.findDistinctCollection(user, novel);
		System.out.println(collection);
	}
	
	@Test
	public void testfindByUser(){
		SnUser user = new SnUser();
		user.setUserId(1);
		List<SnCollection> list = collectionDao.findByUserByPage(user , 1 , 3);
		list.forEach(System.out::println);
	}
	
	@Test
	public void testfindByNovelByPage(){
		SnNovel novel = new SnNovel();
		novel.setNovelId(3);
		List<SnCollection> list = collectionDao.findByNovelByPage(novel , 5 ,1);
		list.forEach(System.out::println);
	}
	
	@Test
	public void testcountByNovel(){
		SnNovel novel = new SnNovel();
		novel.setNovelId(5);
		int count = collectionDao.countByNovel(novel);
		System.out.println(count);
	}
}
