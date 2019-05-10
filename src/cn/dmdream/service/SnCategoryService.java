package cn.dmdream.service;

import java.util.List;

import cn.dmdream.entity.SnCategory;

/**
 * CategoryService层接口
 * 
 * @author KuluS
 *
 */
public interface SnCategoryService {
	/**
	 * 新增分类
	 * 
	 * @param snCategory
	 * @return
	 */
	public boolean save(SnCategory snCategory);

	/**
	 * 删除分类
	 * 
	 * @param snCategory
	 * @return
	 */
	public boolean delete(SnCategory snCategory);

	/**
	 * 修改分类
	 * 
	 * @param snCategory
	 * @return
	 */
	public boolean update(SnCategory snCategory);

	/**
	 * 查询所有父分类
	 * 
	 * @return
	 */
	public List<SnCategory> findAllParent();

	/**
	 * 根据主键查询
	 * 
	 * @param id
	 * @return
	 */
	public SnCategory findById(Integer id);

	/**
	 * 查询改父分类id下的所有子分类
	 * 
	 * @return
	 */
	public List<SnCategory> findByParentId(Integer id);

	/**
	 * 根据分类名模糊查询
	 * 
	 * @param catName
	 * @return
	 */
	public List<SnCategory> findByCatName(String catName);

	/**
	 * 根据男频/女频分类查询
	 * 
	 * @param gender
	 * @return
	 */
	public List<SnCategory> findByCatGender(Integer gender);

	/**
	 * 查询改父分类id下的所有子分类分页版
	 * @return
	 */
	public List<SnCategory> findByParentIdByPage(int id , int pageSize ,int page);

	public Integer findCount(Integer id);
}
