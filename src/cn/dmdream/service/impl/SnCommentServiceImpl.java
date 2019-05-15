package cn.dmdream.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.dmdream.dao.SnCommentDao;
import cn.dmdream.dao.impl.SnCommentDaoImpl;
import cn.dmdream.entity.SnComment;
import cn.dmdream.entity.SnNovel;
import cn.dmdream.entity.SnUser;
import cn.dmdream.entity.JsonEntity.CommentJSON;
import cn.dmdream.service.SnCommentService;

public class SnCommentServiceImpl implements SnCommentService {

	private SnCommentDao commentDao = new SnCommentDaoImpl();
	
	/**
	 * 新增
	 * @param comment
	 * @return
	 */
	public boolean save(SnComment comment){
		int i = commentDao.save(comment);
		return i > 0 ?true :false;
	};
	
	/**
	 * 根据对象删除,删除父评论会删除子评论
	 * @param comment
	 * @return
	 */
	public boolean delete(SnComment comment){
		int i = commentDao.delete(comment);
		return i > 0 ?true :false;
	};
	
	/**
	 * 根据主键删除
	 * @param id
	 * @return
	 */
	public boolean deleteById(Integer id){
		int i = commentDao.deleteById(id);
		return i > 0 ?true :false;
	};
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public SnComment findById(Integer id){
		return commentDao.findById(id);
	};
	
	/**
	 * 更新
	 * @param comment
	 * @return
	 */
	public boolean update(SnComment comment){
		int i = commentDao.update(comment);
		return i > 0 ? true :false;
	};
	
	/**
	 * 根据用户查询所有评论
	 * @param user
	 * @return
	 */
	public List<SnComment> findByUser(SnUser user){
		return commentDao.findByUser(user);
	};
	
	/**
	 * 根据用户查询所有评论,分页版
	 * @param user
	 * @return
	 */
	public List<SnComment> findByUserByPage(SnUser user , int pageSize , int page){
		return commentDao.findByUserByPage(user, pageSize, page);
	};
	
	/**
	 * 根据小说查询所有评论
	 * @param novel
	 * @return
	 */
	public List<SnComment> findByNovel(SnNovel novel){
		return commentDao.findByNovel(novel);
	};
	
	/**
	 * 根据小说查询所有评论,分页版
	 * @param novel
	 * @return
	 */
	public List<SnComment> findByNovelByPage(SnNovel novel , int pageSize , int page){
		return commentDao.findByNovelByPage(novel, pageSize, page);
	};
	
	/**
	 * 查询父评论下的所有子评论
	 * @param comment
	 * @return
	 */
	
	public List<SnComment> findAllChildComment(SnComment comment){
		return commentDao.findAllChildComment(comment);
	};
	
	/**
	 * 查询父评论下的所有子评论,分页版
	 * @param comment
	 * @param pageSize
	 * @param page
	 * @return
	 */
	public List<SnComment> findAllChildCommentByPage(SnComment comment , int pageSize , int page){
		return commentDao.findAllChildCommentByPage(comment, pageSize, page);
	}


	@Override
	public List<SnComment> queryByUser(SnUser user) {
		return commentDao.queryByUser(user);
	};
}
