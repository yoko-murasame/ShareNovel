package cn.dmdream.dao;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import cn.dmdream.dao.impl.SnCommentDaoImpl;
import cn.dmdream.entity.SnComment;
import cn.dmdream.entity.SnNovel;
import cn.dmdream.entity.SnUser;

public class TestSnCommentDao {

	private SnCommentDao commentDao = new SnCommentDaoImpl();
	
	@Test
	public void testSave(){
		SnUser user = new SnUser();
		user.setUserId(1);
		SnNovel novel = new SnNovel();
		novel.setNovelId(1);
		String commContent = "评论1122212322234545675678678124";
		Integer commHitnum = 2;
		SnComment parent = new SnComment();//空对象,注意不能直接赋值null
		parent.setCommId(1);
		
		SnComment comment = new SnComment();
		comment.setCommUser(user);
		comment.setCommNovel(novel);
		comment.setCommContent(commContent);
		comment.setCommHitnum(commHitnum);
		comment.setCommParent(parent);
		int i = commentDao.save(comment);
		Assert.assertEquals(1, i);
	}
	
	@Test
	public void testSave2(){
		SnUser user = new SnUser();
		SnNovel novel = new SnNovel();
		novel.setNovelId(1);
		
		for(int i = 1 ; i <= 30;i++){
			String commContent = "评论12322234545675678678124";
			Integer commHitnum = 2;
			SnComment parent = new SnComment();//空对象,注意不能直接赋值null
			if(i%2 == 0){
				user.setUserId(1);
				parent.setCommId(1);
			}else{
				user.setUserId(2);
			}
			
			SnComment comment = new SnComment();
			comment.setCommUser(user);
			comment.setCommNovel(novel);
			comment.setCommContent(commContent);
			comment.setCommHitnum(commHitnum);
			comment.setCommParent(parent);
			commentDao.save(comment);
		}
	}
	
	@Test
	public void testDelete(){
		int i = commentDao.deleteById(1);
		System.out.println(i);
		//Assert.assertEquals(1, i);
	}
	
	@Test
	public void testfindById(){
		SnComment comment = commentDao.findById(1);
		System.out.println(comment);
	}
	
	@Test
	public void testUpdate(){
		SnComment comment = commentDao.findById(2);
		comment.setCommContent("修改了评论内容333");
		comment.setCommHitnum(5);
		commentDao.update(comment);
	}
	
	@Test
	public void testfindByUser(){
		SnUser user = new SnUser();
		user.setUserId(1);
		List<SnComment> list = commentDao.findByUser(user);
		list.forEach(System.out::println);
	}
	
	@Test
	public void testfindByUserByPage(){
		SnUser user = new SnUser();
		user.setUserId(2);
		List<SnComment> list = commentDao.findByUserByPage(user, 5, 1);
		list.forEach(System.out::println);
	}
	
	@Test
	public void testfindByNovel(){
		SnNovel novel = new SnNovel();
		novel.setNovelId(1);
		List<SnComment> list = commentDao.findByNovel(novel);
		list.forEach(System.out::println);
	}
	
	@Test
	public void testfindByNovelByPage(){
		SnNovel novel = new SnNovel();
		novel.setNovelId(1);
		List<SnComment> list = commentDao.findByNovelByPage(novel, 5, 1);
		list.forEach(System.out::println);
	}
	
	@Test
	public void testfindAllChildComment(){
		SnComment comment = new SnComment();
		comment.setCommId(2);
		List<SnComment> list = commentDao.findAllChildComment(comment);
		list.forEach(System.out::println);
	}
	
	@Test
	public void testfindAllChildCommentByPage(){
		SnComment comment = new SnComment();
		comment.setCommId(2);
		List<SnComment> list = commentDao.findAllChildCommentByPage(comment, 3, 2);
		list.forEach(System.out::println);
	}
}
