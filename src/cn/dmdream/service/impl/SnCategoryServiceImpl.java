package cn.dmdream.service.impl;

import java.util.List;

import cn.dmdream.dao.SnCategoryDao;
import cn.dmdream.dao.impl.SnCategoryDaoImpl;
import cn.dmdream.entity.SnCategory;
import cn.dmdream.service.SnCategoryService;

public class SnCategoryServiceImpl implements SnCategoryService {

	private SnCategoryDao snCategoryDao = new SnCategoryDaoImpl();
	/**
	 * 新增分类
	 * 
	 * @param snCategory
	 * @return
	 */
	public boolean save(SnCategory snCategory){
		int i = snCategoryDao.save(snCategory);
		return i==1?true:false;
	};

	/**
	 * 删除分类
	 * 
	 * @param snCategory
	 * @return
	 */
	public boolean delete(SnCategory snCategory){
		int i = snCategoryDao.delete(snCategory);
		return i==1?true:false;
	};

	/**
	 * 修改分类
	 * 
	 * @param snCategory
	 * @return
	 */
	public boolean update(SnCategory snCategory){
		int i = snCategoryDao.update(snCategory);
		return i==1?true:false;
	};

	/**
	 * 查询所有父分类
	 * 
	 * @return
	 */
	public List<SnCategory> findAllParent(){
		return snCategoryDao.findAllParent();
	};

	/**
	 * 根据主键查询
	 * 
	 * @param id
	 * @return
	 */
	public SnCategory findById(Integer id){
		return snCategoryDao.findById(id);
	};

	/**
	 * 查询改父分类id下的所有子分类
	 * 
	 * @return
	 */
	public List<SnCategory> findByParentId(Integer id){
		return snCategoryDao.findByParentId(id);
	};

	/**
	 * 根据分类名模糊查询
	 * 
	 * @param catName
	 * @return
	 */
	public List<SnCategory> findByCatName(String catName){
		return snCategoryDao.findByCatName(catName);
	};

	/**
	 * 根据男频0/1女频分类查询
	 * 
	 * @param gender
	 * @return
	 */
	public List<SnCategory> findByCatGender(Integer gender){
		return snCategoryDao.findByCatGender(gender);
	};

}
