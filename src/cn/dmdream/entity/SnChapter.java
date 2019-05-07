package cn.dmdream.entity;

public class SnChapter {

	private Integer chapterId;// True 章节id,自增
	private SnNovel snNovel;// True 外键 指向小说id 小说对象
	private String chapterTitle;// True 章节标题
	private String chapterContent;// True 章节文本
	private String chapterUpdatetime;// 章节更新时间

	public SnChapter() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Integer getChapterId() {
		return chapterId;
	}

	public void setChapterId(Integer chapterId) {
		this.chapterId = chapterId;
	}

	public SnNovel getSnNovel() {
		return snNovel;
	}

	public void setSnNovel(SnNovel snNovel) {
		this.snNovel = snNovel;
	}

	public String getChapterTitle() {
		return chapterTitle;
	}

	public void setChapterTitle(String chapterTitle) {
		this.chapterTitle = chapterTitle;
	}

	public String getChapterContent() {
		return chapterContent;
	}

	public void setChapterContent(String chapterContent) {
		this.chapterContent = chapterContent;
	}

	public String getChapterUpdatetime() {
		return chapterUpdatetime;
	}

	public void setChapterUpdatetime(String chapterUpdatetime) {
		this.chapterUpdatetime = chapterUpdatetime;
	}

	public SnChapter(Integer chapterId, SnNovel snNovel, String chapterTitle, String chapterContent,
			String chapterUpdatetime) {
		super();
		this.chapterId = chapterId;
		this.snNovel = snNovel;
		this.chapterTitle = chapterTitle;
		this.chapterContent = chapterContent;
		this.chapterUpdatetime = chapterUpdatetime;
	}

	@Override
	public String toString() {
		return "SnChapter [chapterId=" + chapterId + ", snNovel=" + snNovel + ", chapterTitle=" + chapterTitle
				+ ", chapterContent=" + chapterContent + ", chapterUpdatetime=" + chapterUpdatetime + "]";
	}
}
