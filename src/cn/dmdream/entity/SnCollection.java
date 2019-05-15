package cn.dmdream.entity;

public class SnCollection {

	private Integer collectId;//	True	收藏表主键
	private SnUser collectUser;//	True	外键指向sn_user表
	private SnNovel collectNovel;//	True	外键,指向小说id
	private SnChapter collectChapterHistory;//	False	外键,指向小说章节信息,总是指向最新的章节
	public SnCollection() {
		super();
	}
	public SnCollection(Integer collectId, SnUser collectUser, SnNovel collectNovel, SnChapter collectChapterHistory) {
		super();
		this.collectId = collectId;
		this.collectUser = collectUser;
		this.collectNovel = collectNovel;
		this.collectChapterHistory = collectChapterHistory;
	}
	public Integer getCollectId() {
		return collectId;
	}
	public void setCollectId(Integer collectId) {
		this.collectId = collectId;
	}
	public SnUser getCollectUser() {
		return collectUser;
	}
	public void setCollectUser(SnUser collectUser) {
		this.collectUser = collectUser;
	}
	public SnNovel getCollectNovel() {
		return collectNovel;
	}
	public void setCollectNovel(SnNovel collectNovel) {
		this.collectNovel = collectNovel;
	}
	public SnChapter getCollectChapterHistory() {
		return collectChapterHistory;
	}
	public void setCollectChapterHistory(SnChapter collectChapterHistory) {
		this.collectChapterHistory = collectChapterHistory;
	}
	@Override
	public String toString() {
		return "SnCollection [collectId=" + collectId + ", collectUser=" + collectUser + ", collectNovel="
				+ collectNovel + ", collectChapterHistory=" + collectChapterHistory + "]";
	}
}
