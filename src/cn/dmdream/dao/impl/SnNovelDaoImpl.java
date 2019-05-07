package cn.dmdream.dao.impl;

import java.util.List;
import java.util.Map;

import cn.dmdream.dao.SnNovelDao;
import cn.dmdream.entity.SnNovel;
import cn.dmdream.utils.DbUtil;

/**
 * 小说Dao层接口实现类
 * @author KuluS
 *
 */
public class SnNovelDaoImpl implements SnNovelDao {

	DbUtil dbUtil = new DbUtil();
	/**
	 * 小说新增
	 * @param snNovel
	 * @return
	 */
	public int save(SnNovel snNovel) {

		String sql = "insert into sn_novel values(null,?,?,?,null,?,?,?,?,?,?)";
		String novelTitle = snNovel.getNovelTitle();
		String novelAuthor = snNovel.getNovelAuthor();
		//获取category的id
		Integer catId = snNovel.getSnCategory().getCatId();
		Integer novelIsEnd = snNovel.getNovelIsEnd();
		String novelSummary = snNovel.getNovelSummary();
		//获取外键-分享者id
		Integer userId = snNovel.getNovelShareUser().getUserId();
		Map<String, String> novelDownloadurl = snNovel.getNovelDownloadurl();
		Integer novelCheck = snNovel.getNovelCheck();
		String novelCover = snNovel.getNovelCover();
		
		int i = dbUtil.update(sql, novelTitle,novelAuthor,catId,novelIsEnd,novelSummary,userId,novelDownloadurl,novelCheck,novelCover);
		
		return i;
	}

	@Override
	public int delete(SnNovel snNovel) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(SnNovel snNovel) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SnNovel findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SnNovel> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SnNovel> findAllByPage(int pageSize, int page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SnNovel> findByTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SnNovel> findByAuthor(String author) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SnNovel> findByTitleByPage(String title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SnNovel> findByAuthorByPage(String author) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SnNovel> findByCheck(Integer check) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SnNovel> findByCheckByPage(Integer check, int pageSize, int page) {
		// TODO Auto-generated method stub
		return null;
	}

}
