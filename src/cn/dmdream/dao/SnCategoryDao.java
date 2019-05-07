package cn.dmdream.dao;

import java.util.List;

import cn.dmdream.entity.SnCategory;

/**
 * CategoryDao层接口
 * @author KuluS
 *
 */
public interface SnCategoryDao {

	/**
	 * 新增分类
	 * @param snCategory
	 * @return
	 */
	public int save(SnCategory snCategory);
	
	/**
	 * 删除分类
	 * @param snCategory
	 * @return
	 */
	public int delete(SnCategory snCategory);
	
	/**
	 * 修改分类
	 * @param snCategory
	 * @return
	 */
	public int update(SnCategory snCategory);
	
	/**
	 * 查询所有父分类
	 * @return
	 */
	public List<SnCategory> findAllParent();
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	public SnCategory findById(Integer id);
	
	/**
	 * 查询改父分类id下的所有子分类
	 * @return
	 */
	public List<SnCategory> findByParentId(Integer id);
	
	/**
	 * 根据分类名模糊查询
	 * @param catName
	 * @return
	 */
	public List<SnCategory> findByCatName(String catName);
	
	/**
	 * 根据男频/女频分类查询
	 * @param gender
	 * @return
	 */
	public List<SnCategory> findByCatGender(Integer gender);
}
