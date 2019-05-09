package cn.dmdream.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import cn.dmdream.dao.impl.SnChapterDaoImpl;
import cn.dmdream.dao.impl.SnNovelDaoImpl;
import cn.dmdream.entity.SnChapter;
import cn.dmdream.entity.SnNovel;

public class TestSnChapterDao {

	private SnChapterDao chapterDao = new SnChapterDaoImpl();
	private SnNovelDao snNovelDao = new SnNovelDaoImpl();

	@Test
	public void testSave() {
		SnChapter chapter = new SnChapter();
		chapter.setChapterTitle("风华绝代");
		chapter.setChapterContent(
				"京华大学，华夏国最高学府，坐落于京华市中心之北，在历经数百年风风雨雨的洗礼后沉淀了丰厚的历史和文化底蕴，在全世界的高等学府中有着举足轻重的地位。能入京华，几乎是所有华夏学子的梦想和荣耀。");
		// 获取小说
		SnNovel novel = snNovelDao.findById(1);
		// 设置小说
		chapter.setSnNovel(novel);
		int i = chapterDao.save(chapter);
		Assert.assertEquals(i, 1);
	}

	@Test
	public void testSave2() {
		SnChapter chapter = new SnChapter();
		// 获取小说
		SnNovel novel = snNovelDao.findById(1);
		for (int i = 1; i <= 50; i++) {
			chapter.setSnNovel(novel);
			chapter.setChapterTitle("第" + i + "章-章节标题" + "");
			chapter.setChapterContent(
					"京华大学，华夏国最高学府，坐落于京华市中心之北，在历经数百年风风雨雨的洗礼后沉淀了丰厚的历史和文化底蕴，在全世界的高等学府中有着举足轻重的地位。能入京华，几乎是所有华夏学子的梦想和荣耀。");
			chapterDao.save(chapter);
		}

	}

	@Test
	public void testdelete() {
		SnChapter chapter = new SnChapter();
		chapter.setChapterId(51);
		int i = chapterDao.delete(chapter);
		Assert.assertEquals(1, i);
	}

	@Test
	public void testUpdate() {
		SnChapter chapter = chapterDao.findById(1);
		chapter.setChapterTitle("序章-风华绝代");
		int i = chapterDao.update(chapter);
		Assert.assertEquals(1, i);
	}

	@Test
	public void testfindById() {
		SnChapter chapter = chapterDao.findById(1);
		System.out.println(chapter);
	}

	@Test
	public void testfindByNovel() {
		SnNovel novel = snNovelDao.findById(1);
		List<SnChapter> chapterList = chapterDao.findByNovel(novel);
		chapterList.forEach(System.out::println);
	}

	@Test
	public void testfindByNovelByPage() {
		SnNovel novel=new SnNovel();
		novel.setNovelId(1);
		List<SnChapter> chapterList = chapterDao.findByNovelByPage(novel, 40, 1);
		chapterList.forEach(System.out::println);
	}

	@Test
	public void testfindByChapterTitle() {
		SnNovel novel = snNovelDao.findById(1);
		List<SnChapter> chapterList = chapterDao.findByNovelByChapterTitle(novel, "2");
		chapterList.forEach(System.out::println);
	}

	@Test
	public void testfindByChapterTitleByPage() {
		SnNovel novel = snNovelDao.findById(1);
		List<SnChapter> chapterList = chapterDao.findByNovelByChapterTitleByPage(novel, "2", 5, 2);
		chapterList.forEach(System.out::println);
	}
	
	@Test
	public void testfindByNovelByTitleOrderByPage(){
		SnNovel novel=new SnNovel();
		novel.setNovelId(1);
		List<SnChapter> chapterList = chapterDao.findByNovelByOrdersByPage(novel,SnChapterDao.DESC ,null,null, 10, 1);
		chapterList.forEach(System.out::println);
	}
	@Test
	public void testfindRecentUpdate(){
		long t=1000*60*60;
		List<SnChapter> chapterList = chapterDao.findRecentUpdate(t,10);
		chapterList.forEach(System.out::println);
	}
}
