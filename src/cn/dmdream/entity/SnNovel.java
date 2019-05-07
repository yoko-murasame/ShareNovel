package cn.dmdream.entity;

import java.util.Map;

public class SnNovel {

	private Integer novelId;//True	小说主键id,唯一
	private String novelTitle;//True	小说名称
	private String novelAuthor;//True	作者名称
	private SnCategory snCategory;//True	外键指向类别类型，使用对象存储
	private String novelUpdatetime;//小说最后更新时间，数据库自动生成
	private Integer novelIsEnd;//是否完结 0:未完结 1:已完结
	private String novelSummary;//小说简介
	private SnUser novelShareUser;//True	外键,小说分享者id,方便查询，使用对象储存
	private Map<String,String> novelDownloadurl;//False	下载地址{'站内地址1':'url...','站外地址1':'url..'}
	private Integer novelCheck;//False	审核结果 0:为审核 1:通过 2:未通过
	private String novelCover;//True	小说封面
	public SnNovel() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SnNovel(Integer novelId, String novelTitle, String novelAuthor, SnCategory snCategory,
			String novelUpdatetime, Integer novelIsEnd, String novelSummary, SnUser novelShareUser,
			Map<String, String> novelDownloadurl, Integer novelCheck, String novelCover) {
		super();
		this.novelId = novelId;
		this.novelTitle = novelTitle;
		this.novelAuthor = novelAuthor;
		this.snCategory = snCategory;
		this.novelUpdatetime = novelUpdatetime;
		this.novelIsEnd = novelIsEnd;
		this.novelSummary = novelSummary;
		this.novelShareUser = novelShareUser;
		this.novelDownloadurl = novelDownloadurl;
		this.novelCheck = novelCheck;
		this.novelCover = novelCover;
	}
	public Integer getNovelId() {
		return novelId;
	}
	public void setNovelId(Integer novelId) {
		this.novelId = novelId;
	}
	public String getNovelTitle() {
		return novelTitle;
	}
	public void setNovelTitle(String novelTitle) {
		this.novelTitle = novelTitle;
	}
	public String getNovelAuthor() {
		return novelAuthor;
	}
	public void setNovelAuthor(String novelAuthor) {
		this.novelAuthor = novelAuthor;
	}
	public SnCategory getSnCategory() {
		return snCategory;
	}
	public void setSnCategory(SnCategory snCategory) {
		this.snCategory = snCategory;
	}
	public String getNovelUpdatetime() {
		return novelUpdatetime;
	}
	public void setNovelUpdatetime(String novelUpdatetime) {
		this.novelUpdatetime = novelUpdatetime;
	}
	public Integer getNovelIsEnd() {
		return novelIsEnd;
	}
	public void setNovelIsEnd(Integer novelIsEnd) {
		this.novelIsEnd = novelIsEnd;
	}
	public String getNovelSummary() {
		return novelSummary;
	}
	public void setNovelSummary(String novelSummary) {
		this.novelSummary = novelSummary;
	}
	public SnUser getNovelShareUser() {
		return novelShareUser;
	}
	public void setNovelShareUser(SnUser novelShareUser) {
		this.novelShareUser = novelShareUser;
	}
	public Map<String, String> getNovelDownloadurl() {
		return novelDownloadurl;
	}
	public void setNovelDownloadurl(Map<String, String> novelDownloadurl) {
		this.novelDownloadurl = novelDownloadurl;
	}
	public Integer getNovelCheck() {
		return novelCheck;
	}
	public void setNovelCheck(Integer novelCheck) {
		this.novelCheck = novelCheck;
	}
	public String getNovelCover() {
		return novelCover;
	}
	public void setNovelCover(String novelCover) {
		this.novelCover = novelCover;
	}
	@Override
	public String toString() {
		return "SnNovel [novelId=" + novelId + ", novelTitle=" + novelTitle + ", novelAuthor=" + novelAuthor
				+ ", snCategory=" + snCategory + ", novelUpdatetime=" + novelUpdatetime + ", novelIsEnd=" + novelIsEnd
				+ ", novelSummary=" + novelSummary + ", novelShareUser=" + novelShareUser + ", novelDownloadurl="
				+ novelDownloadurl + ", novelCheck=" + novelCheck + ", novelCover=" + novelCover + "]";
	}
	
}
